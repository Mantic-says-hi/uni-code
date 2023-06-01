#include <stdbool.h>
/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 10 / 10 / 2019                                                  *
 * last Mod : 18 / 05 / 2020                                                  *         
 * Purpose  : Linked list for logs                                            *
 *****************************************************************************/
#ifndef LINKEDLIST_H
#define LINKEDLIST_H


/******************************************************************************
 * Struct - LinkedListNode                                                    *
 *                                                                            *
 * Purpose : Allows each node to know its partner on either side              *
 *           (if it even has 2 partners) plus it's own data                   *
 *****************************************************************************/
typedef struct LinkedListNode {
    void* data;
    struct LinkedListNode* next;
    struct LinkedListNode* prev;
} LinkedListNode;

/******************************************************************************
 * Struct - LinkedList                                                        *
 *                                                                            *
 * Purpose : Double ended linked list                                         *
 *****************************************************************************/
typedef struct { 
           LinkedListNode* head;
           LinkedListNode* tail;
} LinkedList;


LinkedList* createLinkedList();

void        insertStart(LinkedList* list, void* entry);

void*       removeLast(LinkedList* list);

void        freeLinkedList(LinkedList* list); 

bool        isEmpty(LinkedList* list);

bool        isFull(LinkedList* list, int max);

#endif
