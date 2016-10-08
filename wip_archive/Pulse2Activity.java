package org.apache.cordova.pulse2;
// This entire file makes me want to do terrible things

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.app.RemoteInput;
import android.app.Fragment;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.harman.pulsesdk.DeviceModel;
import com.harman.pulsesdk.ImplementPulseHandler;
import com.harman.pulsesdk.PulseColor;
import com.harman.pulsesdk.PulseNotifiedListener;
import com.harman.pulsesdk.PulseThemePattern;



public class Pulse2Activity extends AppCompatActivity implements PulseNotifiedListener {
    public static final String NOT_ID = "notId";
    private static String LOG_TAG = "Pulse2Plugin_Pulse2Activity";    
    private ArrayList<Fragment> fragments;
    //FragColorSplash fragColor;
    //FragPattern fragPattern;
    //FragMic fragMic;
    //FragCamera fragCamera;
    //FragChar fragChar;
    ArrayList<Map<String, Object>> adaptParam;
    //MyFragPagerAdapter vpAdapter;
    static int mWidth, mHeight, statusBarHeight,realHeight, mDensityInt;
    static float scale, mDensity;
    boolean isActive;
    static int FRAG_COLOR_ID = 0, FRAG_PATTERN_ID = 1, FRAG_MIC_ID = 2, FRAG_CAMERA_ID = 3, FRAG_CHAR_ID = 4;
    Timer mTimer=null;
    boolean isConnectBT;    
    public ImplementPulseHandler pulseHandler = new ImplementPulseHandler();

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate");

        pulseHandler.ConnectMasterDevice(this);
        pulseHandler.registerPulseNotifiedListener(this);
        isActive = true;
        setTimer();
    }
    
    public void setTimer()
    {
        if(mTimer!=null)
            return;

        mTimer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run() {
                Pulse2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        if (isActive) {
                            pulseHandler.ConnectMasterDevice(Pulse2Activity.this);
                        }
                    }
                });
            }
        };
        mTimer.schedule(task, 1000, 1500);
    }

    private void cancelTimer()
    {
        if(mTimer!=null)
        {
            mTimer.cancel();
            mTimer=null;
        }
    }

    @Override
    public void onConnectMasterDevice() {
        Log.i(LOG_TAG, "onConnectMasterDevice");
        isConnectBT = true;
        cancelTimer();
        Toast.makeText(this, "onConnectMasterDevice", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnectMasterDevice() {
        Log.i(LOG_TAG, "onDisconnectMasterDevice");
        isConnectBT = false;
        setTimer();
        Toast.makeText(this, "onDisconnectMasterDevice", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLEDPatternChanged(PulseThemePattern pattern) {
        //Toast.makeText(this, "onLEDPatternChanged:" + pattern.name(), Toast.LENGTH_SHORT);
        Log.i(LOG_TAG, "onLEDPatternChanged");

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }

    @Override
    public void onSoundEvent(final int soundLevel) {
        //Toast.makeText(this, "onSoundEvent: level=" + soundLevel, Toast.LENGTH_SHORT);
        Log.i(LOG_TAG, "soundLevel:"+soundLevel);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //fragMic.setSoundValue(soundLevel);
            }
        });
    }

    @Override
    public void onRetCaptureColor(final PulseColor capturedColor) {
//        Toast.makeText(this,
//                "onRetCaptureColor: red=" + capturedColor.red + " green=" + capturedColor.green + " blue=" + capturedColor.blue,
//                Toast.LENGTH_SHORT);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pulseHandler.SetBackgroundColor(capturedColor, false);
                //fragCamera.setPickColorVal(String.format("#%02x%02x%02x", capturedColor.red, capturedColor.green, capturedColor.blue));
                Log.i(LOG_TAG, "red:" + (((int)capturedColor.red)&0xff) + " green:" + (((int)capturedColor.green)&0xff) + " blue:" + (((int)capturedColor.blue)&0xff));
            }
        });
    }

    @Override
    public void onRetCaptureColor(byte red, byte green, byte blue) {
        Toast.makeText(this, "onRetCaptureColor1: red=" + red + " green=" + green + " blue=" + blue, Toast.LENGTH_SHORT);
    }

    @Override
    public void onRetSetDeviceInfo(boolean ret) {
        Toast.makeText(this, "onRetSetDeviceInfo:"+ret, Toast.LENGTH_SHORT);
    }

    @Override
    public void onRetGetLEDPattern(PulseThemePattern pattern) {
        Toast.makeText(this, "onRetGetLEDPattern:" + (pattern== null ? "null":pattern.name()), Toast.LENGTH_SHORT);
    }

    @Override
    public void onRetRequestDeviceInfo(DeviceModel[] deviceModel) {
        Toast.makeText(this, "onRetRequestDeviceInfo:"+deviceModel.toString(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onRetSetLEDPattern(boolean b) {
        Log.i(LOG_TAG, "onRetSetLEDPattern:"+b);
        //if(b && fragPattern != null && fragPattern.isBroadcastSlave){
        //    pulseHandler.PropagateCurrentLedPattern();
        //}
    }

    @Override
    public void onRetBrightness(int i) {

    }

   

    /**
     * Forces the main activity to re-launch if it's unloaded.
     */
    private void forceMainActivityReload() {
        PackageManager pm = getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
        startActivity(launchIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.cancelAll();
    }
}