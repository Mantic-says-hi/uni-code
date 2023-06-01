package oose.assignment1.controller;

import oose.assignment1.model.Generate;
import oose.assignment1.model.NetworkPart;
import oose.assignment1.model.ReadIn;
import oose.assignment1.model.ReadInException;
import oose.assignment1.view.Display;
import oose.assignment1.view.WriteOut;

import java.io.IOException;

public class ThreeArgCont implements Controller{
	public void start(String[] arguments)
	{
		//Won't use all but just need to state them
		Generate gen = null;
		Display dis = null;
		String filename = null;
		ReadIn read = null;
		WriteOut write = null;

		//Setup variables accordingly
		for(String arg : arguments)
		{
			if(arg.equals("-r"))
			{
				read = new ReadIn();
			}
			else if(arg.equals("-g"))
			{
				gen = new Generate();
			}
			else if(arg.equals("-d"))
			{
				dis = new Display(null);
			}
			else
			{
				filename = arg;
			}
		}

		//If READING then DISPLAY
		if(read != null)
		{
			try
			{
				NetworkPart root =  read.readFile(filename);
				dis.setRoot(root);
				dis.fullDisplay();
			}
			catch(IOException | ReadInException e)
			{
				System.out.println(e.getMessage());
			}
		}
		//If WRITING then GENERATE
		else
		{
			gen.generateList();
			NetworkPart root = gen.getRoot();

			write = new WriteOut(root);

			write.writeOut(filename);
		}

	}
}
