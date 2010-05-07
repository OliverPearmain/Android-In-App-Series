package com.sas.inappwifitoggle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Toast;

public class CameraReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {

/*		try
		{
			//Toast.makeText(context, "Entered!", 5).show();
			
			KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			
			if (event == null) {
			return;
			}
			
			// Prevent the native camera application from opening
			// abortBroadcast();
			
			// Launch this applications activity to toggle Wifi
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.setClass(context, ToggleWifi.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
		catch (Exception e)
		{
			// 
		}*/
	}
	
}
