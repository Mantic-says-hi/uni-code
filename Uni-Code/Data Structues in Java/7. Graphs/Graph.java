import java.util.Iterator;
public class Graph {
    private LinkedList verticies, edges;
    private int vertexCount = 0, edgeKey = 0, edgeCount = 0;


    public Graph()
    {
        verticies = new LinkedList();
        edges = new LinkedList();
    }

    public void addVertex(String key, Object value)
    {
        if(!containsKey(key)){
            GraphNode newNode = new GraphNode(key, value);
            verticies.insertFirst(newNode);
            vertexCount++;
        }
    }

    public void addEdge(String keyA, String keyB)
    {
        GraphNode nodeA = null, nodeB = null;
        if(keyA.equals(keyB))
        {
            throw new IllegalArgumentException("Key duplicate error...");

        }else if(verticies.isEmpty()){
            throw new IllegalArgumentException("Cannot add an edge without vertecies...");

        }else{
            Iterator i = verticies.iterator();
            while(i.hasNext()){
                GraphNode curNode = (GraphNode)i.next();

                if(keyA.equals(curNode.getKey())){
                    nodeA = curNode;

                }else if(keyB.equals(curNode.getKey())){
                    nodeB = curNode;

                }
            }
            
            if(nodeA == null || nodeB == null){
                throw new IllegalArgumentException("Error has occured adding vertecies...");

            }else{
                GraphEdge edge = new GraphEdge(edgeKey, nodeA, nodeB);
                edges.insertFirst(edge);
                nodeA.addNode(nodeB);
                nodeA.addEdge(edge);
                nodeB.addNode(nodeA);
                nodeB.addEdge(edge);
                edgeKey++;
                edgeCount++;
            }
        }
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

    public int getEdgeCount()
    {
        return edgeCount;
    }


    public boolean containsKey(String key)
    {
        Iterator i = verticies.iterator();
        boolean found = false;

        while(i.hasNext() && found == false)
        {
            GraphNode curNode = (GraphNode)i.next();

            if(key.equals(curNode.getKey()))
            {
                found = true;
            }
        }

        return found;
    }


    public GraphNode getVertex(String key)
    {
        Iterator i = verticies.iterator();
        boolean found = false;
        GraphNode node = null;

        while(i.hasNext() && found == false)
        {
            GraphNode curNode = (GraphNode)i.next();

            if(key.equals(curNode.getKey()))
            {
                found = true;
                node = curNode;
            }
        }

        return node;
    }

    public LinkedList getAdjacent(String key)
    {
        LinkedList adjacent = new LinkedList();
        if(containsKey(key)){
            GraphNode node = getVertex(key);
            adjacent = node.getLinks();
            
        }
        return adjacent;
    }

    public boolean isAdjacent(String keyA, String keyB)
    {
       boolean adjacent = false;
       GraphNode nodeA = getVertex(keyA);
       
       adjacent = nodeA.isAdjacent(keyB);

       return adjacent;
    }

    public void listOut()
    {
        GraphNode curNode = null, adjacent = null;
        Iterator node = null, adja = null;
        LinkedList adj = null;

        if(verticies.isEmpty()){
            throw new IllegalArgumentException("Cannot print nothing...");
        }else{
            node = verticies.iterator();
            while(node.hasNext()){
                curNode = (GraphNode)node.next();
                adj = curNode.getLinks();
                System.out.print(curNode.getKey() + " | ");
                adja = adj.iterator();
                while(adja.hasNext()){
                    adjacent = (GraphNode) adja.next();
                    System.out.print(adjacent.getKey() + ", ");
                }
                System.out.print("\n");
            }
        }
    }

    public void matrixOut()
    {
        Iterator i1 = null, i2 = null; 
        GraphNode curr, adj; 
        System.out.print("  ");
        for(i2 = verticies.iterator();i2.hasNext();){
            adj = (GraphNode)i2.next();
            System.out.print(adj.toString() + " ");
        }
        System.out.println();
        for(i2 = verticies.iterator();i2.hasNext();)
        {
            adj = (GraphNode)i2.next();
            System.out.print(adj.toString()+" "); 
            
            for(i1 = verticies.iterator(); i1.hasNext();)
            {
                curr = (GraphNode) i1.next(); 
                if(isAdjacent(adj.getKey(),curr.getKey()))
                {
                    System.out.print("1 "); 
                }
                else
                {
                    System.out.print("0 "); 
                }
            
            }
            System.out.println();
        }
    }

    public void breathWSearch(String key)
    {
        DSAQueue queue = new DSAQueue(); 
      
        GraphNode findMe,vertex1; 
        LinkedList adj; 

        findMe = getVertex(key); 

        for(Object random: verticies)
        {
            vertex1 = (GraphNode) random; 
            vertex1.setVisited(false); 
        }

        queue.add(findMe); 
        findMe.setVisited(true);
        
    
        while(!(queue.isEmpty()))
        {
            vertex1 = (GraphNode) queue.remove(); 
            System.out.println("-" +vertex1.getKey()); 

           
            adj = vertex1.getLinks(); 
            Iterator itr = adj.iterator();

            while(itr.hasNext())
            {
                GraphNode vert = (GraphNode) itr.next();
                if(vert.getVisited() == true)
                {
                    queue.add(vert); 
                    vert.setVisited(true); 
                } 
            }
        }
    }

    public void depthWSearch(String key)
    {
        GraphNode findMe, vertex1;  
        findMe = getVertex(key); 

        for(Object random: verticies)
        {
            vertex1 = (GraphNode)random; 
            vertex1.setVisited(false); 
        }
        depthRecurse(findMe); 
    }

    public void depthRecurse(GraphNode now)
    {
        LinkedList adj;
        GraphNode vertex; 

        now.setVisited(true); 
        System.out.print(now.getKey() + "-"); 

        adj = now.getLinks(); 
        Iterator itr = adj.iterator(); 
        while(itr.hasNext())
        {
            vertex = (GraphNode) itr.next(); 
            if(vertex.getVisited() == false)
            {
                depthRecurse(vertex); 
            }            
        }
    }
}