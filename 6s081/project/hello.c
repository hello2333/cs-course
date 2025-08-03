#include <stdio.h>
#include <limits.h>
#include <float.h>
int main()
{
     printf("hello, world\n test for new char, test end.\n");
     printf("data type limits:\n");
     printf("interger limit: %d, long limit: %lu, char limit: %u",
          INT_MAX, LONG_MAX, SCHAR_MAX);
     return 0;
}
