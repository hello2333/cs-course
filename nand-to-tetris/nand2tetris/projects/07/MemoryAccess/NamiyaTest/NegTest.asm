// ### R10 = RAM[SP--]
// ### RAM[SP++] = -R10

// ### Init: SP = 100, RAM[100] = 70
// ### Result: RAM[99] = -70, SP = 100
// RAM[100] = 70
@70
D=A
@100
M=D
// SP = 100
@100
D=A
@SP
M=D

// R10 = RAM[SP--]
@SP
A=M
D=M
@R10
M=D

// SP = SP - 1
@SP
M=M-1

// RAM[SP] = -R10
@R10
D=M
@SP
A=M
M=-D

// SP = SP + 1
@SP
M=M+1