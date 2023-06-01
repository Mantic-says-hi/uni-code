%{
#include <stdio.h>
#include "y.tab.h"
%}
%%
left|right  yylval=!strcmp(yytext,"left");return TURN;
on|off      yylval=!strcmp(yytext,"on");return ENGINE;
move|stop   yylval=!strcmp(yytext,"move");return OPERATING;
speed       return TOKSPEED;
[0-9]+      yylval=atoi(yytext);return NUMBER;   
\n
[ \t]+
%%