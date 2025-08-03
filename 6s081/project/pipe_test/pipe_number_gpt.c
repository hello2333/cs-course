#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    int pipefd[2];
    pid_t pid;
    
    // 创建管道
    if (pipe(pipefd) == -1) {
        perror("pipe");
        return 1;
    }

    pid = fork();
    if (pid == -1) {
        perror("fork");
        return 1;
    }

    if (pid == 0) { // 这是子进程（进程 B）
        int received_num;

        // 关闭管道的写端
        close(pipefd[1]);

        printf("子进程接收并打印整数：\n");
        for (int i = 1; i <= 10; i++) {
            read(pipefd[0], &received_num, sizeof(received_num));
            printf("%d\n", received_num);
        }

        // 关闭管道的读端
        close(pipefd[0]);
    } else { // 这是父进程（进程 A）
        // 关闭管道的读端
        close(pipefd[0]);

        for (int i = 1; i <= 10; i++) {
            write(pipefd[1], &i, sizeof(i));
        }

        // 关闭管道的写端
        close(pipefd[1]);
        printf("parent end\n");

        // 等待子进程结束
        wait(NULL);
    }

    return 0;
}