package in.vbuy.client;



import in.vbuy.client.util.AdapterclassCategory;
import in.vbuy.client.util.Categorypojo;
import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.SubCategorySupportfile;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class HomePage extends Activity {
	ArrayList<String> itemnameadapter = new ArrayList<String>();
	ArrayList<String> categorynamedata = new ArrayList<String>();
	ArrayList<String> categoryiddata = new ArrayList<String>();
	ArrayList<String> maincategorylistname = new ArrayList<String>();
	ArrayList<String> pass = new ArrayList<String>();
	ArrayList<String> categoryname;
	List<String> subcategory;
	String url;
	String area;
	public static GridView menulist = null;
	public String Categoryname[];
	public String splitarray[];
	DisplayImageOptions options;
	SharedPreferences preferences;
	GPSTracker gps;
	TextView error;
	DBAdapter db = new DBAdapter(this);
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.gridlayout);
		error=(TextView) findViewById(R.id.error);
		itemnameadapter = new ArrayList<String>();
		categoryname = new ArrayList<String>();
		categorynamedata = new ArrayList<String>();
		categoryiddata = new ArrayList<String>();
		subcategory = new ArrayList<String>();
		maincategorylistname = new ArrayList<String>();
		gps = new GPSTracker(HomePage.this);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.hidescreen)
		.cacheInMemory()
		.cacheOnDisc()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		
		  db = new DBAdapter(this);
		 db.open();
		 Cursor c = db.getAllContacts();
		   
		   
		   
		    if (db.getAllContactscount(1)){
		    	 c = db.getContact(1);
		    	 area=c.getString(1);
		    }
		    
		    
		   
		else{
			area="Chennai";
		}
		    db.close();
		try {
			if (SubCategorySupportfile.Categorydata.size() > 0)

			{

				dataloadedfromhashmap();

			} else {
				
				if (isNetworkAvailable())
				{
					executeAsyncTask();
				}
				else {
					
					// if false show msg
					showToast("No Network Connection!!!");
				}
				
			}

		} catch (Exception e) {
			Log.d("error", "" + e.toString());

		}
	}
	
	 /*public boolean onCreateOptionsMenu(Menu menu) {
	    	*//** Create an option menu from res/menu/items.xml *//*
	    	getMenuInflater().inflate(R.menu.items, menu);
	    	 ActionBar actionBar = getActionBar();
	    	 actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
	    	 actionBar.setIcon(R.drawable.ic_launcher);
	 	     actionBar.setTitle(Html.fromHtml(area));
	    	
	    	return super.onCreateOptionsMenu(menu);
	    }
	
	 public boolean onOptionsItemSelected(MenuItem item) {
	      int itemId_ = item.getItemId();
	      if (itemId_ == R.id.search) {
	    	  Intent in = new Intent(getApplicationContext(),
						SearchActivity.class);
				
				startActivity(in);
	        return true;
	      }
	      else if (itemId_ == R.id.rateus) {
	    	  Intent in = new Intent(getApplicationContext(),
						RateActivity.class);
				
				startActivity(in);
	        return true;
	      }
	      else if (itemId_ == R.id.share) {
	    	  Intent sendIntent = new Intent();
	    	  sendIntent.setAction(Intent.ACTION_SEND);
	    	  sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey, have you tried vbuy.in new mobile app yet? I think you'd like it."+"\n"+"http://www.vbuy.in/mobile-apps");
	    	  sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Try out VBuy.in app");
	    	  sendIntent.setType("text/plain");
	    	  startActivity(sendIntent);
	        return true;
	      }
	     
	      return false;
	    }*/
	
	public void dataloadedfromhashmap() {
		menulist = (GridView) findViewById(R.id.gridview);
		getvalueforhasmap();
		AdapterclassCategory adapter = new AdapterclassCategory(HomePage.this,
				Categoryname);

		menulist = (GridView) findViewById(R.id.gridview);
		menulist.setAdapter(adapter);
		menulist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String title = ((TextView) view.findViewById(R.id.code))
						.getText().toString();
				String tag=((TextView) view.findViewById(R.id.code))
						.getTag().toString();
				pass = SubCategorySupportfile.Categorydata.get(tag);
				Intent in = new Intent(getApplicationContext(),
						SubcategoryProductActivity.class);
				
				in.putExtra("category", title);
				in.putExtra("id", tag);
				in.putStringArrayListExtra("sub", pass);
				startActivity(in);
				

			}
		});

	}
	public void getvalueforhasmap() {
	Categoryname = null;
	splitarray = null;

	itemnameadapter = SubCategorySupportfile.Categorydata.get("mainarray");
	
     int size = itemnameadapter.size();
     
     
	//itemcategoryvalues = itemnameadapter.		toArray(new String[0]);

	for (int i=0;i<=size;i++) {
		Categorypojo cpojo= new Categorypojo();
         
		categorynamedata.add(cpojo.getCategoryname());
		Log.d("values from hash map", cpojo.getCategoryname()+cpojo.getCateId());
		categoryiddata.add(cpojo.getCateId());
		// Log.d("arraydata",a);
	}

	Categoryname = itemnameadapter.toArray(new String[0]);
	
	}
	
	
	private void executeAsyncTask() {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		GetDeptAyncTask async = new GetDeptAyncTask();
		// ht.put("company_id", "1");
		Hashtable[] ht_array = { ht };
		async.execute(ht_array);
	}
	private   class GetDeptAyncTask extends
	AsyncTask<Hashtable<String, String>, Void, String> {

public   final ProgressDialog progressDialog = new ProgressDialog(
	HomePage.this);

protected void onPreExecute() {
	progressDialog.setCancelable(true);
	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	progressDialog.setIndeterminate(true);
	progressDialog.setMessage("Loading, please wait few seconds");
	progressDialog.setTitle("Connecting with VBuy.in");
	progressDialog.show();
}

protected String doInBackground(Hashtable<String, String>... params)

{
@SuppressWarnings("rawtypes")
Hashtable ht = params[0];
String urladdress=	getString(R.string.categorys);
	String json = HelperHttp.getJSONResponseFromURL(urladdress
			, ht);
	if (json != null)
		parseJsonString(json);
	else {
		return "Invalid Company Id";
	}
	return "SUCCESS";
}

protected void parseJsonString(String json) {
	//itemcategoryvalues = null;
	try {
		JSONArray array = new JSONArray(json);
		Categorypojo cpojo= new Categorypojo();
		for (int i = 0; i <= array.length() - 1; i++) {
			JSONObject j = array.getJSONObject(i);
			subcategory.clear();
			
		
			String catename,cateid;
			String  temp,temp1;
			
			Log.d("id", j.optString("ParentCategoryId", ""));
			cateid=j.optString("ParentCategoryId", "");
			catename=j.optString("ParentCategoryName", "");
			JSONArray subarray=j.getJSONArray("SubMenu");
			for (int k = 0; k <= subarray.length() - 1; k++) {
				JSONObject s = subarray.getJSONObject(k);
				String subid,subname,group;
				subid=s.optString("SubCategoryId", "");
				subname=s.optString("SubCategoryName", "");
				group=s.optString("CategoryGroupTag", "");
				temp1=subname+"~"+subid+"~"+group;
				subcategory.add(temp1);
				Log.d("SubCategory ", subid+" "+subname+" "+group);
			}
			
			
			temp=catename+"~"+cateid;
			categoryname.add(temp);
			boolean status = SubCategorySupportfile.addcategorydata(
					cateid, subcategory);
			if (status) {

				Log.d("Added to sub hash map ", "added Ok");
			} else {
				Log.d("Added to sub hash map ", "not add to hashmap ");
			}
		}

		
		
		
		boolean status = SubCategorySupportfile.addcategorydata(
				"mainarray", categoryname);

		if (status) {

			Log.d("Added to hash map ", "added Ok");
		} else {
			Log.d("Added to hash map ", "not add to hashmap ");
		}
		System.out.println(SubCategorySupportfile.Categorydata);
	} catch (JSONException e) {
		e.printStackTrace();
	}

}

protected void onPostExecute(String result) {

	if (result == "SUCCESS") {
		if(categoryname.isEmpty()){
			error.setVisibility(View.VISIBLE);
			if (this.progressDialog.isShowing()) 
			{
				this.progressDialog.dismiss();
			}
		}
		else{
		getmaincategorydetials();
		AdapterclassCategory adapter = new AdapterclassCategory(HomePage.this,
				Categoryname);
        menulist = (GridView) findViewById(R.id.gridview);
		menulist.setAdapter(adapter);
		progressDialog.dismiss();
		menulist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String title = ((TextView) view.findViewById(R.id.code))
						.getText().toString();
				String tag=((TextView) view.findViewById(R.id.code))
						.getTag().toString();
				
				pass = SubCategorySupportfile.Categorydata.get(tag);
				Intent in = new Intent(getApplicationContext(),
						SubcategoryProductActivity.class);
				
				in.putExtra("category", title);
				in.putStringArrayListExtra("sub", pass);
				in.putExtra("id", tag);
				
				startActivity(in);
				

			}
		});

	}} else {
		progressDialog.dismiss();
		error.setVisibility(View.INVISIBLE);
		Toast.makeText(HomePage.this,
				"Check your network connection", Toast.LENGTH_LONG)
				.show();
		

	}

}

}
	
	public void getmaincategorydetials() {
		Categoryname = null;
		splitarray = null;

		itemnameadapter = SubCategorySupportfile.Categorydata.get("mainarray");
		
         int size = itemnameadapter.size();
         
		   Categorypojo cpojo= new Categorypojo();
		
		Categoryname = itemnameadapter.toArray(new String[0]);
			
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
	
	public void showToast(String msg) {

		error.setVisibility(View.INVISIBLE);
		Toast.makeText(HomePage.this, msg, Toast.LENGTH_LONG).show();
	}
	
	
				}

