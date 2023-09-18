package in.vbuy.client;


import in.vbuy.client.util.Categorypojo;
import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.Item;
import in.vbuy.client.util.OffersExpandableListAdapter;
import in.vbuy.client.util.Productpojo;
import in.vbuy.client.util.SubCategorySupportfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class OffersActivity extends ActionBarActivity {
/** Called when the activity is first created. */
DBAdapter db = new DBAdapter(this);
ArrayList<Productpojo> itemlist=new ArrayList<Productpojo>();
static final String category = "category";
String area;
public String Categoryname[];
public String splitarray[];
private ExpandableListView mExpandablelistView;
private ArrayList<String> parentItems = new ArrayList<String>();
private ArrayList<Object> childItems = new ArrayList<Object>();
private OffersExpandableListAdapter mExpandableListAdapter;
ArrayList<String> splited = new ArrayList<String>();
ArrayList<String> groupvalue = new ArrayList<String>();
ArrayList<String> childarray = new ArrayList<String>();
ArrayList<Item> items = new ArrayList<Item>();
ArrayList<String> itemnameadapter = new ArrayList<String>();
ArrayList<String> sublist = new ArrayList<String>();
ArrayList<String> offersname = new ArrayList<String>();
GPSTracker gps;
LinearLayout offer;
TextView nooffers;
ProgressBar pb;
ImageView noconnection;
SharedPreferences preferences;
public static String title ;
private Toolbar toolbar;

public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//getActionBar().setDisplayHomeAsUpEnabled(true);
setContentView(R.layout.offers);

toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//toolbartitle = (TextView) findViewById(R.id.titletool);


setSupportActionBar(toolbar);
getSupportActionBar().setDisplayShowTitleEnabled(false);



offer=(LinearLayout)findViewById(R.id.top_offers);
pb=(ProgressBar) findViewById(R.id.loading);
nooffers=(TextView)findViewById(R.id.nodata);
noconnection=(ImageView)findViewById(R.id.noconnection);
gps = new GPSTracker(OffersActivity.this);
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
   
   toolbar.setLogo(R.drawable.vbuy);
   toolbar.setNavigationIcon(R.drawable.arrow5);
   toolbar.setNavigationOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	});
   toolbar.setTitle("  "+area);
   
   
   
   try {
		
		
		if (isNetworkAvailable())
		{
			executeAsyncTask();
		}
		else {
			
			// if false show msg
			showToast("No Network Connection!!!");
		}
		
	

} catch (Exception e) {
	Log.d("error", "" + e.toString());

}
   
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

protected void onPreExecute() {
	
}

protected String doInBackground(Hashtable<String, String>... params)

{
@SuppressWarnings("rawtypes")
Hashtable ht = params[0];
String urladdress= getString(R.string.alloffers)+"13.05831&lng=80.21195&radius=20";
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
			
			String ProductId,Name,SubCategoryName,PictureName,SpecialPrice,Price,StoresCount;
			String  temp;
			
			ProductId=j.optString("ProductId", "");
			Name=j.optString("Name", "");
			SubCategoryName=j.optString("SubCategoryName", "");
			PictureName=j.optString("PictureName", "");
			SpecialPrice=j.optString("SpecialPrice", "");
			Price=j.optString("Price", "");
			StoresCount=j.optString("StoresCount", "");
			
			
			
			temp=ProductId+"~"+Name+"~"+SubCategoryName+"~"+PictureName+"~"+SpecialPrice+"~"+Price+"~"+StoresCount;
			offersname.add(temp);
			
		}

		
		
		
		boolean status = SubCategorySupportfile.addcategorydata(
				"offers", offersname);

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
	if(offersname.isEmpty()){
	noconnection.setVisibility(View.INVISIBLE);
	pb.setVisibility(View.INVISIBLE);
	offer.setVisibility(View.INVISIBLE);
	nooffers.setVisibility(View.VISIBLE);
	}
	else{
		noconnection.setVisibility(View.INVISIBLE);
		pb.setVisibility(View.INVISIBLE);
		offer.setVisibility(View.VISIBLE);
		nooffers.setVisibility(View.INVISIBLE);
	
getmaincategorydetials();
mExpandablelistView = (ExpandableListView) findViewById(R.id.expandablelistview);

mExpandableListAdapter = new OffersExpandableListAdapter(OffersActivity.this,groupvalue, childItems);

//setting list adapter
mExpandablelistView.setAdapter(mExpandableListAdapter);

for(int i=0; i < mExpandableListAdapter.getGroupCount(); i++)
	mExpandablelistView.expandGroup(i);
}
	
	}
	else{
		pb.setVisibility(View.INVISIBLE);
		Toast.makeText(OffersActivity.this,
				"Check your network connection", Toast.LENGTH_LONG)
				.show();

		finish();
		}
	}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		overridePendingTransition(0, 0);
	}
	 @Override
		public void onResume() {
			super.onResume();
		    invalidateOptionsMenu();
		}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
		 
		 SubMenu search = menu.addSubMenu(0, 11, 0, "Search");
		 search.getItem().setIcon(R.drawable.searchwhite);
	        search.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		 SubMenu subMenu = menu.addSubMenu(0, 22, 1, "all");
	        subMenu.getItem().setIcon(R.drawable.options);
	        subMenu.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	        DBLogin db = new DBLogin(getApplicationContext());
 		db = new DBLogin(getApplicationContext());
 		 db.open();
 		   if (db.getAllContactscount(1)){
	        subMenu.add(0, 0, 0, "Logout").setIcon(R.drawable.user).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	        subMenu.add(0, 1, 1, "My Orders").setIcon(R.drawable.order).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	        
 		   }
 		   else{
	        subMenu.add(0, 2, 0, "Login").setIcon(R.drawable.user).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
 		   }
 		   db.close();
 		subMenu.add(0, 5, 2, "Cart").setIcon(R.drawable.cart).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	     /*   subMenu.add(0, 3, 3, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
	        subMenu.add(0, 4, 4, "Rate Us").setIcon(R.drawable.rate).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	        return super.onCreateOptionsMenu(menu);
	    }
	 
	
	  
	    
	 public boolean onOptionsItemSelected(MenuItem item) {
		 
		 int itemId_ = item.getItemId();
	      if (itemId_ == 11) {
	    	  Intent in = new Intent(getApplicationContext(),
						SearchActivity.class);
				
				startActivity(in);
	        return true;
	      }
	      else if (itemId_ == 5) {
	    	  Intent in=new Intent(getApplicationContext(),ShopCartActivity.class);
				startActivity(in);
	        return true;
	      }
	      else if (itemId_ == 4) {
	    	  Intent in = new Intent(getApplicationContext(),
						RateActivity.class);
				
				startActivity(in);
	        return true;
	      }
	      
	      else if (itemId_ == 3) {
	    	  Intent sendIntent = new Intent();
	    	  sendIntent.setAction(Intent.ACTION_SEND);
	    	  sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey, have you tried vbuy.in new mobile app yet? I think you'd like it."+"\n"+"http://www.vbuy.in/mobile-apps");
	    	  sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Try out VBuy.in app");
	    	  sendIntent.setType("text/plain");
	    	  startActivity(sendIntent);
	        return true;
	      }
	      else if (itemId_ == 2) {
	    	  Intent in=new Intent(getApplicationContext(),RetailerMain.class);
				in.putExtra("dir", "home");
				startActivity(in);
	        return true;
	      }
	      else if (itemId_ == 1) {
	    	  Intent in=new Intent(getApplicationContext(),TrackOrderActivity.class);
				startActivity(in);
	        return true;
	      }
	      else if (itemId_ == 0) {
	    	  cachetoken.token.clear();
	    	  DBLogin db = new DBLogin(getApplicationContext());
	    		db = new DBLogin(getApplicationContext());
				db.open();
			 if( db.Delete(1)){
				 
			 }
			 db.close();
			 onResume();
		        return true;
		      }
		     
	      return true;
	    }
	
public static Set<String> sortList (List<String> myList){
    Set<String> hashsetList = new HashSet<String>(myList);

    Set<String> treesetList = new TreeSet<String>(myList);
	return treesetList;
}
public void getmaincategorydetials() {
	Categoryname = null;
	splitarray = null;

	itemnameadapter = SubCategorySupportfile.Categorydata.get("offers");
	
     int size = itemnameadapter.size();
     
	   Categorypojo cpojo= new Categorypojo();
	for (int i=0;i<itemnameadapter.size();i++){
	String listvalue=	itemnameadapter.get(i);
	String[]splittemp= listvalue.split("~");
	splited.add(splittemp[2]);
	
	}
	groupvalue=new ArrayList<String> (sortList(splited));
	//ArrayList<String> groupvalue.get(0) = new ArrayList<String>();
	
	

	for(int j=0;j<groupvalue.size();j++){
		 parentItems.add(groupvalue.get(j));
		 ArrayList<String> child = new ArrayList<String>();

	//items.add(new SectionItem(groupvalue.get(j)));
	for (int i=0;i<itemnameadapter.size();i++){
		String listvalue=	itemnameadapter.get(i);
		String[]splittemp= listvalue.split("~");
		if(groupvalue.get(j).equalsIgnoreCase(splittemp[2])){
			child.add(splittemp[0]+"~"+splittemp[1]+"~"+splittemp[3]+"~"+splittemp[4]+"~"+splittemp[5]+"~"+splittemp[6]);	
			
   // items.add(new EntryItem(splittemp[0], splittemp[1]));
		}
		
	}
	childItems.add(child);

	}
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
	noconnection.setVisibility(View.VISIBLE);
	pb.setVisibility(View.INVISIBLE);
	offer.setVisibility(View.INVISIBLE);
	nooffers.setVisibility(View.INVISIBLE);
	Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
}	
}	

