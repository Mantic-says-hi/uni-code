package oose.assignment1.controller;

import oose.assignment1.model.Generate;
import oose.assignment1.view.Display;

public class TwoArgCont implements Controller
{
	public void start(String[] arguments)
	{
		//Do generate + display operation
		Generate gen = new Generate();
		gen.generateList();

		Display disp = new Display(gen.getRoot());
		disp.fullDisplay();
	}
}
