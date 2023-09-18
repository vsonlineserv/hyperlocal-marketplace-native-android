package in.vbuy.client;


import in.vbuy.client.util.Categorypojo;
import in.vbuy.client.util.DescriptionAdapter;
import in.vbuy.client.util.EntryItem;
import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.Item;
import in.vbuy.client.util.ListHeight;
import in.vbuy.client.util.Productpojo;
import in.vbuy.client.util.SectionItem;
import in.vbuy.client.util.SubCategorySupportfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
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
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



public class ProductDescription extends ActionBarActivity {
/** Called when the activity is first created. */
ListView gridView=null ;
ListView basic_desc;
DBAdapter db = new DBAdapter(this);
ArrayList<Productpojo> itemlist=new ArrayList<Productpojo>();
static final String category = "category";
String url;
String proid,description;
String area;
TextView descriptiontext;
ArrayList<HashMap<String, String>> basics ;
public String Categoryname[];
public String splitarray[];
ArrayList<String> splited = new ArrayList<String>();
ArrayList<String> groupvalue = new ArrayList<String>();
ArrayList<Item> items = new ArrayList<Item>();
ArrayList<String> itemnameadapter = new ArrayList<String>();
ArrayList<String> productarray = new ArrayList<String>();
GPSTracker gps;
SharedPreferences preferences;
String hashmapkeyname;
Animation animation = null;

private Toolbar toolbar;

public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//getActionBar().setDisplayHomeAsUpEnabled(true);
setContentView(R.layout.description);




toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//toolbartitle = (TextView) findViewById(R.id.titletool);

setSupportActionBar(toolbar);
getSupportActionBar().setDisplayShowTitleEnabled(false);

gps = new GPSTracker(ProductDescription.this);
gridView = (ListView) findViewById(R.id.desc);
basic_desc = (ListView) findViewById(R.id.basic_desc);
descriptiontext = (TextView) findViewById(R.id.description);
Intent in = getIntent();
proid=in.getStringExtra("id");
description=in.getStringExtra("description");
descriptiontext.setText("  "+description);
basics=(ArrayList<HashMap<String, String>>) in.getSerializableExtra("basicdes");
hashmapkeyname=proid;
Log.d("productname", hashmapkeyname);
String link = proid.replaceAll("\\s+", "%20");
String urladdress=	getString(R.string.product_description);
url =urladdress + link;

String[] from = { "aktivite", "sayi" };
int[] to = { android.R.id.text1, android.R.id.text2 };

SimpleAdapter adapter = new SimpleAdapter(this, basics,
        android.R.layout.simple_list_item_1, from, to);
    basic_desc.setAdapter(adapter);
    ListHeight.getListViewSize(basic_desc);

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
   executeAsyncTask();

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
   /* subMenu.add(0, 3, 3, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
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
ProductDescription.this);

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
String json = HelperHttp.getJSONResponseFromURL(url
		, ht);
if (json != null)
	parseJsonString(json);
else {
	return "Invalid Company Id";
}
return "SUCCESS";
}

protected void parseJsonString(String json) {
try {
	JSONArray array = new JSONArray(json);
	Categorypojo cpojo= new Categorypojo();
	String productid,spec_group,spec_attribute,spec_details;
	for (int i = 0; i <= array.length() - 1; i++) {
		JSONObject j = array.getJSONObject(i);
		productid=j.optString("ProductId", "");
		spec_group=j.optString("SpecificationGroup", "");
		spec_attribute=j.optString("SpecificationAttribute", "");
		spec_details=j.optString("SpecificationDetails", "");
		
		  
		productarray.add(productid+"~"+spec_group+"~"+spec_attribute+"~"+spec_details);
		
	}

	
	
	
	boolean status = SubCategorySupportfile.addcategorydata(
			proid, productarray);

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
	if(productarray.isEmpty()){
		progressDialog.dismiss();
	}
	else{
	progressDialog.dismiss();
getmaincategorydetials();
DescriptionAdapter adapter = new DescriptionAdapter(ProductDescription.this, items);
gridView.setAdapter(adapter);
ListHeight.getListViewSize(gridView);
	}
}

else{
	progressDialog.dismiss();
		Toast.makeText(getApplicationContext(),
				"Check your network connection", Toast.LENGTH_LONG)
				.show();
}
}
}
public void getmaincategorydetials() {
	Categoryname = null;
	splitarray = null;

	itemnameadapter = SubCategorySupportfile.Categorydata.get(proid);
	
     int size = itemnameadapter.size();
     
	   Categorypojo cpojo= new Categorypojo();
	for (int i=0;i<itemnameadapter.size();i++){
	String listvalue=	itemnameadapter.get(i);
	String[]splittemp= listvalue.split("~");
	splited.add(splittemp[1]);
	
	}
	groupvalue=new ArrayList<String> (sortList(splited));
	
	for(int j=0;j<groupvalue.size();j++){
	items.add(new SectionItem(groupvalue.get(j)));
	for (int i=0;i<itemnameadapter.size();i++){
		String listvalue=	itemnameadapter.get(i);
		String[]splittemp= listvalue.split("~");
		if(groupvalue.get(j).equalsIgnoreCase(splittemp[1])){
    items.add(new EntryItem(splittemp[2], splittemp[3]));
		}
	}
	}
	Categoryname = itemnameadapter.toArray(new String[0]);
		
}
	
		
}	

