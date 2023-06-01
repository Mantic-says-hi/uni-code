package oose.assignment1.view;

import oose.assignment1.model.NetworkPart;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.List;

public class WriteOut {

	private NetworkPart root;

	public WriteOut(NetworkPart root)
	{
		this.root = root;
	}


	public void writeOut(String filename)
	{
		try {
			PrintWriter write = new PrintWriter(filename, StandardCharsets.UTF_8);

			//Get the whole tree
			List<NetworkPart> tree = new ArrayList<>();
			root.getTree(tree);

			//Create sub-lists to start sorting the tree (made new root just for consistency)
			NetworkPart rootNode = root;
			List<NetworkPart> nonLeafNode = new ArrayList<>();
			List<NetworkPart> leaf = new ArrayList<>();

			//Sort the tree
			for(NetworkPart part : tree)
			{
				if(part.getParent() == null)
				{
					rootNode = part;
				}
				else if(!part.isLeaf())
				{
					nonLeafNode.add(part);
				}
				else
				{
					leaf.add(part);
				}
			}



			//Print root line
			if(!rootNode.isLeaf())
			{
				write.println(rootNode.getName());
			}
			else
			{
				String[] nameValues  = rootNode.getName().split("/");
				write.println(nameValues[0].stripLeading() + "," + ","
						+ nameValues[1]);
			}

			//Print node lines
			for(NetworkPart part : nonLeafNode)
			{
				write.println(part.getName().stripLeading() + ","
						+ part.getParent().getName().stripLeading());
			}

			//Print leaf nodes
			for(NetworkPart part : leaf)
			{
				String[] nameValues  = part.getName().split("/");
				write.println(nameValues[0].stripLeading() + ","
						+ part.getParent().getName().stripLeading()+ ","
						+ nameValues[1]);
			}


			write.close();

		}catch(IOException e){
			System.out.println("Error  : " + e.getMessage());
		}
	}

/*  //OLD METHOD I USED TO TEST HOW WRITING TO FILE WOULD LOOK BEFORE I IMPLEMENTED IT
	public void displayOut()
	{
		//Get the whole tree
		List<NetworkPart> tree = new ArrayList<>();
		root.getTree(tree);

		//Create sub-lists to start sorting the tree (made new root just for consistency)
		NetworkPart rootNode = root;
		List<NetworkPart> nonLeafNode = new ArrayList<>();
		List<NetworkPart> leaf = new ArrayList<>();

		//Sort the tree
		for(NetworkPart part : tree)
		{
			if(part.getParent() == null)
			{
				rootNode = part;
			}
			else if(!part.isLeaf())
			{
				nonLeafNode.add(part);
			}
			else
			{
				leaf.add(part);
			}
		}



		//Print root line
		if(!rootNode.isLeaf()) {
			System.out.println(rootNode.getName());
		}
		else
		{
			String[] nameValues  = rootNode.getName().split("/");
			System.out.println(nameValues[0].stripLeading() + "," + ","
					           + nameValues[1]);
		}

		//Print node lines
		for(NetworkPart part : nonLeafNode)
		{
			System.out.println(part.getName().stripLeading() + ","
					           + part.getParent().getName().stripLeading());
		}

		//Print leaf nodes
		for(NetworkPart part : leaf)
		{
			String[] nameValues  = part.getName().split("/");
			System.out.println(nameValues[0].stripLeading() + ","
					           + part.getParent().getName().stripLeading()+ ","
					           + nameValues[1]);
		}

	}*/
}
