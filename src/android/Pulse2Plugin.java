package org.apache.cordova.pulse2;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;

import android.app.Activity;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import com.harman.pulsesdk.DeviceModel;
import com.harman.pulsesdk.ImplementPulseHandler;
import com.harman.pulsesdk.PulseColor;
import com.harman.pulsesdk.PulseNotifiedListener;
import com.harman.pulsesdk.PulseThemePattern;

public class Pulse2Plugin extends CordovaPlugin {
    public static final String INITIALIZE = "init";
    public static final String CONNECT = "connect";
    public static final String Tag = "Pulse2Plugin";
    private static CordovaWebView gWebView;
    private static Bundle gCachedExtras = null;
    private static boolean gForeground = false;
    private static CallbackContext pulseContext;

    /**
     * Gets the application context from cordova's main activity.
     * @return the application context
     */
    private Context getApplicationContext() {
        return this.cordova.getActivity().getApplicationContext();
    }

    /**
     * Gets the application main activity.
     * @return the application main activity
     */
    private Activity getApplicationActivity() {
        return this.cordova.getActivity();
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        gForeground = true;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(Tag, "We are entering execute");
        pulseContext = callbackContext;

        if (INITIALIZE.equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    ImplementPulseHandler pulseHandler = new ImplementPulseHandler();
                    Activity activity = cordova.getActivity();
                    pulseHandler.ConnectMasterDevice(activity);
                }
            });
        } else if (CONNECT.equals(action)) {
            // pulseHandler.SetBackgroundColor(new PulseColor((byte)255, (byte)255, (byte)255), false);
            pulseContext.success();
            //pulseHandler.registerPulseNotifiedListener(getApplicationActivity());
        }
        return true;
    }
}
