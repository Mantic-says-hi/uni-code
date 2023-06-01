package oose.assignment1.model;

import java.util.ArrayList;
import java.util.List;

public class NonLeafPart implements NetworkPart {

	private String name;
	private NetworkPart parent;
	private ArrayList<NetworkPart> children = new ArrayList<NetworkPart>();


	//Constructor for root node
	public NonLeafPart(String name) {
		this.name = name;
		parent = null;
	}

	//Constructor for non-root nodes
	public NonLeafPart(String name, NetworkPart parent)
	{
		this.name = name;
		this.parent = parent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public NetworkPart getParent(){return parent;}

	@Override
	public NetworkPart findParent(String parentString)
	{
		List<NetworkPart> tree = new ArrayList<>();
		this.getTree(tree);
		NetworkPart foundParent = null;
		for(NetworkPart part : tree)
		{
			if(part.getParent() == null)
			{
				if(part.getName().equals(parentString)){
					foundParent = part;
				}
			}
			else if(!part.isLeaf())
			{
				if(part.getName().equals(parentString))
				{
					foundParent = part;
				}
			}
		}
		return foundParent;
	}

	@Override
	public void printName(int indent)
	{
		//Indent automatically applied with this
		if(parent == null){
			System.out.println(name);
		}else{
			String indentString = String.format("%1$" + indent + "s", "");
			System.out.println(indentString + name);
		}
		//Recursively prints name, leaf nodes only print
		for(NetworkPart child : children)
		{
			child.printName((indent + 4));
		}
	}

	@Override
	public void getTree(List<NetworkPart> tree)
	{
		tree.add(this);
		//Pretty much same method as printName()
		for(NetworkPart child : children)
		{
			child.getTree(tree);
		}
	}

	@Override
	public void addChild(NetworkPart child)
	{
		children.add(child);
	}

	@Override
	public boolean isLeaf(){return false;}

}
