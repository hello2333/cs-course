// ### SP = 51, THIS = 123
// ### RAM[51] = 123, SP = 52

// ### push pointer 0 -> push this
// ### RAM[SP++] = THIS

// step0: INIT
@51
D=A
@SP
M=D

@123
D=A
@THIS
M=D

// step1: THIS
@THIS
D=M

// step2: RAM[SP]
@SP
A=M

// step3: RAM[SP] = THIS
M=D

// step4: SP = SP + 1
@SP
M=M+1

// ### SP = 52, RAM[52] = 223
// ### THIS = 223, SP = 51
// ### pop pointer 0 -> pop this
// ### THIS = RAM[SP--]

// step0: Init
@223
D=A
@SP
A=M
M=D

// step1: RAM[SP]
@SP
A=M
D=M

// step2: THIS
@THIS
// step3: THIS = RAM[SP]
M=D

// step4: SP = SP - 1
@SP
M=M-1