package oose.assignment1.model;


import java.util.HashMap;
import java.util.List;

public class LeafPart implements NetworkPart
{
	private String name;
	private NetworkPart parent;
	private HashMap<String, Double> powers;

	//Constructor if a leaf is ever made a root
	public LeafPart(String name, HashMap<String, Double>powers)
	{
		this.name = name;
		this.powers = powers;
		parent = null;
	}

	//Constructor for leaf nodes
	public LeafPart(String name, NetworkPart parent, HashMap<String, Double> powers)
	{
		this.name = name;
		this.parent = parent;
		this.powers = powers;
	}

	@Override
	public String getName() { return name + getPowers(); }

	@Override
	public NetworkPart findParent(String parentString)
	{
		//Do nothing since the leaf cannot be a parent
		return null;
	}

	@Override
	public NetworkPart getParent(){return parent;}

	@Override
	public void printName(int indent)
	{
		if(parent == null){
			System.out.println(name);
		}else{
			String indentString = String.format("%1$" + indent + "s", "");
			System.out.println(indentString + name);
		}
	}

	@Override
	public void getTree(List<NetworkPart> tree)
	{
		tree.add(this);
	}

	@Override
	public void addChild(NetworkPart child)
	{
	//Do nothing
	}

	@Override
	public boolean isLeaf(){return true;}

	private String getPowers()
	{
		String[] keys = new String[]{"dm","da","de","em","ea","ee","h","s"};
		String values = "/";
		for(int i = 0; i <= 7; i++)
		{
			if(i == 7){
				values = values + keys[i] + "=" + powers.get(keys[i]);
			}else{
				values = values + keys[i] + "=" + powers.get(keys[i]) + ",";
			}
		}


		return values;
	}
}
