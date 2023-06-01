/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 10 / 10 / 2019                                                  ^
 * Last Mod : 03 / 11 / 2019                                                  *
 * Purpose  : Lineked list for logs and setting as a head                     *
 *****************************************************************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "linkedList.h"
#include "controller.h"
/*This code is almost entierly the same as the code for my prac submission
 *on linked lists*/
/******************************************************************************
 * I have chosen not to explain every method since it is very straight        *
 * forward since it's a linked list and everyone knows whats up with it       *
 *****************************************************************************/
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


void* removeStart(LinkedList* list)
{
    void* entry = NULL;
    LinkedListNode* temp = list->head;    

    if(list->head == NULL)
    {
        printf("Nothing to remove - you get nothing");
    }
    else
    {
        entry = list->head->data;
        list->head = list->head->next;
        if(list->head != NULL){
            list->head->prev = NULL;
        }
        free(temp);
    }
     
    return entry;
}


void insertLast(LinkedList* list, void* entry) 
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
        node->next = NULL;
        node->prev = list->tail;
        list->tail->next = node;
        list->tail = node;
    }
}

void* removeLast(LinkedList* list)
{
    void* entry = NULL;
    LinkedListNode* temp = list->tail;    

    if(list->head == NULL)
    {
        printf("Nothing to remove - you get nothing");
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

void printLinkedList(LinkedList* list,void (*setting)(void* data),
                                      void (*print)(void* data))
{
    LinkedListNode* node = list->head;

    (*setting)((node->data));
    node = node->next;
    while (node != NULL)    
    {
        (*print)((node->data));
        node = node->next;
    }

}

void saveLinkedList(LinkedList* list, void (*setting)(void* data,FILE* f),
                                       void (*save)(void* data,FILE* f),
                                       Setting set)
{
    FILE*  outFile;
    char   outStr[30];
    time_t getTime = time(NULL);
    struct tm time = *localtime(&getTime);
    LinkedListNode* node = list->head;
    /*Really convoluted way of getting the right file name but it works*/
    sprintf(outStr, "MNK_%d-%d-%d_%02d-%02d_%02d-%02d.log",
                                                        set.width,
                                                        set.height,
                                                        set.matchMin,
                                                        time.tm_hour,
                                                        time.tm_min,
                                                        time.tm_mday,
                                                        (time.tm_mon + 1));
    outFile = fopen(outStr,"w");   
    
    (*setting)((node->data),outFile);
    node = node->next;
    while (node != NULL)    
    {
        (*save)((node->data),outFile);
        node = node->next;
    }

    fclose(outFile);
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

int listLength(LinkedListNode* node)
{
    int length = 0;
    
    if (node != NULL)
    {
        length = 1 + listLength(node->next);
    }

    return length;
}

void printSetting(void* set)
{
    printf("\nSETTINGS:");
    printf("\n    M: %d", (*(Setting*)set).width);
    printf("\n    N: %d", (*(Setting*)set).height);
    printf("\n    K: %d\n", (*(Setting*)set).matchMin);
}

void printLog(void* log)
{
    if((*(Log*)log).turn == 1){
        printf("\nGAME %d:",(*(Log*)log).game);
    }
    printf("\n    Turn: %d",(*(Log*)log).turn);
    printf("\n    Player: %c",(*(Log*)log).player);
    printf("\n    Location: %d,%d\n",(*(Log*)log).xpos,(*(Log*)log).ypos);
}


void saveSetting(void* set,FILE* f)
{
    fprintf(f,"\nSETTINGS:");
    fprintf(f,"\n    M: %d", (*(Setting*)set).width);
    fprintf(f,"\n    N: %d", (*(Setting*)set).height);
    fprintf(f,"\n    K: %d\n", (*(Setting*)set).matchMin);
}

void saveLog(void* log,FILE* f)
{
    if((*(Log*)log).turn == 1){
        fprintf(f,"\nGAME %d:",(*(Log*)log).game);
    }
    fprintf(f,"\n    Turn: %d",(*(Log*)log).turn);
    fprintf(f,"\n    Player: %c",(*(Log*)log).player);
    fprintf(f,"\n    Location: %d,%d\n",(*(Log*)log).xpos,(*(Log*)log).ypos);
}
