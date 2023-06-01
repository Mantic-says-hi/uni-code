/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 02/11/2019                                                      *
 * Last Mod : 04/11/2019                                                      *
 * Purpose  : Deals with all the grid logic (printing / check for winner /    *
 *            putting turns into log and list)                                *
 *****************************************************************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "inputFiles.h"
#include "grid.h"
#include "controller.h"

/******************************************************************************
 * Method - printGrid                                                         *
 *                                                                            *
 * Purpose : Does what is says, prints out the grid in a nice way to the user *        
 *****************************************************************************/
void printGrid(char** grid,int height, int width)
{
    int i,j,z;
 
    printf("\n||");
    for(i = 0; i < width; i++){
        printf("----");
    }

    printf("\b||\n");

    for(i = 0; i < height; i++){
        printf("||");
        for(j = 0; j < width; j++){
            printf(" %c |", grid[i][j]);
        }
        printf("|\n||");
        for(z = 0; z < width; z++){
            printf("----");
        }

        printf("\b||\n");
    }
}


/******************************************************************************
 * Method - startGame                                                         *
 *                                                                            *
 * Purpose : Handles the aspects of the game, i.e. user putting in the right  *
 *           and valid co-ordinates all the way to sending back the list      *
 *           with the logs from each turn on it, tells the user who won       *
 *****************************************************************************/
LinkedList* startGame(char** grid, Setting set ,LinkedList* list, int game)
{
    
    int x,y,turns = 0,player,height, width;
    char currPlayer[2] = {'X','O'}, winner;
    Log  currLog;
    height = set.height;
    width = set.width; 

    srand(time(NULL));/*Seeding*/
    player = (rand() % 2);/*Either 1 or 0 for [X or O]*/

    do{
        printf("\n    [Hint] X : 0 - %d | Y : 0 - %d\n", (width -1),
                                                         (height - 1));
        printf("\n        [Player %c's turn]  \n",currPlayer[player]);
        x = getInput('X', width);
        y = getInput('Y', height); 
        if(grid[y][x] != ' '){
            printf("\n    Error : Tile already placed on (%d,%d)\n",x,y);
        }else{
            grid[y][x] = currPlayer[player];
            printGrid(grid, height, width);
            turns = turns + 1;

            currLog.game = game;
            currLog.turn = turns;
            currLog.player = currPlayer[player];
            currLog.xpos = x;
            currLog.ypos = y;

            list = placeLog(list, currLog);
     
            if(player == 1){
                player = 0;
            }else{
                player = 1;
            }
            winner = checkWinner(grid,set);
        }
    }while((turns != (height * width)) && (winner != 'X') && (winner != 'O'));
    /*Only exists if there is a winner or the entire board is filled*/
    
   
    if(winner == 'X' || winner == 'O'){
        printf("\n\n    [%c] IS THE WINNER!!\n\n",winner);
    }else{
        printf("\n\n    There has been a DRAW \n");
        printf("     How anticlimactic...\n\n");
    }

    return list;
}

/******************************************************************************
 * Method - checkWinner                                                       *
 *                                                                            *
 * Purpose : Determines a winner, and is by far the method that I took the    *
 *           longest to wright. I have done extensive testing to get it       *
 *           to this point however i am  not sure                             *
 *           if I have tested all cases.                                      *
 *****************************************************************************/
char checkWinner(char** grid,Setting set)
{
    int i, j, matchNum = 1,gotWin = 0,passes = 0;
    char winner = ' ';

    /*Horizontal check*/
    for(i = 0; i < set.height; i++){
        matchNum = 1;
        for(j = 0; j < set.width - 1; j++){
            if(grid[i][j] == grid[i][j+1] && grid[i][j] != ' '){
                matchNum = matchNum + 1;
                if(matchNum == set.matchMin && gotWin == 0){
                    winner = grid[i][j];
                    gotWin = 1;
                }
            }else{
                matchNum = 1;
            }     
        }
    }
    /*Vertical check if no horizontal winner*/  
    if(gotWin == 0){
        for(i = 0; i < set.width; i++){
            matchNum = 1;
            for(j = 0; j < set.height - 1; j++){
                if(grid[j][i] == grid[j+1][i] && grid[j][i] != ' '){
                    matchNum = matchNum + 1;
                    if(matchNum == set.matchMin && gotWin == 0){
                        winner = grid[j][i];
                        gotWin = 1;
                    }
                }else{
                    matchNum = 1;
                }
            }
        }
    }
    
    /*If still no winner a diagonal starting from the top left*/
    if(gotWin == 0){
        for(i = 0; i <= abs(set.width - set.height); i++){
            matchNum = 1;
            for(j = 0; j < set.matchMin - 1; j++){
                    if(grid[j][j+passes] == grid[j+1][(j+passes)+1] && 
                       grid[j][j+passes] != ' '){
                        matchNum = matchNum + 1;
                        if(matchNum == set.matchMin && gotWin == 0){
                            winner = grid[j][j+passes];
                            gotWin = 1;
                        }
                    }else{
                        matchNum = 1;
                    }
            }
            passes += 1;
        }
    }
    passes = 0;

    /*If STILL NO winner a diagonal starting from the top right*/
    if(gotWin == 0){
        for(i = (set.width - 1); i >=((set.width-1)-
                                       abs(set.width - set.height)); i--){
            matchNum = 1;
            for(j = 0; j < set.matchMin - 1; j++){
                    if(grid[j][(set.width - 1)-(j+passes)] == 
                       grid[j+1][(set.width - 2)-(j+passes)] && 
                       grid[j][(set.width - 1)-(j+passes)] != ' '){
                        matchNum = matchNum + 1;
                        if(matchNum == set.matchMin && gotWin == 0){
                            winner = grid[j][(set.width - 1)-(j+passes)];
                            gotWin = 1;
                        }
                    }else{
                        matchNum = 1;
                    }
            }
            passes += 1;
        }
    }

    return winner;               
}


/******************************************************************************
 * Method - placeLog                                                          *
 *                                                                            *
 * Purpose : Put the turn into the list/log                                   *
 *****************************************************************************/
LinkedList* placeLog(LinkedList* list, Log toAdd)
{
    Log* log = (Log*)malloc(sizeof(Log));
    *log = toAdd;

    insertLast(list,log);

    return list;
}


/******************************************************************************
 * Method - getInput                                                          *
 *                                                                            *
 * Purpose : Gets the input from the user as to what tile they wish to place  *
 *           their turn                                                       *
 *****************************************************************************/
int getInput(char pos, int dimension)
{
    char userInput[20] = " ";
    int value;
    char* ptr;
    do{   
        printf("\n%c : ",pos);
        fgets(userInput,20,stdin);            
        value = strtol(userInput,&ptr,10);
        if(value == 0 && userInput[0] != '0'){
            value = - 1;
        }
        if(!(value <= dimension - 1 && value >= 0)){
            printf( "\nError : %c value must be [%d - %d]\n",pos,0,
                                                            (dimension - 1));
        }
    }while(!(value <= dimension - 1 && value >= 0));

    return value;
}
