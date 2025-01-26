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
  printf(" ( %d )\n", len(l));
}
int len(list l) {
  int i = 0;
  node_ptr n = l->first;
  while (n != NULL) {
    i = i + 1;
    n = n->next;
  }
  return i;
}
int get(list l, int i) {
  node_ptr n = l->first;
  while (i > 1) {
    n = n->next;
    i--;
  }

 return n->elem;
}
void put(list l, int i, int e) {
  node_ptr n = l->first;
  int index = 1;
  while(index != i) {
    n = n->next;
    index = index + 1;
  }
  n->elem = e;
}
void insert(list l, int i, int e) {
  if(i == 1) {
    push(l, e);
  }
  else if (i == len(l) + 1){
    append(l, e);
  }
  else {
    node_ptr newNode = malloc(sizeof(node));
    newNode->elem = e;
    node_ptr n = l->first;
    while(i-1>1) {
      n = n->next;
      i--;
    }
    newNode->next = n->next;
    n->next = newNode;
  }
}
void del(list l, int i) {
  if(i == 1){
    pop(l);
  }
  else {
    node_ptr n = l->first;
    int index = 1;
    while(index != i-1) {
      n = n->next;
      index = index + 1;
    }
    node_ptr frN = n->next;
    n->next = n->next->next;
    free(frN);
    if(i == len(l)-1){
      l->last = n;
    }
  }
 }
void clean(list l) {
  while(!is_empty(l)) {
    pop(l);
  }
}
