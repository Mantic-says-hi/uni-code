public class HeapEntry
{
    public int priority;
    public Object value;

    public HeapEntry(int inPriority, Object inValue)
    {
        priority = inPriority;
        value = inValue;
    }

    public int getPriority()
    {
        return priority;
    }

    public Object getValue()
    {
        return value;
    }
}