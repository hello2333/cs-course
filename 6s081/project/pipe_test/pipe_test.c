// C program to illustrate
// pipe system call in C
// shared by Parent and Child
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#define MSGSIZE 16
char* msg1 = "hello, world #1";
char* msg2 = "hello, world #2";
char* msg3 = "hello, world #3";

int main()
{
	char inbuf[MSGSIZE];
	int p[2], pid, nbytes;

	if (pipe(p) < 0) {
    printf("create pipe fail\n");
		exit(3);
  }

	/* continued */
	if ((pid = fork()) > 0) {
		write(p[1], msg1, MSGSIZE);
        write(p[1], msg2, MSGSIZE);

		// Adding this line will
		// not hang the program
		close(p[1]);
		close(p[0]);
		return 0;
	}

	else {
		// Adding this line will
		// not hang the program
		close(p[1]);
		while ((nbytes = read(p[0], inbuf, MSGSIZE + 5)) > 0) {
			printf("% s, sz=%d\n", inbuf, nbytes);
		}
		if (nbytes != 0) {
      		printf("read fail\n");
			exit(2);
    	}
			
		printf("Finished reading\n");
    	exit(0);
	}
	return 0;
}

/**
 * 运行结果
 * hello, world #1, sz=21
 * , world #2, sz=11
 * Finished reading 
 */
