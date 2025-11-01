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

// FunctionCalls/NestedCall/Sys.vm

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

 // ### push constant 4000
@4000
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop pointer 0
@SP
M=M-1

@SP
A=M
D=M

@THIS
M=D

 // ### push constant 5000
@5000
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop pointer 1
@SP
M=M-1

@SP
A=M
D=M

@THAT
M=D

 // ### call Sys.main 0
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
@0
D=A
@ARG
M=M-D

@SP
D=M
@LCL
M=D

@Sys.main
0;JMP

(Sys.init$ret.0)

 // ### pop temp 1
@SP
M=M-1

@1
D=A
@5
A=D+A
D=A
@R13
M=D

@SP
A=M
D=M

@R13
A=M
M=D

 // ### label LOOP
(Sys.init$LOOP)

 // ### goto LOOP
@Sys.init$LOOP
0;JMP

 // ### function Sys.main 5
(Sys.main)
// RAM[index] = 3
@5
D=A
@origin_index
M=D
@index
M=D

// if index <= 0: goto LOOP_END
(Sys.main.LOOP_INIT_LCL)
@index
D=M
@Sys.main.LOOP_END
D;JLE

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// RAM[*(LCL)+index] = 0
@LCL
A=D+M
M=0

@Sys.main.LOOP_INIT_LCL
0;JMP

(Sys.main.LOOP_END)
// RAM[*SP] = RAM[*LCL] + RAM[index]
@origin_index
D=M
@LCL
D=D+M
@SP
M=D

 // ### push constant 4001
@4001
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop pointer 0
@SP
M=M-1

@SP
A=M
D=M

@THIS
M=D

 // ### push constant 5001
@5001
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop pointer 1
@SP
M=M-1

@SP
A=M
D=M

@THAT
M=D

 // ### push constant 200
@200
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop local 1
@SP
M=M-1

@1
D=A
@LCL
A=D+M
D=A
@R13
M=D

@SP
A=M
D=M

@R13
A=M
M=D

 // ### push constant 40
@40
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop local 2
@SP
M=M-1

@2
D=A
@LCL
A=D+M
D=A
@R13
M=D

@SP
A=M
D=M

@R13
A=M
M=D

 // ### push constant 6
@6
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop local 3
@SP
M=M-1

@3
D=A
@LCL
A=D+M
D=A
@R13
M=D

@SP
A=M
D=M

@R13
A=M
M=D

 // ### push constant 123
@123
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### call Sys.add12 1
@Sys.main$ret.0
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

@Sys.add12
0;JMP

(Sys.main$ret.0)

 // ### pop temp 0
@SP
M=M-1

@0
D=A
@5
A=D+A
D=A
@R13
M=D

@SP
A=M
D=M

@R13
A=M
M=D

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

 // ### push local 2
@2
D=A
@LCL
A=D+M
D=M

@SP
A=M
M=D

@SP
M=M+1

 // ### push local 3
@3
D=A
@LCL
A=D+M
D=M

@SP
A=M
M=D

@SP
M=M+1

 // ### push local 4
@4
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


 // ### function Sys.add12 0
(Sys.add12)
// RAM[index] = 3
@0
D=A
@origin_index
M=D
@index
M=D

// if index <= 0: goto LOOP_END
(Sys.add12.LOOP_INIT_LCL)
@index
D=M
@Sys.add12.LOOP_END
D;JLE

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// RAM[*(LCL)+index] = 0
@LCL
A=D+M
M=0

@Sys.add12.LOOP_INIT_LCL
0;JMP

(Sys.add12.LOOP_END)
// RAM[*SP] = RAM[*LCL] + RAM[index]
@origin_index
D=M
@LCL
D=D+M
@SP
M=D

 // ### push constant 4002
@4002
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop pointer 0
@SP
M=M-1

@SP
A=M
D=M

@THIS
M=D

 // ### push constant 5002
@5002
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop pointer 1
@SP
M=M-1

@SP
A=M
D=M

@THAT
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

 // ### push constant 12
@12
D=A

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

