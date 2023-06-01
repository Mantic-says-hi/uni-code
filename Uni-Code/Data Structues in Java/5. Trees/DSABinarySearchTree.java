import java.util.NoSuchElementException;

public class DSABinarySearchTree implements Serializable
{
    private DSATreeNode root;
    private String[][] exportArray1,exportArray2,exportArray3;
    private int counter;

    public DSABinarySearchTree()
    {
        root = null;
        exportArray1 = new String[100][100];
        exportArray2 = new String[100][100];
        exportArray3 = new String[100][100];
        counter = 0;
    }

    //Wrapper
    public Object find(String key)
    {
        return findRecurse(key, root);
    }


    //Recurse Method
    private Object findRecurse(String key, DSATreeNode currNode)
    {
        Object val = null;

        if(currNode == null){
            //Abort
            throw new NoSuchElementException("Key " + key + " not found.");
        }else if(key.equals(currNode.getKey())){
            val = currNode.getValue();
        }else if(Float.valueOf(key) < Float.valueOf(currNode.getKey())){
            val = findRecurse(key, currNode.getLeft());
        }else{
            val = findRecurse(key, currNode.getRight());
        }

        return val;
    }



    //Wrapper
    public void insert(String key, Object value)
    {
        if(root == null){
            root = insertRecurse(key, value, root);
        }else{
        DSATreeNode updateNode = insertRecurse(key, value, root);
        }        
    }

    //Recurse Method
    public DSATreeNode insertRecurse(String key, Object value, DSATreeNode curr)
    {
        DSATreeNode updateNode = curr;

        if(curr == null){
            updateNode = new DSATreeNode(key, value);
        }else if(key.equals(curr.getKey())){
            //Abort
            throw new NoSuchElementException("Error key given: " + key + "\nMatches a key already in use.");
        }else if(Float.valueOf(key) < Float.valueOf(curr.getKey())){
            curr.setLeft(insertRecurse(key, value, curr.getLeft()));
        }else{
            curr.setRight(insertRecurse(key, value, curr.getRight()));
        }
        
        return updateNode;
    }

    //Wrapper
    public void delete(String key)
    {
        DSATreeNode deleted = deleteRecurse(key, root);
    }

    //Recurse Method
    public DSATreeNode deleteRecurse(String key, DSATreeNode curr)
    {
        DSATreeNode updateNode = curr;

        if(curr == null){
            //Abort
            throw new NoSuchElementException("Error key given: " + key + "\nDoes not exist on tree.");
        }else if(key.equals(curr.getKey())){
            updateNode = deleteNode(key, curr);
        }else if(Float.valueOf(key) < Float.valueOf(curr.getKey())){
            curr.setLeft(deleteRecurse(key, curr.getLeft()));
        }else{
            curr.setRight(deleteRecurse(key, curr.getRight()));
        }

        return updateNode;
    }

    public DSATreeNode deleteNode(String key, DSATreeNode delNode)
    {   
        DSATreeNode updateNode = null;

        if((delNode.getLeft() == null) && (delNode.getRight() == null)){
            updateNode = null;
        }else if((delNode.getLeft() != null) && (delNode.getRight() == null)){
            updateNode = delNode.getLeft();
        }else if((delNode.getLeft() == null) && (delNode.getRight() != null)){
            updateNode = delNode.getRight();
        }else{
            updateNode = promoteSuccessor(delNode.getRight());
            if(updateNode != delNode.getRight()){
                updateNode.setRight(delNode.getRight());
            }
            updateNode.setLeft(delNode.getLeft());
        }

        return updateNode;
    }

    public DSATreeNode promoteSuccessor(DSATreeNode cur)
    {
        DSATreeNode sucessor = cur;

        if(cur.getLeft() != null){
            sucessor = promoteSuccessor(cur.getLeft());
            if (sucessor == cur.getLeft()){
                cur.setLeft(sucessor.getRight());
            }
        }

        return sucessor;
    }

    //Wrapper
    public int height()
    {
        return heightRecuse(root);
    }

    //Recurse method
    public int heightRecuse(DSATreeNode curr)
    {
        int htSoFar, iLeftHt, iRightHt;

        if(curr == null){
            htSoFar = -1;
        }else{
            iLeftHt = heightRecuse(curr.getLeft());
            iRightHt = heightRecuse(curr.getRight());

            if(iLeftHt > iRightHt){
                htSoFar = iLeftHt + 1;
            }else{
                htSoFar = iRightHt + 1;
            }
        }

        return htSoFar;
    }

    public Object minKey()
    {
        return min().getKey();
    }

    public Object minValue()
    {
        return min().getValue();
    }

    //Wrapper
    public DSATreeNode min()
    {
        return minRecurse(root);
    }

    //Recurse method
    public DSATreeNode minRecurse(DSATreeNode startNode)
    {
        DSATreeNode minNode = startNode;
        if(startNode.getLeft() != null){
            minNode = minRecurse(startNode.getLeft());
        }

        return minNode;
    }

    public void exportArray1(String key, String value, int i)
    {
        exportArray1[0][i] = key;
        exportArray1[1][i] = value; 
    }public void exportArray2(String key, String value, int i)
    {
        exportArray2[0][i] = key;
        exportArray2[1][i] = value; 
    }public void exportArray3(String key, String value, int i)
    {
        exportArray3[0][i] = key;
        exportArray3[1][i] = value; 
    }

    //Wrapper
    public String[][] inOrder()
    {
        counter = 0;
        inOrderRecurse(root);
        return exportArray1;
    }

    //Recurse method
    public void inOrderRecurse(DSATreeNode node) {
		if(node !=  null) {
			inOrderRecurse(node.leftChild);
            exportArray1(node.getKey(),(String)node.getValue(),counter);
            counter++;
            inOrderRecurse(node.rightChild);
        }
	}

    //Wrapper
    public String[][] preOrder()
    {

        counter = 0;
        preOrderRecurse(root);
        return exportArray2;
    }

    public void preOrderRecurse(DSATreeNode node) {
		if(node !=  null) {
            exportArray2(node.getKey(),(String)node.getValue(),counter);
            counter++;
            preOrderRecurse(node.leftChild);		
            preOrderRecurse(node.rightChild);
		}
    }
    

    //Wrapper
    public String[][] postOrder()
    {

        counter = 0;
        postOrderRecurse(root);
        return exportArray3;
    }

    public void postOrderRecurse(DSATreeNode node) {
		if(node !=  null) {
			postOrderRecurse(node.leftChild);
            postOrderRecurse(node.rightChild);
            exportArray3(node.getKey(),(String)node.getValue(),counter);
            counter++;
		}
	}

    private class DSATreeNode
    {
        public String key;
        public Object value;
        public DSATreeNode leftChild;
        public DSATreeNode rightChild;

        public DSATreeNode(String inKey, Object inVal) throws IllegalArgumentException
        {
            if(inKey.equals(null)){
                throw new IllegalArgumentException("Key cannot be null");
            }else{
                key = inKey;
                value = inVal;
                rightChild = null;
                leftChild = null;
            }
        }

          
        public String getKey()
        {
            return key;
        }

        public Object getValue()
        {
            return value;
        }

        public DSATreeNode getLeft()
        {
            return leftChild;
        }

        public DSATreeNode getRight()
        {
            return rightChild;
        }

        public void setLeft(DSATreeNode newLeft)
        {
            leftChild = newLeft;
        }

        public void setRight(DSATreeNode newRight)
        {
            rightChild = newRight;
        }
    }
}