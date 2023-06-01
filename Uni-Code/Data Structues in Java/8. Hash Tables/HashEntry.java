public class HashEntry
{
    public String key;
    public Object value;
    public int state;

    public HashEntry()
    {
        key = "";
        value = null;
        state = 0;
    }

    public HashEntry(String inKey, Object inValue)
    {
        key = inKey;
        value = inValue;
        state = 0;
    }

    public String getKey()
    {
        return key;
    }

    public Object getValue()
    {
        return value;
    }

    public void setState(int inState)
    {
        if(inState >= 0 && inState <= 3){
            state = inState;
        }
    }

    public void setValue(Object inObject)
    {
        value = inObject;
    }

    public void setKey(String inKey)
    {
        key = inKey;
    }
}