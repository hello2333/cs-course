from enum import Enum

class InstrutionType(Enum):
  A_INSTRUCTION = 1
  C_INSTRUCTION = 2
  L_INSTRUCTION = 3

# Parser module
class Parser(object):
  def __init__(self, f):
    self.instruction = ""
    self.ins_type = InstrutionType.A_INSTRUCTION
    # with open(asm_file, 'r') as self.f:
    self.f = f
    return

  def has_more_lines(self):
    self.instruction = self.f.readline()
    return len(self.instruction) != 0
  
  def advance(self):
    # do nothing in python
    while len(self.instruction.strip()) == 0 and self.has_more_lines():
      continue
    if len(self.instruction) == 0:
      return
    self._parse_instruction()
    return

  def instruct_type(self):
    # if instruction start with @: A_INSTRUCTION
    # else if instruction start with (: L_INSTRUCTION
    # else: C_INSTRUCTION
    if self.instruction.startswith("@"):
      return InstrutionType.A_INSTRUCTION
    if self.instruction.startswith("("):
      return InstrutionType.L_INSTRUCTION
    return InstrutionType.C_INSTRUCTION

  def symbol(self):
    if self.is_a_instruction():
      return self.instruction[1:]
    if self.is_l_instruction():
      return self.instruction[1:-1]
    return ""
  
  def dest(self):
    if not self.is_c_instruction():
      return ""
    # split instruction by ";"
    #   part1: dest=comp
    #   part2: jump, it is impossible to have only jump part
    # split part1 by "="
    # if result has two parts
    #   return part1 as dest
    # else if result[0] is digit
    #   return empty as dest
    # else if result[0] is alpha
    #   return result[0] as dest
    # else
    #   return empty and print error log
    return self.dest
  
  def comp(self):
    return self.comp
  
  def jump(self):
    return self.jump
  
  def _parse_instruction(self):
    self.ins_type = self.instruct_type()
    self.dest = ""
    self.comp = ""
    self.jump = ""

    if not self.is_c_instruction():
      return

    ins_part = self.instruction.split(";")
    if len(ins_part) == 0:
      return ""
    
    # parse comp part
    comp_part = ins_part[0].split(":")
    if len(comp_part) == 0:
      return ""
    
    if len(comp_part) == 2:
      self.dest = comp_part[0]
      self.comp = comp_part[1]
    elif len(comp_part) == 1:
      self.comp = comp_part[0]
    
    # parse jump part
    if len(ins_part) == 1:
      return
    jump_part = ins_part[1]
    self.jump = jump_part
    return
  
  def is_c_instruction(self):
    self.ins_type == InstrutionType.C_INSTRUCTION
  
  def is_a_instruction(self):
    self.ins_type == InstrutionType.A_INSTRUCTION

  def is_l_instruction(self):
    self.ins_type == InstrutionType.L_INSTRUCTION

# Code generator module
class Code(object):
  def dest(self, asm_dest):
    bin_dest = ""
    bin_dest = self._append_dest_bin("A", asm_dest, bin_dest)
    bin_dest = self._append_dest_bin("D", asm_dest, bin_dest)
    bin_dest = self._append_dest_bin("M", asm_dest, bin_dest)
    return bin_dest

  def comp(self, asm_comp):
    # # # metho1
    # if is 0 1 -1, give the bin code
    # else if len == 2:
    #   gen bin code for D|A|M
    #   gen bin code for !(last c is 1)|-(last second c is 1)
    # else if len == 3:
    #   gen bin code for D|A|M

    # # # metho2
    # use dict, because it is difficulte to convert ins text to bin by rules
    comp_dict = {"0": "0101010", "1": "0111111", "-1": "0111010",
                 "D": "0001100", "A": "0110000", "M": "1110000",
                 "!D": "0001101", "!A": "0110001", "!M": "1110001",
                 "-D": "0001111", "-A": "0110011", "-M": "1110011",
                 "D+1": "0011111", "A+1": "0110111", "M+1": "1110111",
                 "D-1": "0001110", "A-1": "0110010", "M-1": "1110010",
                 "D+A": "0000010", "D+M": "1000010", 
                 "D-A": "0010011", "D-M": "1010011", 
                 "A-D": "0000111", "M-D": "1000111",
                 "D&A": "0000000", "D&M": "1000000",
                 "D|A": "0010101", "D|M": "1010101"}
    if asm_comp in comp_dict.keys():
      return comp_dict[asm_comp]
    print("======= invalid asm_comp: ", asm_comp)
    return ""
  
  def jump(self, asm_jump):
    jump_dict = {"JGT": "001", "JEQ": "010,", "JGE": "011", "JLT": "100",
                 "JNE": "101", "JLE": "110", "JMP": "111"}
    if asm_jump in jump_dict.keys():
      return jump_dict[asm_jump]
    print("======= invalid asm_jump: ", asm_jump)
    return "000"
  
  def symbol(self, asm_symbol):
    bin_symbol = bin(asm_symbol)
    bin_symbol.zfill(16)
    return bin_symbol

  def _append_dest_bin(self, target, asm_dest, bin_dest):
    if str(asm_dest).find(target) != -1:
      bin_dest += "1"
    else:
      bin_dest += "0"
    return bin_dest

# Symbol table module

# peseoucode
# target: convert assembly code to binary code

# open the input file
# while has more lines:
#  get next instruction
#  if instruction is A-instruction, convert it to binary code
#  if instruction is C-instruction, convert it to binary code
#  write the binary code to *.hack file
with open("/Users/namiyazhang/workspace/cs-course/nand-to-tetris/nand2tetris/projects/06/max/MaxL.asm", 'r') as f:
  asm_parser = Parser(f)
  while asm_parser.has_more_lines():
    asm_parser.advance()

    bin_code = ""
    asm_coder = Code()
    if asm_parser.is_c_instruction():
      bin_code = "100"
      bin_code += asm_coder.dest(asm_parser.dest())
      bin_code += asm_coder.comp(asm_parser.comp())
      bin_code += asm_coder.jump(asm_parser.jump())
    elif asm_parser.is_a_instruction():
      bin_code = asm_coder.symbol(asm_parser.symbol())
    print("====== asm: ", asm_parser.instruction, ", bin_code: ", bin_code)

print("hello world")

# Convert A-instruction(without symbols)
# instruction format: @xxx, xxx is decimal value
# input: instruction text
# output: binary code string
# algorithm/process: 
#   get symbol: method1 split the instruction by @; method2 get the sub string[1:] as xxx
#   convert symbol to binary code: convert decimal xxx to binary 

# Convert C-instruction(without symbols)
# assembly instruction format: dest=comp;jump
# binary format: 1xxaccccccdddjjj
# input: instruction text
# output: binary code string
# algorithm/process:
#   get dest and convert dest to binary
#   get comp and convert comp to binary
#   get jump adn convert jump to binary
#   // split instruction by ";": 
#     // first part:dest=comp
#     // secodn part: jump
