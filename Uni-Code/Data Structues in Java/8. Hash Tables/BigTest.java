import java.io.IOException;

public class BigTest {

    public static void main(String[] args) throws IOException
    {
        FileReader fio = new FileReader();
        fio.readFile("RandomNames7000.csv");//Loading file of 7000 names
        String[][] file = fio.getFileArray();

        HashTable ht = new HashTable();

        //long startTime = System.nanoTime(); //Used to test time difference of factors
        for(int i = 0; i < file.length - 1; i++){
            ht.put(file[i][0], file[i][1]); //Tries to enter ever entry from file
        }
        //long endTime = System.nanoTime();
        //System.out.println((int)((double)(endTime - startTime) / 1000.0));
        System.out.println("Number of stored names: " + ht.getCount()); //Number of entries that were actualy entered

        //Searching for key they dosen't exist
        System.out.print("\nContains key 156655? : ");
        System.out.print(ht.containsKey("156655"));

        //Searching for key that exists
        System.out.print("\nContains key 14541837? : ");
        System.out.print(ht.containsKey("14541837"));

        //Removing key that exists
        System.out.println("\n\nRemoving key 14541837...");
        ht.remove("14541837");

        //Searching for key that used to exist
        System.out.print("Contains key 14541837? : ");
        System.out.print(ht.containsKey("14541837"));

        //Removing key that dosent exist
        System.out.print("\n\nRemoving key 1234567890...");
        ht.remove("1234567890");

        //Getting array to print to file
        HashEntry[] table = ht.getArray();

        //Saving file to this name
        fio.save("NamesOut.csv", table);
    }
}