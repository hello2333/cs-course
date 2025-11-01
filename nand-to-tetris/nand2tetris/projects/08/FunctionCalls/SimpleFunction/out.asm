// FunctionCalls/SimpleFunction/SimpleFunction.vm

 // ### function SimpleFunction.test 2
(SimpleFunction.test)
// RAM[index] = local_num
@2
D=A
@index
M=D

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

(.LOOP_INIT)
// RAM[*(LCL+index)] = 0
@LCL
A=D+M
M=0

// RAM[*SP] = RAM[*SP] + 1
@SP
M=M+1

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// if index > 0: goto LOOP_INIT
@index
D=M
@.LOOP_INIT
D;JGE

 // ### push local 0
@0
D=A
@LCL
A=D+M
D=M

@SP
A=M
M=D

@SP
M=M+1

 // ### push local 1
@1
D=A
@LCL
A=D+M
D=M

@SP
A=M
M=D

@SP
M=M+1

 // ### add
@SP
M=M-1
@SP
A=M
D=M

@SP
M=M-1
@SP
A=M
M=D+M

@SP
M=M+1

 // ### not
@SP
M=M-1
A=M
M=!M

@SP
M=M+1

 // ### push argument 0
@0
D=A
@ARG
A=D+M
D=M

@SP
A=M
M=D

@SP
M=M+1

 // ### add
@SP
M=M-1
@SP
A=M
D=M

@SP
M=M-1
@SP
A=M
M=D+M

@SP
M=M+1

 // ### push argument 1
@1
D=A
@ARG
A=D+M
D=M

@SP
A=M
M=D

@SP
M=M+1

 // ### sub
@SP
M=M-1
@SP
A=M
D=M

@SP
M=M-1
@SP
A=M
M=M-D

@SP
M=M+1

 // ### return
// set endFrame
@LCL
D=M
@endFrame
M=D

// get caller retaddr
@5
D=A
@endFrame
A=M-D
D=M 
@retAddr
M=D

// set ret value in ret addr
@SP
M=M-1

@SP
A=M
D=M
@ARG
A=M
M=D

// set caller sp
@ARG
D=M
@SP
M=D+1

// recover_caller_segments
@1
D=A
@endFrame
A=M-D
D=M
@THAT
M=D

@2
D=A
@endFrame
A=M-D
D=M
@THIS
M=D

@3
D=A
@endFrame
A=M-D
D=M
@ARG
M=D

@4
D=A
@endFrame
A=M-D
D=M
@LCL
M=D


// goback caller
@retAddr
A=M
0;JMP

