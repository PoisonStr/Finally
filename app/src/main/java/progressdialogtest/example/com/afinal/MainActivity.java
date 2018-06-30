package progressdialogtest.example.com.afinal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView start;
    private MediaRecorder mediaRecorder;
    private SensorManager sensorManager;
    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        final SensorManagerHelper sensorHelper = new SensorManagerHelper(this);
        sensorHelper.setOnShakeListener(new SensorManagerHelper.OnShakeListener() {
            @Override
            public void onShake() {
                System.out.println("shake");
                Toast.makeText(MainActivity.this, "手机摇动", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSensorChanged( SensorEvent event){
                // TODO Auto-generated method stub
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                            mediaRecorder.start();
                        }
                }
            });
        };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private static final int READ_PHONE_STATE = 1;
    private static String[] CALLS_STATE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    public static void verifyStoragePermissions(Activity activity) {

        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, CALLS_STATE, READ_PHONE_STATE);
        }
    }
}
