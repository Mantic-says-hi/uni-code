class DSAQueue
{

    private int count;
    private Object[] queue;
    private static final int DEFAULT_CAPACITY = 100;


    //Default
    public DSAQueue()
    {
        queue = new Object[DEFAULT_CAPACITY];
        count = 0;
    }

    //Alternate
    public DSAQueue(int maxCapacity)
    {
        queue = new Object[maxCapacity];
        count = 0;
    }

    //Accessors
    public int getCount()
    {
        return count;
    }

    public boolean isEmpty()
    {
        boolean empty;

        empty = (count == 0);
        return empty;
    }

    public boolean isFull()
    {
        boolean full;

        full = (count == queue.length);
        return full;
    }


    //Mutators
    public Object front() throws IllegalArgumentException
    {
        Object frontVal;
        if(isEmpty()){
            throw new IllegalArgumentException("Cannot peek, queue empty.");
        }else{

            frontVal = queue[0];
        }

        return frontVal;

    }

    public void add(Object value) throws IllegalArgumentException
    {
        if(isFull()){
            throw new IllegalArgumentException("Cannot add, queue is full.");
        }else{
            //Queue at count will be the next null
            queue[count] = value;
            count = count + 1;
        }

    }

    public Object remove() throws IllegalArgumentException
    {
        Object frontVal;
        
        if(isEmpty()){
            throw new IllegalArgumentException("Cannot remove, queue is empty.");
        }else{
            frontVal = queue[0]; //First item added, first gone

            for(int i =0; i < (count - 1); i++)
            {
                queue[i] = queue[i + 1];
            }
            
            queue[count - 1] = null;
            count = count - 1;
        }

        return frontVal;

    }


}