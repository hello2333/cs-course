#include <stdio.h>
#include <stdlib.h>

struct my_stru {
  int a;
  int b;
};

int test(struct my_stru *f) {
  struct my_stru *inner_f = (struct my_stru *)malloc(sizeof(struct my_stru));
  inner_f->a = 99;
  inner_f->b = 101;

  f = inner_f;
  return 0;
}

int test_pointer_to_pointer(struct my_stru **pf) {
  struct my_stru *inner_f = (struct my_stru *)malloc(sizeof(struct my_stru));
  inner_f->a = 99;
  inner_f->b = 101;

  *pf = inner_f;
  return 0;
}

int main() {
  struct my_stru *outer_f;
  printf("before result: %d, %d\n", outer_f->a, outer_f->b);
  test(outer_f);
  printf("after  result: %d, %d\n", outer_f->a, outer_f->b);

  test_pointer_to_pointer(&outer_f);
  printf("result for pp: %d, %d\n", outer_f->a, outer_f->b);
  return 0;
}
// [print]
// before result: -571921272, 1
// after  result: -571921272, 1
// result for pp: 99, 101