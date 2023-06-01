package oose.assignment1.controller;

import oose.assignment1.model.NetworkPart;
import oose.assignment1.model.ReadIn;
import oose.assignment1.model.ReadInException;
import oose.assignment1.view.WriteOut;

import java.io.IOException;

public class FourArgCont implements Controller
{

	private String[] filenames = new String[2];
	private ReadIn read = new ReadIn();

	public void start(String[] arguments)
	{

		//Ensure file to READ is [0] and to WRITE is [1]
		if(arguments[0].equals("-r"))
		{
			filenames[0] = arguments[1];
			filenames[1] = arguments[3];
		}
		else
		{
			filenames[0] = arguments[3];
			filenames[1] = arguments[1];
		}

		//Do read / write operation
		try
		{
			NetworkPart root =  read.readFile(filenames[0]);
			WriteOut write = new WriteOut(root);
			write.writeOut(filenames[1]);
		}
		catch(IOException | ReadInException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
