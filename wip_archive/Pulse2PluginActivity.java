package org.apache.cordova.pulse2;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;

import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.harman.pulsesdk.DeviceModel;
import com.harman.pulsesdk.PulseColor;
import com.harman.pulsesdk.PulseThemePattern;
import com.harman.pulsesdk.PulseHandlerInterface;
import com.harman.pulsesdk.PulseNotifiedListener;
import com.harman.pulsesdk.ImplementPulseHandler;

public class Pulse2PluginActivity extends Activity implements PulseNotifiedListener {
  public static final String Tag = "Pulse2PluginActivity";
  Timer mTimer=null;
  boolean isActive;
  boolean isConnectBT;
  Activity pulse2;
  public ImplementPulseHandler pulseHandler = new ImplementPulseHandler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
        this.Pulse2Plugin.runOnUiThread(new Runnable() {
          @Override
          public synchronized void run() {
            if (isActive) {
              pulseHandler.ConnectMasterDevice(this.Pulse2Plugin);
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
    LOG.i(Tag, "onConnectMasterDevice");
    isConnectBT = true;
    cancelTimer();
    //Toast.makeText(this, "onConnectMasterDevice", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDisconnectMasterDevice() {
    LOG.i(Tag, "onDisconnectMasterDevice");
    isConnectBT = false;
    setTimer();
    //Toast.makeText(this, "onDisconnectMasterDevice", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onLEDPatternChanged(PulseThemePattern pattern) {
    //Toast.makeText(this, "onLEDPatternChanged:" + pattern.name(), Toast.LENGTH_SHORT);
    LOG.i(Tag, "onLEDPatternChanged");

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
    LOG.i(Tag, "soundLevel:"+soundLevel);
//    runOnUiThread(new Runnable() {
//      @Override
//      public void run() {
//        //fragMic.setSoundValue(soundLevel);
//      }
//    });
  }

  @Override
  public void onRetCaptureColor(final PulseColor capturedColor) {
//        Toast.makeText(this,
//                "onRetCaptureColor: red=" + capturedColor.red + " green=" + capturedColor.green + " blue=" + capturedColor.blue,
//                Toast.LENGTH_SHORT);
//    runOnUiThread(new Runnable() {
//      @Override
//      public void run() {
//        pulseHandler.SetBackgroundColor(capturedColor, false);
//        //fragCamera.setPickColorVal(String.format("#%02x%02x%02x", capturedColor.red, capturedColor.green, capturedColor.blue));
//        LOG.i(Tag, "red:" + (((int)capturedColor.red)&0xff) + " green:" + (((int)capturedColor.green)&0xff) + " blue:" + (((int)capturedColor.blue)&0xff));
//      }
//    });
  }

  @Override
  public void onRetCaptureColor(byte red, byte green, byte blue) {
    //Toast.makeText(this, "onRetCaptureColor1: red=" + red + " green=" + green + " blue=" + blue, Toast.LENGTH_SHORT);
  }

  @Override
  public void onRetSetDeviceInfo(boolean ret) {
    //Toast.makeText(this, "onRetSetDeviceInfo:"+ret, Toast.LENGTH_SHORT);
  }

  @Override
  public void onRetGetLEDPattern(PulseThemePattern pattern) {
    //Toast.makeText(this, "onRetGetLEDPattern:" + (pattern== null ? "null":pattern.name()), Toast.LENGTH_SHORT);
  }

  @Override
  public void onRetRequestDeviceInfo(DeviceModel[] deviceModel) {
    //Toast.makeText(this, "onRetRequestDeviceInfo:"+deviceModel.toString(), Toast.LENGTH_SHORT);
  }

  @Override
  public void onRetSetLEDPattern(boolean b) {
    LOG.i(Tag, "onRetSetLEDPattern:"+b);
//    if(b && fragPattern != null && fragPattern.isBroadcastSlave){
//      pulseHandler.PropagateCurrentLedPattern();
//    }
  }

  @Override
  public void onRetBrightness(int i) {

  }

}
