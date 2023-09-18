package in.vbuy.client;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {

	/**
	 * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
	 * 192.168.1.5
	 */
	public static final String PREFS_NAME = "notificationpref";
	
	static final String SERVER_URL = "http://www.vbuy.in/api/Android/Register?"; 
	
	/**
	 * Google API project id registered to use GCM.
	 */
	static final String SENDER_ID = "35378023688"; 
	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "GCMDemo";

	/**
	 * Intent used to display a message in the screen.
	 */
	static final String DISPLAY_MESSAGE_ACTION = "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

	/**
	 * Intent's extra that contains the message to be displayed.
	 */
	static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		String temp;
		temp=message.trim();
		SharedPreferences settings =context. getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
 	
 		
	if(temp.equalsIgnoreCase("Successfully"))
			{
	
 		editor.putString("Status","true");
 		editor.commit();
 		Log.d("CommonUtilities if statement ",temp);
			}
	else if(temp.equalsIgnoreCase("unregistered"))
	{Log.d("CommonUtilities else if statement ",temp);
	
	editor.clear();
    editor.commit();
	editor.putString("Status","false");
		editor.commit();
	
	}
		
		
		context.sendBroadcast(intent);
	}
}