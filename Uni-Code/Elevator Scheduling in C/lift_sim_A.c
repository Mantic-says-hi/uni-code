/******************************************************************************
 * Author   : Matthew Matar                                                   *
 * Date     : 13 / 04 / 2020                                                  *
 * last Mod : 18 / 05 / 2020                                                  *         
 * Purpose  : Linked list for logs                                            *
 *****************************************************************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>
#include <unistd.h>
#include "lift_sim_A.h"
#include "linkedList.h"
static int reqNumber = 0, moveNumber = 0;
int TIME = 0, BUFF_MAX = 0;
static pthread_mutex_t key = PTHREAD_MUTEX_INITIALIZER;
static pthread_cond_t notFull = PTHREAD_COND_INITIALIZER;
static pthread_cond_t notEmpty = PTHREAD_COND_INITIALIZER;
bool JOB_COMPLETIONR = false;
bool JOB_COMPLETIONL = false;
LinkedList* buffer = NULL;

int main(int argc, char* argv[])
{
  
    /*must be three arguments | executable | m value | t value|*/
    if(argc == 3 && (atoi(argv[1]) > 0 && atoi(argv[2]) > 0))
    {

        TIME = atoi(argv[2]);
        BUFF_MAX = atoi(argv[1]);
        JOB_COMPLETIONR = false;
        createFile();
        buffer = createLinkedList();/*First in first out | Insert start | Remove Last*/
        pthread_t *thread = malloc(sizeof(pthread_t)*4);  
        createThreads(thread);
        printEnd();
        freeLinkedList(buffer);
        free(thread);
    }
    else
    {
        printf("Invalid starting arguments.\n");
    }

    return 0;
}

/*****************************************************************************
 * Method - printEnd                                                         *
 *                                                                           *
 * Purpose : Prints final lines to the file, with the request and movement   *
 *              informations                                                 *
 *****************************************************************************/
void printEnd()
{
    FILE* fp;  
    fp = fopen("sim_out.txt","a");    
    fprintf(fp,"\n\nTotal number of requests: %d", reqNumber);
    fprintf(fp,"\nTotal number of movements: %d", moveNumber);
    fclose(fp);
}

/*****************************************************************************
 * Method - request                                                          *
 *                                                                           *
 * Purpose : Is the method that the LiftR thread runs for its operation      *
 *****************************************************************************/
void* request()
{
    srand(time(NULL));
    int until = ((rand()%51) + 50);
    /*Random number n between 50 and 100*/

    while(!JOB_COMPLETIONR){
        pthread_mutex_lock(&key);
        while(isFull(buffer, BUFF_MAX))
        {
           pthread_cond_wait(&notFull,&key);
        }
        reqNumber++;
        FILE* input = fopen("sim_input.txt", "r");
        Request* data = (Request*)malloc(sizeof(Request));/*This will be freed after lift removes it from the list*/
        /*Keep getting the next line until the number of requests has been reached*/
        if(reqNumber <= until){
            for(int i = 0; i<reqNumber; i++){
                fscanf(input, "%d %d", &data->start, &data->end);
        }
        fclose(input);}
        insertStart(buffer,data);
        reqPrint(data);
    
        if(reqNumber == until){
            JOB_COMPLETIONR = true;
        }
    
        pthread_cond_signal(&notEmpty);
        pthread_mutex_unlock(&key);    
        

     }
    
    return NULL;
}

/*****************************************************************************
 * Method - reqPrint                                                         *
 *                                                                           *
 * Purpose : Prints a request that was read from the input file              *
 *****************************************************************************/
void reqPrint(void* value)
{
    Request* data = (Request*) value;

    FILE* fp;  
    fp = fopen("sim_out.txt","a");    
    fprintf(fp,"\n-----------------------------------" );
    fprintf(fp,"\nNew Lift Request From Floor %d to Floor %d", data->start, data->end);
    fprintf(fp,"\nRequest No: %d", reqNumber);
    fprintf(fp,"\n-----------------------------------" );
    fclose(fp);
}


/*****************************************************************************
 * Method - lift                                                             *
 *                                                                           *
 * Purpose : Is the method that the Lift(1-3) threads run                    *
 *****************************************************************************/
void* lift(void* value)
{  
    while(!JOB_COMPLETIONL){
        pthread_mutex_lock(&key);
        while(isEmpty(buffer))
        {
            pthread_cond_wait(&notEmpty,&key);
        }
        Lift* data = (Lift*) value;
        Request* entry = (Request*) removeLast(buffer);
        data->req = *entry;
        data->floorPrev = data->floorCurr;
        data->floorCurr = entry->end;
        data->numReq++;
        liftPrint(data);
        free(entry);
        if(JOB_COMPLETIONR && isEmpty(buffer)){
            /*if request job has completed and the buffer is empty then lift should complete*/
            JOB_COMPLETIONL = true;
        }
        pthread_cond_signal(&notFull);
        pthread_mutex_unlock(&key);
        sleep(TIME);
        
    }
    
    return NULL;
}

/*****************************************************************************
 * Method - liftPrint                                                        *
 *                                                                           *
 * Purpose : Prints a detailed event, from the infromation in the struct     *
 *****************************************************************************/
void liftPrint(void* value)
{
    Lift* data = (Lift*) value;
    int movement = abs(data->floorPrev - data->req.start) + abs(data->req.start - data->req.end);
    data->total = data->total + movement;
    moveNumber += movement;
    FILE* fp;
    fp = fopen("sim_out.txt","a");    
    fprintf(fp,"\n\nLift-%d Operation", data->liftID);
    fprintf(fp,"\nPrevious position: Floor %d", data->floorPrev);
    fprintf(fp,"\nRequest: Floor %d to Floor %d", data->req.start, data->req.end);
    fprintf(fp,"\nDetail operations:");
    fprintf(fp,"\n    Go from Floor %d to Floor %d", data->floorPrev, data->req.start);
    fprintf(fp,"\n    Go from Floor %d to Floor %d", data->req.start, data->req.end);
    fprintf(fp,"\n    #movement for this request: %d", movement);
    fprintf(fp,"\n    #request %d", data->numReq);
    fprintf(fp,"\n    Total #movement %d", data->total);
    fprintf(fp,"\nCurrent positon: Floor %d", data->floorCurr);
    fclose(fp);
}


/*****************************************************************************
 * Method - createThreads                                                    *
 *                                                                           *
 * Purpose : Creates the request and lift threads, request needs no value
 *           however each lift needs their own structs with the individual
 *           lift information                                                *
 *****************************************************************************/
void createThreads(pthread_t* thread)
{
    int i;
    Lift liftInfo[3];
        

    pthread_create(&thread[0],NULL,request, NULL);  

    for(i=0; i < 3; i++){
        
        liftInfo[i].liftID = i+1;/*Lift ID will be 1 - 3*/
        liftInfo[i].floorCurr = 1;
        liftInfo[i].floorPrev = 1;
        liftInfo[i].numReq = 0;
        liftInfo[i].total = 0;
        
        pthread_create(&thread[i+1],NULL,lift, &liftInfo[i]);

    }
        
    for(i=0; i < 4; i++){
        pthread_join(thread[i],NULL);
    }
}

/*****************************************************************************
 * Method - createFile                                                       *
 *                                                                           *
 * Purpose : Creates the output file that the threads print to               *
 *****************************************************************************/
void createFile()
{
    FILE* output;            
    output = fopen("sim_out.txt","w");
    fclose(output);
}
