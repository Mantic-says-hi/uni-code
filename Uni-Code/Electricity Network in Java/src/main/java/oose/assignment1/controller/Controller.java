package oose.assignment1.controller;

public interface Controller
{
	public void start(String[] arguments);
}










	/*
	private String[] arguments;
	private int argNumber;

	//Constructor for controller
	public Controller(String[] arguments, int argNumber)
	{
		this.arguments = arguments;
		this.argNumber = argNumber;
	}

	public void start()
	{
		if(argNumber == 2)
		{
			genDisplay();
		}
		else if(argNumber == 3)
		{

			ReadIn write = new ReadIn();
			try {

				NetworkPart root =  write.readFile(arguments[2]);
				Display disp = new Display(root);
				disp.fullDisplay();
			}catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}

	private void genDisplay()
	{
		Generate gen = new Generate();

		gen.generateList();
		Display disp = new Display(gen.getRoot());
		disp.fullDisplay();
	}
}*/