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
  C_COMMENT = 10

class Parser:
  def __init__(self, line):
    self._line = line
    self._parse()

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

  def origin_line(self):
    return self._line
  
  def is_push_pop(self):
    return self._command_type == CommandType.C_PUSH or self._command_type == CommandType.C_POP
  
  def is_arithmetic(self):
    return self._command_type == CommandType.C_ARITHMETIC
  
  def is_comment(self):
    return self._command_type == CommandType.C_COMMENT

  def _is_arithmetic_command(self, command):
    arithmetic_command = {'add', 'sub', 'and', 'or', 'neg', 'not', 'eq', 'lt', 'gt'}
    return command in arithmetic_command

  def _is_push_pop_command(self, command):
    push_pop_command = {'push', 'pop'}
    return command in push_pop_command

  def _parse(self):
    # if line is comment or empty:
    #   return
    
    # split line by space
    # check the line: 
    #   if not 3 parts or 1 one parts
    #     raise exception
    
    # if push/pop:
    #   update command_type by parts[0]
    #   update arg1 by parts[1]
    #   update arg2 by parts[2]
    # elif arithmetic:
    #   update command_type
    #   update arg1 by parts[1]
    # else
    #   raise exception
    self._init_command()
    if self._is_skip_line():
      return
  
    self._line = self._line.strip()
    command_parts = self._line.split(' ')
    if len(command_parts) < 1:
      raise Exception(f'invalid command: {line}')
    command = command_parts[0]
    if self._is_arithmetic_command(command):
      self._parse_arithmetic(command_parts)
    elif self._is_push_pop_command(command):
      self._parse_push_pop(command_parts)
    else:
      raise Exception(f'not support command {command} now')
    pass

  def _parse_arithmetic(self, command_parts):
    # check
    # parse
    if len(command_parts) != 1:
      raise Exception(f'Invalid arithmetic command {self._line}, parts len={command_parts}')
    self._command_type = CommandType.C_ARITHMETIC
    self._arg1 = command_parts[0]
    self._arg2 = ''
    return

  def _parse_push_pop(self, command_parts):
    if len(command_parts) != 3:
      raise Exception(f'Invalid push_pop command {self._line}, parts len={command_parts}')
    if command_parts[0] == 'push':
      self._command_type = CommandType.C_PUSH
    else:
      self._command_type = CommandType.C_POP
    self._arg1 = command_parts[1]
    self._arg2 = command_parts[2]
    return

  def _init_command(self):
    self._commnad = ''
    self._command_type = CommandType.C_COMMENT
    self._arg1 = ''
    self._arg2 = ''
  
  def _is_skip_line(self):
    return self._is_empty_line() or self._is_comment_line()

  def _is_empty_line(self):
    return len(self._line.strip()) == 0
  
  def _is_comment_line(self):
    return self._line.startswith("//")

class CodeWriter:
  def __init__(self):
    pass

  def write_arithmetic(self, origin_line, operator):
    asm = self._write_comment(origin_line)

    two_operand_command = {'add', 'sub', 'and', 'or'}
    one_operand_command = {'neg', 'not'}
    compare_command = {'eq', 'lt', 'gt'}
    if operator in two_operand_command:
      asm += self._write_two_operand_arithmetic(operator)
    elif operator in one_operand_command:
      asm += self._write_one_operand_arithmetic(operator)
    elif operator in compare_command:
      asm += self._write_compare_arithmetic(operator)
    else:
      raise Exception(f'invalid arithmetic: {operator}')
    return asm

  def write_push_pop(self, origin_line, command_type, segment, index):
    asm = self._write_comment(origin_line)

    dynamic_segment = {'local', 'argument', 'this', 'that'}
    static_segment = {'temp', 'static'}
    point_segment = {'point'}
    constant_segment = {"constant"}
    if command_type == CommandType.C_PUSH:
      if segment in dynamic_segment:
        asm += self._push_segment_index(segment, index)
      elif segment in static_segment:
        asm += self._push_static_segment(segment, index)
      elif segment in point_segment:
        asm += self._push_point_index(index)
      elif segment in constant_segment:
        asm += self._push_constant_value(index)
      else:
        raise Exception(f'invalid push segment: {segment}')
    elif command_type == CommandType.C_POP:
      if segment in dynamic_segment:
        asm += self._pop_segment_index(segment, index)
      elif segment in static_segment:
        asm += self._pop_static_segment(segment, index)
      elif segment in point_segment:
        asm += self._pop_point_index(index)
      else:
        raise Exception(f'invalid pop segment: {segment}')
    else:
        raise Exception(f'invalid push_pop command_type: {command_type}')
    return asm
  
  def _write_comment(self, origin_line):
    comment_line = f"\n // ### {origin_line}\n"
    return comment_line

  def _convert_to_op_type(self, operator):
    operator_2_optype_map = {"add": "+", "sub": "-", "neg": "-",
                             "and": "&", "or": "|", "not": "!",
                             "eq": "JEQ", "gt": "JGT", "lt": "JLT"}
    if operator_2_optype_map.get(operator):
      return operator_2_optype_map[operator]
    raise Exception(f'not find op_type. invalid operator {operator}')
  
  def _write_two_operand_arithmetic(self, operator):
    op_type = self._convert_to_op_type(operator)
    asm = ''
    asm += '@SP\n'
    asm += 'M=M-1\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'D=M\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M-1\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += f'M=D{op_type}M\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm

  def _write_one_operand_arithmetic(self, operator):
    op_type = self._convert_to_op_type(operator)
    asm = ''
    asm += '@SP\n'
    asm += 'M=M-1\n'
    asm += 'A=M\n'
    asm += f'M={op_type}M\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm

  def _write_compare_arithmetic(self, operator):
    op_type = self._convert_to_op_type(operator)
    asm = ''
    asm += '@SP\n'
    asm += 'M=M-1\n'
    asm += 'A=M\n'
    asm += 'D=M\n'
    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M-1\n'
    asm += 'A=M\n'
    asm += 'M=D-M\n'
    asm += 'D=M\n'

    asm += '\n'
    asm += '@MATCH\n'
    asm += f'D;{op_type}\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=0\n'
    asm += '@UPDATE_SP\n'
    asm += '0;JMP\n'

    asm += '\n'
    asm += '(MATCH)\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=-1\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm
  
  def _push_segment_index(self, segment, index):
    asm = ''
    asm += f'@{index}\n'
    asm += 'D=A\n'
    asm += f'@{segment}\n'
    asm += 'A=D+M\n'
    asm += 'D=M\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=D\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm

  def _pop_segment_index(self, segment, index):
    asm = ''
    asm += '@SP\n'
    asm += 'M=M-1\n'

    asm += '\n'
    asm += f'@{index}\n'
    asm += 'D=A\n'
    asm += f'@{segment}\n'
    asm += 'A=D+M\n'
    asm += 'D=A\n'
    asm += '@R13\n'
    asm += 'M=D\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'D=M\n'

    asm += '\n'
    asm += '@R13\n'
    asm += 'A=M\n'
    asm += 'M=D\n'
    return asm
  
  def _push_static_segment(self, segment, index):
    asm = ''
    asm += f'@{index}\n'
    asm += 'D=A\n'
    asm += f'@{segment}\n'
    asm += 'A=D+A\n'
    asm += 'D=M\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=D\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm

  def _pop_static_segment(self, segment, index):
    asm = ''
    asm += '@SP\n'
    asm += 'M=M-1\n'

    asm += '\n'
    asm += f'@{index}\n'
    asm += 'D=A\n'
    asm += f'@{segment}\n'
    asm += 'A=D+A\n'
    asm += 'D=A\n'
    asm += '@R13\n'
    asm += 'M=D\n'

    asm += '\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'D=M\n'

    asm += '\n'
    asm += '@R13\n'
    asm += 'A=M\n'
    asm += 'M=D\n'
    return asm

  def _push_point_index(self, index):
    op_type = 'THIS'
    if index == '1':
      op_type = 'THAT'
    asm = ''
    asm += f'@{op_type}\n'
    asm += 'A=M\n'
    asm += 'D=M\n'
    asm += '\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=D\n'
    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm

  def _pop_point_index(self, index):
    op_type = 'THIS'
    if index == '1':
      op_type = 'THAT'
    asm = ''
    asm += '@SP\n'
    asm += 'M=M-1\n'
    asm += '\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'D=M\n'
    asm += '\n'
    asm += f'@{op_type}\n'
    asm += 'A=M\n'
    asm += 'M=D\n'
    return asm

  def _push_constant_value(self, value):
    asm = ''
    asm += f'@{value}\n'
    asm += 'D=A\n'
    asm += '\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=D\n'
    asm += '\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm

# Process 看了眼一年前写的伪代码，太细节了，分层做的不好。
# 伪代码只做流程层面的事情，对抽象的函数进行调用。不应该有细节的的东西
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

# Process 2025.10.08
# check param
if len(sys.argv) != 2:
  print("Usuage: python vmtranslator.py file_absolute_name")
  exit(0)

read_file_name = sys.argv[1]
write_file_name = read_file_name.replace("vm", "asm")
print(read_file_name, write_file_name)

vm_lines = 0
with open(write_file_name, 'w', encoding='utf-8') as write_f:
  with open(read_file_name, 'r', encoding='utf-8') as read_f:
    for line in read_f:
      # initiate parser for current line
      parser = Parser(line)
      writer = CodeWriter()
      asm = ''
      if parser.is_comment():
        continue
      elif parser.is_push_pop():
        asm = writer.write_push_pop(parser.origin_line(), parser.command_type(), parser.arg1(), parser.arg2())
      elif parser.is_arithmetic():
        asm = writer.write_arithmetic(parser.origin_line(), parser.arg1())
      else:
        raise Exception(f'Not support command {parser.origin_line()}, command_type={parser.command_type()} now')
      vm_lines += 1
      write_f.write(asm)

print(f'success. handle lines {vm_lines}')