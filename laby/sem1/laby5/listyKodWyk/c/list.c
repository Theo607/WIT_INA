#include <stdio.h>
#include <stdlib.h>
#include "list.h"

bool is_empty(list l) {
  return l->first == NULL;
}
int pop(list l) {
  node_ptr n = l->first;
  int e = n->elem;
  l->first = l->first->next;
  if (l->first == NULL)  // last element
    l->last = NULL;
  free(n);
  return e;
}
void push(list l, int e) {
  node_ptr n = malloc(sizeof(node));
  n->elem = e;
  n->next = l->first;
  l->first = n;
  if (l->last == NULL)  // first element
    l->last = n;
}
void append(list l, int e) {
  node_ptr n = malloc(sizeof(node));
  n->elem = e;
  if (l->first == NULL)  // first element
    l->first = n;
  else
    l->last->next = n;
  l->last = n;
}
void print(list l) {
  node_ptr n = l->first;
  while (n != NULL) {
    printf(" %d", n->elem);
    n = n->next;
  }
  printf(" ( %d )\n", length(l));
}
int length(list l) {
  int i = 0;
  node_ptr n = l->first;
  while (n != NULL) {
    i = i + 1;
    n = n->next;
  }
  return i;
}

