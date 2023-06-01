package curtin.edu.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;
//Author Matthew Matar
//Last mod 31 / 10 / 2019
//I believe this is overly complicated for what it is
public class DetailsActivity extends AppCompatActivity
{
    Button backButton, confirm;
    MapElement structure;
    MapElement[][] map;
    TextView description, type, grid;
    ImageView tick;
    int row, col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setValues();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Dosent show a confirm button until after a user clicks to enter a new text
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm.setVisibility(View.VISIBLE);
            }
        });


       confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                structure.getStructure().setDescription((description.getText()).toString());
                sendBackStructure();
                map[row][col] = structure;

                //Gives user a visible indication that their new name has worked
                tick.setVisibility(View.VISIBLE);
                Timer fade = new Timer(false);
                fade.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tick.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                },800);
            }
       });
    }

    //Makes onCreate a lot cleaner
    private void setValues()
    {
        structure = (MapElement) getIntent().getSerializableExtra("TILE");
        map = (MapElement[][]) getIntent().getSerializableExtra("MAP");
        row = getIntent().getIntExtra("ROW", 0);
        col = getIntent().getIntExtra("COL", 0);
        grid = (TextView) findViewById(R.id.grid);
        type = (TextView) findViewById(R.id.classType);
        description = (TextView) findViewById(R.id.editDescription);
        tick = (ImageView) findViewById(R.id.tick);
        backButton = (Button) findViewById(R.id.backButton);
        confirm = (Button) findViewById(R.id.changeText);
        confirm.setVisibility(View.INVISIBLE);
        description.setHint(structure.getStructure().getDescription());
        type.setText(structure.getStructure().getClassID());
        grid.setText("Row : " + row + "\nColumn : " + col);
    }

    //Sends back to mapactivity with the changes to add to the map
    private void sendBackStructure()
    {
        Intent toMap = new Intent(DetailsActivity.this,MapActivity.class);
        toMap.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toMap.putExtra("Changed Map", map);
        setResult(RESULT_OK,toMap);
        finishActivity(5);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sendBackStructure();

    }
}
