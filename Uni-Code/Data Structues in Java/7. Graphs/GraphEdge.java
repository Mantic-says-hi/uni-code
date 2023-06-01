public class GraphEdge
{
    private int key;
    private GraphNode adj1, adj2;
    private boolean visited;

    public GraphEdge(int inKey, GraphNode inAdj1, GraphNode inAdj2)
    {
        key = inKey;
        adj1 = inAdj1;
        adj2 = inAdj2;
        visited = false;
    }

    public int getKey()
    {
        return key;
    }

    public GraphNode getAdj1()
    {
        return adj1;
    }

    public GraphNode getAdj2()
    {
        return adj2;
    }

    public boolean visitedO()
    {
        return visited;
    }

    public void setVisited(boolean tF)
    {
        visited = tF;
    }
}