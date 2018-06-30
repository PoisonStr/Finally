package progressdialogtest.example.com.afinal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class StartPhoneBoradCase extends AppCompatActivity {

    private static final String ACTION_BOOT ="StartPhoneBoardCase" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_phone_borad_case);

    }
    public void onReceive(Context context, Intent intent) {
        if(ACTION_BOOT.equals(intent.getAction())){
            //检测到系统开机
            Toast.makeText(context,"已开机",Toast.LENGTH_LONG).show();
            Intent service=new Intent();
            service.setClass(context, MyService.class);
            service.setClass(context,SensorManagerHelper.class);
            context.startService(service);
        }
    }
}
