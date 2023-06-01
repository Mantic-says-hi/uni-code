package oose.assignment1.view;

import oose.assignment1.model.NetworkPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Display
{
	private NetworkPart root;

	public Display(NetworkPart root)
	{
		this.root = root;
	}

	//For setting the root in cases where you start Display with null root
	public void setRoot(NetworkPart root){this.root = root;}

	public void fullDisplay()
	{
		displayTree();
		displayPower();
	}

	private void displayTree()
	{
		System.out.println("-------------------------------Printing Complete Tree------------------------------------");
		root.printName(0);
		System.out.println("------------------------------------End of Tree------------------------------------------");
	}

	private void displayPower()
	{
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("-----------------------------Printing Power Consumption----------------------------------");
		double[] totalValues = getTotalValues();
		HashMap<Integer, String>  category = setupCategories();
		int key = 1;
		for(double value : totalValues)
		{
			System.out.println(category.get(key) + (Math.round(((value*100.0)))/100.0));
			//Enforce to 2 decimal places
			key++;
		}
		System.out.println("--------------------------------End of Power Values--------------------------------------");
	}

	private double[] getTotalValues()
	{
		List<NetworkPart> leaf = new ArrayList<>();
		List<NetworkPart> temp = new ArrayList<>();
		double[] totalValues = new double[8];


		root.getTree(temp);

		for(NetworkPart part : temp)
		{
			if(part.isLeaf())
			{
				leaf.add(part);
			}
		}

		for(NetworkPart part : leaf)
		{
			//Splits a line that looks like this | name/dm=1.0,da=2.0,de=3.0,em=4.0,ea=5.0,ee=6.0,h=7.0,s=8.0
			String[] namesAndValues  = part.getName().split("/");
			//Now we have this | dm=1.0,da=2.0,de=3.0,em=4.0,ea=5.0,ee=6.0,h=7.0,s=8.0
			String[] values = namesAndValues[1].split(",");
			//Now we have this | dm=1.0 da=2.0  de=3.0 em=4.0 ea=5.0 ee=6.0 h=7.0 s=8.0
			int index = 0;
			for(String value : values)
			{
				String[] nameAndNumber = value.split("=");
				//Now take all of this | 1.0  2.0  3.0  4.0  5.0  6.0  7.0  8.0
				totalValues[index] = totalValues[index] + (Double.parseDouble(nameAndNumber[1]));
				//Put each value into an array that holds the total for each category...
				index++;
			}
		}

		return totalValues;
	}

	private HashMap<Integer, String> setupCategories()
	{
		HashMap<Integer, String>  category = new HashMap<>();

		category.put(1, "Weekday morning    : ");
		category.put(2, "Weekday afternoon  : ");
		category.put(3, "Weekday evening    : ");
		category.put(4, "Weekend morning    : ");
		category.put(5, "Weekend afternoon  : ");
		category.put(6, "Weekend evening    : ");
		category.put(7, "Heatwave           : ");
		category.put(8, "Special event      : ");

		return category;
	}
}
