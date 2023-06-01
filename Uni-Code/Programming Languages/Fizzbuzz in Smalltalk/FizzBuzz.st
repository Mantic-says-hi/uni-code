#!/usr/bin/gst

output := ''.
1 to: 100 do: [:count|

	(count \\ 15 == 0) ifTrue: 
	[
		output := 'FizzBuzz'.	
	]
	ifFalse: 
	[
		(count \\ 5 == 0) ifTrue: 
		[
			output :=  'Buzz'.
		]
		ifFalse:
		[
			(count \\ 3 == 0) ifTrue: 
			[
				output := 'Fizz'.
			] 
			ifFalse: 
			[
				output := count asString.
			].
		].
	].
	(output) displayNl.
].
