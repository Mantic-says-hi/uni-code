/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 06/10/2019                                                      *
 * Last Mod : 02/11/2019                                                      *
 * Purpose  : To check validity of file given to start program and give main  *
 *            the values for the settings.                                    *
 *****************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "inputFiles.h"
 
/******************************************************************************
 * Method  - checkFile                                                         *
 *                                                                            *
 * Purpose : Returns a struc with all of the settings retrived from the       *
 *           reading of the file, after all data is validated of course.      *
 *****************************************************************************/
Setting checkFile(char* settings)
{
    FILE* file = fopen(settings, "r");
    char** lines;
    Setting toSend = {0,0,0};

    if (file == NULL)
    {
        printf("\n    ERROR | Could not open [ %s ]", settings);
        printf("\n        Please ensure that the right file name exists\n\n");
    }
    else
    { 
        if (checkLines(file) == 3){
            lines  = retriveLines(file);

            toSend = packLines(lines);
        }
    fclose(file);
    }

    return toSend;
}


/******************************************************************************
 * Method  - checkLines                                                       *
 *                                                                            *
 * Purpose : Returns a count of all lines in the file that are of length 3 or *
 *           more, as this is the minimum length required for valid input.    *
 *****************************************************************************/
int checkLines(FILE* file)
{
    char currentChar,
         spaceCheck = 'c';
    int  lineCount = 0, 
         lengthCheck = 0;


    do { 
        currentChar = fgetc(file);
        lengthCheck++;
        if (currentChar == ' ' )
        {
            spaceCheck = currentChar;
        }
        if (currentChar == '\n')
        {
            if (lengthCheck > 2)
            {
                lineCount++;
            }
            /*End of line reached, reset to 0*/
            lengthCheck = 0;  
        }      
    } while (!feof(file));

    if (spaceCheck == ' ')
    {
        lineCount = 0;
        printf("\n    ERROR: Settings file can not contain spaces \n\n");
    }
    rewind(file);

    return lineCount;   
}


/******************************************************************************
 * Method  - retriveLines
 *
 * Purpose : Returns 3 lines (hopefully M = x, K = x and N = x
 *           There are multiple checks to ensure this;
 *           line length must be 
 *****************************************************************************/
char** retriveLines(FILE* file)
{
    /*Need 3 Strings*/
    char** values = (char**)malloc(3 * sizeof(char*));
    char   processedLine[6] = "";
    int    lineLength, 
           limit = 0,
           i;
    
    for (i = 0; i < 3; i++)
    {
        /*STX|(M/N/K)|=|00|\n| therefore max of 6*/
        values[i] = (char*)malloc(6 * sizeof(char));
    }  

    while (processedLine != NULL && limit < 3) 
    {
        fgets(processedLine, 5, file);
        lineLength = strlen(processedLine);
        if (lineLength >= 3)
        {
            strcpy(values[limit],processedLine);
            limit++;
        }
           
    }
        
    return values; 
}


/******************************************************************************
 * Method - checkEqualsFormat                                                 *
 *                                                                            *
 * Purpose : Checks if the second character in the string is an equals sign.  *
 *           If not then the imported data is not of valid line format        *
 *****************************************************************************/
int checkEqualsFormat(char** data)
{
    int  valid = 0, 
         i; 
 

    for( i = 0; i < 3; i++)
    {
        if(*(data[i]+1) != '=')
        {
            printf("\n    ERROR: Equals sign not found in correct possition");
            printf(" [Found %c]\n",*(data[i]+1));
            valid = 1;
        }   
    }

    return valid;
}


/******************************************************************************
 * Method - packLines                                                         *
 *                                                                            *
 * Purpose : Retrives the number data from the string, by this point the      *
 *           program knows the string starts with '(M/N/K)=' so it just       *
 *           has to figure out the last 1 or 2 characters. By using atoi      *
 *           on the (string+2) this gets only the characters afer the '='.    *
 *           If there is any non-digits then atoi will return 0 unless        *
 *           there was a digit then a non-digit which it will still accept    *
 *           and just ignore the non-digit.                                   *
 *           Also does not pack the data into the Setting if the required     *
 *           numbers / logic is not met.                                      *
 *****************************************************************************/
Setting packLines(char** data)
{
    Setting toPack = {0,0,0};
    int i, temp;

    if (checkDupe(data)         == 0 && 
        checkEqualsFormat(data) == 0)
    {
        for (i = 0; i < 3; i++)
        {
            temp = atoi(data[i]+2);
            if (*(data[i]) == 'M') {   
                if(temp >= 3 && temp <=25){
                    toPack.width = temp;  
                }else{
                    printf("\nWidth (M) must be of value 3 - 25 inclusive\n");
                }
            } 
            else if (*(data[i]) == 'N') {   
                if(temp >= 3 && temp <=25) {
                    toPack.height = temp;  
                }else{
                    printf("\nHeight (N) must be of value 3 - 25 inclusive\n");
                }
            }
            else if (*(data[i]) == 'K') {
                if(temp >= 3 && temp <= 25) { 
                    toPack.matchMin = temp;
                }else{
                    printf("\nMatching tiles (K) has an invalid value\n");
                }
            }   
        }   
        
        if (toPack.width > toPack.height){
            if(toPack.matchMin >= toPack.height && 
               toPack.matchMin <= toPack.width){
            }else{
                printf("\n(K) must be between (N) & (M) for correct logic\n"); 
                toPack.matchMin = 0; 
            } 
        }else{
            if(toPack.matchMin <= toPack.height && 
               toPack.matchMin >= toPack.width){
            }else{
                printf("\n(K) must be between (N) & (M) for correct logic\n");  
                toPack.matchMin = 0;
            }
        }

        if(toPack.width == 0 || toPack.height == 0 || toPack.matchMin == 0)
        {
            toPack.height = 0;
            printf("\n    Error : Invalid format or M/N/K values\n");
        }       
        
    }
    else
    {
        printf("\n    Exiting program on basis of the above problems.\n");
    }
    freeUsed(data);

    return toPack;
}


/******************************************************************************
 * Method - checkDupe                                                         *
 *                                                                            *
 * Purpose : Checks that there are no duplicate (M/N/K) values after          *
 *           converting to uppercase if a lowercase is found. This is done    *
 *           by using the ASCII table numbers.                                *
 *           With my design it only accepts lines that look like              *
 *           'M=5' or 'n=10' where it is either 3 - 4 visible characters long.*
 *           So this is also checked before duplication comparison            *
 *****************************************************************************/
int checkDupe(char** data)
{
    int checker = 0;
    int i;

    /*97(a) - 32 = 65(A)*/
    for ( i = 0; i < 3; i++)
    {
        if(*(data[i])>='a' && *(data[i]) <= 'z')
        {
            *(data[i]) = *(data[i]) - 32;
        }
        if(!((*(data[i]) == 'M') || *(data[i]) == 'N' || *(data[i]) == 'K'))
        {
            printf("\n    ERROR: Incorrect (M,N,K) [Found %c]\n", *(data[i]));
            printf("\n           Please make sure the line length is not");
            printf("\n           larger than 4.  Correct input looks like : ");
            printf("\n               '(M/N/K)=(1-2 Digit number)'\n"); 
            checker = 1;
        }
    }

    if ((*(data[0]) == *(data[1])) || 
        (*(data[0]) == *(data[2])) ||
        (*(data[1]) == *(data[2])))
    {
        printf("\n    ERROR: Duplicate (M,N,K) values\n"); 
        checker = 1;
    }

    return checker;
}


/******************************************************************************
 * Method - freeUsed                                                          *
 *                                                                            *
 * Purpose : Frees the memory I allocated to hols the data from the settings  * 
 *           file reading. This is done just before the Setting struc has been*
 *           sent back to main. And is when I do not need this data anymore   *
 *****************************************************************************/
void freeUsed(char** data)
{
    int i;    

    for (i = 0; i < 3; i++)
    {
        free(data[i]);
    }  
    free(data);
}
