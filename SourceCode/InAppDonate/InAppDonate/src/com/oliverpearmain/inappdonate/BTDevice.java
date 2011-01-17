package com.oliverpearmain.inappdonate;

import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class BTDevice {
     private static final String TAG = "BTDevice";

     public static final String ENABLED_ACTION = "android.bluetooth.intent.action.ENABLED";
     public static final String DISABLED_ACTION = "android.bluetooth.intent.action.DISABLED";

     private final Context mContext;
     private Object mDevice;
     private Method mEnable;
     private Method mDisable;
     private Method mIsEnabled;
     private IntentFilter mIntentFilter;
     private boolean mWaitState;
     private boolean mIsBusy = false;

     public BTDevice(Context context) {
     		String debug = "1";
          mContext = context;
          debug = "2";
          try {
               mDevice = mContext.getSystemService("bluetooth");
               debug = "3";
               if (mDevice != null) {
                   debug = "4";
                    Class<?> c = mDevice.getClass();
                    mEnable = c.getMethod("enable");
                    mEnable.setAccessible(true);
                    mDisable = c.getMethod("disable");
                    mDisable.setAccessible(true);
                    mIsEnabled = c.getMethod("isEnabled");
                    mIsEnabled.setAccessible(true);
               }
               else
               {
            	   DisplayToast("mDevice was null, trying to get alternative");

                   mDevice = mContext.getSystemService("android.bluetooth.BluetoothAdapter");
                   if (mDevice != null) 
                   {
                	   DisplayToast("Fuck me sideways");
                   }
                   else
                   {
                	   DisplayToast("mDevice was STILL null");
                   }
                   
               }
               debug = "5";
               mIntentFilter = new IntentFilter(BTDevice.ENABLED_ACTION);
               debug = "6";
               mIntentFilter.addAction(BTDevice.DISABLED_ACTION);
               debug = "7";
               mIsBusy = false;
          } catch (Exception e) {
        	  DisplayToast(debug + ": " + e.toString());
               e.printStackTrace();
          }
     }

     private void DisplayToast(String msg)
     {
    	Toast toast = Toast.makeText(mContext, msg, 10);
    	toast.show();
     }
     
     /**
      * Requests for the device to be enabled. Returns true if the request is
      * successful, but it does not mean that the device was successfully
      * enabled. You will be notified via the OnChangeListener when the device's
      * state is actually changed.
      */
     public boolean enable() {
  		String debug = "enable1";
          try {
               if ((Boolean) mEnable.invoke(mDevice) == true) {
            	   debug = "2";
                    mIsBusy = true;
                    mWaitState = true;
               }
        	   debug = "enable2";
               callOnChangeListener();
        	   debug = "enable3";
          } catch (Exception e) {
        	  DisplayToast(debug + ": " + e.toString());
               e.printStackTrace();
          }
          return mIsBusy;
     }

     /**
      * Requests for the device to be disabled. Returns true if the request is
      * successful, but it does not mean that the device was successfully
      * disabled. You will be notified via the OnChangeListener when the device's
      * state is actually changed.
      */
     public boolean disable() {
   		String debug = "disable1";
          try {
               if ((Boolean) mDisable.invoke(mDevice) == true) {
            	   debug = "disable2";
                    mIsBusy = true;
                    mWaitState = false;
               }
        	   debug = "disable3";
               callOnChangeListener();
        	   debug = "disable4";
          } catch (Exception e) {
        	  DisplayToast(debug + ": " + e.toString());
               e.printStackTrace();
          }
          return mIsBusy;
     }

     /**
      * Requests for the device's power state to be toggled. Returns true if the
      * request is successful, but it does not mean that the device was
      * successfully toggled. You will be notified via the OnChangeListener when
      * the device's state is actually changed.
      */
     public boolean toggle() {
          if (isEnabled())
               return disable();
          else
               return enable();
     }

     /**
      * Returns true if the device is currently enabled.
      */
     public boolean isEnabled() {
    		String debug = "isEnabled1";
          boolean bEnabled = false;
          try {
         		debug = "isEnabled2";
               bEnabled = (Boolean) mIsEnabled.invoke(mDevice);
        		debug = "isEnabled3";
          } catch (Exception e) {
        	  DisplayToast(debug + ": " + e.toString());
               e.printStackTrace();
          }
          return bEnabled;
     }

     /**
      * Returns true if the device is in the process of being enabled/disabled.
      */
     public boolean isBusy() {
          return mIsBusy;
     }

     /**
      * Returns a string describing the current status of the device if it is
      * busy.
      */
     public String getStatus() {
          String str = null;
          if (isBusy()) {
               if (mWaitState == true)
                    str = "Enabling...";
               else
                    str = "Disabling...";
          }
          return str;
     }

     /**
      * Be sure to call pause() whenever your Activity is paused.
      */
     public void pause() {
          mContext.unregisterReceiver(mBluetoothReceiver);
     }

     /**
      * Be sure to call resume() whenever your Activity is paused.
      */
     public void resume() {
          mContext.registerReceiver(mBluetoothReceiver, mIntentFilter);
     }

     /**
      * Register this BroadcastReceiver to be notified of whenever Bluetooth is
      * enabled/disabled
      */
     private final BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
          @Override
          public void onReceive(Context context, Intent intent) {
               String action = intent.getAction();
               if (action.equals(BTDevice.ENABLED_ACTION)) {
                    Log.v(TAG, "Recieved broadcast intent "
                              + BTDevice.ENABLED_ACTION);
               } else if (action.equals(BTDevice.DISABLED_ACTION)) {
                    Log.v(TAG, "Recieved broadcast intent "
                              + BTDevice.DISABLED_ACTION);
               }
               mIsBusy = false;
               callOnChangeListener();
          }
     };

     /**
      * Listener used to dispatch state change events.
      */
     protected OnChangeListener mOnChangeListener = null;

     /**
      * Call this device's OnChangeListener, if it is defined.
      */
     protected void callOnChangeListener() {
          if (mOnChangeListener != null) {
               mOnChangeListener.onChange(this);
          }
     }

     /**
      * Register a callback to be invoked when this device's state changes.
      *
      * @param l
      *            The callback that will run
      */
     public void setOnChangeListener(OnChangeListener l) {
          mOnChangeListener = l;
     }

     /**
      * Interface definition for a callback to be invoked when the device's state
      * changes.
      */
     public interface OnChangeListener {
          /**
           * Called when the device's state changes.
           *
           * @param d
           *            The device that has changed.
           */
          void onChange(BTDevice d);
     }
} 