public class QueueTest
{
    public static void main(String[] args)
    {
        try
        {
           DSAQueue[] queue = new DSAQueue[2];
            Tester value = new Tester();
            
            //queue object creation
            queue[0] = new DSAQueue();
            queue[1] = new DSAQueue(5);

            //print out created objects
            System.out.println("CONSTRUCTOR TESTS:");
            for(int i = 0; i < queue.length; i++)
            {
                System.out.println("queue[" + i + "] is empty? : " + queue[i].isEmpty());
            }


            for(int i = 0; i < queue.length; i++)
            {
                System.out.println("queue[" + i + "] is full? : " + queue[i].isFull());
            }
            //equals method
            System.out.println("\nEQUALS TEST:");
            System.out.println("Same object expected TRUE: " + queue[0].equals(queue[0]));
            System.out.println("Different object values expected FALSE: " + queue[0].equals(queue[1]));
   
            //getters and setters
            System.out.println("\nADDING, PEEKING AND REMOVING:");

            System.out.println("\nTry to add 8 to the que, with array length of 5.");
            try{
                for(int i = 0; i < 8; i++)
                {
                    queue[1].add(value);
                }
            }
            catch(IllegalArgumentException f)
            {
                System.out.println(f.getMessage());
            }
            System.out.println("Queue count at this point: " + queue[1].getCount());
            System.out.println("Queue is full? : " + queue[1].isFull());


            System.out.println("\nTrying peek, with items test.");
            Tester front = (Tester) queue[1].front();
            System.out.println("Number stored is : " + front.getNumber());

            System.out.println("\nTry to remove 7 with 5 in the queue.");
            try{
                for(int i = 0; i < 7; i++)
                {
                    queue[1].remove();
                }
            }
            catch(IllegalArgumentException f)
            {
                System.out.println(f.getMessage());
            }
           

            System.out.println("Queue count at this point: " + queue[1].getCount());
            System.out.println("Queue is empty? : " + queue[1].isEmpty());
           

            System.out.println("\nTrying peek, with no items test.");
            Tester front2 = (Tester) queue[1].front();

        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}