import java.util.Arrays;

public class HashTable
{
    private HashEntry[] hashTable;
    private int count;
    private double factor = 0.6;
    
    public HashTable()    
    {
        int tableSize = 500, actualSize = findNextPrime(tableSize);
        hashTable = new HashEntry[actualSize]; 
        for(int i = 0; i < actualSize - 1; i++){
            hashTable[i] = null;
        }
        count = 0;
    }


    public HashTable(int tableSize)    
    {
        int actualSize = findNextPrime(tableSize);
        hashTable = new HashEntry[actualSize]; 
        for(int i = 0; i < actualSize - 1; i++){
            hashTable[i] = null;
        }
        count = 0;
    }

   


    public void put(String key, Object value)
    {
        HashEntry entry = new HashEntry(key, value);
        int place = hash(key), i = place + 1;
        boolean added = false;


        if(!duplicate(key)){
            if(hashTable[place] == null){
                hashTable[place] = entry;
                added = true;
                entry.setState(1);
            }else{
                while(!added && i < hashTable.length){
                    if(hashTable[i] == null){
                        hashTable[i] = entry;
                        added = true;
                        entry.setState(1);
                    }
                    i++;
                }
                i = 0;
                while(!added && i < place - 1){
                    if(hashTable[i] == null){
                        hashTable[i] = entry;
                        added = true;
                        entry.setState(1);
                    }
                    i++;
                }
            }
            count++;
            if(((double) count / (double) hashTable.length) >= factor)
            {
                upSize(hashTable.length * 2);
            }
        }else{
            System.out.println("Ignoring duplicate key: " + key);
        }
    }


    private void put(HashEntry entry)
    {
        String key = entry.getKey();
        int place = hash(key), i = place + 1;
        boolean added = false;


        if(!duplicate(key)){
            if(hashTable[place] == null){
                hashTable[place] = entry;
                added = true;
                entry.setState(1);
            }else{
                while(!added && i < hashTable.length){
                    if(hashTable[i] == null){
                        hashTable[i] = entry;
                        added = true;
                        entry.setState(1);
                    }
                    i++;
                }
                i = 0;
                while(!added && i < place - 1){
                    if(hashTable[i] == null){
                        hashTable[i] = entry;
                        added = true;
                        entry.setState(1);
                    }
                    i++;
                }
            }
            count++;
            
            if(((double) count / (double) hashTable.length) >= factor)
            {
                upSize(hashTable.length * 2);
            }
        }else{
            System.out.println("Ignoring duplicate key: " + key);
        }
    }


    public Object get(String key)
    {
        HashEntry hashEntry = new HashEntry();
        int place = hash(key), i = place + 1;
        boolean got = false;


        if(hashTable[place].getKey().equals(key)){
            hashEntry = hashTable[place];
            got = true;
        }else{
            while(!got && i < hashTable.length){
                if(hashTable[i] != null){
                    if(hashTable[i].getKey().equals(key)){
                        hashEntry = hashTable[i];
                        got = true;
                    }
                }   
                i++;
            }
            i = 0;
            while(!got && i < place - 1){
                if(hashTable[i] != null){
                    if(hashTable[i].getKey().equals(key)){
                        hashEntry = hashTable[i];
                        got = true;
                    }
                }
                i++;
            }

            
        }
        
        if(!got){
                System.out.println("Item with key of: " + key + "\nNot stored...");
            }

        return hashEntry;
    }


    public Object remove(String key)
    {
        HashEntry hashEntry = new HashEntry(), entryOut = new HashEntry();
        int place = hash(key), i = place + 1;
        boolean removed = false;


        if(hashTable[place].getKey().equals(key)){
            entryOut = hashTable[place];
            hashTable[place].setState(-1);
            hashTable[place] = hashEntry;
            removed = true;
            
        }else{
            while(!removed && i < hashTable.length){
                if(hashTable[i] != null){
                    if(hashTable[i].getKey().equals(key)){
                        entryOut = hashTable[i];
                        hashTable[i].setState(-1);
                        hashTable[i] = hashEntry;
                        removed = true;
                    }
                }   
                i++;
            }
            i = 0;
            while(!removed && i < place - 1){
                if(hashTable[i] != null){
                    if(hashTable[i].getKey().equals(key)){
                        entryOut = hashTable[i];
                        hashTable[i].setState(-1);
                        hashTable[i] = hashEntry;
                        removed = true;
                    }
                }
                i++;
            }

            
        }
        
        if(!removed){
                System.out.println("\nItem with key of: " + key + "\nNot stored...\n");
            }else{
                System.out.println("\nItem with key of: " + key + "\nHas been removed...\n");
                count--;
                if(count / hashTable.length < factor - 0.2)
                {
                    deSize(hashTable.length);
                }
            }
        return entryOut;
    }


    public boolean containsKey(String key)
    {
        int place = hash(key), i = place + 1;
        boolean found = false;

        if(hashTable[place] != null && hashTable[place].getKey().equals(key)){
            found = true;
        }else{
            while(!found && i < hashTable.length){
                if(hashTable[i] != null){
                    if(hashTable[i].getKey().equals(key)){
                        found = true;
                    }
                }   
                i++;
            }
            i = 0;
            while(!found && i < place - 1){
                if(hashTable[i] != null){
                    if(hashTable[i].getKey().equals(key)){
                        found = true;
                    }
                }
                i++;
            }
        }

        return found;
    }


    public void upSize(int size)
    {
        
        int actualSize = findNextPrime(size);
        HashEntry[] oldTable = hashTable;

        System.out.println("Upgrading size from : " + size/2 + "\nTo: " + actualSize);
        hashTable = new HashEntry[actualSize]; 
        for(int i = 0; i < actualSize - 1; i++){
            hashTable[i] = null;
        }

        for(int i = 0; i < oldTable.length - 1; i++)
        {
            hashTable[i] = oldTable[i]; 
        }

    }


    public void deSize(int size)
    {
       /* int actualSize = findNextPrime(size);
        HashEntry[] oldTable = hashTable;

        hashTable = new HashEntry[actualSize]; 
        for(int i = 0; i < actualSize - 1; i++){
            hashTable[i] = null;
        }

        for(int i = 0; i < oldTable.length - 1; i++)
        {
            hashTable[i] = oldTable[i]; 
        }*///not Done yet, need more time to do the process, i.e. moving values from the cut off into new nulls
    }


    private int hash(String key)
    {
        int index = 0;
        for(int i = 0; i < key.length(); i++){
            index = index + key.charAt(i);
        }

        return index % hashTable.length;
    }


    public int findNextPrime(int inVal)
    {
        int primeVal;
        if(inVal % 2 == 0){
            primeVal = inVal + 1;
        }else{
            primeVal = inVal;
        }
        primeVal = primeVal - 2;

        boolean isPrime = false;
        while(!isPrime){
            primeVal = primeVal + 2;

            int ii = 3;
            isPrime = true;
            while((ii*ii <= primeVal) && (isPrime)){
                if(primeVal % ii == 0){
                    isPrime = false;
                }else{
                    ii = ii + 2;
                }
            }
        }

        return primeVal;
    }


    public boolean duplicate(String key)
    {
        boolean duplicate = false;

        for(int i = 0; i < hashTable.length - 1; i++)
        {
            if((hashTable[i] != null) && (hashTable[i].getKey().equals(key))) 
            {
                duplicate = true;
            }
        }

        return duplicate;
    }


    public int getCount()
    {
        return count;
    }


    public HashEntry[] getArray()
    {
        return hashTable;
    }



}