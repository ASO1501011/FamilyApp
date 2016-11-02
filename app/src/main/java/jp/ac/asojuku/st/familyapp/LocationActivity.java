package jp.ac.asojuku.st.familyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.Time;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private Button button_start,button_stop;

    private TextView walk_log;
    private String text = "";

    private static final int MinTime = 1000;
    private static final float MinDistance = 50;

    private float move_result = 0.0f;
    private double latitude_before = 0;
    private double longitude_before = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //LocationManagerのインスタンス作成
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        //log出力のtextview取得
        walk_log = (TextView)findViewById(R.id.walk_log);

        //GPS測位開始
        button_start = (Button)findViewById(R.id.start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGPS();
            }
        });

        //GPSの測位終了
        button_stop = (Button)findViewById(R.id.stop);
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopGPS();
            }
        });

    }
    protected void startGPS(){
        Log.d("LocationActivity()","onResume()");

        text += "GPSの設定確認\n";
        walk_log.setText(text);

        Log.d("LocationActivity","gpsEnabled");
        final boolean gpsEnables = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsEnables){
            //GPSの設定を促す
            enableLocationSetting();
        }

        if(locationManager != null){
            Log.d("LocationActivity", "locationManager.requestLocationUpdates");
            //バックグラウンドから戻ってしまうと例外が発生する可能性がある
            try{
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MinTime,MinDistance,this);
            }catch(Exception e){
                e.printStackTrace();

                Toast.makeText(this, "例外が発生:位置情報のPermissionを許可していますか？", Toast.LENGTH_SHORT).show();

                //Activityに終了
                finish();
            }
        }else{
            Log.d("LocationActivity","locationManager=null");
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        if(locationManager != null){
            Log.d("LocationActivity","locationmanager.removeUpdates");
            //updateを止める
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
            locationManager.removeUpdates(this);
        }else{
            Log.d("LocationActivity","onPause()");
        }

        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LocationActivity","Latitude=" + String.valueOf(location.getLatitude()));
        Log.d("LocationActivity","Longitude=" + String.valueOf(location.getLongitude()));
        Log.d("LocationActivity","Accuracy=" + String.valueOf(location.getAccuracy()));
        Log.d("LocationActivity","Altitude=" + String.valueOf(location.getAltitude()));
        Log.d("LocationActivity","Time=" + String.valueOf(location.getTime()));
        Log.d("LocationActivity","Speed=" + String.valueOf(location.getSpeed()));
        Log.d("LocationActivity","Bearing=" + String.valueOf(location.getBearing()));

        //0に距離 1に始点から終点までの方位角 2に終点から始点までの方位角が入る
        float[] results = new float[3];
        Location.distanceBetween(latitude_before, longitude_before, location.getLatitude(), location.getLongitude(), results);

        if(!(latitude_before == 0 && longitude_before == 0))move_result += results[0];

        text += "位置情報取得\n";
        text += "緯度" + String.valueOf(location.getLatitude()) + "経度" + String.valueOf(location.getLongitude()) + "\n";
        text += "移動距離" + String.format("%.2f",move_result/1000) + "\n";
        walk_log.setText(text);

        latitude_before = location.getLatitude();
        longitude_before = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch(status){
            case LocationProvider.AVAILABLE:
                Log.d("LocationActivity","LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("LocationActivity","LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("LocationActivity","LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    private void enableLocationSetting(){
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

    private void stopGPS(){
        if(locationManager != null){
            Log.d("LocationActivity","onStop()");
            //updateを止める
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
            locationManager.removeUpdates(this);

            text += "GPS測定停止\n";
            walk_log.setText(text);

            //mail送信処理
            Resources res = getResources();
            Uri uri = Uri.parse("mailto:" + res.getString(R.string.mail_to).toString());
            Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
            Time time = new  Time("Asia/Tokyo");
            time.setToNow();
            String date = time.year + "年" + (time.month+1) + "月" + time.monthDay + "日　" + time.hour + "時";
            intent.putExtra(Intent.EXTRA_SUBJECT, date);
            intent.putExtra(Intent.EXTRA_TEXT,String.format("%.1f",move_result/1000) + "km歩きました");
            startActivity(intent);
        }else{
            Log.d("LocationActivity","onStop()");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopGPS();
    }

}
