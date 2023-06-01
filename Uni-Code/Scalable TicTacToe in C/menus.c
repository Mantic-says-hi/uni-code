/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 02/11/2019                                                      *
 * Last Mod : 04/11/2019                                                      *
 * Purpose  : To handle menu's and their upper and lower limits to now allow  *
 *            the user to enter a wrong input                                 *
 *****************************************************************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "menus.h"
#include "inputFiles.h"

/******************************************************************************
 * Method - menuSelection                                                     *
 *                                                                            *
 * Purpose : Handles which menu to show when given a menuCode(String)         *
 *           not used it as much as I had hoped because limited menu screens  *
 *****************************************************************************/
int menuSelection(char* menuCode)
{
    int selection = 0;

    if(strcmp(menuCode,"MAIN") == 0){
        selection = mainMenu();
    }else if(strcmp(menuCode,"EDSET") == 0){
        selection = edit();
    }

    return selection;
}

/******************************************************************************
 * Method - edit                                                              *
 *                                                                            *
 * Purpose : Makes the user double check if they really want to edit the      *
 *           settings, incase they did it on accident and they dont want to   *
 *****************************************************************************/
int edit()
{
    int selection = 0;
    char userInput[20] = " ";/*Reason for this same as mainMenu*/

    printf("\nAre you sure you want to edit the settings?\n");
    printf("\n1: Yes\n");
    printf("2: No\n");
    do {
        fgets(userInput,20,stdin);
        selection = atoi(userInput);
        if(!(selection >= 1 && selection <= 2)){
            printf("\nPlease enter between 1 and 2 : ");
        }
    }while(!(selection >= 1 && selection <= 2));
    
    return selection;
}

/******************************************************************************
 * Method - editSettings                                                      *
 *                                                                            *
 * Purpose : If the user does want to edit the settings then this method      *
 *           handles that by making sure they enter in the correct values     *
 *           for that                                                         *
 *****************************************************************************/
Setting editSettings(Setting set)
{
    int newWidth, newHeight, newMatch;
    char userInput[20] = " ";

    printf("\nEnter new height [is currently %d] : ",set.height);
    do {
        fgets(userInput,20,stdin);
        newHeight = atoi(userInput);
        if(!(newHeight >= 3 && newHeight <= 25)){
            printf("\nPlease enter between 3 and 25 : ");
        }
    }while(!(newHeight >= 3 && newHeight <= 25));

    printf("\nEnter new width [is currently %d] : ",set.width);
    do {
        fgets(userInput,20,stdin);
        newWidth = atoi(userInput);
        if(!(newWidth >= 3 && newWidth <= 25)){
            printf("\nPlease enter between 3 and 25 : ");
        }
    }while(!(newWidth >= 3 && newWidth <= 25));

    printf("\nEnter new matching tiles [is currently %d] : ",set.matchMin);
    if(newWidth > newHeight){
        do {
            fgets(userInput,20,stdin);
            newMatch = atoi(userInput);

            if(!(newMatch >= newHeight && newMatch <= newWidth)){
                printf("\nPlease enter between %d and %d : ",newHeight,
                                                             newWidth);
            }
        }while(!(newMatch >= newHeight && newMatch <= newWidth));
    }else if(newHeight >= newWidth){
        do {
            fgets(userInput,20,stdin);
            newMatch = atoi(userInput);

            if(!(newMatch >= newWidth && newMatch <= newHeight)){
                printf("\nPlease enter between %d and %d : ",newWidth,
                                                             newHeight);
            }
        }while(!(newMatch >= newWidth && newMatch <= newHeight));
    }
    /*This whole bit was a pain and yes there is a lot of repeated code
     *However it would have not done much to improve things. Since I would
     *have had to pass the strings for 'width' and 'height' and 
     *'matching tiles' as well as the ints for the logic behind choosing
     *a new matching tiles value*/

    set.height = newHeight;
    set.width = newWidth;
    set.matchMin = newMatch;

    return set;
}

/******************************************************************************
 * Method - mainMenu                                                          *
 *                                                                            *
 * Purpose : Very thorughly thought out process to accout for normal,         *
 *           SECRET, EDITOR and SECRET+EDITOR conditional compilations.       *
 *           Displays correct menu options to user because of this            *
 *****************************************************************************/
int mainMenu()
{
    int selection = 0, upper = 5, lower = 1;
    char userInput[20] = "  ";/*Only need it to be 2 but it is 20 beacaue
                                I dont expect the user to enter more than
                                20 chars but I do expect them to go slighty
                                over 2 on accident for example*/
    
    #ifdef SECRET
    upper = 4;
    #endif
    #ifdef EDITOR
    upper = 6;
    #endif
    #if defined(SECRET) && defined(EDITOR)
    upper = 5;
    #endif
    printf("\n%d: Start new game\n", lower);
    printf("%d: View the settings of the game\n", (lower + 1));
    printf("%d: View the current logs\n", (lower + 2));
    printf("%d: Save the logs to a file\n", (lower + 3));
    #ifdef SECRET
    for(selection = 0; selection < 117; selection++){/*I dont know if this is*/
        printf("\b");                                /*a good way of doing it*/
    }                                                /*But it works and it's*/
    #endif                                           /*cool, so I like it*/
    #if defined(EDITOR) && !defined(SECRET)
    printf("%d: Edit the settings of the game\n",(lower + 4));
    #endif
    #if defined(SECRET) && defined(EDITOR)
    printf("%d: Edit the settings of the game\n",(lower + 3));
    #endif

    printf("%d: Exit the application                  \n", upper);
    printf("\nSELECTION : ");
    do {
        fgets(userInput,20,stdin);
        selection = atoi(userInput);
        if(!(selection >= lower && selection <= upper)){
            printf("\nPlease enter between %d and %d : ",lower, upper);
        }
    }while(!(selection >= lower && selection <= upper));

    return selection;
}
