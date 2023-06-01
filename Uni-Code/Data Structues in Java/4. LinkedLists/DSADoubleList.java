public class DSADoubleList
{

    private DSAListNode head;
    private DSAListNode tail;

    //Default
    public DSADoubleList()
    {
        head = null;
        tail = null;
    }

    //Accessor
    public boolean isEmpty()
    {
        return (head == null);
    }

    //Accessor
    public Object peekFirst() throws IllegalArgumentException
    {
        if(isEmpty()){
            throw new IllegalArgumentException();
        }
        return head.getValue();
    }

    //Accessor
    public Object peekLast() throws IllegalArgumentException
    {
        if(isEmpty()){
            throw new IllegalArgumentException();
        }
        return tail.getValue();
    }

    //Mutator
    public void insertFirst(Object newValue)
    {
        DSAListNode newNd;

        newNd = new DSAListNode(newValue);

        if(isEmpty()){
            head = newNd;
            tail = newNd;
        }else{
            newNd.setNext(head);
            head.prev = newNd;
            newNd = head;
        }
    }

    //Mutator
    public void insertLast(Object newValue)
    {
        DSAListNode newNd;

        newNd = new DSAListNode(newValue);

        if(isEmpty()){
            head = newNd;
            tail = newNd;
        }else{
            newNd.setPrev(tail);
            tail.prev = newNd;
            newNd = tail;            
        }
    }

    //Mutator
    public Object removeFirst() throws IllegalArgumentException
    {
        Object nodeValue;

        if(isEmpty()){
            throw new IllegalArgumentException();
        }else{
            nodeValue = head.getValue();
            head = head.getNext();
            head.prev = null;     
        }

        return nodeValue;
    }

    //Mutator
    public Object removeLast() throws IllegalArgumentException
    {
        Object nodeValue;

        if(isEmpty()){
            throw new IllegalArgumentException();
        }else if(head.getNext() == null){
            nodeValue = head.getValue();
            head = null;
        }else{
            nodeValue = tail.getValue();
            tail = tail.getPrev();
            tail.next = null;
        }

        return nodeValue;
    }

    private class DSAListNode
    {   //Private inner class
        public Object value;
        public DSAListNode next;
        public DSAListNode prev;

        //Alternate
        public DSAListNode(Object inValue)
        {
            value = inValue;
            next = null;
            prev = null;
        }

        //Accessor
        public Object getValue()
        {
            return value;
        }

        //Accessor
        public DSAListNode getNext()
        {
            return next;
        }

        //Accessor
        public DSAListNode getPrev()
        {
            return prev;
        }

        //Mutator
        public void setValue(Object inValue)
        {
            value = inValue;
        }

        //Mutator
        public void setNext(DSAListNode newNext)
        {
            next = newNext;
        }

        //Mutator
        public void setPrev(DSAListNode newPrev)
        {
            next = newPrev;
        }
    }
}