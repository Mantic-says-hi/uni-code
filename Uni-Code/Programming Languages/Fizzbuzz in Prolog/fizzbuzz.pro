%Entry Rules

fizzbuzz(X) :- X < 1001, X > 0,recursebuzz(1,X).


%Output Rules

output(X) :- 0 is X mod 15, print('FizzBuzz'),nl.
output(X) :- 0 is X mod 5, print('Buzz'),nl.
output(X) :- 0 is X mod 3, print('Fizz'),nl.
output(X) :- print(X),nl.


%Recursive Rules

%Base Case

recursebuzz(R,X) :- R > X.

%Recursive Case

recursebuzz(R,X) :-
    RE is R+1,
    output(R),recursebuzz(RE,X).
