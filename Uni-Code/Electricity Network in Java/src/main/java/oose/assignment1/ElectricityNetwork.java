package oose.assignment1;


import oose.assignment1.controller.Controller;
import oose.assignment1.controller.FourArgCont;
import oose.assignment1.controller.ThreeArgCont;
import oose.assignment1.controller.TwoArgCont;

import java.util.HashMap;

public class ElectricityNetwork {

	private static boolean START_PROGRAM = false;
	private static String ERROR = "PROGRAM CANNOT START DUE TO ";
	private static HashMap<Integer, Controller> control;

	public static void main(String[] args) {

		setupControllers();


		switch (args.length)
		{
			case 2: case 3: case 4:
				checkArgs(args, args.length);
				break;
			default:
				ERROR = ERROR + ": INVALID NUMBER OF ARGUMENTS ( " + args.length + " )";
				break;
		}

		if(START_PROGRAM)
		{
			Controller path = control.get(args.length);
			path.start(args);
		}else
		{
			System.out.println(ERROR);
		}

	}

	private static void checkArgs(String[] args, int easyCheck)
	{
		if(easyCheck == 2 && ((args[0].equals("-g") && args[1].equals("-d")) ||
				              (args[0].equals("-d") && args[1].equals("-g"))))
		{
			//If there are 2 arguments they can only be -g or -d in either order
			//Therefore if you get here the program can start
			START_PROGRAM = true;
		}
		else if(easyCheck == 3 && ((args[0].equals("-r") || args[1].equals("-r")) ||
				                   (args[0].equals("-w") || args[1].equals("-w"))))
		{
			//-r or w- if used in 3 args must be on either the first or second...
			//argument to be able to have a .csv or .txt after themselves
			//Therefore if you get here the program may be able to start after further checking

			if((args[0].equals("-r") && args[2].equals("-d")))
			{
				if(checkFileName(args[1]))
				{
					START_PROGRAM = true;
				}

			}
			else if((args[0].equals("-d") && args[1].equals("-r")))
			{
				if(checkFileName(args[2]))
				{
					START_PROGRAM = true;
				}
			}
			else if((args[0].equals("-w") && args[2].equals("-g")))
			{
				if(checkFileName(args[1]))
				{
					START_PROGRAM = true;
				}
			}
			else if((args[0].equals("-g") && args[1].equals("-w")))
			{
				if(checkFileName(args[2]))
				{
					START_PROGRAM = true;
				}
			}
			else
			{
				//None of the any 4 valid cases?? then you cannot start program
				ERROR = ERROR + ": INVALID COMBINATION OF ARGUMENTS";
			}
		}
		else if(easyCheck == 4 && ((args[0].equals("-r") && args[2].equals("-w"))  ||
				                   (args[0].equals("-w") && args[2].equals("-r"))) &&
				                   ((!args[1].equals(args[3]))))
		{
			//-r or w- if used in 4 args must be on either the first or third...
			//argument to be able to have a .csv or .txt after themselves
			//Also filenames must be different (they can only be on index 1 or 3)
			//Therefore if you get here the program may be able to start after further checking

			if(checkFileName(args[1]) && checkFileName(args[3])) {
				START_PROGRAM = true;
			}
		}
		else if(easyCheck == 3 && (args[2].equals("-r") || args[2].equals("-w") ))
		{
			ERROR = ERROR + ": FILENAME CANNOT BE BEFORE ARGUMENT -r OR -w";
		}
		else if(easyCheck == 4 && (args[1].equals(args[3])))
		{
			ERROR = ERROR + ": FILENAMES MUST BE DIFFERENT";
		}
		else{
			ERROR = ERROR + ": INVALID / UNSUPPORTED ARGUMENTS";
		}
	}

	private static boolean checkFileName(String filename)
	{
		boolean cleared = true;
		String[] BANNED = new String[]{"compositenodes.txt", "leafnodes.txt", "rootnodes.txt", "testinput.txt"};
		String[] checkTxtCsv = filename.split("\\.");


		//Cannot attempt to put a file in that is like asdf.txt.exe
		if((checkTxtCsv.length != 2))
		{
			ERROR = ERROR + ": USUPPORTED FILE TYPE EXTENSIONS FOR FILE [ " + filename + " ] ";
			cleared = false;
		}
		//Cannot attempt to put a file in that is not .txt or .csv
		else if(!(checkTxtCsv[1].equals("csv") || checkTxtCsv[1].equals("txt")))
		{
			cleared = false;
			ERROR = ERROR + ": USUPPORTED FILE TYPE [ ." + checkTxtCsv[1] + " ] " ;
		}

		//Cannot try to use any of the .txt in the resources folder
		for (String name : BANNED)
		{
			if(filename.equals("resources/" + name))
			{
				cleared = false;
				ERROR = ERROR + ": BANNED FILE NAME [ " + name + " ] ";
			}
		}

		if(filename.equals("README.txt"))
		{
			cleared = false;
			ERROR = ERROR + ": BANNED FILE NAME [ README.txt ] ";
		}

		return cleared;
	}

	private static void setupControllers()
	{
		control = new HashMap<>();
		Controller twoArgs = new TwoArgCont();
		Controller threeArgs = new ThreeArgCont();
		Controller fourArgs = new FourArgCont();

		control.put(2,twoArgs);
		control.put(3,threeArgs);
		control.put(4,fourArgs);
	}

}
