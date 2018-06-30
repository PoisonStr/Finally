package progressdialogtest.example.com.afinal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.nfc.Tag;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MyService extends Service {
    public static  final String TAG="MyService";
    @Override
    public void onCreate() {
        super.onCreate();
        //获取电话管理器对象
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //设置电话监听器，监听电话状态
        telephonyManager.listen(new MyTelephoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //定义监听内部类实现监听录音
    class MyTelephoneListener extends PhoneStateListener {
        private MediaRecorder recorder;
        private boolean record;
        private File audioFile;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//从麦克风采集声音
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //内容输出格式
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //输出到缓存目录，此处可以添加上传录音的功能，也可以存到其他位置
                    audioFile = new File(getCacheDir(), "/recoderdemo/"  + System.currentTimeMillis() + ".3gp");
                    recorder.setOutputFile(audioFile.getAbsolutePath());
                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    recorder.start();
                    record = true;
                    Log.i(TAG, "电话已经摘机");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    String mobile = incomingNumber;
                    Log.i(TAG, "电话已响铃");
                    Log.i(TAG, mobile + "来电");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (record) {
                        recorder.stop(); //停止刻录
                        recorder.release(); //释放资源
                        Log.i(TAG, "电话空闲");
                        record = false;
                    }
                    break;
            }
        }


        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
