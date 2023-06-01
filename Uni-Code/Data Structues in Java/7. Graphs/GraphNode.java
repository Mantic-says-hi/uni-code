import java.util.Iterator;

public class GraphNode
    {
        private String key;
        private Object value;
        private LinkedList edgeLinks;
        private LinkedList links;
        private boolean visited;

        public GraphNode (String inKey, Object inValue)
        {
            key = inKey;
            value = inValue; 
            edgeLinks = new LinkedList();
            links = new LinkedList();
           
            visited = false;
        }


        public String getKey()
        {
            return key;
        }
        
        public Object getValue()
        {
            return value;
        }

        public LinkedList getEdgeLinks()
        {
            return edgeLinks;
        }

        public LinkedList getLinks()
        {
            return links;
        }

        public boolean getVisited()
        {
            return visited;
        }

        public void addNode(GraphNode inNode)
        {
            links.insertFirst(inNode);
        }

        public void addEdge(GraphEdge inEdge)
        {
            edgeLinks.insertFirst(inEdge);
        }

        public void setVisited(boolean tF)
        {
            visited = tF;
        }

        public boolean isAdjacent(String key)
        {
            boolean adjacent = false;
            Iterator findAdj = links.iterator();

            while(findAdj.hasNext())
            {
                GraphNode adj = (GraphNode) findAdj.next();
                if(adj.getKey().equals(key)){
                    adjacent = true;
                }

            }

            return adjacent;
        }
        
        public String toString()
        {
            String temp; 
            temp =  key; 
            return temp; 
        }
    }