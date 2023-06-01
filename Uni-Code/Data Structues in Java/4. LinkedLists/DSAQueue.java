abstract public class DSAQueue<E>
{

    private DSALinkedList<E> queue;

    //Default
    public DSAQueue()
    {
        queue = new DSALinkedList<E>();
    }

    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    public E peekFirst() throws IllegalArgumentException
    {
        return queue.peekFirst();
    }

    //Mutators
    public void insertLast(E value)
    {
    }

    public E removeFirst() 
    {          
        return queue.removeFirst();
    }

}//end DSAqueue class