from enum import Enum
import sys

class CommandType(Enum):
  C_INVALID = 0
  C_ARITHMETIC = 1
  C_PUSH = 2
  C_POP = 3
  C_LABLE = 4
  C_GOTO = 5
  C_IF = 6
  C_FUNCTION = 7
  C_RETURN = 8
  C_CALL = 9

class Parser:
  def __init__(self, vm_f):
    self.vm_f = vm_f
    self._init_command()

  def has_more_lines(self):
    self._commnad = self.vm_f.readline()
    return len(self._commnad) != 0
  
  def advance(self):
    # while current line is empty and has_more_lines:
    #   update currnet_line
    # if current_line is not empty
    #   parse current line
    # else
    #   exit
    while not self._is_valid_line(self._commnad) and self.has_more_lines():
      continue
    if not self._is_valid_line(self._commnad):
      return
    self._init_command()
    self._parse(self._commnad)

  def command_type(self):
    return self._command_type
  
  def arg1(self):
    return self._arg1

  def arg2(self):
    # C_PUSH/C_POP/C_FUNCTION/C_CALL
    command_with_arg2 = {CommandType.C_PUSH, CommandType.C_POP, CommandType.C_FUNCTION, CommandType.C_CALL}
    if self._command_type not in command_with_arg2:
      raise Exception(f'invalid command_type {self._command_type}') 
    # TODO: 这里强转为整型是不对的
    return self._arg2

  def is_arithmetic_command(self):
    return self._command_type == CommandType.C_ARITHMETIC
  
  def is_push_pop_command(self):
    return self._command_type == CommandType.C_POP or self._command_type == CommandType.C_PUSH

  def command_type(self):
    return self._command_type

  def _is_arithmetic_command(self, command):
    arithmetic_command = {'add', 'sub', 'and', 'or', 'neg', 'not', 'eq', 'lt', 'gt'}
    return command in arithmetic_command

  def _is_push_pop_command(self, command):
    push_pop_command = {'push', 'pop'}
    return command in push_pop_command

  def _parse(self, line):
    command_line = ''
  
    line_parts = line.strip().split('//')
    if len(line_parts) > 0:
      command_line = line_parts[0]
    command_parts = command_line.split(' ')
    # rm command comment
    # check command type
    # if is_arithmetic_command:
    #   parse arithemetic
    # elif is_push_pop_command:
    #   parse push_pop
    # else:
    #   not support
    if len(command_parts) < 1:
      raise Exception(f'invalid command: {line}')
    command = command_parts[0]
    if self._is_arithmetic_command(command):
      self._parse_arithmetic(command_parts)
    elif self._is_push_pop_command(command):
      self._parse_push_pop(command)
    else:
      raise Exception(f'not support command {command} now')
    pass

  def _parse_arithmetic(self, command_parts):
    # check
    # parse
    if len(command_parts) != 1:
      raise Exception(f'Invalid arithmetic command {self._commnad}, parts len= {len(command_parts)}')
    self._command_type = CommandType.C_ARITHMETIC
    self._arg1 = command_parts[0]
    self._arg2 = ''
    return

  def _parse_push_pop(self, command_parts):
    if len(command_parts) != 3:
      raise Exception(f'Invalid push_pop command {self._commnad}, parts len= {len(command_parts)}')
    if command_parts[0] == 'push':
      self._command_type = CommandType.C_PUSH
    else:
      self._command_type = CommandType.C_POP
    self._arg1 = command_parts[1]
    self._arg2 = command_parts[2]
    return

  def _init_command(self):
    self._commnad = ''
    self._command_type = CommandType.C_INVALID
    self._arg1 = ""
    self._arg2 = 0
  
  def _is_valid_line(self, line):
    return (not self._is_empty_line(line)) and (not self._is_comment_line(line))

  def _is_empty_line(self, line):
    return len(line.strip()) == 0
  
  def _is_comment_line(self, line):
    return line.startswith("//")

class CodeWriter:
  def __init__(self, out_file):
    self.out_file = out_file
    pass

  def write_arithmetic(self, command):
    two_operand_command = {'add', 'sub', 'and', 'or'}
    one_operand_command = {'neg', 'not'}
    compare_command = {'eq', 'lt', 'gt'}
    if command in two_operand_command:
      self._write_two_operand_arithmetic(command)
    elif command in one_operand_command:
      self._write_one_operand_arithmetic(command)
    elif command in compare_command:
      self._write_compare_arithmetic(command)
    else:
      raise Exception(f'invalid arithmetic: {command}')
    pass

  def write_push_pop(self, command_type, segment, index):
    dynamic_segment = {'LCL', 'ARG', 'TEMP', 'THIS', 'THAT', 'STATIC'}
    point_segment = {'POINT'}
    constant_segment = {"CONSTANT"}
    if command_type == CommandType.C_PUSH:
      if segment in dynamic_segment:
        self._push_segment_index(segment, index)
      elif segment in point_segment:
        self._push_point_index(index)
      elif segment in constant_segment:
        self._push_constant_value(index)
      else:
        raise Exception(f'invalid push segment: {segment}')
    elif command_type == CommandType.C_POP:
      if segment in dynamic_segment:
        self._pop_segment_index(segment, index)
      elif segment in point_segment:
        self._pop_point_index(index)
      else:
        raise Exception(f'invalid pop segment: {segment}')
    else:
        raise Exception(f'invalid push_pop command_type: {command_type}')
    pass
  
  def close(self):
    pass

  def _pop_stack(self, target_register):
    # target_register = RAM[--SP]
    pass
  
  def _push_stack(self):
    # RAM[SP++]=D
    pass
  
  def _write_two_operand_arithmetic(self, operator):
    # _pop_stack(R10)
    # _pop_stack(R11)
    # _two_operand_calculate(R10, R11, operator)
    #   R10 = R10 operator R11
    #   D=M
    # _push_stack()
    pass

  def _write_one_operand_arithmetic(self, operator):
    # _pop_stack(R10)
    # _one_operand_calcuate
    #   D=operator M
    # _push_stack()
    pass

  def _write_compare_arithmetic(self, command):
    # _pop_stack(R10)
    # _pop_stack(R11)
    # R10 = R10 - R11
    # D=M
    # D;jump_type
    # (NOT_MATCHED)
    # D=0
    # _push_stack()
    # @END
    # 0;JMP
    # (MATCHED)
    # D=1
    # _push_stack()
    # (END)
    # @END
    # 0;JMP

    # _pop_stack(R10)
    # _pop_stack(R11)
    # _two_operand_calculate(R10, R11)
    # _jump_by_result(jump_type)
    # _compare_mismatched()
    # _compare_matched()
    pass

  def _two_operand_calculate(self, op1, op2, operator):
    # op1 = op1 operator op2
    # D = op1
    pass

  def _one_operand_calcuate(slef, op, operator):
    # D = operator op
    pass

  def _compare_matched():
    # RAM[SP++] = 1
    pass

  def _compare_mismatched():
    # RAM[SP++] = 0
    pass
  
  def _push_segment_index(self, segment, index):
    # _access_segment_i(segment, index)
    # _push_stack()
    pass

  def _pop_segment_index(self, segment, index):
    # _pop_stack(R10)
    # _update_segment_i(segment, index)
    pass

  def _push_point_index(self, index):
    # _access_point_index(index)
    # _push_stack()
    pass

  def _pop_point_index(self, index):
    # _pop_stack(R10)
    # _update_point_index(index)
    pass

  def _push_constant_value(self, value):
    # @value
    # D=A
    # _push_stack()
    pass

# Process
# while parser.has_more_lines():
#   parse current lines
#     split the line by space
#     if item[0] is arithmetic command: parse_arithmetic--update command_type/arg1=command/arg2=""
#     if item[0] is push/pop command  : parse_push_pop--update command_type/arg1=item[1]/arg2=item[2]
#     else throw exception
#   if command is arithmetic_command:
#     coder.write_arithmetic(command)
#       write_add/sub/neg/eq/gt/lt/and/or/not
#       add/sub/and/or:
#         R10=RAM[--SP] R11=RAM[--SP] R10=R10'*'R11 RAM[SP++]=R10
#       eq/gt/lt:
#         R10=RAM[--SP] R11=RAM[--SP] R10=R10-R11
#         D;JEQ/JGT/JLT
#         (NOT_MATCHED) RAM[SP++]=0
#         (MATCHED)     RAM[SP++]=1
#       neg/not:
#         R10=RAM[--SP] RAM[SP++]=-R10/!R10
#   if command is push_pop_command:
#     coder.write_push_pop(command_type, parser.arg1(), parser.arg2())
#       LCL/ARG/THIS/THAT:
#         push segment i: RAM[SP++]=RAM[segment+i]
#         pop  segment i: RAM[segment+i] = RAM[--SP]
#       POINT:
#         push point 0/1: RAM[SP++]=THIS/THAT
#         pop  point 0/1: THIS/THAT=RAM[--SP]
#       CONSTANT:
#         push constant i: RAM[SP++]=i
#       STATIC/TEMP:
#         push static/temp i: RAM[SP++]=RAM[16/5+i]
#         pop  static/temp i: RAM[16/5+i]=RAM[--SP]

# check param
if len(sys.argv) != 2:
  print("Usuage: python vmtranslator.py file_absolute_name")
  exit(0)

# parse source file name and generate target file name
read_file_name = sys.argv[1]
write_file_name = read_file_name.replace("vm", "asm")
print(read_file_name, write_file_name)

# open the file and handle file
vm_lines = 0
with open(write_file_name, 'w') as asm_f:
  with open(read_file_name, 'r') as vm_f:
    vm_parser = Parser(vm_f)
    while vm_parser.has_more_lines():
      vm_parser.advance()
      vm_lines += 1
      vm_coder = CodeWriter(asm_f)
      if vm_parser.is_arithmetic_command():
        vm_coder.write_arithmetic(vm_parser.arg1())
      elif vm_parser.is_push_pop_command():
        vm_coder.write_push_pop(vm_parser.command_type(), vm_parser.arg1(), vm_parser.arg2())
      else:
        raise Exception(f'Not support command {vm_parser.arg1} now')

print(f'success. handle lines {vm_lines}')