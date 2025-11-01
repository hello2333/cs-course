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

// FunctionCalls/StaticsTest/Class1.vm

 // ### function Class1.set 0
(Class1.set)
// RAM[index] = 3
@0
D=A
@origin_index
M=D
@index
M=D

// if index <= 0: goto LOOP_END
(Class1.set.LOOP_INIT_LCL)
@index
D=M
@Class1.set.LOOP_END
D;JLE

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// RAM[*(LCL)+index] = 0
@LCL
A=D+M
M=0

@Class1.set.LOOP_INIT_LCL
0;JMP

(Class1.set.LOOP_END)
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

 // ### pop static 0
@SP
M=M-1

@SP
A=M
D=M

@Class1$static.0
M=D

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

 // ### pop static 1
@SP
M=M-1

@SP
A=M
D=M

@Class1$static.1
M=D

 // ### push constant 0
@0
D=A

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


 // ### function Class1.get 0
(Class1.get)
// RAM[index] = 3
@0
D=A
@origin_index
M=D
@index
M=D

// if index <= 0: goto LOOP_END
(Class1.get.LOOP_INIT_LCL)
@index
D=M
@Class1.get.LOOP_END
D;JLE

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// RAM[*(LCL)+index] = 0
@LCL
A=D+M
M=0

@Class1.get.LOOP_INIT_LCL
0;JMP

(Class1.get.LOOP_END)
// RAM[*SP] = RAM[*LCL] + RAM[index]
@origin_index
D=M
@LCL
D=D+M
@SP
M=D

 // ### push static 0
@Class1$static.0 
D=M 

@SP
A=M
M=D

@SP
M=M+1

 // ### push static 1
@Class1$static.1 
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

// FunctionCalls/StaticsTest/Class2.vm

 // ### function Class2.set 0
(Class2.set)
// RAM[index] = 3
@0
D=A
@origin_index
M=D
@index
M=D

// if index <= 0: goto LOOP_END
(Class2.set.LOOP_INIT_LCL)
@index
D=M
@Class2.set.LOOP_END
D;JLE

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// RAM[*(LCL)+index] = 0
@LCL
A=D+M
M=0

@Class2.set.LOOP_INIT_LCL
0;JMP

(Class2.set.LOOP_END)
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

 // ### pop static 0
@SP
M=M-1

@SP
A=M
D=M

@Class2$static.0
M=D

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

 // ### pop static 1
@SP
M=M-1

@SP
A=M
D=M

@Class2$static.1
M=D

 // ### push constant 0
@0
D=A

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


 // ### function Class2.get 0
(Class2.get)
// RAM[index] = 3
@0
D=A
@origin_index
M=D
@index
M=D

// if index <= 0: goto LOOP_END
(Class2.get.LOOP_INIT_LCL)
@index
D=M
@Class2.get.LOOP_END
D;JLE

// RAM[index] = RAM[index] - 1
@index
M=M-1
D=M

// RAM[*(LCL)+index] = 0
@LCL
A=D+M
M=0

@Class2.get.LOOP_INIT_LCL
0;JMP

(Class2.get.LOOP_END)
// RAM[*SP] = RAM[*LCL] + RAM[index]
@origin_index
D=M
@LCL
D=D+M
@SP
M=D

 // ### push static 0
@Class2$static.0 
D=M 

@SP
A=M
M=D

@SP
M=M+1

 // ### push static 1
@Class2$static.1 
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

// FunctionCalls/StaticsTest/Sys.vm

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

 // ### push constant 6
@6
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### push constant 8
@8
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### call Class1.set 2
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
@2
D=A
@ARG
M=M-D

@SP
D=M
@LCL
M=D

@Class1.set
0;JMP

(Sys.init$ret.0)

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

 // ### push constant 23
@23
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### push constant 15
@15
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### call Class2.set 2
@Sys.init$ret.1
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
@2
D=A
@ARG
M=M-D

@SP
D=M
@LCL
M=D

@Class2.set
0;JMP

(Sys.init$ret.1)

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

 // ### call Class1.get 0
@Sys.init$ret.2
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

@Class1.get
0;JMP

(Sys.init$ret.2)

 // ### call Class2.get 0
@Sys.init$ret.3
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

@Class2.get
0;JMP

(Sys.init$ret.3)

 // ### label WHILE
(Sys.init$WHILE)

 // ### goto WHILE
@Sys.init$WHILE
0;JMP
