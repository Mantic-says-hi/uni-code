package curtin.edu.assignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.CharArrayReader;
//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Activity for the settings, shows the current settings as a hint
//Guide for what settings u can change urself and also
//hard medium and easy option to quickly enter a game
public class SettingsActivity extends AppCompatActivity {

    CharSequence text;
    Settings settings;
    TextView inWidth, inHeight, inMoney, setting,
             hard, medium, easy;
    Button   confirm, back;
    Context  context;
    Toast    toast;
    int      width, height, money, duration = Toast.LENGTH_LONG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = (Settings) getIntent().getSerializableExtra("Game Settings");
        if(getIntent().getIntExtra("NEW SETTING", 1) == 1) {
            settings = new Settings();
        }
        setUp();

        //Lots of checks to make sure the data is usable for the game
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInt() == true) {

                    width = Integer.valueOf(inWidth.getText().toString());
                    height = Integer.valueOf(inHeight.getText().toString());
                    money = Integer.valueOf(inMoney.getText().toString());

                    if (height > width)
                    {
                        text = "Error : Height must be less than width";
                        toast = Toast.makeText(context,text,duration);
                        toast.show();
                    }else if (money < 620){
                        text = "Error : Not enough money to start game";
                        toast = Toast.makeText(context,text,duration);
                        toast.show();
                    }else if (height > 25){
                        text = "Error : Height must be under 25";
                        toast = Toast.makeText(context,text,duration);
                        toast.show();
                    }else if (width > 100){
                        text = "Error : Height must be under 100";
                        toast = Toast.makeText(context,text,duration);
                        toast.show();
                    }else if (width < 1 || height < 1){
                        text = "Don't be silly...";
                        toast = Toast.makeText(context,text,duration);
                        toast.show();
                    }else {
                        settings.setMapWidth(width);
                        settings.setMapHeight(height);
                        settings.setInitialMoney(money);

                        Intent toMain = new Intent(SettingsActivity.this, MainActivity.class);
                        toMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        toMain.putExtra("Settings", settings);
                        toMain.putExtra("START", 1);
                        setResult(RESULT_OK, toMain);
                        finish();
                    }
                }
                else
                {
                    text = "Error : Enter numbers in all 3 categories";
                    toast = Toast.makeText(context,text,duration);
                    toast.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Settings", settings);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.setMapWidth(15);
                settings.setMapHeight(10);
                settings.setInitialMoney(620);

                Intent toMain = new Intent(SettingsActivity.this, MainActivity.class);
                toMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                toMain.putExtra("Settings", settings);
                toMain.putExtra("START", 1);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.setMapWidth(30);
                settings.setMapHeight(15);
                settings.setInitialMoney(1800);

                Intent toMain = new Intent(SettingsActivity.this, MainActivity.class);
                toMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                toMain.putExtra("Settings", settings);
                toMain.putExtra("START", 1);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.setMapWidth(50);
                settings.setMapHeight(20);
                settings.setInitialMoney(4500);

                Intent toMain = new Intent(SettingsActivity.this, MainActivity.class);
                toMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                toMain.putExtra("Settings", settings);
                toMain.putExtra("START", 1);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });


    }


    //declutters onCreate
    private void setUp()
    {
        setting   = (TextView) findViewById(R.id.settings);
        inWidth   = (TextView) findViewById(R.id.widthEntry);
        inHeight  = (TextView) findViewById(R.id.heightEntry);
        inMoney   = (TextView) findViewById(R.id.moneyEntry);
        hard      = (TextView) findViewById(R.id.hard);
        medium    = (TextView) findViewById(R.id.medium);
        easy      = (TextView) findViewById(R.id.easy);
        confirm   = (Button) findViewById(R.id.confirmation);
        back      = (Button) findViewById(R.id.back);
        inWidth.setHint(Integer.toString(settings.getMapWidth()));
        inHeight.setHint(Integer.toString(settings.getMapHeight()));
        inMoney.setHint(Integer.toString(settings.getInitialMoney()));
        context = getApplicationContext();
    }

    //sends the settings back to main so it can send it to the map activity
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("Settings", settings);
        setResult(RESULT_OK,intent);
        finish();
    }

    //Double check to make sure all variables are ints
    public boolean isInt()
    {
        boolean check = false;
        try {
            Integer.parseInt(inWidth.getText().toString());
            Integer.parseInt(inHeight.getText().toString());
            Integer.parseInt(inMoney.getText().toString());
            check = true;
        }
        catch(Exception e) {
        }
        return check;
    }
}
