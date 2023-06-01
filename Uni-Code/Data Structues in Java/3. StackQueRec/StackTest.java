public class StackTest
{
    public static void main(String[] args)
    {
        try
        {
            DSAStack[] stack = new DSAStack[2];
            Tester value = new Tester();
            
            //Stack object creation
            stack[0] = new DSAStack();
            stack[1] = new DSAStack(5);

            //print out created objects
            System.out.println("CONSTRUCTOR TESTS:");
            for(int i = 0; i < stack.length; i++)
            {
                System.out.println("stack[" + i + "] is empty? : " + stack[i].isEmpty());
            }

            //System.out.println("stack length: " + stack[1].getStackLength());

            for(int i = 0; i < stack.length; i++)
            {
                System.out.println("stack[" + i + "] is full? : " + stack[i].isFull());
            }
            //equals method
            System.out.println("\nEQUALS TEST:");
            System.out.println("Same object expected TRUE: " + stack[0].equals(stack[0]));
            System.out.println("Different object values expected FALSE: " + stack[0].equals(stack[1]));
   
            //getters and setters
            System.out.println("\nPUSHING, TOP AND POPPING:");

            System.out.println("\nTry to push 8 with array length of 5.");
            try{
                for(int i = 0; i < 8; i++)
                {
                    stack[1].push(value);
                }
            }
            catch(IllegalArgumentException f)
            {
                System.out.println(f.getMessage());
            }
            System.out.println("Stack count at this point: " + stack[1].getCount());
            System.out.println("Stack is full? : " + stack[1].isFull());


            System.out.println("\nTrying top, with items test.");
            Tester top = (Tester) stack[1].top();
            System.out.println("Number stored is : " + top.getNumber());

            System.out.println("\nTry to pop 7 with 5 in the stack.");
            try{
                for(int i = 0; i < 7; i++)
                {
                    stack[1].pop();
                }
            }
            catch(IllegalArgumentException f)
            {
                System.out.println(f.getMessage());
            }
           

            System.out.println("Stack count at this point: " + stack[1].getCount());
            System.out.println("Stack is empty? : " + stack[1].isEmpty());
           
            System.out.println("\nTrying top, with no items test.");
            Tester top2 = (Tester) stack[1].top();

        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}