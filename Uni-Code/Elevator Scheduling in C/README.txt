Program can be run with the use of the Makefile provided
either by typing on the command line: 

make

OR

make lifta

Alternatively you may also type on the command line:

gcc -o lift_sim_A lift_sim_A.c linkedList.c -lpthread -Wall -Werror -g

And to run the program on the command line type:

./lift_sim_A m t (where m and t are non negative integers and m is greater than 0)

e.g. ./lift_sim_A 5 8


However given the fault with my programming to get it to function as expected you must type in:

valrgind ./lift_sim_A m t