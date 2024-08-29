from enum import Enum
import sys

class InstrutionType(Enum):
  A_INSTRUCTION = 1
  C_INSTRUCTION = 2
  L_INSTRUCTION = 3

# Parser module
class Parser(object):
  def __init__(self, f):
    self.instruction = ""
    self.ins_type = InstrutionType.A_INSTRUCTION
    self.f = f
    self.ins_index = 0
    return

  def has_more_lines(self):
    self.instruction = self.f.readline()
    return len(self.instruction) != 0
  
  def advance(self):
    # do nothing in python
    while (len(self.instruction.strip()) == 0 or self.instruction.startswith("//")) and self.has_more_lines():
      continue
    if len(self.instruction) == 0:
      return
    self._parse_instruction()
    return

  def forward(self):
    self.ins_index += 1
  
  def address(self):
    return self.ins_index

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
    return self.dest_str
  
  def comp(self):
    return self.comp_str
  
  def jump(self):
    return self.jump_str
  
  def _parse_instruction(self):
    self.instruction = self.instruction.strip()
    self.ins_type = self.instruct_type()
    self.dest_str = ""
    self.comp_str = ""
    self.jump_str = ""
    # print("====== read construction: ", self.instruction, ", type: ", self.ins_type)

    instruction_comment_split = self.instruction.split("//")
    if len(instruction_comment_split) > 0:
      self.instruction = instruction_comment_split[0].strip()

    if not self.is_c_instruction():
      return

    ins_part = self.instruction.split(";")
    if len(ins_part) == 0:
      return ""
    
    # parse comp part
    comp_part = ins_part[0].split("=")
    if len(comp_part) == 0:
      return ""
    
    if len(comp_part) == 2:
      self.dest_str = comp_part[0]
      self.comp_str = comp_part[1]
    elif len(comp_part) == 1:
      self.comp_str = comp_part[0]
    
    # parse jump part
    if len(ins_part) == 1:
      return
    jump_part = ins_part[1]
    self.jump_str = jump_part
    return
  
  def is_c_instruction(self):
    return self.ins_type == InstrutionType.C_INSTRUCTION
  
  def is_a_instruction(self):
    return self.ins_type == InstrutionType.A_INSTRUCTION

  def is_l_instruction(self):
    return self.ins_type == InstrutionType.L_INSTRUCTION

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
    # print("======= invalid asm_jump: ", asm_jump)
    return "000"
  
  def symbol(self, asm_symbol):
    bin_symbol = bin(int(asm_symbol))
    bin_symbol = bin_symbol[2:].zfill(16)
    return bin_symbol

  def _append_dest_bin(self, target, asm_dest, bin_dest):
    if str(asm_dest).find(target) != -1:
      bin_dest += "1"
    else:
      bin_dest += "0"
    return bin_dest

# Symbol table module
class SymbolTable:
  def __init__(self) -> None:
    self.entrys = {"SP": 0, "LCL": 1, "ARG": 2, "THIS": 3, "THAT": 4,
                   "R0" : 0, "R1" : 1, "R2" : 2, "R3" : 3, "R4" : 4, 
                   "R5" : 5, "R6" : 6, "R7" : 7, "R8" : 8, "R9" : 9, 
                   "R10" : 10, "R11" : 11, "R12" : 12, "R13" : 13, "R14" : 14, "R15" : 15,
                   "SCREEN": 16384, "KBD": 24576}
    self.ram_address = 16
    pass

  def forward(self):
    self.ram_address += 1
  
  def get_ram_address(self):
    return self.ram_address

  def add_entry(self, symbol, address):
    self.entrys[symbol] = address
    print("======= add_entry symbol:", symbol, "address:", address)

  def contains(self, symbol):
    return symbol in self.entrys.keys()

  def get_address(self, symbol):
    if self.contains(symbol):
      return self.entrys[symbol]
    return -1

# peseoucode
# target: convert assembly code to binary code

# Pass1: Gen Symbol Table
# open the input file
# while has more lines:
#   get next instruction
#   if instruction is L-instruction, add entry(symbol, ins_index) in symbol table
#   forward_index

# open the input file
# while has more lines:
#  get next instruction
#  if instruction is A-instruction, convert it to binary code
#  if instruction is C-instruction, convert it to binary code
#  if instruction is L-instruction, convert symbol to decimal, then convert it to binary code
#  write the binary code to *.hack file

# convert symbol to decimal
# if symbol table contains symbol, return decimal
# else 
#   forward ram_symbol_pointer
#   add (symbol, ram_sybol_pointer) in entry
#   return ram_symbol_pointer

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

if len(sys.argv) != 2:
  print("Usuage: python assembler.py file_absolute_name")
  exit(0)

read_file_name = sys.argv[1]
write_file_name = read_file_name.replace("asm", "hack")
print(read_file_name, write_file_name)


symbol_table = SymbolTable()
with open(read_file_name, 'r') as read_f:
  asm_parser = Parser(read_f)
  while asm_parser.has_more_lines():
    asm_parser.advance()
    if asm_parser.is_l_instruction():
      symbol_table.add_entry(asm_parser.symbol(), asm_parser.address())
    else:
      asm_parser.forward()

with open(write_file_name, 'w') as write_f:
  with open(read_file_name, 'r') as f:
    asm_parser = Parser(f)
    while asm_parser.has_more_lines():
      asm_parser.advance()

      bin_code = ""
      asm_coder = Code()
      if asm_parser.is_c_instruction():
        bin_code = "111"
        bin_code += asm_coder.comp(asm_parser.comp())
        bin_code += asm_coder.dest(asm_parser.dest())
        bin_code += asm_coder.jump(asm_parser.jump())
      elif asm_parser.is_a_instruction():
        symbol_start = asm_parser.symbol()[0]
        # error : the method to check if symbol is a number is wrong
        # error : not replace alpha symbol with decimal for a_instruction
        if symbol_start >= '0' and symbol_start <= '9':
          bin_code = asm_coder.symbol(asm_parser.symbol())
        else:
          if not symbol_table.contains(asm_parser.symbol()):
            symbol_table.add_entry(asm_parser.symbol(), symbol_table.get_ram_address())
            symbol_table.forward()
          bin_code = asm_coder.symbol(symbol_table.get_address(asm_parser.symbol()))
      # error 1: not skip write for l_instruction
      elif asm_parser.is_l_instruction():
        continue
      print("====== asm: ", asm_parser.instruction, ", bin_code: ", bin_code, 
            "dest=", asm_parser.dest(), asm_coder.dest(asm_parser.dest()),
            "comp=", asm_parser.comp(), asm_coder.comp(asm_parser.comp()),
            "jump=", asm_parser.jump(), asm_coder.jump(asm_parser.jump()))
      write_f.write(bin_code + "\n")

print("hello world")
