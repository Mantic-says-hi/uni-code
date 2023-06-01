import java.io.IOException;

public class BigTest {

    public static void main(String[] args) throws IOException
    {
        FileReader fio = new FileReader();
        FileReader io = new FileReader();
        System.out.println("\nLoading up RandomNames file\n\n");
        io.readFile("RandomNames7000.csv");//Loading file of 7000 names
        System.out.println("\nLoading up test file\n\n");
        fio.readFile("test.txt");
        String[][] file = fio.getFileArray();
        String[][] file2 = io.getFileArray();

        Heap hp = new Heap(file.length);
        Heap names = new Heap(file2.length);
        Heap remove = new Heap(file.length);

        for(int i = 0; i < file.length; i++){

            hp.add(Integer.parseInt(file[i][0]), file[i][1]); //Tries to enter ever entry from file
        }
        for(int i = 0; i < file.length; i++){

            remove.add(Integer.parseInt(file[i][0]), file[i][1]); //Tries to enter ever entry from file
        }

        for(int i = 0; i < file2.length; i++){

            names.add(Integer.parseInt(file2[i][0]), file2[i][1]); //Tries to enter ever entry from file
        }

        
        //Getting array to print to file
        HeapEntry[] heap = hp.getArray();

        //Saving file to this name
        System.out.println("\nSaving copy of heap to HeapOut.csv...\n");
        fio.save("HeapOut.csv", heap);

        System.out.println("\nSaving copy of sorted heap to SortOut.csv...\n");
        hp.heapSort();
        HeapEntry[] hea = hp.getArray();
        fio.save("SortOut.csv", hea);

        System.out.println("\nRemoving all but 1 from test file...\n");
        for(int i = 0; i <= 6; i++){
            System.out.println(remove.remove().getPriority() + " : Has been removed.\n");
        }
        System.out.println("\nSaving names heap to NameHeapOut.csv...\n");
        HeapEntry[] name = names.getArray();
        io.save("NameHeapOut.csv", name);

        System.out.println("\nSaving sorted names heap to SortNamesOut.csv...\n");
        names.heapSort();
        HeapEntry[] sortName = names.getArray();
        io.save("SortNamesOut.csv", sortName);

    }
}