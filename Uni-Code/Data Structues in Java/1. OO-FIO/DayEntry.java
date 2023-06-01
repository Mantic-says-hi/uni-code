/*****************************************************************************
*Class: DayEntry
*Author: Matthew Matar
*Date: 17/10/2018
*Purpose: Holds record, digital and cassette arrays.
*****************************************************************************/
import java.util.*;


public class DayEntry implements Serializable
{
    private StockInfo[] dateArray = new StockInfo[50];
    private int numDays = 0;
    private String name;

    //Constructors
    //Default
    public DayEntry()
    {
        StockInfo[] dateArray = {};
        String name = "ZZZ";
   
    }

    //Alternate
    public DayEntry(StockInfo[] inDateArray, String inName)
    {
        dateArray = inDateArray;
        name = inName;
    }

    //Copy
    public DayEntry(DayEntry inDayEntry, String inName)
    {
        dateArray = inDayEntry.getDateArray();
        name = inDayEntry.getName();
    }
  

    //Mutators
    public void setMusicArray(StockInfo[] inDateArray)
    {
        dateArray = inDateArray;
    }  

    public void setName(String inName)
    {
        name = inName;
    }
    
    //Accessors
    public StockInfo[] getDateArray()
    {
        return dateArray;
    }

    public String getName()
    {
        return name;
    }
    
    //To String
    public String toString()
    {
        return ("Number of days stored for " + name 
                + ": " + numDays + "\n");
    }


    //Clone
    public DayEntry clone()
    {
        DayEntry cloneDayEntry;

        cloneDayEntry = new DayEntry(this.dateArray, this.name);

        return cloneDayEntry;
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


	//Adds a si, tells you if not
    public void addStockInfo(StockInfo si)
    {

        try{
	            dateArray[nextNull(getDateArray())] = si.clone();
                numDays++;
		}catch(ArrayIndexOutOfBoundsException e){

		}
    }

	//Number of created songs, gets printed after all objects are done
	public void numCreated()
	{
		System.out.println("\nNumber of days stored : " + numDays + "\n");
    }
    
    //CSV Output
    public String saveCSV()
    {
        return (this.name + ",");   
    }
} 
