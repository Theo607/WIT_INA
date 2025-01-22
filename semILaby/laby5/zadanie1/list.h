#pragma once

#include <stdbool.h>

typedef struct node {
  int elem;
  struct node* next;
} node;
typedef node* node_ptr;

typedef struct list_t {
  node_ptr first;
  node_ptr last;
  int length;
} list_t;
typedef list_t* list;

bool is_empty(list l);

int  pop(list l);
void push(list l, int e);
void append(list l, int e);

//now f-cje, proc.
int get(list l, int i);
void put(list l, int i, int e);
void insert(list l, int i, int e);
void del(list l, int i);

void print(list l);
int  len(list l);
void clean(list l); //nowa proc. - czyszczenie listy
