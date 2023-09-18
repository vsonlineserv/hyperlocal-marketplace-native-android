package in.vbuy.client;


import static in.vbuy.client.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static in.vbuy.client.CommonUtilities.EXTRA_MESSAGE;
import static in.vbuy.client.CommonUtilities.SENDER_ID;
import static in.vbuy.client.CommonUtilities.SERVER_URL;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

public class NotificationActivity  extends Activity 
{

ImageView about, contact, promotion, collection, shop;
public Boolean conn;
private static final String PRIVATE_PREF = "VBuy.in";
private static final String VERSION_KEY = "version_number";
TextView mDisplay;
AsyncTask<Void, Void, Void> mRegisterTask;
@Override
protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
super.onCreate(savedInstanceState);

requestWindowFeature(Window.FEATURE_NO_TITLE);

setContentView(R.layout.splash);
pushmessage();

}
public void pushmessage()
{
	checkNotNull(SERVER_URL, "SERVER_URL");
	checkNotNull(SENDER_ID, "SENDER_ID");
	GCMRegistrar.checkDevice(this);
	GCMRegistrar.checkManifest(this);
	
	registerReceiver(mHandleMessageReceiver, new IntentFilter(
			DISPLAY_MESSAGE_ACTION));
	final String regId = GCMRegistrar.getRegistrationId(this);
	if (regId.equals("")) {
		// Automatically registers application on startup.
		GCMRegistrar.register(this, SENDER_ID);
	} else {
		// Device is already registered on GCM, check server.
		if (GCMRegistrar.isRegisteredOnServer(this)) {						
			
			Log.d("Allready registered",getString(R.string.already_registered) + "\n");
		} else {
			
			final Context context = this;
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					boolean registered = ServerUtilities.register(context,
							regId);
					
					if (!registered) {
						//GCMRegistrar.unregister(context);
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
				}

			};
			mRegisterTask.execute(null, null, null);
		}
	}	
	Intent i = new Intent();
	i.setClass(getApplicationContext(), CategoryProductActivity.class);
	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	  finish();
	startActivity(i);
   
}	
private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
		
		
		Log.d("Message",newMessage);
	}
};

@Override
public void onDestroy()  {
	
	if (mRegisterTask != null) {
		mRegisterTask.cancel(true);
	}
	unregisterReceiver(mHandleMessageReceiver);
	GCMRegistrar.onDestroy(this);
	
	super.onDestroy();
	}

public void checkNotNull(Object reference, String name) {
	if (reference == null) {
		throw new NullPointerException(getString(R.string.error_config,
				name));
	}
  }
}