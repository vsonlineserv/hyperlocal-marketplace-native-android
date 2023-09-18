package in.vbuy.client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

public class SplashscreenActivity extends Activity {
	// how long until we go to the next activity
	protected int _splashTime = 1000;

	private Thread splashTread;
	private static final String PRIVATE_PREF = "VBuy.in";
	private static final String VERSION_KEY = "version_number";
	DBLogin db = new DBLogin(this);
	private static final String package_name="in.vbuy.client";
	static String userName,passWord; 
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		final SplashscreenActivity sPlashScreen = this;
		
		boolean flag_products_status = Wish_flag.flag_products("productsflag","0");
		boolean flag_features_status = Wish_flag.flag_features("featuresflag","0");
		
		// thread for displaying the SplashScreen
		splashTread = new Thread() {

			public void run() {
                 /* Apply our splash exit (fade out) and main
                    entry (fade in) animation transitions. */
				 overridePendingTransition(R.anim.fadein,
                         R.anim.fadeout);
				try {
					synchronized (this) {
						wait(_splashTime);
					}

				} catch (InterruptedException e) {
				} finally {
					SharedPreferences sharedPref  	= getSharedPreferences(PRIVATE_PREF, Context.MODE_PRIVATE);
			     	int currentVersionNumber		= 0;
			 		
			 		int savedVersionNumber			= sharedPref.getInt(VERSION_KEY, 0);
			 		
			 		try {
			    	 		PackageInfo pi 			= getPackageManager().getPackageInfo(getPackageName(), 0);
			     	 	currentVersionNumber	= pi.versionCode;
			    	 	} catch (Exception e) {}
			    	 	
			    	 	
			    	 	if (currentVersionNumber > savedVersionNumber) {   	 		
			    	 		
			    			
			    	 		Intent i = new Intent();
							i.setClass(sPlashScreen, NotificationActivity.class);
							startActivity(i);
							
							
							
			    	 		Editor editor	= sharedPref.edit();
			    	 		
			    	 		editor.putInt(VERSION_KEY, currentVersionNumber);
			    	 		editor.commit();
			    	 		
			    	 		
			    	 	}
			    	 	else{
			    	 		 db = new DBLogin(getApplicationContext());
			    			 db.open();
			    			 Cursor c = db.getAllContacts();
			    			 try {
			    				 
			    				 if (isNetworkAvailable()){
			    					if (cachetoken.token.size() > 0)

			    					{
			    						
			    						Intent in = new Intent(getApplicationContext(),
			    								CategoryProductActivity.class);
			    						in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    			 			in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			    			 			finish();
			    			 			startActivity(in);
			    						
			    					} else {
			    						 
			    						if (isNetworkAvailable())
			    						{
			    							if (db.getAllContactscount(1)){
			    						    	 c = db.getContact(1);
			    						    	userName=(c.getString(2));
			    						    	passWord=(c.getString(3));
			    						    	new Userlogin().execute();
			    						    }
			    							else{
			    								Intent in = new Intent(getApplicationContext(),
			    										CategoryProductActivity.class);
			    								in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    					 			in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			    					 			finish();
			    					 			startActivity(in);
			    							}
			    						}
			    						else {
			    							
			    							
			    							Intent in = new Intent(getApplicationContext(),
			    									CategoryProductActivity.class);
			    							in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    				 			in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			    				 			finish();
			    				 			startActivity(in);
			    						}
			    						
			    					}
			    				 
			    				 }
			    				 else {
			    						
			    					 Intent in = new Intent(getApplicationContext(),
			    							 CategoryProductActivity.class);
			    						in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    			 			in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			    			 			finish();
			    			 			startActivity(in);
			    					}
			    				} catch (Exception e) {
			    					Log.d("error", "" + e.toString());

			    				}
			    			 
			    			 
			    			   
			    			    db.close();
			    		
							
							
			    	 	}
					// start a new activity			

				}
			}
		};
		splashTread.start();
		
	}

	// Function that will handle the touch

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (splashTread) {
				splashTread.notifyAll();
			}
		}
		return true;
	}

	 private void init() {
	     	SharedPreferences sharedPref  	= getSharedPreferences(PRIVATE_PREF, Context.MODE_PRIVATE);
	     	int currentVersionNumber		= 0;
	 		
	 		int savedVersionNumber			= sharedPref.getInt(VERSION_KEY, 0);
	 		
	 		try {
	    	 		PackageInfo pi 			= getPackageManager().getPackageInfo(getPackageName(), 0);
	     	 	currentVersionNumber	= pi.versionCode;
	    	 	} catch (Exception e) {}
	    	 	
	    	 	
	    	 	if (currentVersionNumber > savedVersionNumber) {   
	    	 		
	    	 		Editor editor	= sharedPref.edit();
	    	 		editor.putInt(VERSION_KEY, currentVersionNumber);
	    	 		editor.commit();
	      }
	 }
	 class Userlogin extends AsyncTask<String, String, String> {

		 	/**
		 	 * Before starting background thread Show Progress Dialog
		 	 * */
		
		 	@Override
		 	protected void onPreExecute() {
		 	}

		 	/**
		 	 * Checking Username and Password
		 	 * */
		 	protected String doInBackground(String... args) {
		 		String temp = null;
		 		
		 		
		 		 HttpParams myParams = new BasicHttpParams();
			 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
			 	    //HttpClient httpclient = new DefaultHttpClient(myParams );
		 	    

		 	    try {
		 	    	HttpPost httppost = new HttpPost(getString(R.string.login));
		 	    	httppost.setHeader("Content-type", "application/json");
		 	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		 	    	nameValuePairs.add(new BasicNameValuePair("grant_type",    "password"));
		 	    	nameValuePairs.add(new BasicNameValuePair("username",      userName));
		 	    	nameValuePairs.add(new BasicNameValuePair("password",  passWord));
		 	    	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		 	    	HttpClient httpClient = new DefaultHttpClient(myParams);
		 	    	HttpResponse response = httpClient.execute(httppost);
		 	    	 temp = EntityUtils.toString(response.getEntity());
		 	    	
		 	    	Log.i("tag", temp);

		 	    }
		 	   catch (ClientProtocolException e) {
		 	    	return "Failed";
		 	    } 
		 	   catch (HttpHostConnectException e){
		 		  return "Failed";
		 	    }
		 	  
		 	   catch (IOException e) {
		 		  return "Failed";
		 	    } 
		 	   catch (Exception e) {
					return "Failed";
				}
		 	
		 		return temp;
			}

		 	
			/**
		 	 * After completing background task Dismiss the progress dialog
		 	 * **/
		 	protected void onPostExecute(String result) {
		 		
		 		if(result.equalsIgnoreCase("Failed")){
		 			Intent in = new Intent(getApplicationContext(),
							CategoryProductActivity.class);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 			in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		 			finish();
		 			startActivity(in);
		 			
		 		}
		 		else{
		 		
		 		try{
		 			JSONObject jsonObject = new JSONObject(result);
		 			if(jsonObject.isNull("access_token")){
		 				String error = jsonObject.getString("error_description");
		 				cachetoken.token.clear();
		 				  DBLogin db = new DBLogin(getApplicationContext());
		 					db = new DBLogin(getApplicationContext());
		 					db.open();
		 				 if( db.Delete(1)){
		 					 
		 				 }
		 				 db.close();
		 				
		 			}
		 			else{
		 				String response = jsonObject.getString("access_token");
			 	    	Log.i("JSON", response);
			 	    	boolean status = cachetoken.addcategorydata(
			 					"token", response);

			 			if (status) {

			 				Log.d("Added to hash map ", "added Ok");
			 				Intent in = new Intent(getApplicationContext(),
									CategoryProductActivity.class);
							in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 			in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
				 			finish();
				 			startActivity(in);
			 				
			 			} else {
			 				
			 				Log.d("Added to hash map ", "not add to hashmap ");
			 			}
			 			
		 			}
		 	    	
		 		}
		 		catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 	   
		 		}
		 	}

		 }
	 
	 
	 
	 public boolean isNetworkAvailable() {
			ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity == null) {
				return false;
			} else {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
			}
			return false;
		}
}