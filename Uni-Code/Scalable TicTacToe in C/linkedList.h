/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 10 / 10 / 2019                                                  *
 * last Mod : 03 / 11 / 2019                                                    *         
 * Purpose  : Linked list for logs                                            *
 *****************************************************************************/
#ifndef LINKEDLIST_H
#define LINKEDLIST_H
#include "inputFiles.h"

/******************************************************************************
 * Struct - LinkedListNode                                                    *
 *                                                                            *
 * Purpose : Allows each node to know its partner on either side              *
 *           (if it even has 2 partners) plus it's data                       *
 *****************************************************************************/
typedef struct LinkedListNode {
    void* data;
    struct LinkedListNode* next;
    struct LinkedListNode* prev;
} LinkedListNode;

/******************************************************************************
 * Struct - LinkedList                                                        *
 *                                                                            *
 * Purpose : Doubly ended linked list, oh yea yea                             *
 *****************************************************************************/
typedef struct { 
           LinkedListNode* head;
           LinkedListNode* tail;
} LinkedList;

void        printSetting(void* set);

void        printLog(void* log);

void        saveSetting(void* set,FILE* file);

void        saveLog(void* log,FILE* file);


LinkedList* createLinkedList();

void        insertStart(LinkedList* list, void* entry);

void*       removeStart(LinkedList* list);

void        insertLast(LinkedList* list, void* entry);

void*       removeLast(LinkedList* list);

void        printLinkedList(LinkedList* list, void(*setting)(void* set),
                                               void(*print)(void* data));

void        saveLinkedList(LinkedList* list, void(*setting)(void* set,FILE* f),
                                             void(*print)(void* data,FILE* f),
                                             Setting settings);

void        freeLinkedList(LinkedList* list);

int         listLength(LinkedListNode* node);
    
#endif
