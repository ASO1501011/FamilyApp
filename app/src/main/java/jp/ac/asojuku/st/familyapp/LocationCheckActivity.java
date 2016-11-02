package jp.ac.asojuku.st.familyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Len-R on 2016/10/28.
 */

public class LocationCheckActivity extends AppCompatActivity{

    private final int REQUEST_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Android6.0以上,API23以上でパーミッションの確認
        if(Build.VERSION.SDK_INT >= 23){
            checkPermission();
        }else{
            locationActivity();
        }
    }
    //位置情報の許可の確認
    public void checkPermission(){
        //既に許可している
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            locationActivity();
            //拒否していた場合
        }else{
            requestLocationPermissions();
        }
    }

    //許可を求める
    private void requestLocationPermissions(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            ActivityCompat.requestPermissions(LocationCheckActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION);
        }else{
            Toast.makeText(this,"許可されないと実行できません",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(LocationCheckActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION);
        }

    }

    //結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION){
            //使用が許可された
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                locationActivity();
                return;
            }else{
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this, "これ以上なにもできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void locationActivity(){
        Intent intent = new Intent(getApplication(), LocationActivity.class);
        startActivity(intent);
    }

}
