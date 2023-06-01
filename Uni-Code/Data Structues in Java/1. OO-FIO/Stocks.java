/*****************************************************************************
*Class: Stocks
*Author: Matthew Matar
*Date: 17/10/2018
*Purpose: 
*****************************************************************************/
import java.util.*;


public class Stocks implements Serializable
{

    private DayEntry[] stockArray = new DayEntry[2000];
	private int numStock = 0;

    //Constructors
    //Default
    public Stocks()
    {
        DayEntry[] stockArray = {};
   
    }

    //Alternate
    public Stocks(DayEntry[] inStockArray)
   {
        stockArray = inStockArray;
    }

    //Copy
    public Stocks(Stocks inStocks)
    {
        stockArray = inStocks.getStockArray();
    }
  

    //Mutators
    public void setStockArray(DayEntry[] inStockArray)
    {
        stockArray = inStockArray;
    }  


    
    //Accessors
    public DayEntry[] getStockArray()
    {
        return stockArray;
    }

    
    //To String
    public String toString()
    {
        return ("\nNumber of stocks recorded: " + numStock + "\n");
    }


    //Equals
    public boolean equals(Object inObj)
    {
        boolean same = false;
        if(inObj instanceof Stocks){
            Stocks inStocks = (Stocks)inObj;
        
            same = (Arrays.deepEquals(stockArray,(inStocks.getStockArray())));
        }
        
        return same;
    }  

    //Clone
    public Stocks clone()
    {
        Stocks cloneStocks;

        cloneStocks = new Stocks(this.stockArray);

        return cloneStocks;
    }

	//PRIVATE MODULE
	//Finds the next null in the array
	private static int nextNull(Object[] array)
    throws ArrayIndexOutOfBoundsException
    {
        int i = 0;

        while (array[i] != null)
        {
            i++;
        }

        return i;
    }


	//Adds a DayEntry, tells you if not
    public void addDayEntry(DayEntry de)
    {

        try{
		        stockArray[nextNull(getStockArray())] = de;
                numStock++;
           
		}catch(ArrayIndexOutOfBoundsException e){

		}
    }

	//Number of created stocks, gets printed after all objects are done
	public void numCreated()
	{
        System.out.println("\nNumber of stocks that are stored: " +
                           numStock + "\n");
    }
    
    
} 
