/*****************************************************************************
 * Class: Controller                                                         *
 * Author: Matthew Matar                                                     *
 * Date: 10/15/2018                                                          *
 * Last edited: 10/15/2018                                                   *
 * Purpose: Controller class that manages the functionality.                 *
 * **************************************************************************/


import java.util.*;
import java.io.*;


public class Controller
{

    private static FileIO fio;
	private static Stocks st;

    
    //CONSTRUCTOR: default
    public Controller()
    {

        fio = new FileIO();
        st = new Stocks();
    } 


    //starts...
    public void startProg()
    {
        int selection;
        boolean halt = false;

        do{
            System.out.println("\n1. Read CSV\n2. Read Serialized\n3. Display"
                               + "\n4. Save to CSV\n5. Save serialization\n"
                               + "6.Exit\n");
            selection = inptInt(1, 6);
            
            if (selection == 1){
                readCSV();

            }else if (selection == 2){
                readSER();

            }else if (selection == 3){
                display();

            }else if (selection == 4){
                saveCSV();

            }else if (selection == 5){
                saveSER();            
    
            }else if (selection == 6){//only way to exit program
                System.out.println("Good-bye");
                halt = true;

            }else{
                System.out.println("Error...Exiting");
                halt = true;

            }

        }while(halt != true);//infinite loop unless option 6 is selected
    }
    
    

    //Read info using csv
    private void readCSV()
    {
        
        int selection, numSkip = 0;
        String fileName;
        
        System.out.println("1. Continue\n" + "2. Go back");
        selection = inptInt(1, 2);

        if(selection == 1){

            System.out.println("Enter file name to read from.");
            fileName = inptStr();

            try{
                numSkip = fio.startFile(fileName, st);
            }catch(Exception e){
                System.out.println("\nError processing file\n");
            }
            
            System.out.println("\n"+  numSkip + " lines have been skipped " +
							   "due to " + "errors in the CSV file.\n");

        }else if (selection == 2){
            System.out.println("\nGoing back now.\n");
        }
    }
    
    private void readSER()
    {
        
    }

   //Prints 
   private void display()
   {
        DayEntry[] stocks = st.getStockArray();
        DayEntry stock;

        for(int i = 0; i < stocks.length; i++){
            if(stocks[i] != null){
                stock = stocks[i];
                System.out.println(stock.toString());
            }
        } 
                     
        

   }

    //Saves current objects to file
    private void saveCSV()
    {  
        fio.saveCSV("outputTxt.csv", st); 
    }   

    //Saves current serialized objects to file
    private void saveSER()
    {    
        fio.save(st, "outputSer.csv"); 
    }

    //Input for integer
    public int inptInt(int down, int up)
    {
        int integer;
        Scanner sc = new Scanner(System.in);
        do{
            try{
                integer = sc.nextInt();
            }
            catch (InputMismatchException e){
                sc.nextLine();
                System.out.println("\nA valid whole number " +
                                   "was not entered, try again.\n");
                integer = -1;
            }
        }while (integer == -1 || (integer < down && integer > up));    
        return integer;
    }

    //Input for strings
    public String inptStr()
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