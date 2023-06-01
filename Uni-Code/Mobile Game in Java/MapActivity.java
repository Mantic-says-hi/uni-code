package curtin.edu.assignment;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.min;

//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Based on my work for prac 3 and lecture slides however is not entierly the same
//Lots of data types i.e. for checking if the settings have changed,
//                        checking if there is an active construction,
//                        game over or detail event
public class MapActivity extends AppCompatActivity {

    private MediaPlayer     gameMusic;
    private FragmentManager fragmentManager;
    private static GameData gameData;
    private Structure       activeStructure = null;
    private Map             mapFragment;
    private Selector        selectorFragment;
    private static TextView cash, time, popu, empl, diff;
    private Button          timeElapsed, detail;
    private static int      tempTime,savedHeight,savedWidth,
                            population = 0,nResidential = 0,nCommercial = 0;
    private int             hour = 0, min = 0;
    private static double   employmentRate = 0.0;
    private boolean         activeConstruction = false, detailStatus = false,
                            gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        gameMusic = MediaPlayer.create(this, R.raw.moosik);
        gameData = (GameData) getIntent().getSerializableExtra("Game Data");
        gameMusic.start();
        gameMusic.setLooping(true);
        renderMap();
        renderSelector();
        setLayout();

        //The "RUN" or move forward in time button to calculate new money
        timeElapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewTime();
                newDayMoney();
            }
        });

        //Sets up the detail status when the "?" detail button is pressed
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDetailStatus(true);
            }
        });


    }

    //Updates all the icon's values on the top of the screen when event happens i.e building made/removed
    public void updateVariables() {
        population = gameData.getSettings().getFamiliySize() * nResidential;
        if(population != 0) {
            employmentRate = min(1.0, ((nCommercial * gameData.getSettings().getShopSize()) / (double) population));
        }
        DecimalFormat percent = new DecimalFormat("##.##%");
        empl.setText(percent.format(employmentRate));
        popu.setText(Integer.toString(population));
        cash.setText(Integer.toString(gameData.getMoney()));
    }

    //Sets the layout up, to make onCreate less cluttered
    private void setLayout() {
        cash = (TextView) findViewById(R.id.money);
        time = (TextView) findViewById(R.id.time);
        popu = (TextView) findViewById(R.id.population);
        empl = (TextView) findViewById(R.id.employment);
        diff = (TextView) findViewById(R.id.difference);
        timeElapsed = (Button) findViewById(R.id.timeWarp);
        detail = (Button) findViewById(R.id.details);
        time.setText(String.format("%02d:%02d",hour,min));
        cash.setText(Integer.toString(gameData.getMoney()));
        popu.setText(Integer.toString(population));
        DecimalFormat percent = new DecimalFormat("##.##%");
        empl.setText(percent.format(employmentRate));
    }

    //Will reset all stored values for the map screen
    private void reset() {
        cash.setText(Integer.toString(gameData.getMoney()));
        tempTime = 0;
        setTime();
        population = 0;
        nResidential = 0;
        nCommercial = 0;
        hour = 0;
        min = 0;
        employmentRate = 0.0;
    }

    //Takes away the cost of a house from the total money when a house is built
    public void setNewResident(int newHouse)
    {
        if(newHouse > nResidential){
            addExpendeture(gameData.getSettings().getHouseBuildingCost());
        }
        nResidential = newHouse;
        updateVariables();
    }

    //Takes away the cost of a comm building from the total money when a comm building is built
    public void setNewCommercial(int newCommercial)
    {
        if(newCommercial > nCommercial){
            addExpendeture(gameData.getSettings().getCommBuildingCost());
        }
        nCommercial = newCommercial;

        updateVariables();
    }

    //Shows the difference of the most recent cost incurred
    public void addExpendeture(int expenditure)
    {
        int money = gameData.getMoney();
        int difference = money;
        money -= expenditure;
        gameData.setMoney(money);
        cash.setText(Integer.toString(gameData.getMoney()));
        difference = money - difference;
        showCashChange(difference);
    }

    //code for calculating new money for next time increment also checks if game is over
    public void newDayMoney() {
        int money = gameData.getMoney();
        int difference = money;

        money += (int) (population * (employmentRate * //Always rounded down
                 gameData.getSettings().getSalary() *
                 gameData.getSettings().getTaxRate() -
                 gameData.getSettings().getServiceCost()));
        gameData.setMoney(money);
        cash.setText(Integer.toString(gameData.getMoney()));
        difference = money - difference;
        showCashChange(difference);
        if(money < 0 && !gameOver) {
            Intent intent = new Intent(MapActivity.this, GameOverActivity.class);
            startActivity(intent);
            gameOver = true;
        }
    }


    public void setDetailStatus(boolean set)
    {
        detailStatus = set;
    }

    public boolean getDetailStatus()
    {
        return detailStatus;
    }

    public GameData getGameData()
    {
        return gameData;
    }

    //Shows either green or red numbers under the total money when money changes
    private void showCashChange(int addedMoney)
    {
        diff.setVisibility(View.VISIBLE);
        if (addedMoney >= 0 ){
            diff.setTextColor(Color.parseColor("#17F147"));
            diff.setText("+" + Integer.toString(addedMoney));
        }else {
            diff.setTextColor(Color.parseColor("#F11729"));
            diff.setText(Integer.toString(addedMoney));
        }

        Timer fade = new Timer(false);
        fade.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        diff.setVisibility(View.INVISIBLE);
                    }
                });
            }
        },800);

    }

    //Sets the new time on the clock after a time increment has increased
    private void setNewTime() {
        String defaultTime = "00:00";
        tempTime = gameData.getGameTime();
        tempTime++;
        gameData.setGameTime(tempTime);
        min = tempTime % 60 + Integer.parseInt(defaultTime.substring(3,4));
        hour = tempTime / 60 + Integer.parseInt(defaultTime.substring(0,1));
        time.setText(String.format("%02d:%02d",hour,min));
    }

    //sets to default time
    private void setTime(){
        String defaultTime = "00:00";
        tempTime = gameData.getGameTime();
        min = tempTime % 60 + Integer.parseInt(defaultTime.substring(3,4));
        hour = tempTime / 60 + Integer.parseInt(defaultTime.substring(0,1));
        time.setText(String.format("%02d:%02d",hour,min));
    }

    //Creates the map fragment
    private void renderMap() {
        Bundle b = new Bundle();
        b.putSerializable("MAP DATA", gameData);

        fragmentManager = getSupportFragmentManager();
        mapFragment = (Map) fragmentManager.findFragmentById(R.id.map);
        if(mapFragment == null) {
            mapFragment = new Map();
            mapFragment.setArguments(b);
            fragmentManager.beginTransaction()
                    .add(R.id.map, mapFragment)
                    .commit();
        }
    }

    //Creates the selector fragment
    private void renderSelector() {
        fragmentManager = getSupportFragmentManager();
        selectorFragment = (Selector) fragmentManager.findFragmentById(R.id.selector);
        if(selectorFragment == null) {
            selectorFragment = new Selector();
            fragmentManager.beginTransaction()
                    .add(R.id.selector, selectorFragment)
                    .commit();
        }
    }

    //When a user selects a building they can build this remembers what they wanted before they
    //click the map to add it
    public void setActiveBuild(boolean isActive, Structure buildType)
    {
        activeConstruction = isActive;
        activeStructure = buildType;
    }

    public boolean getActiveConstruction()
    {
        return activeConstruction;
    }

    public Structure getActiveStructure()
    {
        return activeStructure;
    }

    //Part of the details process of updating the map with the new description on a structure
    //Result code 5 for map changes from (DetailsActivity)
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5){
            if (resultCode == RESULT_OK) {
                MapElement[][] map = (MapElement[][]) data.getSerializableExtra("Changed Map");

                Bundle newMap = new Bundle();
                newMap.putSerializable("Updated Map", map);

                mapFragment.setMap(map);
            }
        }
    }

    //Keeps track of the height and width, because it can be changed in the settings of main screen
    @Override
    protected void onPause() {
        super.onPause();
        gameMusic.pause();
        savedHeight = gameData.getSettings().getMapHeight();
        savedWidth = gameData.getSettings().getMapWidth();

    }

    //Makes sure the variables are correct on resuming
    @Override
    protected void onResume() {
        super.onResume();
        gameMusic.start();
        gameMusic.setLooping(true);
        if (savedHeight != gameData.getSettings().getMapHeight() ||
            savedWidth  != gameData.getSettings().getMapWidth() ) {
            reset();
            setTime();
            updateVariables();
        }else {
            cash.setText(Integer.toString(gameData.getMoney()));
            setTime();
            updateVariables();
        }
        detailStatus = false;
    }

    //Sends the game data back to main so the settings can use it, was also meant to use that for
    //the database but didnt figure it out
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("Current Game", gameData);
        setResult(RESULT_OK,intent);
        finish();
    }
}

