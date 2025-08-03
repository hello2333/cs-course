#include <stdio.h>
#include <stdlib.h>

struct my_node {
  struct left_node* left;
  struct right_node* right;
};

struct single_node {
  struct single_node * next;
};

struct init_node {
  struct init_node * init;
} init_test;

struct simple_stru {
  struct inner_stru *inner_pointer;
};

struct inner_stru {
  int num1;
  int num2;
};

void struct_copy_test_right() {
  struct inner_stru i1 = {10, 13};
  struct simple_stru s1 = {&i1};
  printf("s1: %d, s1.inner_pointer: %p\n", s1.inner_pointer->num1, s1.inner_pointer);

  struct simple_stru s2;
  s2.inner_pointer = (struct inner_stru *) malloc(sizeof(struct inner_stru));
  *(s2.inner_pointer) = *(s1.inner_pointer);
  printf("s1: %d, s1.inner_pointer: %p, s2.inner_pointer: %p\n", s1.inner_pointer->num1, s1.inner_pointer, s2.inner_pointer);
  printf("s1: %d, s2: %d\n", s1.inner_pointer->num1, s2.inner_pointer->num1);

  free(s2.inner_pointer);

  struct inner_stru *i2 = &i1;
  struct inner_stru i3 = i1;
  printf("i2: %d, %d \n", i2->num1, i2->num2);
  printf("i3: %d, %d \n", i3.num1, i3.num2);

  i2->num2 = 99;
  i3.num1 = 98;
  printf("after modify - i2: %d, %d, i1: %d, %d \n", i2->num1, i2->num2, i1.num1, i1.num2);
  printf("after modify - i3: %d, %d, i1: %d, %d \n", i3.num1, i3.num2, i1.num1, i1.num2);

  struct inner_stru i4;
  printf("initial i: %d, %d \n", i4.num1, i4.num2);

  struct simple_stru s3;
  printf("initial s: %p \n", s3.inner_pointer);
}

void struct_copy_tes_wrong() {
  struct inner_stru i1 = {10, 13};
  struct simple_stru s1 = {&i1};
  printf("s1: %d, s1.inner_pointer: %p\n", s1.inner_pointer->num1, s1.inner_pointer);

  struct simple_stru s2;
  // 运行到这里会segment fault
  *(s2.inner_pointer) = *(s1.inner_pointer);
  printf("s1: %d, s1.inner_pointer: %p, s2.inner_pointer: %p\n", s1.inner_pointer->num1, s1.inner_pointer, s2.inner_pointer);
  printf("s1: %d, s2: %d\n", s1.inner_pointer->num1, s2.inner_pointer->num1);
}

int main(int argc, char ** argv) {
  struct single_node * before_single;
  printf("before inner: %p, outer: %p\n", before_single->next, before_single);

  struct my_node * test_node;
  printf("left: %p, right: %p, currnt: %p\n", test_node->left, test_node->right, test_node);

  struct single_node * after_single;
  printf("after inner: %p, outer: %p\n", after_single->next, after_single);

  printf("global inner: %p, outer: %p\n", init_test.init, &init_test);

  struct_copy_test_right();
  return 0;
}

