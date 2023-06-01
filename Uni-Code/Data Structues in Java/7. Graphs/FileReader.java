import java.io.*;

public class FileReader
{
    private static String[][] fileArray;
    public static void readFile(String fileName) throws IOException
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        int numLines;
        String line; 
        

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
         
    }

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

    public static String[] processLine(String line)
    {
        String[] stringArray;
        stringArray = line.split(" ");
        return stringArray;
    }

    public static String[][] getFileArray()
    {
        return fileArray;
    }

   /* public void save(String fileName,int[] table)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;

        try{
            fileStrm = new FileOutputStream(fileName);
            pw = new PrintWriter(fileStrm);
                        
            for(int i = 0; i < table.length; i++){
                if(table[i] != null){

                    pw.println(table[i].getKey() + "," + table[i].getValue());       
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
    }*/
}