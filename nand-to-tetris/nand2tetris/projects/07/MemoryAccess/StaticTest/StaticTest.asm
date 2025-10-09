
 // ### push constant 111
@111
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### push constant 333
@333
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### push constant 888
@888
D=A

@SP
A=M
M=D

@SP
M=M+1

 // ### pop static 8
@SP
M=M-1

@8
D=A
@static
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

 // ### pop static 3
@SP
M=M-1

@3
D=A
@static
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

 // ### pop static 1
@SP
M=M-1

@1
D=A
@static
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

 // ### push static 3
@3
D=A
@static
A=D+A
D=M

@SP
A=M
M=D

@SP
M=M+1

 // ### push static 1
@1
D=A
@static
A=D+A
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

 // ### push static 8
@8
D=A
@static
A=D+A
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
