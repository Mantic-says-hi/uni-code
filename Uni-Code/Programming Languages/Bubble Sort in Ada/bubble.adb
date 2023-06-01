with Ada.Text_IO;
use Ada.Text_IO;


procedure Bubble is 
    type My_Int_Array is array (0 .. 10) of integer;
    Sorted : Boolean      := FALSE;
    K      : integer      := 0;
    Temp   : integer;
    Arr    : My_Int_Array := (3,7,1,9,0,8,2,5,4,6,10);
    function Print (Var : My_Int_Array) return integer;
    function Print (Var : My_Int_Array) return integer
    is
    begin
        for I in 0 .. 10 loop  
        Put(integer'Image (Arr(I)));
	    if I/=10 then 
            Put(","); 
	    end if;
        end loop;
        put(" }");
        New_Line;
        return 0;
    end Print;
begin
    
    Put("Array to sort: {");
    Temp := Print(Arr);

    while not Sorted loop
	Sorted := TRUE;
	for J in 0 .. (9-K) loop
	    if(Arr(J) > Arr((J+1))) then
	        Temp := Arr(J);
		Arr(J) := Arr(J+1);
		Arr(J+1) := temp;
		Sorted := FALSE;
	    end if;
	end loop;
	K := K + 1;
    end loop;
    
    Put("Sorted  array: {");
    K :=  Print(Arr);

end Bubble;