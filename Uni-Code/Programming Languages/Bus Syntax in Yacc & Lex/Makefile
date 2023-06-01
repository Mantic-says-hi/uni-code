bus: bus.l bus.y
	lex bus.l
	yacc -d bus.y
	gcc lex.yy.c y.tab.c -o bus
	rm lex.yy.c y.tab.c y.tab.h

clean:  bus
	rm bus
