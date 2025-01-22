#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include "list.h"

int main() {
  char command[20];
  bool cont = true;
  int r;
  list l = malloc(sizeof(list_t));
  l->first = l->last = NULL;
  while (cont) {
    printf("Command: ");
    scanf("%s", command);
    if (!strcmp(command, "pop")) {
      if (!is_empty(l)) {
        r = pop(l);
        printf("Result: %d\n", r);
      } else {
        printf("Error - stack is empty!\n");
      }
    }
    else if (!strcmp(command, "push")) {
      printf("Value: ");
      scanf("%d", &r);
      push(l, r);
      printf("Result: OK\n");
    }
    else if (!strcmp(command, "append")) {
      printf("Value: ");
      scanf("%d", &r);
      append(l, r);
      printf("Result: OK\n");
    }
    else if (!strcmp(command, "print")) {
      printf("Result: ");
      print(l);
    }
    else if (!strcmp(command, "len")) {
      r = len(l);
      printf("Result: %d\n", r);
    }
    else if (!strcmp(command, "get")) {
      int i;
      printf("Index: ");
      scanf("%d", &i);
      if(i > len(l) || i < 1)
        printf("Error - bad index!\n");
      else {
        r = get(l, i);
        printf("Result: %d\n", r);
      }
    }
    else if (!strcmp(command, "put")) {
      int i;
      printf("Index: ");
      scanf("%d", &i);
      if(i > len(l) || i < 1)
        printf("Error - bad index!\n");
      else {
        printf("Value: ");
        scanf("%d", &r);
        put(l, i, r);
        printf("Result: OK\n");
      }
    }
    else if (!strcmp(command, "insert")) {
      int i;
      printf("Index: ");
      scanf("%d", &i);
      if(i > len(l) + 1 || i < 1)
        printf("Error - bad index!\n");
      else {
        printf("Value: ");
        scanf("%d", &r);
        insert(l, i, r);
        printf("Result: OK\n");
      }
    }
    else if (!strcmp(command, "del")) {
      int i;
      printf("Index: ");
      scanf("%d", &i);
      if(i > len(l) || i < 1)
        printf("Error - bad index!\n");
      else {
        del(l, i);
        printf("Result: OK\n");
      }
    }
    else if (!strcmp(command, "clean")) {
      clean(l);
      printf("Result: OK\n");
    }
    else if (!strcmp(command, "exit")) {
      cont = false;
    }
    else
      printf("Unknown command!\n");
  }
  while (!is_empty(l))
    pop(l);
  free(l);

  return 0;
}
