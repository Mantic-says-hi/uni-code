/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Data     : 06/10/2019                                                      *
 * Last Mod : 04/11/2019                                                      *
 * Purpose  : Setting up the whole program and letting it go                  *
 *****************************************************************************/
#include <stdio.h>
#include <string.h>
#include "inputFiles.h"
#include "controller.h"     

/******************************************************************************
 * Method - main                                                              *
 *                                                                            *
 * Purpose : Checks the args for correct value and a file name with .txt to   *
 *           start reading to start the program                               *
 *****************************************************************************/
int main(int argc, char* argv[])
{
    Setting set;
    if (argc == 2 && 
        strstr(argv[1],".txt") != NULL)
    {
        set = checkFile(argv[1]);
        if(set.height != 0)
        {
            launchGame(set); 
        }   
        else
        {
            printf("\n    [Program is now exiting]\n");
        }
    }   
    else 
    {
        printf("\n        INVALID argument or number of agruments\n");
        printf("\n            Please ensure you include '.txt' with");
        printf("\n            the file name of your settings\n\n");
    }

    return 0 ;
}
