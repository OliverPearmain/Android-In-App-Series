package com.sas.inappwifitoggle;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class ToggleActivity extends Activity {

    @Override
    public void onStart() 
    {
    	super.onStart();
    	
    	try
    	{
            WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
            
	        int state = wifiManager.getWifiState();
	        
	        String toastErrorMessage = "An unexpected error occurred";
	        String toastMessage;
	        Boolean toggleResult = false;
	        
	        if (state == WifiManager.WIFI_STATE_DISABLED)
	        {
	        	toggleResult = wifiManager.setWifiEnabled(true);
	        	toastMessage = "Enabling Wifi Connection";
	        }
	        else if (state == WifiManager.WIFI_STATE_ENABLED)
	        {
	        	toggleResult = wifiManager.setWifiEnabled(false);
	        	toastMessage = "Disabling Wifi Connection";
	        }
	        else
	        {
	        	toggleResult = true;
	        	toastMessage = "Wifi Connection in Intermediate State";
	        }
	        
	        if (toggleResult)
	        	DisplayToast(toastMessage);
	        else 
	        	DisplayToast(toastErrorMessage);

	    	
    	}
    	catch (Exception e)
    	{
    		DisplayToast(e.toString(), 30);    	
    	}
    	finally
    	{
	        moveTaskToBack(true);
	        finish();
    	}

    }
    
    private void DisplayToast(String message)
    {
    	DisplayToast(message, Toast.LENGTH_SHORT);
    }

    private void DisplayToast(String message, int duration)
    {
    	Toast toast = Toast.makeText(this, message, duration);
    	toast.show();
    }

}