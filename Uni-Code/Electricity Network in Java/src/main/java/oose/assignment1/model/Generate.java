package oose.assignment1.model;


import java.util.*;

public class Generate
{

	private List<String> building, town, city;
	private HashMap<Integer, String>  powerComps = new HashMap<>();
	private NetworkPart root;
	private static int MAX_DEPTH;

	public Generate()
	{
			ReadIn loader = new ReadIn();
			//Load in sample names for City, Town and Building (Root, Node and Leaf)
			city = loader.getSampleNames("rootnodes.txt");
			town = loader.getSampleNames("compositenodes.txt");
			building = loader.getSampleNames("leafnodes.txt");
			//Set the depth between 5 and 1
			MAX_DEPTH = randomInt(5,1);

	}

	public void generateList()
	{
		           //"Ironic." - Darth Sideous (Revenge of the Sith)
		root = new NonLeafPart(city.remove(randomInt((city.size() - 1),0)));


		setupComps();

		if(MAX_DEPTH > 1){ genRecurse(root,1);}
		//Obviously we are done here if there is already a root, no need to recurse unless a depth of 2-5 is needed

	}

	//Recursive
	private void genRecurse(NetworkPart curr,  int indentValue)
	{
		for (int i = 0; i < randomInt(5,2); i++) {
			int r = randomInt(2, 1);
			// Randomly pick between another node or a leaf | Unless you are about to create a node on the lowest depth
			if (r == 2 && (indentValue + 1)!=MAX_DEPTH)
			{
				//New node with the correct indentation and a random unique name
				NetworkPart node = new NonLeafPart( town.remove(randomInt((town.size() - 1), 0)), curr);
				curr.addChild(node);
				genRecurse(node,  indentValue + 1);
				//Keep going, generate more nodes or leaves until completion
			}
			else
			{
				HashMap<String, Double> powers = new HashMap<>();
				//Generate power values for all categories for the new leaf about to be made
				setupPowers(powers);
				//New leaf with the correct indentation, random unique name and the new power values
				NetworkPart leaf = new LeafPart( building.remove(randomInt((building.size() - 1), 0)),
												curr, powers);
				curr.addChild(leaf);
				//No leaves have children, recursion stops here in all cases
			}
		}
	}



	private int randomInt(int upper, int lower)
	{
		Random random = new Random();
		//+1 so it is between upper and lower, so need to -1 when dealing with the Lists
		return random.nextInt((upper + 1) - lower) + lower;
	}

	private double randomDouble(double upper, double lower)
	{
		Random random = new Random();
		//Random double to 2 decimal places
		return (Math.round((lower + ((upper) - lower) * random.nextDouble() * 1000.0)) / 1000.0);
	}

	//Generates a power from 0.00 - 1000.00 for all 8 categories
	private void setupPowers(HashMap<String, Double> powers)
	{
		for(int key = 1;key <= 8;key++) {
			powers.put(powerComps.get(key), randomDouble(1000.0, 0.0));
		}
	}



	//All string values are the keys for holding a power value
	private void setupComps()
	{
		powerComps.put(1, "dm");
		powerComps.put(2, "da");
		powerComps.put(3, "de");
		powerComps.put(4, "em");
		powerComps.put(5, "ea");
		powerComps.put(6, "ee");
		powerComps.put(7, "h");
		powerComps.put(8, "s");
	}

	public NetworkPart getRoot(){return root;}
}
