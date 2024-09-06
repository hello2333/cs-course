// ### Target
// # expression: RAM[300] = RAM[60+7]
// # expect: RAM[300] = 177, SP = 301

// ### Init
// ## local = 60
// ## i = 7
// ## SP = 300
// ## RAM[67] = 177

// # local = 60
@60
D=A
@local
M=D

// # i = 7 -> no need

// # SP = 300
@300
D=A
@SP
M=D

// # RAM[67] = 177
@177
D=A
@67
M=D

// # push local i
// # RAM[SP++] = RAM[local + i]

// ### step1: RAM[local + i]
@local
D=M
@7
A=A+D
D=M

// ### step2: RAM[SP] = RAM[local+i]
@SP
A=M
M=D

// ### step3: SP = SP + 1
@SP
M=M+1