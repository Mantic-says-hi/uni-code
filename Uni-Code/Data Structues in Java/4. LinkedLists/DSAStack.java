abstract public class DSAStack<E>
{

    private DSALinkedList<E> stack;

    //Default
    public DSAStack()
    {
        stack = new DSALinkedList<E>();

    }

    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    public E peekFirst() throws IllegalArgumentException
    {
        return stack.peekFirst();
    }

    //Mutators
    public void insertLast(E value)
    {
    }

    public E removeLast() 
    {          
        return stack.removeLast();
    }

}//end DSAStack class