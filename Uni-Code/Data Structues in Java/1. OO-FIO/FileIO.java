/*****************************************************************************
* Author :  Matthew Matar
* Date :  27/09/2018
* Purpose : 
******************************************************************************/

import java.util.*;
import java.io.*;


public class FileIO
{  


    //Starts reading and allocating data from given CSV 
    public int startFile(String fileName, Stocks st)
    throws IOException
    {
        String[][] file;
        int numSkip;
        
        
        file = readCSV(fileName);
        numSkip = 0;

        
        for(int i = 0; i < file.length; i++){
            try{
                addEntry(file[i], st);
            }catch(Exception e){
                numSkip++;
            }
        }
		st.numCreated();
        return numSkip;
    
    }
    

    //Reads the CSV
    public String[][] readCSV(String fileName)
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

    
    //allocates the data between the comas into an array
    private String[] processLine(String row)
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
        
    //Adds all imported data to the correct place
    public void addEntry(String[] entry, Stocks st)
    {
        StockInfo si = new StockInfo();
        DayEntry[] stocks = st.getStockArray();
        DayEntry stock;      
        
        if(stocks[0] == null){
            stock = new DayEntry();

            stock.setName(entry[0]);
            si.setDate(entry[1]);
            si.setOpen(Double.parseDouble(entry[2]));
            si.setHigh(Double.parseDouble(entry[3]));
            si.setLow(Double.parseDouble(entry[4]));
            si.setClose(Double.parseDouble(entry[5]));
            si.setVolume(Integer.parseInt(entry[6]));
                
            stock.addStockInfo(si);
            st.addDayEntry(stock);         
        }else{
            int i = 0;
            boolean match = false;

            while(match = false || stocks[i] != null){
                if(stocks[i].getName().equals(entry[0])){
                    si.setDate(entry[1]);
                    si.setOpen(Double.parseDouble(entry[2]));
                    si.setHigh(Double.parseDouble(entry[3]));
                    si.setLow(Double.parseDouble(entry[4]));
                    si.setClose(Double.parseDouble(entry[5]));
                    si.setVolume(Integer.parseInt(entry[6])); 
                            
                    stocks[i].addStockInfo(si);

                    match = true;
                }else{
                    i++;
                }
            }

            if(match == false){
                stock = new DayEntry();

                stock.setName(entry[0]);
                si.setDate(entry[1]);
                si.setOpen(Double.parseDouble(entry[2]));
                si.setHigh(Double.parseDouble(entry[3]));
                si.setLow(Double.parseDouble(entry[4]));
                si.setClose(Double.parseDouble(entry[5]));
                si.setVolume(Integer.parseInt(entry[6]));
                    
                stock.addStockInfo(si);
                st.addDayEntry(stock);
            }
        }    
    }


    //saves to CSV
    public void saveCSV(String fileName, Stocks st)
    {
        DayEntry[] stocks = st.getStockArray();
        DayEntry stock = new DayEntry();
        StockInfo[] stInfo;
        StockInfo info;

        FileOutputStream fileStrm = null;
        PrintWriter pw;

        try{
            fileStrm = new FileOutputStream(fileName);
            pw = new PrintWriter(fileStrm);
                        
            for(int i = 0; i < stocks.length; i++){
                if(stocks[i] != null){
                    stock = stocks[i];
                    stInfo = stock.getDateArray();
                    pw.println(stock.saveCSV());
                    //for(int ii = 0; ii < stInfo.length; ii++){
                      //  if(stInfo[i] != null){
                         //   info = stInfo[ii];
                        
                         //   pw.println(stock.saveCSV() + info.saveCSV()); 
                     //   }                     
                    //}           
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


    public void save(Stocks st, String filename)
    {
        FileOutputStream fileStrm;
        ObjectOutputStream objStrm;

        try{
            fileStrm = new FileOutputStream(filename);
            objStrm = new  ObjectOutputStream(fileStrm);
            objStrm.writeObject(st);

            objStrm.close();
        }
        catch (Exception e){
            throw new IllegalArgumentException("Cannot save object.");
        }
    }
}
