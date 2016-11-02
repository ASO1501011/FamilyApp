package jp.ac.asojuku.st.familyapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Len-R on 2016/10/28.
 */

public class Move_Distance_Holder extends RecyclerView.ViewHolder{

    View base;
    TextView textViewNumber;
    TextView textViewComment;

    public Move_Distance_Holder(View v){
        super(v);
        this.base = v;
        this.textViewNumber = (TextView)v.findViewById(R.id.number);
        this.textViewComment = (TextView)v.findViewById(R.id.comment);
    }
}
