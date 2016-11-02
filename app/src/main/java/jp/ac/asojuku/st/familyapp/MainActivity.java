package jp.ac.asojuku.st.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button roulette_S,gps_S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roulette_S = (Button)findViewById(R.id.roulette_start);
        roulette_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_roulette();
            }
        });
        gps_S = (Button)findViewById(R.id.gps_start);
        gps_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_walk();
            }
        });
    }

    private void start_roulette(){
        Log.d("MainActivity","start_roulette()");
        Intent intent = new Intent(getApplication(),RouletteActivity.class);
        startActivity(intent);
    }

    private void start_walk(){
        Log.d("MainActivity","start_walk()");
        Intent intent = new Intent(getApplication(),LocationCheckActivity.class);
        startActivity(intent);
    }


}
