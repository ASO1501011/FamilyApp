package jp.ac.asojuku.st.familyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class RouletteActivity extends AppCompatActivity {

    private Button button_R;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        //縦スクロール
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llManager);

        ArrayList<Move_Distance_Data> move_distances = new ArrayList<Move_Distance_Data>();
        for(int i = 0;i < MyData.commentArray.length; i++) {
            move_distances.add(new Move_Distance_Data(
                    MyData.numberArray[i],
                    MyData.additionArray[i],
                    MyData.commentArray[i]
            ));
        }

        RecyclerView.Adapter adapter = new Move_Distance_Adapter(move_distances);
        recyclerView.setAdapter(adapter);
        recyclerView.smoothScrollToPosition(move_distances.size() - 1);

        button_R = (Button)findViewById(R.id.Button_R);
        button_R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
