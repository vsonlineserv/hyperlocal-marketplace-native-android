package in.vbuy.client;


import static in.vbuy.client.CommonUtilities.SENDER_ID;
import static in.vbuy.client.CommonUtilities.displayMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;



/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	@SuppressWarnings("hiding")
	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		displayMessage(context, getString(R.string.gcm_registered));
		ServerUtilities.register(context, registrationId);
		//generateNotification(context, "http://education.mnhs.org/historyday/sites/default/files/HistoryDay_HorzLockUp_COLOR.jpg");
		
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		displayMessage(context, getString(R.string.gcm_unregistered));
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			ServerUtilities.unregister(context, registrationId);
			String a="",b="";
			generateNotification(context, "Notification service disabled successfully",a,b);
		} else {
			// This callback results from the call to unregister made on
			// ServerUtilities when the registration to the server failed.
			Log.i(TAG, "Ignoring unregister callback");
		}
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		// String message ="hello"; /*getString(R.string.gcm_message);*/
		String title = intent.getExtras().getString("title");
		String message = intent.getExtras().getString("message");
		String url = intent.getExtras().getString("url");
		displayMessage(context, message);
		// notifies user
		generateNotification(context, title,message,url);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		displayMessage(context, message);
		// notifies user
		String a="",b="";
		generateNotification(context, message,a,b);
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		displayMessage(context,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	       
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	private static void generateNotification(Context context,String title, String message,String url) {
		int icon = R.drawable.icon;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager)
		          context.getSystemService(Context.NOTIFICATION_SERVICE);
		 
		Intent notificationIntent = new Intent(context, OffersActivity.class);
		       
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
		 | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		NotificationCompat.BigPictureStyle notiStyle = new 
		        NotificationCompat.BigPictureStyle();
		notiStyle.setBigContentTitle(Html.fromHtml("<font color=\"white\">"+title+ "</font>"));
		notiStyle.setSummaryText(message);
		if(url.equalsIgnoreCase("")){
			
		}
		else{
			Bitmap bitmap = getBitmapFromURL(url);
		notiStyle.bigPicture(bitmap);
		}
		PendingIntent intent =
		        PendingIntent.getActivity(context, 0,
		         notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		if(url.equalsIgnoreCase("")){
			 NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
			    bigTextStyle.bigText(message);
		Notification notification = new NotificationCompat.Builder(context)
		 .setContentIntent(intent)
		 .setSmallIcon(icon)
		 .setContentTitle(title)
	     .setContentText(message)
		 .setWhen(when).build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		 
		// Play default notification sound
		 notification.defaults |= Notification.DEFAULT_SOUND;
		 notification.defaults |= Notification.DEFAULT_VIBRATE;
		 notificationManager.notify(1, notification);
		}
		else{
			Notification notification = new NotificationCompat.Builder(context)
			 .setContentIntent(intent)
			 .setSmallIcon(icon)
			 .setWhen(when)
			 .setStyle(notiStyle).build();
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			 
			// Play default notification sound
			 notification.defaults |= Notification.DEFAULT_SOUND;
			 notification.defaults |= Notification.DEFAULT_VIBRATE;
			 notificationManager.notify(1, notification);
		}
		
	}

}
