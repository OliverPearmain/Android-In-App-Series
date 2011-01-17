package com.oliverpearmain.inappdonate;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.widget.Toast;

public class BluetoothToggle2 {

	
	public void Toggle(Context context) throws Exception
	{	
		String debug = "ERROR 201: ";
		
		try
		{			
			BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			
			debug = "ERROR 202: ";
				
			if (bluetoothAdapter == null)
			{
				DisplayToast(context, debug + "Device does not support bluetooth");    			
			}
	
			debug = "ERROR 203: ";
			
			Boolean isEnabled = bluetoothAdapter.isEnabled();
			
	        String toastErrorMessage = "Bluetooth in Intermediate State";
	        String toastMessage;
	        Boolean toggleResult = false;
	
			debug = "ERROR 204: ";
	
			if (isEnabled)
			{
				debug = "ERROR 205: ";
				toggleResult = bluetoothAdapter.disable();
	        	toastMessage = "Disabling Bluetooth Connection";
			}
			else
			{
				debug = "ERROR 206: ";
				toggleResult = bluetoothAdapter.enable();
	        	toastMessage = "Enabling Bluetooth Connection";
			}
	
			debug = "ERROR 207: ";
			
	        if (toggleResult)
	        	DisplayToast(context, toastMessage);
	        else 
	        	DisplayToast(context, toastErrorMessage);
	
			debug = "ERROR 208: ";
		
		}
    	catch (Exception e)
    	{
    		DisplayToast(context, debug + e.toString(), 30);
    		throw e;
    	}
	}

    private void DisplayToast(Context context, String message)
    {
    	DisplayToast(context, message, Toast.LENGTH_SHORT);
    }

    private void DisplayToast(Context context, String message, int duration)
    {
    	Toast toast = Toast.makeText(context, message, duration);
    	toast.show();
    }
}
