/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 10 / 10 / 2019                                                  ^
 * Last Mod : 18 / 05 / 2020                                                  *
 * Purpose  : Linked list for buffer                                          *
 *****************************************************************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include "linkedList.h"
/*Part of this code is almost entierly the same as the code for my prac submission
 *on linked lists in UCP*/

LinkedList* createLinkedList()
{
    LinkedList* list = (LinkedList*)malloc(sizeof(LinkedList));

    list->head = NULL;
    list->tail = NULL;

    return list;
}

void insertStart(LinkedList* list, void* entry)
{
    LinkedListNode* node = (LinkedListNode*)malloc(sizeof(LinkedListNode));

    if(list->head == NULL)
    {
        node->data = entry;
        list->head = node;
        list->tail = node;
    }
    else 
    {
        node->data = entry;
        list->head->prev = node;
        node->next = list->head;
        list->head = node;
    }
}


void* removeLast(LinkedList* list)
{
    void* entry = NULL;
    LinkedListNode* temp = list->tail;    

    if(list->head == NULL)
    {
        printf("Nothing to remove");
    }
    else if(list->head->next == NULL)
    {
        entry =  list->head->data;
        list->head = NULL;
        free(temp);
    }
    else
    {
        entry = list->tail->data;
        list->tail = list->tail->prev;
        list->tail->next = NULL;
        free(temp); 
    }
    return entry;
}

bool isEmpty(LinkedList* list)
{
    return(list->head == NULL);
}

bool isFull(LinkedList* list, int max)
{
    int check = 0;
    LinkedListNode* temp = list->head;
    while(temp!=NULL)
    {
        check++;
        temp = temp->next;
    }


    return(check == max);
}

void freeLinkedList(LinkedList* list) 
{
    LinkedListNode *node,
                   *nextNode;

    node = list->head;
    while(node != NULL) 
    {
        nextNode = node->next;
        free(node->data);
        free(node);
        node = nextNode;
    }
    
    free(list);
}



