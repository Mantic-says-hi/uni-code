/*****************************************************************************
 * Author :  Matthew Matar
 * Date :  10/03/2019
 * Purpose : 
 * **************************************************************************/


import java.util.*;


public class MainClass
{

    public static void main (String [] args)
    {   
        Controller start = new Controller();

        try{
            start.startProg();
        }
        catch (Exception e){
            System.out.println("Error starting program.");
        }
        
    }

}
