/*****************************************************************************
*Class: StockInfo
*Author: Matthew Matar
*Date: 09/03/2019
*Purpose: StockInfo object.
*****************************************************************************/
import java.util.*;

public class StockInfo implements Serializable
{
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private int volume;
    

    //Constructors
    //Default Constructor
    public StockInfo()
    {
        date = "00000000";
        open = 0.00;
        high = 0.00;
        low = 0.00;
        close = 0.00;
        volume = 0;
    }

    //Alternate
    public StockInfo(String inDate, double inOpen, double inHigh, 
                     double inLow, double inClose, int inVolume)
    {
        date = inDate;
        open = inOpen;
        high = inHigh;
        low = inLow;
        close = inClose;
        volume = inVolume;
    }



    //Mutators
    public void setDate(String inDate)
    {
        date = inDate;
    }

    public void setOpen(double inOpen)
    {
        open = inOpen; 
    }

    public void setHigh(double inHigh)
    {
        high = inHigh;   
    }
    
    public void setLow(double inLow)
    {
        low = inLow; 
    }

    public void setClose(double inClose)
    {
        close = inClose;   
    }

    public void setVolume(int inVolume)
    {
        volume = inVolume;
    }


    //Accessors

    public String getDate()
    {
        return date;
    }

    public double getOpen()
    {
        return open; 
    }

    public double getHigh()
    {
        return high; 
    }

    public double getLow()
    {
        return low; 
    }
    public double getClose()
    {
        return close;
    }

    public int getVolume()
    {
        return volume;
    }


    //To String
    public String toString()
    {
        return ("\nDate : " + this.date +"\nOpen : " + this.open + "\nHigh : " +
                this.high + "\nLow : " + this.low + "\nClose : " + this.close + 
                "\nVolume : " + this.volume);
    }

    //Clone
    public StockInfo clone()
    {
        StockInfo cloneStockInfo;

        cloneStockInfo = new StockInfo(this.getDate(), this.getOpen(),
                                       this.getHigh(), this.getLow(),
                                       this.getClose(), this.getVolume());

        return cloneStockInfo;
    }

    //CSV Output
    public String saveCSV()
    {
        return (this.date + "," + this.open + "," + this.high + "," + this.low + "," +
                this.close + "," + this.volume);   
    }
	

} 