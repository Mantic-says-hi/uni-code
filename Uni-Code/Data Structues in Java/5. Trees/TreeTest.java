import java.io.*;
import java.util.*;

public class TreeTest
{
    public static void main(String[] args)
    {
        try{
            DSABinarySearchTree tree = new DSABinarySearchTree();

            System.out.println("Testing tree with default file : \n");
            
            String[][] file = readCSV("tree.txt");

            System.out.println("    " + Arrays.toString(file[0]));
            System.out.println("    " + Arrays.toString(file[1]) + "\n");

            for(int i = 0; i < (file[0].length); i++){
                tree.insert(file[0][i], file[1][i]);
            }
          
            String[][] inOrd = tree.inOrder();
            saveCSV("inOrd.txt", inOrd);
            String[][] preOrd = tree.preOrder();
            saveCSV("preOrd.txt", preOrd);
            String[][] postOrd = tree.postOrder();
            saveCSV("postOrd.txt", postOrd);
            /*System.out.println("Deleting fruit for key = 56...");
            tree.delete("56");

            System.out.println("Finding fruit for key = 52:  " + tree.find("52"));
            System.out.println("Height of tree: " + tree.height());
            System.out.println("Min key : " + tree.minKey());
            System.out.println("Value of min : " + tree.minValue());
            System.out.println("Tree, in-order: ");
            String[][] inTree = tree.inOrder();

            System.out.println("Trying deleting a key that dosent exist...");
            try{
                tree.delete("742");
            }catch(NoSuchElementException e){
                System.out.println(e.getMessage());
            }


            System.out.println("Saving to CSV...");
            saveCSV("test.txt", inTree);

            System.out.println("Saving to serialized file...");
            save(tree, "Serial.txt");

            try{
                System.out.println("Loading serialized file...");
                DSABinarySearchTree serTree = load("Serial.txt");
            

                System.out.println("Saving serialized file to csv...");
                String[][] sTree = serTree.inOrder();
                saveCSV("SerTest.txt", sTree);
            }catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }*/
        }catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    } 

    //Serialization
    public static void save(DSABinarySearchTree tree, String filename)
        {
            FileOutputStream fileStrm;
            ObjectOutputStream objStrm;

            try{
                fileStrm = new FileOutputStream(filename,true);
                objStrm = new  ObjectOutputStream(fileStrm);
                objStrm.writeObject(tree);

                objStrm.close();
            }
            catch (Exception e){
                throw new IllegalArgumentException("Cannot save object.");
            }
        }

    public static DSABinarySearchTree load(String filename)
    {
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream(filename);
            objectinputstream = new ObjectInputStream(streamIn);
            DSABinarySearchTree serTree = (DSABinarySearchTree) objectinputstream.readObject();
            
            if(objectinputstream != null){
                objectinputstream .close();
            }

            return serTree;
        }catch (Exception e) {
            throw new IllegalArgumentException("Uh oh.");
        } 
    }
 

    //saves to CSV
    public static void saveCSV(String fileName, String[][] exportArray)
    {
        String[] keys, values;
        String key, value;
        FileOutputStream fileStrm = null;
        PrintWriter pw;

        keys = exportArray[0];
        values = exportArray[1];

        int k = keys.length, v = values.length;

        try{
            fileStrm = new FileOutputStream(fileName);
            pw = new PrintWriter(fileStrm);
                        
            for(int i = 0; i < keys.length; i++){
                if(keys[i] != null){  
                        key = keys[i];
                        pw.print(key+",");
                              
                }
            }for(int i = 0; i < values.length; i++){
                if(values[i] != null){
                    if(i == 0){
                        pw.println("");
                        value = values[i];
                        pw.print(value+",");
                    }else{    
                        value = values[i];
                        pw.print(value+",");
                    }         
                }
            }
            pw.close();
        }catch(IOException e){
            System.out.println("Error in file processing");
            if(fileStrm != null){
                try{
                    fileStrm.close();
                }catch(IOException ex2){
                }
            }
        }
    }


    //Reads the CSV
    public static String[][] readCSV(String fileName)
    throws IOException
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        int numLines;
        String line; 
        String[][] fileArray;

        numLines = getLines(fileName);

        try{
            fileStrm = new FileInputStream(fileName);
            rdr = new InputStreamReader(fileStrm);
            bufRdr = new BufferedReader(rdr);

            fileArray = new String[numLines][];
            line = bufRdr.readLine();

            for(int i = 0; i < numLines; i++){
                fileArray[i] = processLine(line);
                line = bufRdr.readLine();

            }
            
            fileStrm.close();

        }catch(ArrayIndexOutOfBoundsException e){
            if(fileStrm != null){
                try{
                    fileStrm.close();
                }catch(IOException ex2){
                }
            }
        throw new IOException("Error in file processing");    
        }
         
        return fileArray;
   
    }


    //makes readCSV easier to read  
    public static int getLines(String fileName)
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        String line;
        int lineNum = 0;
        
        try{
            fileStrm = new FileInputStream(fileName);
            rdr = new InputStreamReader(fileStrm);
            bufRdr = new BufferedReader(rdr);
            line = bufRdr.readLine();
            
            while(line != null){
                lineNum++;
                line = bufRdr.readLine();

            }
        }catch(IOException e){
            if(fileStrm != null){
                try{
                    fileStrm.close();

                }catch(IOException ex2){
                }
            }
        System.out.println("Error in processing file. " + e.getMessage());
        }

        return lineNum;
                
    } 

    
    //allocates the data between the comas into an array
    private static String[] processLine(String row)
    {
        //String  thisToken = null;
        StringTokenizer strTok;
        int colNum = 0;
        
        strTok = new StringTokenizer(row, ",");
        
        //finds number of columns
        while(strTok.hasMoreTokens()){
            strTok.nextToken();
            colNum++;
        }
        
        String[] line = new String[colNum];
        strTok = new StringTokenizer(row, ",");

        //processes the values given the information about the columns
        for(int i = 0; i < colNum; i++){
            line[i] = strTok.nextToken();
        }

        return line;
       
    }






     //Input for strings
     public static String inptStr()
     {
         String str;
         Scanner sc = new Scanner(System.in);
         do{
             try{
                 str = sc.nextLine();
             }
             catch (InputMismatchException e){
                 sc.nextLine();
                 System.out.println("\nError... a valid string " +
                                    "was not entered, try again\n");
                 str = null;
             }
         }while (str == null);
         return str;
     }
}