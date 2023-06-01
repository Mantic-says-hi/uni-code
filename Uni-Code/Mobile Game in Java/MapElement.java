package curtin.edu.assignment;

import java.io.Serializable;

//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Simple map tiles easy for setting the ground and structure
public class MapElement implements Serializable
{
    private Structure structure;
    private int groundTile;

    public MapElement()
    {
        structure = null;
        groundTile = 0;
    }

    public  MapElement(Structure struc, int tile)
    {
        structure = struc;
        groundTile = tile;
    }

    public Structure getStructure()
    {
        return structure;
    }

    public void setStructure(Structure inStructure)
    {
        structure = inStructure;
    }

    public int getGroundTile()
    {
        return groundTile;
    }

    public void setGroundTile(int inGroundTile)
    {
        groundTile = inGroundTile;
    }
}