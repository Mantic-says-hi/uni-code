/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 13 / 04/ 2020                                                   *
 * last Mod : 18 / 05 / 2020                                                  *         
 * Purpose  : Linked list for logs                                            *
 *****************************************************************************/
#ifndef LIFT_SIM_A_H
#define LIFT_SIM_A_H

/******************************************************************************
 * Struct - Request                                                           *
 *                                                                            *
 * Purpose : Is a place to store thestart and finish of a request when read   *
 *           from an input file                                               *
 *****************************************************************************/
typedef struct Request{
    int start;
    int end;
}Request;

/******************************************************************************
 * Struct - Lift                                                              *
 *                                                                            *
 * Purpose : Holds every peice of data needed by a lift thread to successfully*
 *           print out to file                                                *
 *****************************************************************************/
typedef struct Lift{
    int liftID;
    int floorCurr;
    int floorPrev;
    int numReq;
    int total;
    Request req;
}Lift;

void printEnd();

void* request();

void reqPrint(void* value);

void* lift(void* value);

void liftPrint(void* value);

void createThreads(pthread_t* thread);

void createFile();
#endif
