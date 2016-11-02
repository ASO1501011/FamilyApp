package jp.ac.asojuku.st.familyapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Len-R on 2016/10/28.
 */

public class Move_Distance_Adapter extends RecyclerView.Adapter<Move_Distance_Holder>{

    private ArrayList<Move_Distance_Data> rouletteDataSet;

    public Move_Distance_Adapter(ArrayList<Move_Distance_Data> roulette){

        this.rouletteDataSet = roulette;
    }

    //新しいViewを作成する
    //レイアウトマネージャーにより再起動される
    @Override
    public Move_Distance_Holder onCreateViewHolder(ViewGroup parent, int viewType){
        //parentはRecyclerView
        //public View inflate (int resources, ViewGroup root, boolean attachToRoot)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout,parent,false);

        return new Move_Distance_Holder(view);
    }
    //Viewの内容を交換する(リサイクルだから)
    //レイアウトマネージャーにより起動される

    @Override
    public void onBindViewHolder(final Move_Distance_Holder holder, final int listPosition){

        holder.textViewNumber.setText(rouletteDataSet.get(listPosition).getnumber() + "km");
        holder.textViewComment.setText(rouletteDataSet.get(listPosition).getComment());
        holder.base.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //vはcardView
                Toast.makeText(v.getContext(), "おまけ" + rouletteDataSet.get(listPosition).getAddition() + "km", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount(){
        return rouletteDataSet.size();
    }
}
