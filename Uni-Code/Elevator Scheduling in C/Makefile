 ##############################################################################
 # Author    : Matthew Matar												  #
 # Date      : 06/10/2019													  #
 # Last Mod  : 18/05/2020													  #
 # Purpose   : Comprehensive Makefile for the assignment            	      #
 # 									   										  #
 # Reference : Part of this code is adapted from the Makefile in the UCP	  #
 # 			   baclboard resoruces, written by Jonathon Winter				  #
 #																			  #
 ##############################################################################   			   
CFLAGS = -lpthread -Wall -Werror -g
CC     = gcc
OBJ    = lift_sim_A.o linkedList.o
EXEC   = lift_sim_A

$(EXEC) : $(OBJ)
		  $(CC) $(OBJ) -o  $(EXEC) $(CFLAGS)

lifta     : lift_sim_A.c
		  $(CC) -c lift_sim_A.c linkedList.c $(CFLAGS)

clean   :
		  rm -f $(EXEC) $(OBJ)

softCl  :
		  rm -f $(OBJ)
