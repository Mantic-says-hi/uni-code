
#ifndef CONTROLLER_H
#define CONTROLLER_H
#include "inputFiles.h"
#include "linkedList.h"

/******************************************************************************
 * Struct - Log                                                               *
 *                                                                            *
 * Purpose : Collect all the data required for each turn in order to store    *
 *           said data in a linked list to be viewed and written to a file    *
 *****************************************************************************/    
typedef struct {
    int  game;
    int  turn;
    char player;
    int  xpos;
    int  ypos;
} Log;


void launchGame(Setting settings);

char** setupGrid(Setting settings);

char** resetGrid(char** grid, Setting setings);

char** freeGrid(char** grid, Setting settings);

void displaySettings(Setting settings);

LinkedList* setupList(LinkedList* list, Setting set);
#endif
