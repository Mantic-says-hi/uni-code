 ##############################################################################
 # Author    : Matthew Matar												  #
 # Date      : 06/10/2019													  #
 # Last Mod  : 04/11/2019													  #
 # Purpose   : Comprehensive Makefile for the assignment which includes		  #
 # 			   conditional compilation 									      #
 # 									   										  #
 # Reference : Part of this code is adapted from the Makefile in the UCP	  #
 # 			   baclboard resoruces, written by Jonathon Winter				  #
 #																			  #
 # Usage     : make 													      #
 # 		     : make SECRET=1												  #	
 # 		     : make EDITOR=1											      #
 # 		     : make SECRET=1 EDITOR=1										  #
 ##############################################################################   			   
CFLAGS = -Wall -ansi -pedantic -Werror -g -std=c89
CC     = gcc
OBJ    = main.o inputFiles.o menus.o controller.o grid.o linkedList.o
EXEC   = TicTacToe

ifdef SECRET
CFLAGS += -D SECRET
endif

ifdef EDITOR
CFLAGS += -D EDITOR
endif

$(EXEC) : $(OBJ)
		  $(CC) $(OBJ) -o  $(EXEC)

all     : all
		  $(CC) -c main.c inputFiles.c menus.c controller.c 
				   grid.c linkedList.c $(CFLAGS)

main    : main.c inputFiles.h controller.h
	      $(CC) -c main.c $(CFLAGS)

inputF  : inputFiles.c inputFiles.h
		  $(CC) -c inputFiles.c $(CFLAGS)

menu    : menus.c menus.h inputFiles.h
		  $(CC) -c menus.c $(CFLAGS)

control : controller.c menus.h inputFiles.h controller.h linkedList.h
		  $(CC) -c controller.c $(CFLAGS)

grid    : grid.c inputFiles.h grid.h controller.h
		  $(CC) -c grid.c $(CFLAGS)

list    : linkedList.c linkedList.h controller.h
		  $(CC) -c linkedList.c $(CFLAGS)

clean   :
		  rm -f $(EXEC) $(OBJ)

softCl  :
		  rm -f $(OBJ)
