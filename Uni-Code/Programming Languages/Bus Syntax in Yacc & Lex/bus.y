%{
#include <stdio.h>
#include <string.h>

void yyerror(const char *str)
{
    fprintf(stderr, "error: %s\n",str);
}

int yywrap()
{
    return 1;
}

main()
{
    yyparse();
}

%}

%token TURN ENGINE OPERATING TOKSPEED NUMBER
%%
commands : | commands command;

command : turn | engine_switch | engine_operation | setspeed;

turn:   TURN 
        {
            if($1)
                printf("\tBus turning left\n");
            else
                printf("\tBus turning right\n");
        };

engine_switch:  ENGINE
                {
                    if($1)
                        printf("\tEngine is now running\n");
                    else
                        printf("\tEngine is now not running\n");
                };

engine_operation:   OPERATING
                    {
                        if($1)
                            printf("\tEngine is now moving\n");
                        else
                            printf("\tEngine is now stopping\n");
                    };

setspeed:   TOKSPEED NUMBER 
            {
                if(($2 >= 0) && ($2 < 60))
                    printf("\tSpeed set to %d\n", $2);
                else
                    printf("\tCANNOT be set to this speed: %d\n", $2);
            };