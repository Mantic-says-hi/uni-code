import java.util.Iterator;
public class DSALinkedListS<E>
extends DSAStack<E> 
implements Iterable<E> 
{

    private DSAListNode<E> head;

    //Default
    public DSALinkedListS()
    {
        head = null;
    }

    //Accessor
    public boolean isEmpty()
    {
        return (head == null);
    }

    //Accessor
    public E peekFirst() throws IllegalArgumentException
    {
        if(isEmpty()){

            throw new IllegalArgumentException();
        }
        return head.getValue();
    }

    //Accessor
    public E peekLast() throws IllegalArgumentException
    {
        E nodeValue;
        DSAListNode<E> currNd;

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
    public void insertFirst(E newValue)
    {
        DSAListNode<E> newNd;

        newNd = new DSAListNode<E>(newValue);

        if(isEmpty()){

            head = newNd;
        }else{

            newNd.setNext(head);
            head = newNd;
        }
    }

    //Mutator
    public void insertLast(E newValue)
    {
        DSAListNode<E> newNd, currNd;

        newNd = new DSAListNode<E>(newValue);

        if(isEmpty()){

            head = newNd;
        }else{
            currNd = head;
            while(currNd.getNext() != null){
                currNd = currNd.getNext();
            }

            currNd.setNext(newNd);
        }
    }

    //Mutator
    public E removeFirst() throws IllegalArgumentException
    {
        E nodeValue;

        if(isEmpty()){
            throw new IllegalArgumentException();
        }else{
            nodeValue = head.getValue();
            head = head.getNext();
        }

        return nodeValue;
    }

    //Mutator
    public E removeLast() throws IllegalArgumentException
    {
        E nodeValue;
        DSAListNode<E> prevNd, currNd;

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

        return nodeValue;
    }

    //Iterator
    public Iterator<E> iterator()
    {
        return new DSALinkedListIterator<E>(this);
    }

    private class DSAListNode<E>
    {    //Private inner class
        public E value;
        public DSAListNode<E> next;
            
        //Alternate
        public DSAListNode(E inValue)
        {
            value = inValue;
            next = null;
        }

        //Accessor
        public E getValue()
        {
            return value;
        }

        //Accessor
        public DSAListNode<E> getNext()
        {
            return next;
        }

        //Mutator
        public void setValue(E inValue)
        {
            value = inValue;
        }

        //Mutator
        public void setNext(DSAListNode<E> newNext)
        {
            next = newNext;
        }
    }

    private class DSALinkedListIterator<E> implements Iterator<E>
    {
        public DSALinkedListS<E>.DSAListNode<E> iterNext;

        public DSALinkedListIterator(DSALinkedListS<E> theList)
        {
            iterNext = theList.head;
        }

        public boolean hasNext()
        {
            return (iterNext != null);
        }

        public E next()
        {
            E value;
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