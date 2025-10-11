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
  
  def is_label(self):
    return self._command_type == CommandType.C_LABLE
  
  def is_goto(self):
    return self.command_type == CommandType.C_GOTO
  
  def is_if(self):
    return self.command_type == CommandType.C_IF
  
  def is_function(self):
    return self.command_type == CommandType.C_FUNCTION
  
  def is_call(self):
    return self.command_type == CommandType.C_CALL
  
  def is_return(self):
    return self.command_type == CommandType.C_RETURN
  
  def is_comment(self):
    return self._command_type == CommandType.C_COMMENT
  
  def _get_command_type(self, command):
    command_type_map = {'label': CommandType.C_LABLE, 'goto': CommandType.C_GOTO, 'if-goto': CommandType.C_IF,
                    'function': CommandType.C_FUNCTION, 'call': CommandType.C_CALL, 'return': CommandType.C_RETURN,
                    'add': CommandType.C_ARITHMETIC, 'sub': CommandType.C_ARITHMETIC, 'and': CommandType.C_ARITHMETIC,
                    'or': CommandType.C_ARITHMETIC, 'neg': CommandType.C_ARITHMETIC, 'not': CommandType.C_ARITHMETIC, 
                    'eq': CommandType.C_ARITHMETIC, 'lt': CommandType.C_ARITHMETIC, 'gt': CommandType.C_ARITHMETIC,
                    'push': CommandType.C_PUSH, 'pop': CommandType.C_POP}
    if command in command_type_map:
      return command_type_map[command]
    raise Exception(f'invalid command for _get_command_type: {line}')

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
    self._command_type = self._get_command_type(command)
    if self.is_arithmetic():
      self._parse_arithmetic(command_parts)
    elif self.is_push_pop():
      self._parse_push_pop(command_parts)
    elif self.is_label():
      self._parse_label(command_parts)
    elif self.is_goto():
      self._parse_goto(command_parts)
    elif self.is_if():
      self._parse_if(command_parts)
    elif self.is_function():
      self._parse_function(command_parts)
    elif self.is_call():
      self._parse_call(command_parts)
    elif self.is_return():
      self._parse_return(command_parts)
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
  
  def _parse_label(self, command_parts):
    if len(command_parts) != 2:
      raise Exception(f'Invalid label command {self._line}, parts len={command_parts}')
    self._arg1 = command_parts[1]
    self._arg2 = ''

  def _parse_goto(self, command_parts):
    if len(command_parts) != 2:
      raise Exception(f'Invalid goto command {self._line}, parts len={command_parts}')
    self._arg1 = command_parts[1]
    self._arg2 = ''

  def _parse_if(self, command_parts):
    if len(command_parts) != 2:
      raise Exception(f'Invalid if command {self._line}, parts len={command_parts}')
    self._arg1 = command_parts[1]
    self._arg2 = ''

  def _parse_function(self, command_parts):
    if len(command_parts) != 3:
      raise Exception(f'Invalid function command {self._line}, parts len={command_parts}')
    self._arg1 = command_parts[1]
    self._arg2 = command_parts[2]

  def _parse_call(self, command_parts):
    if len(command_parts) != 3:
      raise Exception(f'Invalid call command {self._line}, parts len={command_parts}')
    self._arg1 = command_parts[1]
    self._arg2 = command_parts[2]

  def _parse_return(self, command_parts):
    if len(command_parts) != 1:
      raise Exception(f'Invalid return command {self._line}, parts len={command_parts}')
    self._arg1 = ''
    self._arg2 = ''

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
    self._compare_index = 0
    self._filename = ''
    self._curr_func_name = ''
    self._curr_func_ret_index = 0
    pass

  def set_filename(self, filename):
    self._filename = filename

  def write_arithmetic(self, origin_line, operator):
    asm = self.write_comment(origin_line)

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
    asm = self.write_comment(origin_line)

    dynamic_segment = {'local', 'argument', 'this', 'that'}
    static_segment = {'temp', 'static'}
    point_segment = {'pointer'}
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
  
  def write_comment(self, origin_line):
    comment_line = f"\n // ### {origin_line}\n"
    return comment_line

  def _convert_to_op_type(self, operator):
    operator_2_optype_map = {"add": "+", "sub": "-", "neg": "-",
                             "and": "&", "or": "|", "not": "!",
                             "eq": "JEQ", "gt": "JGT", "lt": "JLT"}
    if operator_2_optype_map.get(operator):
      return operator_2_optype_map[operator]
    raise Exception(f'not find op_type. invalid operator {operator}')
  
  def _convert_to_cpu_segment(self, segment):
    cpu_segment_map = {'local': "LCL", 'argument': 'ARG',
                       'this': "THIS", 'that': 'THAT',
                       'temp': "5"}
    if segment in cpu_segment_map:
      return cpu_segment_map[segment]
    return segment
  
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
    if op_type == '-':
      asm += f'M=M{op_type}D\n'
    else:
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

  def _update_compare_index(self):
    self._compare_index += 1

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
    asm += 'M=M-D\n'
    asm += 'D=M\n'

    asm += '\n'
    asm += f'@MATCH_{self._compare_index}\n'
    asm += f'D;{op_type}\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=0\n'
    asm += f'@UPDATE_SP_{self._compare_index}\n'
    asm += '0;JMP\n'

    asm += '\n'
    asm += f'(MATCH_{self._compare_index})\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=-1\n'

    asm += '\n'
    asm += f'(UPDATE_SP_{self._compare_index})\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'

    self._update_compare_index()
    return asm
  
  def _push_segment_index(self, segment, index):
    cpu_segment = self._convert_to_cpu_segment(segment)
    asm = ''
    asm += f'@{index}\n'
    asm += 'D=A\n'
    asm += f'@{cpu_segment}\n'
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
    cpu_segment = self._convert_to_cpu_segment(segment)
    asm = ''
    asm += '@SP\n'
    asm += 'M=M-1\n'

    asm += '\n'
    asm += f'@{index}\n'
    asm += 'D=A\n'
    asm += f'@{cpu_segment}\n'
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
    cpu_segment = self._convert_to_cpu_segment(segment)
    asm = ''
    asm += f'@{index}\n'
    asm += 'D=A\n'
    asm += f'@{cpu_segment}\n'
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
    cpu_segment = self._convert_to_cpu_segment(segment)
    asm = ''
    asm += '@SP\n'
    asm += 'M=M-1\n'

    asm += '\n'
    asm += f'@{index}\n'
    asm += 'D=A\n'
    asm += f'@{cpu_segment}\n'
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
  
  def write_label(self, label):
    inject_label = self._gen_label(label)
    asm = ''
    asm += f'({inject_label})'
    return asm

  def write_goto(self, label):
    inject_label = self._gen_label(label)
    asm = ''
    asm += f'@{inject_label}\n'
    asm += '0;JMP\n'
    return asm

  def write_if(self, label):
    asm = ''
    inject_label = self._gen_label(label)

    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'D=M\n'
    asm += '@cond\n'
    asm += 'M=D\n'
    asm += '@SP\n'
    asm += 'M=M-1\n'
    asm += '@cond\n'
    asm += 'D=M\n'
    asm += f'@{inject_label}\n'
    asm += 'D;JNE\n'
    return asm

  def write_function(self, func_name, arg_count):
    func_label = self._gen_function_label(func_name)
    asm = f'({func_label})'

    self._init_func_context(func_name)
    return asm

  def write_call(self, callee_func_name, arg_count):
    asm = ''
    asm += self._call_store_caller_retaddr(self._curr_func_name, self._curr_func_ret_index)
    asm += self._call_store_caller_segments()
    asm += self._call_set_callee_arg(arg_count)
    asm += self._call_set_callee_lcl()
    asm += self._call_goto_callee(callee_func_name)
    asm += self._call_inject_caller_retaddr_label(self._curr_func_name, self._curr_func_ret_index)

    self._forword_func_ret_index()
    return asm

  def write_return(self):
    asm = ''
    asm += self._return_set_endframe()
    asm += self._return_recover_caller_retaddr()
    asm += self._return_set_ret_value()
    asm += self._return_set_caller_sp()
    asm += self._return_recover_caller_segments()

    self._clear_func_context()
    return asm

  # Xxx.functionName$ret.i
  def _gen_ret_label(self, caller_func, ret_index):
    return f'{self._filename}.{caller_func}$ret.{ret_index}'
  
  # Xxx.functionName
  def _gen_function_label(self, func_name):
    return f'{self._filename}.{func_name}'

  # Xxx.functionName$label or Xxx$label
  def _gen_label(self, label):
    if len(self._curr_func_name) > 0:
      return f'{self._filename}.{self._curr_func_name}${label}'
    return f'{self._filename}${label}'
  
  def _init_func_context(self, func_name):
    self._curr_func_name = func_name
    self._curr_func_ret_index = 0

  def _clear_func_context(self):
    self._curr_func_name = ''
    self._curr_func_ret_index = 0

  def _forword_func_ret_index(self):
    self._curr_func_ret_index += 1
 
  def _call_store_caller_retaddr(self, caller_func, ret_index):
    asm = ''
    ret_label = self._gen_ret_label(caller_func, ret_index)
    asm += f'@{ret_label}\n'
    asm += 'D=A\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=D\n'

    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm

  def _call_store_caller_segments(self):
    asm = ''
    asm += self._call_store_caller_segment('LCL')
    asm += self._call_store_caller_segment('ARG')
    asm += self._call_store_caller_segment('THIS')
    asm += self._call_store_caller_segment('THAT')
    return asm

  def _call_store_caller_segment(self, segment):
    asm = ''
    asm += '@segment\n'
    asm += 'D=M\n'
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'M=D\n'
    asm += '@SP\n'
    asm += 'M=M+1\n'
    return asm

  def _call_set_callee_arg(self, args_count):
    asm = ''
    asm += '// ARG=SP\n'
    asm += '@SP\n'
    asm += 'D=M\n'
    asm += '@ARG\n'
    asm += 'M=D\n'
    asm += '// ARG = ARG -5\n'
    asm += '@5\n'
    asm += 'D=A\n'
    asm += '@ARG\n'
    asm += 'M=M-D\n'
    asm += '// ARG = ARG - nArgs\n'
    asm += f'@{args_count}\n'
    asm += 'D=M\n'
    asm += '@ARG\n'
    asm += 'M=M-D\n'
    return asm

  def _call_set_callee_lcl(self):
    asm = ''
    asm += '@SP\n'
    asm += 'D=M\n'
    asm += '@LCL\n'
    asm += 'M=D\n'
    return asm

  def _call_goto_callee(self, callee_func):
    asm = ''
    func_label = self._gen_function_label(callee_func)
    asm += f'@{func_label}\n'
    asm += '0;JMP\n'
    return asm

  def _call_inject_caller_retaddr_label(self, caller_func, ret_index):
    asm = ''
    ret_label = self._gen_ret_label(caller_func, ret_index)
    asm += f'({ret_label})'
    return asm

  def _return_set_endframe(self):
    asm = ''
    asm += '@LCL\n'
    asm += 'D=M\n'
    asm += '@endFrame\n'
    asm += 'M=D\n'
    return asm

  def _return_recover_caller_retaddr(self):
    asm = ''
    asm += '@5\n'
    asm += 'D=A\n'
    asm += '@endFrame\n'
    asm += 'A=M-D\n'
    asm += 'D=M \n'
    asm += '@retAddr\n'
    asm += 'M=D\n'
    return asm

  def _return_set_ret_value(self):
    asm = ''
    asm += '@SP\n'
    asm += 'A=M\n'
    asm += 'D=M\n'
    asm += '@ARG\n'
    asm += 'A=M\n'
    asm += 'M=D\n'
    return asm

  def _return_set_caller_sp(self):
    asm = ''
    asm += '@ARG\n'
    asm += 'D=M\n'
    asm += '@SP\n'
    asm += 'M=D+1\n'
    return asm

  def _return_recover_caller_segment(self, segment, dert):
    asm = ''
    asm += f'@{dert}\n'
    asm += 'D=A\n'
    asm += '@endFrame\n'
    asm += 'A=M-D\n'
    asm += 'D=M\n'
    asm += f'@{segment}\n'
    asm += 'M=D\n'
    return asm

  def _return_recover_caller_segments(self):
    asm = ''
    asm += self._return_recover_caller_segment('THAT', 1)
    asm += self._return_recover_caller_segment('THIS', 1)
    asm += self._return_recover_caller_segment('ARG', 1)
    asm += self._return_recover_caller_segment('LCL', 1)
    return asm

  def _return_goback_caller(self):
    asm = ''
    asm += '@retAddr\n'
    asm += 'A=M\n'
    asm += '0;JMP\n'
    return asm

# Process 看了眼一年前写的伪代码，太细节了，分层做的不好。
# 伪代码只做流程层面的事情，对抽象的函数进行调用。不应该有细节的的东西
# 省略了第一次写的伪代码

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
  writer = CodeWriter()
  with open(read_file_name, 'r', encoding='utf-8') as read_f:
    writer.set_filename(read_file_name)
    for line in read_f:
      # initiate parser for current line
      parser = Parser(line)
      asm = ''
      if parser.is_comment():
        continue
      
      asm = writer.write_comment(parser.origin_line())
      write_f.write(asm)

      if parser.is_push_pop():
        asm = writer.write_push_pop(parser.origin_line(), parser.command_type(), parser.arg1(), parser.arg2())
      elif parser.is_arithmetic():
        asm = writer.write_arithmetic(parser.origin_line(), parser.arg1())
      elif parser.is_label():
        asm = writer.write_label(parser.arg1())
      elif parser.is_goto():
        asm = writer.write_goto(parser.arg1())
      elif parser.is_if():
        asm = writer.write_if(parser.arg1())
      elif parser.is_function():
        asm = writer.write_function(parser.arg1(), parser.arg2())
      elif parser.is_call():
        asm = writer.write_call(parser.arg1(), parser.arg2())
      elif parser.is_return():
        asm = writer.write_return()
      else:
        raise Exception(f'Not support command {parser.origin_line()}, command_type={parser.command_type()} now')
      vm_lines += 1
      write_f.write(asm)

print(f'success. handle lines {vm_lines}')