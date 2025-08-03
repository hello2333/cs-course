#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int test_with_fork() {
  pid_t pid1, pid2;

  pid1 = fork();
  if (pid1 < 0) {
    printf("fork pid1 failed.\n");
    exit(-1);
  }
  if (pid1 == 0) {
    printf("pid1 start\n");

    char c = '\0';
    int a = 0;
    while ((c = getchar()) != '\n') {
      if (a >= 5) {
        break;
      }
      a ++;
      printf("1-%c\n", c);
    }
    exit(0);
  }
  
  printf("parent continue.\n");
  pid2 = fork();
  if (pid2 < 0) {
    printf("fork pid2 failed.\n");
    exit(-1);
  }
  if (pid2 == 0) {
    printf("pid2 start\n");
    char c = '\0';
    int a = 0;
    while ((c = getchar()) != '\n') {
      if (a >= 5) {
        break;
      }
      a ++;
      printf("2-%c\n", c);
    }
    exit(0);
  }

  wait(NULL);
  return 0;
}

int main(int argc, char ** argv) {
  char c = '\0';
  // while ((c = getchar()) != '\n') {
  //   // printf("char: %c\n", c);
  //   putchar(c);
  // }

  test_with_fork();
  return 0;
}

