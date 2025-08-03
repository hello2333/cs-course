// ### R0 = RAM[SP--]
// ### R1 = RAM[SP--]
// ### R1 = RO + R1
// ### RAM[SP++] = R1

// ### Init: SP = 100, RAM[100] = 20, RAM[99] = 30
// ### Result: RAM[98] = 50 SP = 99
// # RAM[99] = 30
@30
D=A
@99
M=D
// # RAM[100] = 20
@20
D=A
@100
M=D
// # SP=100
@100
D=A
@SP
M=D

// # R0 = RAM[SP--]
@SP
A=M
D=M
@R10
M=D

// # SP = SP - 1
@SP
M=M-1


// # R1 = RAM[SP--]
@SP
A=M
D=M
@R11
M=D

// # SP = SP - 1
@SP
M=M-1

// # R1 = R0 + R1
@R10
D=M
@R11
M=M+D

// # RAM[SP++] = R1
D=M
@SP
A=M
M=D

@SP
M=M+1