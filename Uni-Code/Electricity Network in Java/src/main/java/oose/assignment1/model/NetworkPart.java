package oose.assignment1.model;


import java.util.List;

public interface NetworkPart
{
	String getName();
	NetworkPart getParent();
	NetworkPart findParent(String parentString);
	//printName(Int) might be a confusing name but it actually does print the name of
	//the NetworkPart object, however if it is not a leaf it will recurse
	//and then print the next one for the tree :)
	void printName(int indent);
	void addChild(NetworkPart part);
	void getTree(List<NetworkPart> tree);
	boolean isLeaf();
}
