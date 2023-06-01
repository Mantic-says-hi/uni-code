public class TestPut
{

    public static void main(String[] args)
    {
        HashTable ht = new HashTable(50);

        Object f;
        
        ht.put("159906", "Name Jeff");
        ht.put("159906", "Name Apple");
        ht.put("169906", "Name Jeff");
        ht.put("6", "Name Apple");
        ht.put("1", "Name Jeff");
        f = ht.get("159906");

        System.out.println(((HashEntry)f).getValue());
        f = ht.get("6");
        System.out.println(((HashEntry)f).getValue());
        
        ht.remove("1");
        ht.get("1");
    }
}