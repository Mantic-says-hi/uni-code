#ifndef GRID_H
#define GRID_H
#include "controller.h"
int  getInput(char pos, int dimension);

void printGrid(char** grid, int height, int width);

LinkedList* startGame(char** grid, Setting set,LinkedList* list,int game);

char checkWinner(char** grid, Setting set);

LinkedList* placeLog(LinkedList* list, Log toAdd);

#endif
