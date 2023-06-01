package curtin.edu.assignment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Selecton fragment for the map activity
public class Selector extends Fragment
{
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup ui,
                             Bundle bundle)
    {

        View view = inflater.inflate(R.layout.fragment_selector, ui, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.selectorRecyclerView);

        //Grid view but will be 2D not 3D which is what we need for this
        recyclerView.setLayoutManager(new GridLayoutManager(
                getActivity(),
                1,
                GridLayoutManager.HORIZONTAL,
                false));


        SelectorAdapter adapter = new SelectorAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }


    public class SelectorAdapter extends RecyclerView.Adapter<Selector.SelectorDataViewHolder>
    {

        @Override
        public Selector.SelectorDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
        {
            return new Selector.SelectorDataViewHolder(LayoutInflater.from(getActivity()),viewGroup);
        }


        @Override
        public void onBindViewHolder(@NonNull Selector.SelectorDataViewHolder mapView, int index)
        {
            mapView.bind(StructureData.BUILDINGS.get(index));
        }

        @Override
        public int getItemCount()
        {
            return (StructureData.BUILDINGS.size());
        }
    }

    public class SelectorDataViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView structure;
        private TextView desc;

        public SelectorDataViewHolder(LayoutInflater li, final ViewGroup viewGroup) {
            super(li.inflate(R.layout.build_tile, viewGroup, false));

            final int size = viewGroup.getMeasuredHeight();
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = size;
            layoutParams.height = size + 100;
            structure = itemView.findViewById(R.id.structure);
            desc = (TextView) itemView.findViewById(R.id.description);

            //Could not figure out how to layer one button over the two view's  with sucess
            // so I had to make them
            //do the same thing as eachother to act as one button
            structure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    MapActivity activity = (MapActivity) getActivity();
                    if(checkMoney(StructureData.BUILDINGS.get(getLayoutPosition()))) {
                        activity.setActiveBuild(true, StructureData.BUILDINGS.get(getLayoutPosition()));
                    }else {
                        activity.setActiveBuild(false, null);
                    }
                }
            });

            desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MapActivity activity = (MapActivity) getActivity();
                    if(checkMoney(StructureData.BUILDINGS.get(getLayoutPosition()))) {
                        activity.setActiveBuild(true, StructureData.BUILDINGS.get(getLayoutPosition()));
                    }else {
                        activity.setActiveBuild(false, null);
                    }
                }
            });
        }

        //checks and shows u if u have enough money to build a structure you have selected
        //Renders the text green or red depending on this
        public boolean checkMoney(Structure structure)
        {
            boolean enoughFunds = false;
            MapActivity activity = (MapActivity) getActivity();

            if(structure instanceof Utility){
                enoughFunds = true;
            }else if(structure instanceof Residential &&
                    activity.getGameData().getMoney() >=
                            activity.getGameData().getSettings().getHouseBuildingCost()) {
                enoughFunds = true;
            }else if(structure instanceof Commercial &&
                    activity.getGameData().getMoney() >=
                            activity.getGameData().getSettings().getCommBuildingCost()) {
                enoughFunds = true;
            }else if(structure instanceof Road &&
                    activity.getGameData().getMoney() >=
                            activity.getGameData().getSettings().getRoadBuildingCost()) {
                enoughFunds = true;
            }


            if(!enoughFunds){
                Context context = getContext();
                CharSequence text = "Not enough funds to build.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }

            bind(structure); // Sets structure to red or green

            return enoughFunds;
        }

        //Red text for cant afford green for vice versa, does not dynamically change
        //only if u scroll the view out of view and then bring it back, will it be correct colour
        //or if you click on it
        public void bind(Structure element)
        {
            MapActivity activity = (MapActivity) getActivity();
            structure.setImageResource(element.getImageId());
            desc.setText(element.getDescription());
            if(element instanceof Residential)
            {
                desc.setText(element.getDescription() +"\n" + "$" + activity.getGameData().getSettings().getHouseBuildingCost());
                if(activity.getGameData().getMoney() < activity.getGameData().getSettings().getHouseBuildingCost()) {
                    desc.setTextColor(Color.parseColor("#F11729"));
                } else {
                    desc.setTextColor(Color.parseColor("#17F147"));
                }
            }
            else if(element instanceof Commercial)
            {
                desc.setText(element.getDescription() +"\n" + "$" + activity.getGameData().getSettings().getCommBuildingCost());
                if(activity.getGameData().getMoney() < activity.getGameData().getSettings().getCommBuildingCost()) {
                    desc.setTextColor(Color.parseColor("#F11729"));
                } else{
                    desc.setTextColor(Color.parseColor("#17F147"));
                }
            }
            else if(element instanceof  Road)
            {
                desc.setText(element.getDescription() +"\n" + "$" + activity.getGameData().getSettings().getRoadBuildingCost());
                if(activity.getGameData().getMoney() < activity.getGameData().getSettings().getRoadBuildingCost()) {
                    desc.setTextColor(Color.parseColor("#F11729"));
                } else {
                    desc.setTextColor(Color.parseColor("#17F147"));
                }

            }
            else
            {
                desc.setText(element.getDescription() +"\n" + "$0");
                desc.setTextColor(Color.parseColor("#17F147"));
            }


        }
    }
}
