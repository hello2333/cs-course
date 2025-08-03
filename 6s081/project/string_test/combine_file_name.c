#include <string.h> 
#include <stdio.h>

void get_complete_path(char* parent_path, char* child_name, char* complete_path) {
  char *p;
  memcpy(complete_path, parent_path, strlen(parent_path));
  p = complete_path + strlen(complete_path);
  *p++ = '/';
  memmove(p, child_name, strlen(child_name));
  p += strlen(child_name);
  //*p = '\0';
}

int main() {
  char* dir_name = "dir_name/level1/level2";
  char* child_name = "child_file";
  char combine_name[strlen(dir_name) + strlen(child_name) + 1];
  get_complete_path(dir_name, child_name, combine_name);
  printf("result: %s\n", combine_name);
  return 0;
}

// 1 0001 0000