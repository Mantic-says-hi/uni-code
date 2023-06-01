package curtin.edu.assignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Map fragment for rendering the map on screen after settings have been done
public class Map extends Fragment
{
    private MapElement[][] map;
    private RecyclerView recyclerView;
    private GameData gameData;
    private static StructureData structureData;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup ui,
                             Bundle bundle)
    {
        structureData = new StructureData();
        Bundle b = this.getArguments();
        gameData = (GameData) b.getSerializable("MAP DATA");
        map = gameData.getMap();
        View view = inflater.inflate(R.layout.fragment_map, ui, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.mapRecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(
                getActivity(),
                gameData.getSettings().getMapHeight(),
                GridLayoutManager.HORIZONTAL,
                false));


        MapAdapter  adapter = new MapAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setMap(MapElement[][] inMap)
    {
        map = inMap;
    }


    public class MapAdapter extends RecyclerView.Adapter<MapDataViewHolder>
    {

        @Override
        public MapDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
        {
            return new MapDataViewHolder(LayoutInflater.from(getActivity()),viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull MapDataViewHolder mapView, int index)
        {
            int row = index % gameData.getSettings().getMapHeight();
            int col = index / gameData.getSettings().getMapHeight();
            mapView.bind(map[row][col]);
        }

        @Override
        public int getItemCount()
        {
            return (gameData.getSettings().getMapHeight() * gameData.getSettings().getMapWidth());
        }
    }



    public class MapDataViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView mapTile;
        private ImageView structure;

        public MapDataViewHolder(LayoutInflater li, final ViewGroup viewGroup) {
            super(li.inflate(R.layout.grid_tile, viewGroup, false));

            final int size = viewGroup.getMeasuredHeight() / gameData.getSettings().getMapHeight() + 1;
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = size;
            layoutParams.height = size;
            structure = itemView.findViewById(R.id.building);
            mapTile = itemView.findViewById(R.id.land);

            structure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    MapActivity activity = (MapActivity) getActivity();
                    //Because the index from item count represents every item, you need to div and
                    //mod to get the X and Y
                    //1,3
                    //2,4
                    //this is how a 2 x 2 map is represented by the index number
                    int i = getLayoutPosition() % gameData.getSettings().getMapHeight();
                    int j = getLayoutPosition() / gameData.getSettings().getMapHeight();
                    if(activity.getActiveConstruction())
                    {
                        buildClick(activity, i ,j);
                    }else if (activity.getDetailStatus())
                    {
                        detailClick(activity,i,j);
                    }

                }
            });
        }

        //I think I made detail overly complicated for myself
        //However giving it the exact location structure and the whole map
        //was my solution, so it can know exactly what structure it is updating
        private void detailClick(MapActivity activity,int i, int j)
        {
            MapElement structure = map[i][j];
            Intent intent = new Intent(activity, DetailsActivity.class);

            if(!(structure.getStructure() instanceof Utility)) {
                intent.putExtra("TILE", structure);
                intent.putExtra("MAP", map);
                intent.putExtra("ROW", i);
                intent.putExtra("COL", j);
                activity.startActivityForResult(intent, 5);
            }
        }



        //Analyses where you have clicked and whether you may build what you are trying to there
        private void buildClick(MapActivity activity, int i, int j){
            Structure newBuilding = constructStructure(activity.getActiveStructure());
            if(((map[i][j].getStructure() instanceof Utility) ||
                 activity.getActiveStructure() instanceof Utility)) {
                if((activity.getActiveStructure() instanceof Residential ||
                        activity.getActiveStructure() instanceof Commercial) &&
                        !checkRoadAdjacency(i, j)) {

                }else {
                    updateStructures(newBuilding);
                    if(activity.getActiveStructure() instanceof Utility)
                    {
                        removeResCom(map[i][j].getStructure());
                    }
                    map[i][j].setStructure(newBuilding);
                    bind(map[i][j]);

                }
                //Cancels build if valid tile not pressed
                //Resets active build if tile was built on
                activity.setActiveBuild(false, null);
            }else{
                //Stops you from building on something with a building or road on it
                Context context = getContext();
                CharSequence text = "Cannot build on existing structure";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }

        }


        //When a res or com is removed we have to do more work than just getting rid of it
        //Therefore no need to worry about roads
        private void removeResCom(Structure removable)
        {
            MapActivity activity = (MapActivity) getActivity();
            if(removable instanceof Residential)
            {
                structureData.removeResidential((Residential) removable);
                activity.setNewResident(structureData.getNumResidential());
            }else if( removable instanceof Commercial)
            {
                structureData.removeCommercial((Commercial) removable);
                activity.setNewCommercial(structureData.getNumCommercial());
            }
        }

        //Because you need to keep track of each comm or res built
        //only road can go straight into addExpenditure, this is so the num res and comm
        //may be updated with the update of a structure
        private void updateStructures(Structure update)
        {
            MapActivity activity = (MapActivity) getActivity();
            if(update instanceof Residential)
            {
                structureData.addResidential((Residential) update);
                activity.setNewResident(structureData.getNumResidential());
            }else if( update instanceof Commercial)
            {
                structureData.addCommercial((Commercial) update);
                activity.setNewCommercial(structureData.getNumCommercial());
            }else if( update instanceof Road){
                activity.addExpendeture(gameData.getSettings().getRoadBuildingCost());
                activity.updateVariables();
            }
        }

        //Renders a structure on the map given what class it is
        public Structure constructStructure(Structure inStruc)
        {
            Structure newStruc;
            if(inStruc instanceof Commercial) {
                newStruc = new Commercial(inStruc.getImageId(),"Commercial");
            } else if (inStruc instanceof Residential) {
                newStruc = new Residential( inStruc.getImageId(), "Residential");
            }else if (inStruc instanceof Road) {
                newStruc = new Road( inStruc.getImageId(), "Road");
            }else {
                newStruc = new Utility(R.drawable.invisible, "");
            }
            return newStruc;
        }

        //Ensures you can only build a building if it is directly adjacent to it, not diagonally
        public boolean checkRoadAdjacency(int row, int col)
        {
            boolean nearRoad = false;

            if (col - 1 >= 0) {
                if (map[row][col - 1].getStructure() instanceof Road){
                    nearRoad = true;
                }
            }
            if (col + 1 < gameData.getSettings().getMapWidth()){
                if (map[row][col + 1].getStructure() instanceof Road){
                    nearRoad = true;
                }
            }
            if (row - 1 >=0) {
                if (map[row - 1][col].getStructure() instanceof Road){
                    nearRoad = true;
                }
            }
            if (row + 1 < gameData.getSettings().getMapHeight()) {
                if (map[row + 1][col].getStructure() instanceof Road){
                    nearRoad = true;
                }
            }

            if(!nearRoad){
                Context context = getContext();
                CharSequence text = "No road near-by please build one.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }

            return nearRoad;
        }

        //All structures are not null, instead they will by default, be a utility structure
        //This makes it easier for me to figure out how to make all of them clickable at all times
        public void bind(MapElement element)
        {
            mapTile.setImageResource(element.getGroundTile());
            structure.setImageResource(element.getStructure().getImageId());
        }
    }
}
