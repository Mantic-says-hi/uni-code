README

program can be started in the following way on the command line: 

>gradlew run --args=""

where the args you can put will need to have a space between them and are limited to...

-r : Read from file

-w : Write to file

-d : Display full tree and power values

-g : Generate tree randomly



-r and -w need to have a specified file name in .txt or .csv form one space after, i.e (>gradlew run --args="-r file1.txt -w file2.csv")



-r and -g both create a new tree and THUS CANNOT BE USED TOGETHER

-d and -w both display an existing tree and THUS CANNOT BE USED TOGETHER




Therefore there are only 3 possible argument lengths

2 : -g and -d (in any order)
3 : -g with -w + filename OR -d with -r + filename (in any order, while having filename after -r/-w)
4 : -r + filename with -w + filename (in any order, while having filename after -r/-w)

JDK Used : 11.0.9
