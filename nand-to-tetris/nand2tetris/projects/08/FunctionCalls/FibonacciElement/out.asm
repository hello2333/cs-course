@256
D=A
@SP
M=D

@ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1

@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1

@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1

@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1

@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1


// ARG=SP
@SP
D=M
@ARG
M=D
// ARG = ARG -5
@5
D=A
@ARG
M=M-D
// ARG = ARG - nArgs
@0
D=A
@ARG
M=M-D

@SP
D=M
@LCL
M=D

@Sys.init
0;JMP

(ret.0)

// FunctionCalls/FibonacciElement/Main.vm

 // ### function Main.fibonacci 0
(Main.fibonacci)
// RAM[index] = 3
@0
D=A
@origin_index
M=D
@index
M=D

// if index <= 0: goto LOOP_END
(Main.fibonacci.LOOP_INIT_LCL)
@index
D=M
@Main.fibonacci.LOOP_END
D;JLE

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// RAM[*(LCL)+index] = 0
@LCL
A=D+M
M=0

@Main.fibonacci.LOOP_INIT_LCL
0;JMP

(Main.fibonacci.LOOP_END)
// RAM[*SP] = RAM[*LCL] + RAM[index]
@origin_index
D=M
@LCL
D=D+M
@SP
M=D

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

 // ### push constant 2
@2
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### lt
@SP
M=M-1
A=M
D=M

@SP
M=M-1
A=M
M=M-D
D=M

@MATCH_0
D;JLT
@SP
A=M
M=0
@UPDATE_SP_0
0;JMP

(MATCH_0)
@SP
A=M
M=-1

(UPDATE_SP_0)
@SP
M=M+1

 // ### if-goto IF_TRUE
@SP
M=M-1
@SP
A=M
D=M
@Main.fibonacci$IF_TRUE
D;JNE

 // ### goto IF_FALSE
@Main.fibonacci$IF_FALSE
0;JMP

 // ### label IF_TRUE
(Main.fibonacci$IF_TRUE)

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


 // ### label IF_FALSE
(Main.fibonacci$IF_FALSE)

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

 // ### push constant 2
@2
D=A

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

 // ### call Main.fibonacci 1
@Main.fibonacci$ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1

@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1

@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1

@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1

@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1


// ARG=SP
@SP
D=M
@ARG
M=D
// ARG = ARG -5
@5
D=A
@ARG
M=M-D
// ARG = ARG - nArgs
@1
D=A
@ARG
M=M-D

@SP
D=M
@LCL
M=D

@Main.fibonacci
0;JMP

(Main.fibonacci$ret.0)

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

 // ### push constant 1
@1
D=A

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

 // ### call Main.fibonacci 1
@Main.fibonacci$ret.1
D=A
@SP
A=M
M=D
@SP
M=M+1

@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1

@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1

@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1

@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1


// ARG=SP
@SP
D=M
@ARG
M=D
// ARG = ARG -5
@5
D=A
@ARG
M=M-D
// ARG = ARG - nArgs
@1
D=A
@ARG
M=M-D

@SP
D=M
@LCL
M=D

@Main.fibonacci
0;JMP

(Main.fibonacci$ret.1)

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

// FunctionCalls/FibonacciElement/Sys.vm

 // ### function Sys.init 0
(Sys.init)
// RAM[index] = 3
@0
D=A
@origin_index
M=D
@index
M=D

// if index <= 0: goto LOOP_END
(Sys.init.LOOP_INIT_LCL)
@index
D=M
@Sys.init.LOOP_END
D;JLE

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// RAM[*(LCL)+index] = 0
@LCL
A=D+M
M=0

@Sys.init.LOOP_INIT_LCL
0;JMP

(Sys.init.LOOP_END)
// RAM[*SP] = RAM[*LCL] + RAM[index]
@origin_index
D=M
@LCL
D=D+M
@SP
M=D

 // ### push constant 4
@4
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### call Main.fibonacci 1
@Sys.init$ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1

@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1

@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1

@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1

@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1


// ARG=SP
@SP
D=M
@ARG
M=D
// ARG = ARG -5
@5
D=A
@ARG
M=M-D
// ARG = ARG - nArgs
@1
D=A
@ARG
M=M-D

@SP
D=M
@LCL
M=D

@Main.fibonacci
0;JMP

(Sys.init$ret.0)

 // ### label WHILE
(Sys.init$WHILE)

 // ### goto WHILE
@Sys.init$WHILE
0;JMP
