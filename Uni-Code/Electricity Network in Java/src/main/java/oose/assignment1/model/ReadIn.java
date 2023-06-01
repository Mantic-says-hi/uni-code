package oose.assignment1.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadIn
{
	//Used for generating random
	public List<String> getSampleNames(String fileName)
	{
		List<String> list = new ArrayList<String>();
		try {
			readSampleNames(fileName, list);
		}catch (IOException e){
			System.out.println("This probably should not happen : " + e.getMessage());
		}

		return list;
	}

	private void readSampleNames(String fileName, List<String> list) throws IOException
	{
		//Looks for the specified hard coded file name for generation setup within resources
		fileName = "resources/" + fileName;
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = reader.readLine();
		while(line != null)
		{
			list.add(line);
			line = reader.readLine();
		}
		reader.close();
	}

	public NetworkPart readFile(String filename) throws IOException, ReadInException
	{
		NetworkPart root = null;
		boolean neverCreatedLeaf = true;
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = reader.readLine();

		if(line == null) { throw new ReadInException("FILE IS EMPTY!"); }

		while(line != null)
		{
			String[] lineInfo = line.split(",");
			HashMap<String, Double> values = new HashMap<>();
			boolean leafNodeCreation = false;


			if( !lineInfo[0].isBlank() && (lineInfo.length == 1 && root != null) || (lineInfo.length > 1 && lineInfo[1].isBlank()) )
			{throw new ReadInException("FORMAT ERROR FOR LINE : [ " + line + " ] PARENT DOES NOT EXIST / HAVE A NAME");}

			//Root may be a LeafPart
			if(lineInfo.length > 2)
			{
				makeValues(values, lineInfo);
				leafNodeCreation = true;
			}

			if(root == null && (lineInfo.length == 0 || lineInfo[0].length() == 0)) { throw new ReadInException("EMPTY LINE BEFORE ROOT NODE");}

			//Root is a NonLeafPart
			if(lineInfo.length == 1 && root == null)
			{
				root = new NonLeafPart(line);
			}//Root is a LeafPart
			else if(lineInfo.length > 2 && lineInfo[1].length() == 0 && root == null)
			{
				root = new LeafPart(lineInfo[0],values);
			}

			if(lineInfo.length > 1 && root != null && !root.isLeaf())
			{



				NetworkPart parent = root.findParent(lineInfo[1]);
				if(!leafNodeCreation && parent != null)
				{
					if(neverCreatedLeaf)
					{
						NetworkPart newNode = new NonLeafPart(lineInfo[0],parent);
						parent.addChild(newNode);
					}else{
						throw new ReadInException("FILE IS INCORRECTLY FORMATTED, TRYING TO CREATE A COMPOSITE NODE AFTER A LEAF ALREADY EXISTS : [ " + line + " ]");
					}
				}
				else if(leafNodeCreation && parent != null)
				{


					NetworkPart newNode = new LeafPart(lineInfo[0], parent, values);
					neverCreatedLeaf = false;
					parent.addChild(newNode);
				}else
				{
					throw new ReadInException("FIRST REFERENCE OF PARENT FOR LINE :  [ " + line + " ] \nPARENT DOES NOT EXIST OR FILE IS INCORRECTLY FORMATTED");
				}
			}

			line = reader.readLine();
		}

		reader.close();

		return root;
	}

	public void makeValues(HashMap<String, Double> values, String[] line)
	{
		int length = line.length;

		values.put("dm",0.0);
		values.put("da",0.0);
		values.put("de",0.0);
		values.put("em",0.0);
		values.put("ea",0.0);
		values.put("ee",0.0);
		values.put("h",0.0);
		values.put("s",0.0);

		if(length <= 10)
		{
			for (int i = 2; i < (length); i++) {
				String[] nameValue = line[i].split("=");
				if (values.containsKey(nameValue[0])) {
					values.replace(nameValue[0], Double.valueOf(nameValue[1]));
				}
			}
		}
	}
}
