package curtin.edu.assignment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Simple game over screen when $ goes below 0, appears only once
public class GameOverActivity extends AppCompatActivity
{
    Button backToMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        backToMap = (Button) findViewById(R.id.butoon);

        backToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
