class DSAStack
{

    private int count;
    private Object[] stack;
    private static final int DEFAULT_CAPACITY = 100;


    //Default
    public DSAStack()
    {
        stack = new Object[DEFAULT_CAPACITY];
        count = 0;
    }

    //Alternate
    public DSAStack(int maxCapacity)
    {
        stack = new Object[maxCapacity];
        count = 0;
    }

    //Accessors
    public int getCount()
    {
        return count;
    }

    public int getLength()
    {
        return stack.length;
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

        full = (stack.length == count);
  
        return full;
    }

    public Object top() throws IllegalArgumentException
    {
        Object topVal = null;

        if(isEmpty()){
            throw new IllegalArgumentException("Cannot do this, stack is empty.");
        }else{
            topVal = stack[count-1];
        }

        return topVal;
    }

    //Mutators
    public void push(Object value) throws IllegalArgumentException 
    {

        if(isFull()){
            throw new IllegalArgumentException("Cannot add, stack is full.");
        }else{
            stack[count] = value;
            count = count + 1;
        }
        
    }

    public Object pop() 
    {
        Object topVal;

        topVal = top();
        stack[count - 1] = null; //Not necessary but is good to have
        count = count - 1;

        return topVal;

    }

}//end DSAStack class