package curtin.edu.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import curtin.edu.assignment.DataSchema.GameTable;
import curtin.edu.assignment.DataSchema.SettingTable;
import curtin.edu.assignment.DataSchema.MapTable;
import curtin.edu.assignment.DataSchema.StructureTable;
//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Main activity, where everything starts and databases do not get made
public class MainActivity extends AppCompatActivity
{

    Button settings,
           start;
    MediaPlayer menuMusic;
    public static Settings startingSetting = null;
    public static GameData gameData = null;
    private SQLiteDatabase db;
    public void load(Context context)
    {
        this.db = new DataDbHelper(context.getApplicationContext()).getWritableDatabase();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuMusic = MediaPlayer.create(this, R.raw.menu);
        settings = (Button) findViewById(R.id.settingsButton);
        start    = (Button) findViewById(R.id.startButton);


        menuMusic.start();
        menuMusic.setLooping(true);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                if (startingSetting == null) {
                    intent.putExtra("NEW SETTING", 1);
                }
                else {
                    intent.putExtra("NEW SETTING" , 0);
                }
                intent.putExtra("Game Settings", startingSetting);
                startActivityForResult(intent, 1);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startingSetting != null){
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    if(gameData == null) {
                        createGameData();
                    }
                    intent.putExtra("Game Data", gameData);
                    startActivityForResult(intent, 2);
                }else{
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    if (startingSetting == null) {
                        intent.putExtra("NEW SETTING",1);
                    }
                    else {
                        intent.putExtra("NEW SETTING" , 0);
                    }
                    intent.putExtra("Game Settings", startingSetting);
                    startActivityForResult(intent, 1);
                }

            }
        });
    }

    public void createGameData()
    {
        gameData = gameData.startGame();
        gameData.setSettings(startingSetting);
        gameData.setMap(gameData.createMap());
        gameData.setMoney(startingSetting.initialMoney);

    }

    //Gets the settings from request 1 (SettingsActivity)
    //Gets the game data from request 2 (MapActivity)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent set)
    {
        super.onActivityResult(requestCode, resultCode,set);
        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                startingSetting = (Settings) set.getSerializableExtra("Settings");
                createGameData();
                if (set.getIntExtra("START", 0) == 1){
                    Intent map = new Intent(MainActivity.this, MapActivity.class);
                    map.putExtra("Game Data", gameData);
                    startActivityForResult(map, 2);
                }
            }
        } else if (requestCode == 2){
            if (resultCode == RESULT_OK) {
                gameData = (GameData) set.getSerializableExtra("Current Game");
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        menuMusic.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        menuMusic.start();
        menuMusic.setLooping(true);
    }

    //all and below is for my failed database
    public void addStruc(Structure structure)
    {
        ContentValues cv = new ContentValues();
        cv.put(StructureTable.Cols.IMAGE,structure.getImageId());
        cv.put(StructureTable.Cols.DESC,structure.getDescription());
        db.insert(StructureTable.NAME,null,cv);
    }

    public void addSetting(Settings settings)
    {
        ContentValues cv = new ContentValues();
        cv.put(SettingTable.Cols.WIDTH,settings.getMapWidth());
        cv.put(SettingTable.Cols.HEIGHT,settings.getMapHeight());
        cv.put(SettingTable.Cols.INMONEY,settings.getInitialMoney());
        db.insert(SettingTable.NAME,null,cv);
    }

    public void addMap(MapElement mapElement)
    {
        ContentValues cv = new ContentValues();
        cv.put(MapTable.Cols.TILE,mapElement.getGroundTile());
        db.insert(MapTable.NAME,null,cv);
    }

    public void addGame(GameData data)
    {
        ContentValues cv = new ContentValues();
        cv.put(GameTable.Cols.MONEY,data.getMoney());
        cv.put(GameTable.Cols.TIME,data.getGameTime());
        db.insert(GameTable.NAME,null,cv);
    }
}
