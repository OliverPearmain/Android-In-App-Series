package com.oliverpearmain.inappdonate;

import android.app.Activity;
import android.os.Build;
import android.widget.Toast;


public class ToggleBluetoothActivity extends Activity {

    @Override
    public void onStart() 
    {
    	String debug = "ERROR 001: ";
    	    	
    	int deviceApiVersion = 0;
    	
    	try
    	{
    		deviceApiVersion = Integer.parseInt(Build.VERSION.SDK);
    	}
    	catch (Exception e)
    	{
        	DisplayToast(debug + "Parsing Build.VERSION.SDK threw exception: " + e.toString());
    		deviceApiVersion = 0;
    	}
    	
    	try
    	{
    		debug = "ERROR 002: ";
    		    		
    		if (deviceApiVersion >= 5)
    		{
        		debug = "ERROR 200: ";
        		
	        	//DisplayToast("Device is version 2.0 or higher");
    		
	        	BluetoothToggle2 bt2 = new BluetoothToggle2();
	        	
	        	bt2.Toggle(this);
    		}
    		else
    		{
        		debug = "ERROR 101: ";
        		
	        	//DisplayToast("Device is version 1.6 or lower");
	    		
	    		BTDevice bluetoothDevice = new BTDevice(getApplicationContext());

	    		debug = "ERROR 102: ";
	    		
	    		Boolean isEnabled = bluetoothDevice.isEnabled();

		        String toastErrorMessage = "Bluetooth in Intermediate State";
		        String toastMessage;
		        Boolean toggleResult = false;
		        
	    		debug = "ERROR 103: ";
		        toastMessage = "Successfully toggled";
	
	    		if (isEnabled)
	    		{
	        		debug = "ERROR 104: ";
	    			toggleResult = bluetoothDevice.toggle();
		        	toastMessage = "Disabling Bluetooth Connection";
	    		}
	    		else
	    		{
	        		debug = "ERROR 105: ";
	    			toggleResult = bluetoothDevice.toggle();
		        	toastMessage = "Enabling Bluetooth Connection";
	    		}

        		debug = "ERROR 106: ";
        		
		        if (toggleResult)
		        	DisplayToast(toastMessage);
		        else 
		        	DisplayToast(toastErrorMessage);
    		}

    		debug = "ERROR 107: ";
    		
        	super.onStart();
    	}
    	catch (Exception e)
    	{
    		DisplayToast(debug + e.toString(), 30);    	
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