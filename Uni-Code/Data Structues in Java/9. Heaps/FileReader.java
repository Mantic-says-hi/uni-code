import java.io.*;

public class FileReader
{
    private String[][] fileArray;
    public void readFile(String fileName) throws IOException
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

    public int getLines(String fileName)
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

    public String[] processLine(String line)
    {
        String[] stringArray;
        stringArray = line.split(",");
        return stringArray;
    }

    public String[][] getFileArray()
    {
        return fileArray;
    }

    public void save(String fileName, HeapEntry[] heap)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;

        try{
            fileStrm = new FileOutputStream(fileName);
            pw = new PrintWriter(fileStrm);
                        
            for(int i = 0; i < heap.length; i++){
                if(heap[i] != null){

                    pw.println(heap[i].getPriority() + "," + heap[i].getValue());       
                }
            }
            pw.close();
        }catch(IOException e){
            System.out.println("Error in file processing");
        }
    }
}