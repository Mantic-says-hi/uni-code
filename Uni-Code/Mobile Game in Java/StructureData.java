package curtin.edu.assignment;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Based on code from Prac 3

//Holds all avaliable drawables for the selector fragment to reder
//Also stores the number of residencies and commercial structures on the map
public class StructureData
{
    private static List<Residential> residential;
    private static List<Commercial>  commercials;

    public static List<Structure> BUILDINGS = Arrays.asList(new Structure[] {
            new Utility(R.drawable.hammer,"Demolisher"),
            new Residential(R.drawable.ic_building1, "Cottage"),
            new Residential(R.drawable.ic_building2, "House"),
            new Commercial(R.drawable.ic_building3, "Barn"),
            new Residential(R.drawable.ic_building4,"Mansion"),
            new Commercial(R.drawable.ic_building5,"Facility"),
            new Commercial(R.drawable.ic_building6,"Warehouse"),
            new Commercial(R.drawable.ic_building7,"Stores"),
            new Commercial(R.drawable.ic_building8,"Factory"),
            new Road(R.drawable.ic_road_ew,""),
            new Road(R.drawable.ic_road_ns,""),
            new Road(R.drawable.ic_road_se,""),
            new Road(R.drawable.ic_road_sw,""),
            new Road(R.drawable.ic_road_ne,""),
            new Road(R.drawable.ic_road_nw,""),
            new Road(R.drawable.ic_road_nse,""),
            new Road(R.drawable.ic_road_nsw,""),
            new Road(R.drawable.ic_road_new,""),
            new Road(R.drawable.ic_road_sew,""),
            new Road(R.drawable.ic_road_nsew, ""),
            new Road(R.drawable.ic_road_e,""),
            new Road(R.drawable.ic_road_w,""),
            new Road(R.drawable.ic_road_n,""),
            new Road(R.drawable.ic_road_s,""),
            });


    public StructureData()
    {
        residential =  new ArrayList<Residential>();
        commercials  =  new ArrayList<Commercial>();
    }

    public void addResidential(Residential newHouse)
    {
        residential.add(newHouse);
    }

    public int getNumResidential()
    {
        return residential.size();
    }

    public int removeResidential(Residential kickedOut)
    {
        residential.remove(kickedOut);
        return residential.size();
    }

    public void addCommercial(Commercial newBuissiness)
    {
        commercials.add(newBuissiness);
    }

    public int getNumCommercial()
    {
        return commercials.size();
    }

    public int removeCommercial(Commercial bankrupt)
    {
        commercials.remove(bankrupt);
        return commercials.size();
    }
}
