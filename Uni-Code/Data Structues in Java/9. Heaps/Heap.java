public class Heap
{
    private HeapEntry[] heap;
    private int count; 

    public Heap(int maxSize)
    {
        heap = new HeapEntry[maxSize];
        count = 0;

    }

    public void add(int priority, Object value)
    {
        HeapEntry entry = new HeapEntry(priority, value);
        heap[nextNull()] = entry;
        count++;
        up(count - 1);
    }

    public HeapEntry remove()
    {
        HeapEntry out = heap[0];
        heap[0] = heap[count - 1];
        heap[count - 1] = null;
        count--;

        heapify();
        return out;
    }

    public HeapEntry[] heapify()
    {
        for(int i = ((count / 2) - 1); i >= 0; i--){
            down(heap, i, count);
        }
        return heap;
    }


    public void heapSort()
    {
        heapify();
        for(int i = count - 1; i >= 1; i--){
            heap = swap(heap, 0, i);
            down(heap, 0, i);
        }
    }

    public HeapEntry[] swap(HeapEntry[] heapAr, int largeIndex, int index)
    {
        HeapEntry temp;
        temp = heapAr[largeIndex];
        heapAr[largeIndex] = heapAr[index];
        heapAr[index] = temp;
        return heapAr;
    }

    public void up(int index)
    {
        heap = upRecurse(heap, index);
    }

    public HeapEntry[] upRecurse(HeapEntry[] heapAr, int current)
    {
        int parent = ((current - 1 )/ 2);
        HeapEntry temp;
        if(current > 0){
            if(heapAr[current].getPriority() > heapAr[parent].getPriority()){
                temp = heapAr[parent];
                heapAr[parent] = heapAr[current];
                heapAr[current] = temp;
                upRecurse(heapAr, parent);
            }
        }
        return heapAr;
    }

    public void down(HeapEntry[] heapAr, int index, int numItems)
    {
        heap = downRecurse(heapAr, index, numItems);
    }

    public HeapEntry[] downRecurse(HeapEntry[] heapAr, int current, int numItems)
    {
        int leftChild, rightChild, largeIndex;
        leftChild = (current * 2) + 1;
        rightChild = leftChild + 1;

        if(leftChild < numItems){
            largeIndex = leftChild;
            if(rightChild < numItems){
                if(heapAr[leftChild].getPriority() < heapAr[rightChild].getPriority()){
                    largeIndex = rightChild;
                }
            }
            if(heapAr[largeIndex].getPriority() > heapAr[current].getPriority()){
                swap(heapAr, largeIndex, current);
                downRecurse(heapAr, largeIndex, numItems);
            }
        }
        return heapAr;
    }

    public int nextNull()
    {
        int i = 0;
        while(i < heap.length && heap[i] != null)
        {
            i++;
        }
        return i;
    }

    public int getCount()
    {
        return count;
    }

    public HeapEntry[] getArray()
    {
        return heap;
    }
}