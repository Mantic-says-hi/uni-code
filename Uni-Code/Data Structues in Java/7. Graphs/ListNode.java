public class ListNode 
{    //Private inner class
    public Object value;
    public ListNode next;
        
    //Alternate
    public ListNode(Object inValue)
    {
        value = inValue;
        next = null;
    }

    //Accessor
    public Object getValue()
    {
        return value;
    }


    //Accessor
    public ListNode getNext()
    {
        return next;
    }


    //Mutator
    public void setValue(Object inValue)
    {
        value = inValue;
    }


    //Mutator
    public void setNext(ListNode newNext)
    {
        next = newNext;
    }
}
