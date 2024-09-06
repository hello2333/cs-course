// ### Target
// # expression: RAM[130 + 7] = RAM[500]
// # expect: RAM[137] = 931, SP = 499

// ### Init
// ## local = 130
// ## i = 7
// ## SP = 500
// ## RAM[500] = 931

// # local = 130
@130
D=A
@local
M=D

// # i = 7 -> no need

// # SP = 500
@500
D=A
@SP
M=D

// # RAM[500] = 931
@931
D=A
@SP
A=M
M=D

// # pop local i
// # RAM[local + i] = RAM[SP--]

// ### RAM[local + i] = RAM[SP]
// ## RAM[SP]
@SP
A=M
D=M
@R1
M=D

// ## RAM[local+i]
@local
D=M
@7
A=A+D
D=A
@R2
M=D

// ## RAM[local+i] = RAM[SP]
@R1
D=M
@R2
A=M
M=D

// ### step2: SP = SP - 1
@SP
M=M-1