/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 02/11/2019                                                      *
 * Last Mod : 04/11/2019                                                      *
 * Purpose  : Controlls all the main inputs and outputs of the program        *
 *            as well as freeing all the memory allocated during the program  *
 *****************************************************************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "menus.h"
#include "grid.h"
#include "inputFiles.h"
#include "controller.h"
#include "linkedList.h"

/******************************************************************************
 * Method - launchGame                                                        *
 *                                                                            *
 * Purpose : Determines what mode(s) the program has been compiled with then  *
 *           sets about the option menus accordingly to the possible user     *
 *           input and goes from there                                        *
 *****************************************************************************/
void launchGame(Setting settings)
{
    int option = 0, exitCode = 5,games = 0, hardCase = 0;
    static char** grid;
    LinkedList* logList = NULL;
    Setting* set,*temp;

    logList = setupList(logList, settings);
    grid = setupGrid(settings);

    #ifdef SECRET
    exitCode = 4;
    #endif
    #ifdef EDITOR
    exitCode = 6;
    #endif
    #if defined(SECRET) && defined(EDITOR)
    exitCode = 5;/*Unsure if the above ifndef change this*/
    hardCase = 1;
    #endif

    do{
        option = menuSelection("MAIN");
            
        if(option == 1){
            games = games + 1;
            grid = resetGrid(grid,settings);
            printGrid(grid, settings.height, settings.width);
            logList = startGame(grid, settings, logList, games);
        }else if(option == 2){
            displaySettings(settings); 
        }else if(option == 3){
            printLinkedList(logList,printSetting,printLog);  
        }else if(option == 4 && exitCode != 4 && hardCase == 0){
            saveLinkedList(logList,saveSetting,saveLog,settings);
        }else if((option == 5 && exitCode == 6) || 
                 (option == 4 && exitCode == 5)){
            if(menuSelection("EDSET") == 1){
                grid = freeGrid(grid, settings);
                settings = editSettings(settings);
                grid = setupGrid(settings);
                temp = removeStart(logList);
                free(temp);
                set = (Setting*)malloc(sizeof(Setting));
                *set = settings;
                insertStart(logList,set);
                /*A lot of work just to change setting and change head*/
            }
        }
        /***************************************************
         *SECRET      NORMAL      EDITOR     SECRET+EDITOR *
         *1.Start     1.Start     1.Start    1.Start       *
         *2.VSet      2.VSet      2.VSet     2.VSet        *
         *3.VLog      3.VLog      3.VLog     3.VLog        *
         *4.Exit      4.SLog      4.SLog     4.ELog        *
         *            5.Exit      5.ELog     5.Exit        *
         *                        6.Exit                   *
         **************************************************/
    }while(option != exitCode);
    freeGrid(grid,settings);
    freeLinkedList(logList);
    printf("\n\n    [Exiting program]\n\n");
}

/******************************************************************************
 * Method - setupList                                                         *
 *                                                                            *
 * Purpose : Creates the list and puts the settings at the head, where        *
 *           it needs to be for the print and save methods                    *
 *****************************************************************************/
LinkedList* setupList(LinkedList* list, Setting set)
{
    Setting* settings = (Setting*)malloc(sizeof(Setting));
    *settings = set;

    list = createLinkedList();
    insertStart(list,settings);
    
    return list;
}


/******************************************************************************
 * Method - setupGrid                                                         *
 *                                                                            *
 * Purpose : Very similar in idea to the setupList however it allocates       *
 *           enough memory for the whole grid                                 *
 *****************************************************************************/
char** setupGrid(Setting settings)
{
    int i;
    char** grid;
    grid = (char**)malloc(settings.height * sizeof(char*));
    for ( i = 0; i < settings.height; i++)
    {
        grid[i] = (char*)malloc((2*settings.width)  * sizeof(char));
    }
    return grid;
}


/******************************************************************************
 * Method - resetGrid                                                         *
 *                                                                            *
 * Purpose : Every time a new game starts, the board must be wiped            *
 *****************************************************************************/
char** resetGrid(char** grid, Setting settings)
{
    int i, j;
    for ( i = 0; i < settings.height; i++)
    {
        for ( j = 0; j < settings.width; j++)
        {
            grid[i][j] = ' ';
        }
    }

    return grid;
}


/******************************************************************************
 * Method - freeGrid                                                          *
 *                                                                            *
 *                                                                            * 
 * Purpose : Standard freeing of allocated memory | frees the grid basically  *
 *****************************************************************************/
char** freeGrid(char** grid, Setting settings)
{
    int i;
    for(i = 0; i < settings.height; i++)
    {
        free(grid[i]);
    }
    free(grid);

    return grid;
}


/******************************************************************************
 * Method - displaySetings                                                    *
 *                                                                            *
 * Purpose : A nice clean way to show the user the current settings           *
 *****************************************************************************/
void  displaySettings(Setting settings)
{
    printf("\n    Height         : %d",settings.height);
    printf("\n    Width          : %d",settings.width);
    printf("\n    Matching tiles : %d\n\n",settings.matchMin);
}
