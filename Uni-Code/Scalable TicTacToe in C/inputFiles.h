/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 06/10/2019                                                      *
 * Last Mod : 06/10/2019                                                      *
 * Purpose  : Header file for the inputFiles file and definition of Setting   *
 *************************************************************************** */
#ifndef INPUTFILES_H
#define INPUTFILES_H

/******************************************************************************
 * Struct - Setting                                                           *
 *                                                                            *
 * Purpose : Once file has been read all three settings are packaged into     *
 *           this struct and sent back to the main method to continure        *  
 *           with the progress of the program's operations                    *
 *****************************************************************************/
typedef struct {
    int width;
    int height;
    int matchMin;
} Setting;
 

Setting  checkFile(char* fileName);

int      checkLines(FILE* file);

char**   retriveLines(FILE* file);

Setting  packLines(char** lines);

int      checkEqualsFormat(char** data);

int      checkDupe(char** data);

void     freeUsed(char** data);
#endif
