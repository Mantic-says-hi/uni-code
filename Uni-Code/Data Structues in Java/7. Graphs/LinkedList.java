import java.util.Iterator;


public class LinkedList implements Iterable
{
    private ListNode head;
    private int count = 0;

    //Default
    public LinkedList()
    {
        head = null;
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
        Object nodeValue;
        ListNode currNd;

        if(isEmpty()){

            throw new IllegalArgumentException();
        }else{

            currNd = head;
            while(currNd.getNext() != null){
                currNd = currNd.getNext();
            }
            nodeValue = currNd.getValue();
        }

        return nodeValue;
    }


    //Mutator
    public void insertFirst(Object newValue)
    {
        ListNode newNd;

        newNd = new ListNode(newValue);

        if(isEmpty()){

            head = newNd;
        }else{

            newNd.setNext(head);
            head = newNd;
        }

        count++;
    }


    //Mutator
    public void insertLast(Object newValue)
    {
        ListNode newNd, currNd;

        newNd = new ListNode(newValue);

        if(isEmpty()){

            head = newNd;
        }else{

            currNd = head;
            while(currNd.getNext() != null){
                currNd = currNd.getNext();
            }

            currNd.setNext(newNd);
        }
         
        count++;
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
        }

        count--;

        return nodeValue;
    }


    //Mutator
    public Object removeLast() throws IllegalArgumentException
    {
        Object nodeValue;
        ListNode prevNd, currNd;

        if(isEmpty()){

            throw new IllegalArgumentException();
        }else if(head.getNext() == null){

            nodeValue = head.getValue();
            head = null;
        }else{

            prevNd = null;
            currNd = head;
            while(currNd.getNext() != null){
                prevNd = currNd;
                currNd = currNd.getNext();
            }
            prevNd.setNext(null);
            nodeValue = currNd.getValue();
        }

        count--;

        return nodeValue;
    }


    //Iterator
    public Iterator iterator()
    {
        return new DSALinkedListIterator(this);
    }


   

    private class DSALinkedListIterator implements Iterator
    {
        public ListNode iterNext;

        public DSALinkedListIterator(LinkedList theList)
        {
            iterNext = theList.head;
        }

        public boolean hasNext()
        {
            return (iterNext != null);
        }


        public Object next()
        {
            Object value;
            if(iterNext == null){
                value = null;
            }else{
                value = iterNext.getValue();
                iterNext = iterNext.getNext();
            }

            return value;
        }

        public void remove()
        {
            throw new UnsupportedOperationException("Not supported");
        }

    }
}