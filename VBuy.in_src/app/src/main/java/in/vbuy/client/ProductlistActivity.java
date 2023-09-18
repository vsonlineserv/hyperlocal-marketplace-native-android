package in.vbuy.client;



import in.vbuy.client.SellerListActivity.ViewHolder;
import in.vbuy.client.ShopCartActivity.MyOrderAdapter.UpdateProducts;
import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.Productpojo;
import in.vbuy.client.util.SubCategorySupportfile;
import in.vbuy.client.util.listviewAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;



public class ProductlistActivity extends BaseActivity implements OnClickListener{
/** Called when the activity is first created. */
ListView list,minilist;
RelativeLayout store_row,ltoh_row,htol_row,atoz_row,ztoa_row;
ImageView store_img,ltoh_img,htol_img,atoz_img,ztoa_img;
TextView store,ltoh,htol,atoz,ztoa;
GridView gridView ;
LinearLayout filter_click;
ImageView clear1,clear2;
String pro_id,pro_img,selectpid;
String autocomtextval,areanameval;
/*TextView sort_name;*/
public static ListView menulist = null;
static final String category = "category";
ProgressBar pb1,pb2;
String url;
String proname,hash;
/*ImageView listbutton,gridbutton,minilistbutton;*/
ImageView wishlisted1,wishlist1;
String productid;
String lati,longi;
int sort=0;
private int visibleThreshold = 5;
private int currentPage = 0;
private int previousTotal = 0;
private boolean loading = true;
gridviewAdapter1 gridadapter;
listviewAdapter listadapter;
ArrayList<Productpojo>users=new ArrayList<Productpojo>();
Hashtable<String, String> ht = new Hashtable<String, String>();
/*SeekBar MapRadius;*/
int MapRadiusval;
GPSTracker gps;
double latitude;
double longitude;
int scrollposition;
int gridlist=1;
long id;
DBAdapter db = new DBAdapter(this);
AutoCompleteTextView autocompletetext,areafiltertext;//search product and area name
DisplayImageOptions options;
SharedPreferences preferences;
HashMap<String, ArrayList<Productpojo>> temp = SubCategorySupportfile.productdetials;
String hashmapkeyname;
String catID;
String finallati,finallongi;
TextView prname;
String actiontitle;
String finalpro;
String mapvaluedetails;
String areaname;
String from1,to1;
String emailid;
int countstart=1;
double totalcount;
ListView lv1,lv;
String selection,lat,lon;
int wish_pos;
String token;
ArrayList<String> sort_array=new ArrayList<String>();
private List<String>filterproduct=new ArrayList<String>();
List<String> latList = new ArrayList<String>();
List<String> lonList = new ArrayList<String>();
List<String> pid = new ArrayList<String>();
List<String> img_url = new ArrayList<String>();
List<String> product = new ArrayList<String>();
List<String> arealist = new ArrayList<String>();
private Toolbar toolbar;
TextView wish_name1;



public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//getActionBar().setDisplayHomeAsUpEnabled(true);
setContentView(R.layout.productdisplaypage);


toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//toolbartitle = (TextView) findViewById(R.id.titletool);
setSupportActionBar(toolbar);
getSupportActionBar().setDisplayShowTitleEnabled(false);


/*MapRadius=(SeekBar)findViewById(R.id.seekBar1);*/
/*km=(TextView)findViewById(R.id.km);*/
lv1 = (ListView) findViewById(R.id.listView2);
lv = (ListView) findViewById(R.id.listView1);
prname=(TextView)findViewById(R.id.categoryname);
/*sort_name=(TextView)findViewById(R.id.sort_ans);*/
gridView = (GridView) findViewById(R.id.grid_view);
/*listbutton=(ImageView) findViewById(R.id.list);
gridbutton=(ImageView) findViewById(R.id.grid);
*//*minilistbutton=(ImageView) findViewById(R.id.listsmall);*/
pb1=(ProgressBar) findViewById(R.id.loadmore);
pb2=(ProgressBar) findViewById(R.id.progress);
/*sort_click=(LinearLayout)findViewById(R.id.sort_bylay);*/
filter_click=(LinearLayout)findViewById(R.id.filterlay);
gridView.setOnScrollListener(new PauseOnScrollListener(true, true));


preferences = PreferenceManager.getDefaultSharedPreferences(this);
token = cachetoken.token.get("token");
Intent in = getIntent();
final String title = in.getStringExtra("productname");
final String catid=in.getStringExtra("productid");
final String Mapval=in.getStringExtra("mapradius");
final String from=in.getStringExtra("from");
final String to=in.getStringExtra("to");
final String lati=in.getStringExtra("lati");
final String longi=in.getStringExtra("longi");
final String area=in.getStringExtra("areaname");
 finalpro=title.replace("%20", " ");
mapvaluedetails=Mapval;
from1=from;
to1=to;
proname=title;
areaname=area;
catID=catid;
finallati=lati;
finallongi=longi;
 MapRadiusval= Integer.parseInt(Mapval);
 /*MapRadius.setProgress(MapRadiusval);*/
/* km.setText("Search within"+" "+Mapval+" "+"Kms");*/
 /*sort_click.setOnClickListener(this);*/
 filter_click.setOnClickListener(this);
/* listbutton.setOnClickListener(this);*//*
 minilistbutton.setOnClickListener(this);
 gridbutton.setOnClickListener(this);*/
options = new DisplayImageOptions.Builder()
.showImageForEmptyUri(R.drawable.hidescreen)
.cacheInMemory()
.cacheOnDisc()
.bitmapConfig(Bitmap.Config.RGB_565)
.build();

//create class object
gps = new GPSTracker(ProductlistActivity.this);

users = new ArrayList<Productpojo>();
gridadapter = new gridviewAdapter1(getApplicationContext(), R.layout.productsgridlayoutview1, users);
gridView.setAdapter(gridadapter);

// Seekbar Function
/*if( SubCategorySupportfile.sort.size()>0)
{
	sort=SubCategorySupportfile.sort.get("sort");
	switch (sort) {
    case 1:  sort_name.setText("Store Count");
             break;
    case 2: sort_name.setText("Price Low To High");
             break;
    case 3:  sort_name.setText("Price High To Low");
             break;
    case 4:  sort_name.setText("A to Z");
             break;
    case 5:  sort_name.setText("Z To A");
             break;
	}
}
else{
	sort_name.setText("");
}*/
/*MapRadius.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (isNetworkAvailable())
		{
			countstart=1;
			executeAsyncTask();
		}
		else {
			
			showToast("No Network Connection!!!");
		}
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
		*//*km.setText("Search within"+" "+String.valueOf(progress+1)+" "+"Kms");*//*
		MapRadiusval=progress+1;
		gridadapter.clear();
			
	}
});*/

db.open();
Cursor c = db.getAllContacts();
if (db.getAllContactscount(1)){
	 c = db.getContact(1);
	 actiontitle=c.getString(1);
	 hash=c.getString(2)+c.getString(3);
}
else {
	
		actiontitle="Chennai";
		hash= "13.062657742091597"+"80.22018974609375";
		}
	
db.close();
hashmapkeyname=title+Mapval+actiontitle+hash+from+to;

toolbar.setLogo(R.drawable.vbuy);
toolbar.setNavigationIcon(R.drawable.arrow5);
toolbar.setNavigationOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		onBackPressed();
	}
});
toolbar.setTitle("  "+actiontitle);


Log.d("productname", hashmapkeyname);

try{
	/*if(temp.size()>0)
	
	{
		
	ArrayList<Productpojo> si =temp.get(title);
		
		
		  boolean ifavilable = temp.get(hashmapkeyname) != null;
			
		if(ifavilable==true)
		{
			dataloadedfromhashmap();
		}
		else
		{*/
			if (isNetworkAvailable())
			{
				executeAsyncTask();
			}
			else {
				
				// if false show msg
				showToast("No Network Connection!!!");
			}
		/*}
		
		
		
		}
	else
	{
		if (isNetworkAvailable())
		{
			executeAsyncTask();
		}
		else {
			
			// if false show msg
			showToast("No Network Connection!!!");
		}	
	}
	*/
	
	}
	catch(Exception e)
	{
		Log.d("error",""+e.toString());
	}
	


}	
    

//Custom Title
@Override
public void onRestart()
{
    super.onRestart();
    if(Wish_flag.product.get("productsflag").equalsIgnoreCase("1")){
    	finish();
        startActivity(getIntent());
    }
    
}
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
	/*subMenu.add(0, 6, 3, "Wishlist").setIcon(R.drawable.wish_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	subMenu.add(0, 3, 4, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
    subMenu.add(0, 4, 5, "Rate Us").setIcon(R.drawable.rate).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    return super.onCreateOptionsMenu(menu);
}




public boolean onOptionsItemSelected(MenuItem item) {
 
 int itemId_ = item.getItemId();
  if (itemId_ == 11) {
	  search();
    return true;
  }
  else if (itemId_ == 6) {
	  
	  
	  DBLogin	db1 = new DBLogin(getApplicationContext());
		
      db1.open();
      
		 try {
			 
			 if (isNetworkAvailable()){
				 if (db1.getAllContactscount(1)){
				if (cachetoken.token.size() > 0)

				{
					 Intent in=new Intent(getApplicationContext(),WishListPage.class);
						in.putExtra("dir", "home");
						in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(in);
					
				}
				else{
					 Intent intent = new Intent(
								ProductlistActivity.this.getApplicationContext(),
								RetailerMain.class);
						intent.putExtra("dir", "session");
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(intent);
				 }
				 } else {
					 
					if (isNetworkAvailable())
					{
					
							Intent intent = new Intent(getApplicationContext(), RetailerMain.class);
							intent.putExtra("dir", "home");
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							 startActivity(intent);
					   
							
						
					}
					else {
						
						
						showToast("No Network Connection!!!");
					}
					
				}
			 
			 }
			 else {
					
					// if false show msg
					showToast("No Network Connection!!!");
				}
			} catch (Exception e) {
				Log.d("error", "" + e.toString());

			}
		 
		 
		   
		    db1.close();
	  
	  
    return true;
  }
  else if (itemId_ == 5) {
	  Intent in=new Intent(getApplicationContext(),ShopCartActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(in);
    return true;
  }
  else if (itemId_ == 4) {
	  Intent in = new Intent(getApplicationContext(),
				RateActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
		in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(in);
    return true;
  }
  else if (itemId_ == 1) {
	  Intent in=new Intent(getApplicationContext(),TrackOrderActivity.class);
	  in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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


private void sorted() {
	// TODO Auto-generated method stub
	 final	Dialog dialog = new Dialog(ProductlistActivity.this, R.style.mydialogstyle);
 	
	    // dialog.setTitle("Animation Dialog");
	     dialog.setContentView(R.layout.sortby_dialog);
	     dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
	     store_row=(RelativeLayout)dialog.getWindow(). findViewById(R.id.storecount_row);
	     htol_row=(RelativeLayout)dialog.getWindow(). findViewById(R.id.hightolow_row);
	     ltoh_row=(RelativeLayout)dialog.getWindow(). findViewById(R.id.lowtohigh_row);
	     atoz_row=(RelativeLayout)dialog.getWindow(). findViewById(R.id.atoz_row);
	     ztoa_row=(RelativeLayout)dialog.getWindow(). findViewById(R.id.ztoa_row);
	     store_img=(ImageView)dialog.getWindow(). findViewById(R.id.storecount_img);
	     htol_img=(ImageView)dialog.getWindow(). findViewById(R.id.hightolow_img);
	     ltoh_img=(ImageView)dialog.getWindow(). findViewById(R.id.lowtohigh_img);
	     atoz_img=(ImageView)dialog.getWindow(). findViewById(R.id.atoz_img);
	     ztoa_img=(ImageView)dialog.getWindow(). findViewById(R.id.ztoa_img);
	     store=(TextView)dialog.getWindow(). findViewById(R.id.store);
	     htol=(TextView)dialog.getWindow(). findViewById(R.id.htol);
	     ltoh=(TextView)dialog.getWindow(). findViewById(R.id.ltoh);
	     atoz=(TextView)dialog.getWindow(). findViewById(R.id.atoz);
	     ztoa=(TextView)dialog.getWindow(). findViewById(R.id.ztoa);
	     dialog.show();
	     Window window = dialog.getWindow();
	     window.setGravity(Gravity.CENTER);

	     window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	     dialog.setTitle(null);
	WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
	params.y = 0; params.x = 0;
	params.gravity = Gravity.CENTER | Gravity.CENTER;       
	dialog.getWindow().setAttributes(params); 
	
	switch (sort) {
    case 1:  store_img.setVisibility(View.VISIBLE);
    store.setTypeface(null, Typeface.BOLD);
	htol.setTypeface(null, Typeface.NORMAL);
	ltoh.setTypeface(null, Typeface.NORMAL);
	atoz.setTypeface(null, Typeface.NORMAL);
	ztoa.setTypeface(null, Typeface.NORMAL);
             break;
    case 2:  ltoh_img.setVisibility(View.VISIBLE);
    store.setTypeface(null, Typeface.NORMAL);
	htol.setTypeface(null, Typeface.NORMAL);
	ltoh.setTypeface(null, Typeface.BOLD);
	atoz.setTypeface(null, Typeface.NORMAL);
	ztoa.setTypeface(null, Typeface.NORMAL);
             break;
    case 3:  htol_img.setVisibility(View.VISIBLE);
    store.setTypeface(null, Typeface.NORMAL);
	htol.setTypeface(null, Typeface.BOLD);
	ltoh.setTypeface(null, Typeface.NORMAL);
	atoz.setTypeface(null, Typeface.NORMAL);
	ztoa.setTypeface(null, Typeface.NORMAL);
             break;
    case 4:  atoz_img.setVisibility(View.VISIBLE);
    store.setTypeface(null, Typeface.NORMAL);
	htol.setTypeface(null, Typeface.NORMAL);
	ltoh.setTypeface(null, Typeface.NORMAL);
	atoz.setTypeface(null, Typeface.BOLD);
	ztoa.setTypeface(null, Typeface.NORMAL);
             break;
    case 5:  ztoa_img.setVisibility(View.VISIBLE);
    store.setTypeface(null, Typeface.NORMAL);
	htol.setTypeface(null, Typeface.NORMAL);
	ltoh.setTypeface(null, Typeface.NORMAL);
	atoz.setTypeface(null, Typeface.NORMAL);
	ztoa.setTypeface(null, Typeface.BOLD);
             break;
	}
	  
	store_row.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
        	 SubCategorySupportfile.addsort("store", 1);
        	/* sort_name.setText("Store Count");*/
        	store_img.setVisibility(View.VISIBLE);
        	htol_img.setVisibility(View.INVISIBLE);
        	ltoh_img.setVisibility(View.INVISIBLE);
        	atoz_img.setVisibility(View.INVISIBLE);
        	ztoa_img.setVisibility(View.INVISIBLE);
        	
        	if(sort==1){
        		dialog.dismiss();
        	}
        	else{
        	countstart=1;
			executeAsyncTask();
			dialog.dismiss();
			gridadapter.clear();
        	}
        }
    });
	htol_row.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
       	 SubCategorySupportfile.addsort("htol", 3);
       	/* sort_name.setText("Price High To Low");*/
        	store_img.setVisibility(View.INVISIBLE);
        	htol_img.setVisibility(View.VISIBLE);
        	ltoh_img.setVisibility(View.INVISIBLE);
        	atoz_img.setVisibility(View.INVISIBLE);
        	ztoa_img.setVisibility(View.INVISIBLE);

        	
        	if(sort==3){
        		dialog.dismiss();
        	}
        	else{
        	countstart=1;
			executeAsyncTask();
			dialog.dismiss();
			gridadapter.clear();
        	}
        }
    });
	ltoh_row.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
       	 SubCategorySupportfile.addsort("ltoh", 2);
       /*	sort_name.setText("Price Low To High");*/
        	store_img.setVisibility(View.INVISIBLE);
        	htol_img.setVisibility(View.INVISIBLE);
        	ltoh_img.setVisibility(View.VISIBLE);
        	atoz_img.setVisibility(View.INVISIBLE);
        	ztoa_img.setVisibility(View.INVISIBLE);

        	
        	if(sort==2){
        		dialog.dismiss();
        	}
        	else{
        	countstart=1;
			executeAsyncTask();
			dialog.dismiss();
			gridadapter.clear();
        	}
        }
    });
	atoz_row.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
       	 SubCategorySupportfile.addsort("atoz", 4);
       /*	sort_name.setText("A to Z");*/
        	store_img.setVisibility(View.INVISIBLE);
        	htol_img.setVisibility(View.INVISIBLE);
        	ltoh_img.setVisibility(View.INVISIBLE);
        	atoz_img.setVisibility(View.VISIBLE);
        	ztoa_img.setVisibility(View.INVISIBLE);

        	
        	if(sort==4){
        		dialog.dismiss();
        	}
        	else{
        	countstart=1;
			executeAsyncTask();
			dialog.dismiss();
			gridadapter.clear();
        	}
        }
    });
	ztoa_row.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
       	 SubCategorySupportfile.addsort("ztoa", 5);
       /*	sort_name.setText("Z To A");*/
        	store_img.setVisibility(View.INVISIBLE);
        	htol_img.setVisibility(View.INVISIBLE);
        	ltoh_img.setVisibility(View.INVISIBLE);
        	atoz_img.setVisibility(View.INVISIBLE);
        	ztoa_img.setVisibility(View.VISIBLE);

        	
        	if(sort==5){
        		dialog.dismiss();
        	}
        	else{
        	countstart=1;
			executeAsyncTask();
			dialog.dismiss();
			gridadapter.clear();
        	}
        }
    });
}


private void search() {
	// TODO Auto-generated method stub
	 final	Dialog dialog = new Dialog(ProductlistActivity.this, R.style.mydialogstyle);
 	
	    // dialog.setTitle("Animation Dialog");
	     dialog.setContentView(R.layout.dialoglayout);
	     dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
	     
	     dialog.show();
	     Window window = dialog.getWindow();
	     window.setGravity(Gravity.TOP);

	     window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	     dialog.setTitle(null);
	WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
	params.y = 0; params.x = 0;
	params.gravity = Gravity.TOP | Gravity.LEFT;       
	dialog.getWindow().setAttributes(params); 
	clear1=(ImageView)dialog.getWindow(). findViewById(R.id.clear1);
	clear2=(ImageView)dialog.getWindow(). findViewById(R.id.clear2);
	autocompletetext=(AutoCompleteTextView)dialog.getWindow().findViewById(R.id.autoCompleteTextView1);
	areafiltertext=(AutoCompleteTextView)dialog.getWindow().findViewById(R.id.autoCompleteTextView2);
	areafiltertext.setThreshold(1);
	autocompletetext.setThreshold(1);
	try {
	      String destPath = "/data/data/" + getPackageName()
	          + "/databases/MyDB";
	      File f = new File(destPath);
	      if (!f.exists()) {
	        CopyDB(getBaseContext().getAssets().open("mydb"),
	            new FileOutputStream(destPath));
	      }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  db = new DBAdapter(this);
	 db.open();
	 Cursor c = db.getAllContacts();
	   
	   
	   
	    if (db.getAllContactscount(1)){
	    	 c = db.getContact(1);
	    	areafiltertext.setText(c.getString(1));
	    }
	    else{
	    	 id = db.insertContact("Chennai",
	    	        "13.062657742091597","80.22018974609375");
	    	 c = db.getContact(1);
	    	 areafiltertext.setText(c.getString(1));
	    	}
	    	db.close();
	    	//product and area filter autocomplete text views
	    	autocompletetext.setAdapter(new FilterproductAdapter(ProductlistActivity.this,autocompletetext.getText().toString()));
			areafiltertext.setAdapter(new AreaAdapter(ProductlistActivity.this,areafiltertext.getText().toString()));
			autocompletetext.setOnItemClickListener(new OnItemClickListener(){

			    public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
			    	autocomtextval=autocompletetext.getText().toString();	
		    		areanameval=areafiltertext.getText().toString();
		    		selectpid = (String) parent.getItemAtPosition(position);
		    		 int pos=product.indexOf(selectpid);
		             pro_id=pid.get(pos);
		            pro_img=img_url.get(pos);
		    		if(autocomtextval.length()>0 && areanameval.length()>0 ){
		    			db.open();
		    			Cursor c = db.getAllContacts();
		    		    c = db.getContact(1);
		    		    String areadata=c.getString(1);
		    			String latdata=c.getString(2);
		    			String londata=c.getString(3);
		    			db.close();
		    			Intent int1 = new Intent(getApplicationContext(), SellerListActivity.class);

			        	int1.putExtra("productname",autocomtextval );
			        	int1.putExtra("productid", pro_id);
			        	int1.putExtra("mapvalue", "5");
						int1.putExtra("imageurl", "http://images.vbuy.in/VBuyImages/small/"+pro_img);
						int1.putExtra("image", pro_img);
						int1.putExtra("latitude", latdata);
						int1.putExtra("longitude", londata);
						int1.putExtra("area", areadata);
						int1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    			int1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    			int1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    			finish();
		    			SubCategorySupportfile.mylist.clear();
		    			SubCategorySupportfile.mybrand.clear();
		    			SubCategorySupportfile.filterprice.clear();
		    	    	SubCategorySupportfile.sort.clear();
		    	    	SubCategorySupportfile.filterhash.clear();
						startActivity(int1);
			    		}
			    }
			    });
			areafiltertext.setOnItemClickListener(new OnItemClickListener(){

    public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
    	gps = new GPSTracker(ProductlistActivity.this);
     selection = (String) parent.getItemAtPosition(position);
       if(selection.equalsIgnoreCase("Current Location")){
    		if(gps.canGetLocation()== false){
           		gps.showSettingsAlert();
           		areafiltertext.setText("");
        		}
        	else{
        		
        		 new LongOperation().execute("");
        		
        	}
           	   
           	   
              }
      else{
    	  int pos=arealist.indexOf(selection);
          lat=latList.get(pos);
         lon=lonList.get(pos);
         db.open();
     	db.updateContact(1, selection,lat,lon);
     	db.close();
      }
      
   
        
    }
});
			 clear1.setOnClickListener(new OnClickListener() { 
                 @Override
                 public void onClick(View v) {
                	 autocompletetext.setText("");
                 }
               });
			 clear2.setOnClickListener(new OnClickListener() { 
                 @Override
                 public void onClick(View v) {
                	 areafiltertext.setText("");
                 }
               });
			 autocompletetext.addTextChangedListener(new TextWatcher() {

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void afterTextChanged(Editable s) {
						if(s.length()>0)
						{
							clear1.setVisibility(View.VISIBLE);
						}
						else{
							clear1.setVisibility(View.INVISIBLE);
						}
					}
			    	
			    });
			 autocompletetext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			     
			        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			                searchproduct();
			                return true;
			            }
			            return false;
			        }
			    });
			    areafiltertext.addTextChangedListener(new TextWatcher() {

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void afterTextChanged(Editable s) {
						if(s.length()>0)
						{
							clear2.setVisibility(View.VISIBLE);
						}
						else{
							clear2.setVisibility(View.INVISIBLE);
						}
					}
			    	
			    });
			    areafiltertext.setOnFocusChangeListener(new OnFocusChangeListener() {          
			    	
			        public void onFocusChange(View v, boolean hasFocus) {
			        	
			           	 db.open();
			   		 Cursor c = db.getAllContacts();
			   		   
			   		if(!hasFocus)
			   		{
			   		    if (db.getAllContactscount(1)){
			   		    	 c = db.getContact(1);
			   		    	areafiltertext.setText(c.getString(1));
			   		    }
			   		    else{
			   		    	 id = db.insertContact("Chennai",
			   		    	        "13.062657742091597","80.22018974609375");
			   		    	 c = db.getContact(1);
			   		    	 areafiltertext.setText(c.getString(1));
			   		    }
			   		}
			   		    db.close();
			        }
			    });
	     
}
public void CopyDB(InputStream inputStream, OutputStream outputStream)
	      throws IOException {
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = inputStream.read(buffer)) > 0) {
	      outputStream.write(buffer, 0, length);
	    }
	    inputStream.close();
	    outputStream.close();
	  }
private class LongOperation extends
AsyncTask<String, Void, String> {

private final ProgressDialog progressDialog = new ProgressDialog(ProductlistActivity.this);

protected void onPreExecute() {
progressDialog.setCancelable(false);
progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
progressDialog.setIndeterminate(true);
progressDialog.setMessage("Fetching Location");
progressDialog.show();
}

protected String doInBackground(String... params) {
	try {
		 latitude = gps.getLatitude();
		longitude = gps.getLongitude();

       lati=Double.toString(latitude);
       longi=Double.toString(longitude);
       
           	  for(int i=1000; i<10000;){
           		  Thread.sleep(i);
           		  if(lati.length() < 5 && longi.length() < 5){
           			  i=i+1000;
           		  }
           		  else{
           			  i=i+10000;
           		  }
           	  }
           			
           	              
           	  
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
       
  
   
   return null;
}

@Override
protected void onPostExecute(String result) {
	if(lati.length() < 5 && longi.length() < 5){
		 if (this.progressDialog.isShowing()) {
				this.progressDialog.dismiss();
			}
		 areafiltertext.setText("");
	Toast.makeText(ProductlistActivity.this, "Try Again...", Toast.LENGTH_LONG).show();
	 }
	 else {
latitude = gps.getLatitude();
longitude = gps.getLongitude();
lat=Double.toString(latitude);
lon=Double.toString(longitude);
System.out.println("position " + lat+" "+lon);
db.open();
db.updateContact(1, selection,lat,lon);
db.close();
if (this.progressDialog.isShowing()) {
	this.progressDialog.dismiss();
}
}
}
}
public void searchproduct(){
	 
    autocomtextval=autocompletetext.getText().toString();	
	areanameval=areafiltertext.getText().toString();
	if(autocomtextval.length()>0 && areanameval.length()>0 ){
		db.open();
		Cursor c = db.getAllContacts();
	    c = db.getContact(1);
	    String areadata=c.getString(1);
		String latdata=c.getString(2);
		String londata=c.getString(3);
		db.close();
		String temp=autocomtextval;
		Intent intent = new Intent(
				ProductlistActivity.this.getApplicationContext(),
				ProductlistActivity.class);
		intent.putExtra("productname", temp);
		intent.putExtra("areaname", areadata);
		intent.putExtra("mapradius", "5");
		intent.putExtra("from",  "0");
		intent.putExtra("to", "100000");
		intent.putExtra("lati", latdata);
		intent.putExtra("longi", londata);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		 System.out.println("position " + latdata+" "+londata); //check it now in Logcat
        finish();
        SubCategorySupportfile.mylist.clear();
    	SubCategorySupportfile.mybrand.clear();
    	SubCategorySupportfile.filterprice.clear();
    	SubCategorySupportfile.sort.clear();
    	SubCategorySupportfile.filterhash.clear();
		startActivity(intent);
		 
	
		}
	else{
	
	Toast.makeText(ProductlistActivity.this, "Enter The Search Value", Toast.LENGTH_LONG).show();
	}
}

private class GetDeptAyncTask extends
	AsyncTask<Hashtable<String, String>, Void, String> {


protected void onPreExecute() {
	
}

@Override
protected String doInBackground(Hashtable<String, String>... params) {
	Hashtable ht = params[0];
	
	// Get sort id
	
	if( SubCategorySupportfile.sort.size()>0)
	{
		sort=SubCategorySupportfile.sort.get("sort");
		
	}
	else{
		sort=0;
		
	}
	//Get Search Location	
		db.open();
		 Cursor c = db.getAllContacts();
		if (db.getAllContactscount(1)){
	    	 c = db.getContact(1);
	    	 actiontitle=c.getString(1);
	    	 finallati=c.getString(2);
	    	 finallongi=c.getString(3);
	    }
		else{
			latitude = 13.062657742091597;
			finallati=Double.toString(latitude);
			longitude = 80.22018974609375;
			finallongi=Double.toString(longitude);
			actiontitle="Chennai";
		}
		db.close();
		if(catID != null){
		url=getString(R.string.product);
		}
	else{
		url=getString(R.string.searchproduct);
				
	}
	ht.clear();
	Log.d("url address",url);
	String method="POST";
	
	// Filters
	JSONArray jsonfilter=new JSONArray();
	 try {
	if(SubCategorySupportfile.mylist.size()>0){
		for(int i=0; i<SubCategorySupportfile.mylist.size(); i++)
		{
		 JSONObject jsonob=new JSONObject();
		jsonob.put("FilterValueText", SubCategorySupportfile.mylist.get(i).get("FilterValueText")) ;
		jsonob.put("FilterParameter", SubCategorySupportfile.mylist.get(i).get("FilterParameter")) ;
		jsonfilter.put(jsonob);
		}
	}
	 }
	catch(JSONException ex) {
		
        ex.printStackTrace();

    }
	// Sort and Brand
	JSONObject productParameterFilterSet=new JSONObject();
	 try {

		 JSONObject jsonsort=new JSONObject();
			jsonsort.put("id", sort);
			productParameterFilterSet.put("SortBy", jsonsort);
			
	JSONArray jsort=new JSONArray();
	JSONObject jbrandlist=new JSONObject();
	JSONArray jselected = new JSONArray();
	if(SubCategorySupportfile.mybrand.size()>0){
	for (int j = 0; j < SubCategorySupportfile.mybrand.size(); j++) {
		System.out.println(SubCategorySupportfile.mybrand.get(j).get("FilterParameter"));
			String brandValue=SubCategorySupportfile.mybrand.get(j).get("FilterParameter");
			jsort.put(brandValue);
			jbrandlist.put("Id", brandValue);
			
			jselected.put(jbrandlist);
	}
	
	}
	
	productParameterFilterSet.put("SelectedBrandIdList", jsort);
	
	
	productParameterFilterSet.put("SortById", sort);
	
	productParameterFilterSet.put("SelectedBrandList", jselected);
	 }
	
	     catch(JSONException ex) {
	
	         ex.printStackTrace();
	
	     }
    System.out.println(productParameterFilterSet.toString());
    JSONObject param=new JSONObject();
	//List<NameValuePair> param = new ArrayList<NameValuePair>();
	try {
		param.put("filter", productParameterFilterSet);
		param.put("id", catID);
		param.put("lat", finallati);
		param.put("lng", finallongi);
		param.put("mapRadius", ""+MapRadiusval);
		param.put("pageSize", "20");
		param.put("pageStart", ""+countstart);
		param.put("priceRangeFrom", from1);
		param.put("priceRangeTo", to1);
		param.put("productFilter", proname);
		param.put("selectedProductFilter",jsonfilter);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String json = HelperHttp.getJSONFromUrl(url,method, param);
	if (json != null){
		parseJsonString( json);
	}
	else {
		return "Invalid Company Id";
	}
	return "SUCCESS";
}

protected void parseJsonString(
		String json) {
	try {
		JSONArray array = new JSONArray(json);
		for (int i = 0; i <= array.length()-1; i++) {
			JSONObject j = array.getJSONObject(i);
			String  temp="http://images.vbuy.in/VBuyImages/small/";
			String stores=(j.optString("StoresCount", ""));
			String pid=(j.optString("ProductId", ""));
			String imagename=j.optString("PictureName", "");
			String id=(j.optString("Price", ""));
			String image = imagename.replaceAll("\\s+", "%20");
			String imageurl=(temp+image);
			String name=(j.optString("Name", ""));
			String price=(j.optString("SpecialPrice", ""));
			String wish=(j.optString("FlagWishlist", ""));
			Log.d("productname", j.optString("Name", ""));
			String count=j.optString("TotalCount", "");
			totalcount=Double.parseDouble(count);
			users.add(new Productpojo(id,pid,name,imageurl,price,stores,imagename,wish));
		}
		
		
		

		
	} catch (JSONException e) {
		e.printStackTrace();
	}
	
}
protected void onCancelled() {

	   Toast toast = Toast.makeText(ProductlistActivity.this, 
	     "Error connecting to Server", Toast.LENGTH_LONG);
	   toast.setGravity(Gravity.TOP, 25, 400);
	   toast.show();
	 
	  }
@Override
protected void onPostExecute(String result) {
	
	if (result == "SUCCESS") {
		if(users.isEmpty()){
			/*km.setVisibility(View.VISIBLE);
			km.setText("There are no products matching the selection");*/
			pb2.setVisibility(View.GONE);
			
		}
		
		else{
			Wish_flag.flag_products("productsflag","0");
			/*km.setVisibility(View.VISIBLE);*/
			pb1.setVisibility(View.GONE);
			pb2.setVisibility(View.GONE);
			/*listbutton.setVisibility(View.VISIBLE);*/
			/*MapRadius.setVisibility(View.VISIBLE);*/
			prname.setVisibility(View.VISIBLE);
			if(catID !=null){
				prname.setText(finalpro);
				
			}
			else{
				String finalproname="Search for "+finalpro;
				prname.setText(finalproname);
			}
			if(gridlist==1){
		final GridView gridView = (GridView) findViewById(R.id.grid_view);
		gridView.setVisibility(View.VISIBLE);
		
		 scrollposition = gridView.getLastVisiblePosition();
		gridView.setSelection(scrollposition);
		// Instance of ImageAdapter Class
		gridadapter.notifyDataSetChanged();
			}
			else if(gridlist==2){
				gridView.setVisibility(View.INVISIBLE);
				/*gridbutton.setVisibility(View.VISIBLE);
				listbutton.setVisibility(View.INVISIBLE);*/
				lv.setVisibility(View.VISIBLE);
				gridadapter.notifyDataSetChanged();
				lv.setSelectionFromTop(scrollposition,0);
			}
			else if(gridlist==3){
				
				gridView.setVisibility(View.INVISIBLE);
				lv.setVisibility(View.INVISIBLE);
			/*	gridbutton.setVisibility(View.VISIBLE);
				listbutton.setVisibility(View.INVISIBLE);
				minilistbutton.setVisibility(View.INVISIBLE);*/
				lv1.setVisibility(View.VISIBLE);
				listadapter.notifyDataSetChanged();
				lv1.setSelectionFromTop(scrollposition,0);
			}
		/**
		 * On Click event for Single Gridview Item
		 * */
		
		gridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view,
					int scrollState) { // TODO Auto-generated method stub
				int threshold = 2;
				int count = gridView.getCount();
				
				if (scrollState == SCROLL_STATE_IDLE) {
					if (gridView.getLastVisiblePosition() >= count
							- threshold) {
						// Execute LoadMoreDataTask AsyncTask
						if((totalcount/countstart)>1.0){
							if(pb1.getVisibility() == View.GONE){
								countstart=countstart+20;
								pb1.setVisibility(View.VISIBLE);
							executeAsyncTask();
								}
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
 
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
	            
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            	Intent int1 = new Intent(getApplicationContext(), SellerListActivity.class);

	            	int1.putExtra("productname",users.get(position).getProname()  );
	            	int1.putExtra("productid", users.get(position).getPid());
	            	int1.putExtra("mapvalue",""+ MapRadiusval);
					int1.putExtra("imageurl", users.get(position).getImgurl());
					int1.putExtra("image", users.get(position).getimage());
					int1.putExtra("latitude", finallati);
					int1.putExtra("longitude", finallongi);
					int1.putExtra("area", actiontitle);
					int1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(int1);
					
				}
			});
		
		
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view,
					int scrollState) { // TODO Auto-generated method stub
				int threshold = 2;
				int count = lv.getCount();
				scrollposition = lv.getLastVisiblePosition();
				if (scrollState == SCROLL_STATE_IDLE) {
					if (lv.getLastVisiblePosition() >= count
							- threshold) {
						
						// Execute LoadMoreDataTask AsyncTask
						if((totalcount/countstart)>1.0){
							if(pb1.getVisibility() == View.GONE){
								countstart=countstart+20;
								pb1.setVisibility(View.VISIBLE);
							executeAsyncTask();
								}
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}

		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		    
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		        	Intent int1 = new Intent(getApplicationContext(), SellerListActivity.class);

		        	int1.putExtra("productname",users.get(position).getProname()  );
	            	int1.putExtra("productid", users.get(position).getPid());
	            	int1.putExtra("mapvalue",""+ MapRadiusval);
					int1.putExtra("imageurl", users.get(position).getImgurl());
					int1.putExtra("image", users.get(position).getimage());
					int1.putExtra("latitude", finallati);
					int1.putExtra("longitude", finallongi);
					int1.putExtra("area", actiontitle);
					int1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(int1);
					
					
				}
			});
		
		

		lv1.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view,
					int scrollState) { // TODO Auto-generated method stub
				int threshold = 2;
				int count = lv1.getCount();
				scrollposition = lv1.getLastVisiblePosition();
				if (scrollState == SCROLL_STATE_IDLE) {
					if (lv1.getLastVisiblePosition() >= count
							- threshold) {
						
						// Execute LoadMoreDataTask AsyncTask
						if((totalcount/countstart)>1.0){
							if(pb1.getVisibility() == View.GONE){
							countstart=countstart+20;
							pb1.setVisibility(View.VISIBLE);
						executeAsyncTask();
							}
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}

		});
		
		lv1.setOnItemClickListener(new OnItemClickListener() {
		    
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		        	Intent int1 = new Intent(getApplicationContext(), SellerListActivity.class);

		        	int1.putExtra("productname",users.get(position).getProname()  );
	            	int1.putExtra("productid", users.get(position).getPid());
	            	int1.putExtra("mapvalue",""+ MapRadiusval);
					int1.putExtra("imageurl", users.get(position).getImgurl());
					int1.putExtra("image", users.get(position).getimage());
					int1.putExtra("latitude", finallati);
					int1.putExtra("longitude", finallongi);
					int1.putExtra("area", actiontitle);
					int1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(int1);
					
					
				}
			});
		
		//gridView.setOnScrollListener(new PauseOnScrollListener(true, true));
		
		}

	}else {
		Toast.makeText(ProductlistActivity.this,
				"Check your network connection", Toast.LENGTH_LONG)
				.show();
		pb2.setVisibility(View.GONE);
			
		
	}

}

}

 

private void executeAsyncTask() {

GetDeptAyncTask async = new GetDeptAyncTask();
// ht.put("company_id", "1");
Hashtable[] ht_array = { ht };
async.execute(ht_array);
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
	pb2.setVisibility(View.GONE);
	Toast.makeText(ProductlistActivity.this, msg, Toast.LENGTH_LONG).show();
	finish();
}

public void onClick(View v) {
	/*
	 if (v.getId() == R.id.list) {
		
		gridView.setVisibility(View.INVISIBLE);
		gridbutton.setVisibility(View.INVISIBLE);
		listbutton.setVisibility(View.INVISIBLE);
		minilistbutton.setVisibility(View.VISIBLE);
		lv.setVisibility(View.VISIBLE);
		lv1.setVisibility(View.INVISIBLE);
		gridlist=2;
		gridadapter = new gridviewAdapter1(getApplicationContext(), R.layout.productsgridlayoutview1, users);
		lv.setAdapter(gridadapter);
		 scrollposition = gridView.getLastVisiblePosition();
		lv.setSelectionFromTop(scrollposition,0);
		 }
else if (v.getId() == R.id.grid) {
	    lv.setVisibility(View.INVISIBLE);
		gridView.setVisibility(View.VISIBLE);
		listbutton.setVisibility(View.VISIBLE);
		gridbutton.setVisibility(View.INVISIBLE);
		minilistbutton.setVisibility(View.INVISIBLE);
		lv1.setVisibility(View.INVISIBLE);
		gridlist=1;
		gridadapter = new gridviewAdapter1(getApplicationContext(), R.layout.productsgridlayoutview1, users);
		 scrollposition = lv.getLastVisiblePosition();
		gridView.setSelection(scrollposition);
		gridView.setAdapter(gridadapter);
		 }
else if (v.getId() == R.id.listsmall) {
	    lv1.setVisibility(View.VISIBLE);
	    lv.setVisibility(View.INVISIBLE);
		gridView.setVisibility(View.INVISIBLE);
		listbutton.setVisibility(View.INVISIBLE);
		minilistbutton.setVisibility(View.INVISIBLE);
		gridbutton.setVisibility(View.VISIBLE);
		gridlist=3;
		listadapter = new listviewAdapter(getApplicationContext(), R.layout.listrow, users);
		lv1.setAdapter(listadapter); 
		scrollposition = lv.getLastVisiblePosition();
		lv1.setSelectionFromTop(scrollposition,0);
		 }
else if (v.getId() == R.id.sort_bylay) {

		sorted();
		 }*/
 if (v.getId() == R.id.filterlay) {
	boolean status = SubCategorySupportfile.addfilterid(proname,catID,finalpro);
	Intent intent = new Intent(
			ProductlistActivity.this.getApplicationContext(),
			FilterProductActivity.class);
	startActivity(intent);		
	
	 }

}
public void onBackPressed(){
	SubCategorySupportfile.mylist.clear();
	SubCategorySupportfile.mybrand.clear();
	SubCategorySupportfile.filterprice.clear();
	SubCategorySupportfile.sort.clear();
	SubCategorySupportfile.filterhash.clear();
	this.finish();
	overridePendingTransition(0, 0);
}
public class gridviewAdapter1 extends  ArrayAdapter<Productpojo> {
	
	ArrayList<Productpojo> productlist;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;
	Context cnt;
	DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
public gridviewAdapter1(Context context, int resource, ArrayList<Productpojo> objects) {
	super(context, resource, objects);
	vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	Resource = resource;
	productlist = objects;
	cnt=context;
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.hidescreen)
		.cacheInMemory()
		.cacheOnDisc()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();

	
		
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		
		final View rowView = vi.inflate(Resource, null);
		
		final ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
		final ProgressBar spinner = (ProgressBar) rowView.findViewById(R.id.loadingBar);
		final TextView name=(TextView)rowView.findViewById(R.id.name);
		final TextView sprice=(TextView)rowView.findViewById(R.id.code);
		final TextView stores=(TextView)rowView.findViewById(R.id.store);
		final TextView price=(TextView)rowView.findViewById(R.id.price);
	/*	final TextView wish_name=(TextView)rowView.findViewById(R.id.wish_name);*/
		/*final ImageView wishlisted=(ImageView)rowView.findViewById(R.id.wishlisted);*/
		/*final ImageView wishlist=(ImageView)rowView.findViewById(R.id.wishlist);*/
		final View view =(View)rowView.  findViewById(R.id.RelativeLayout1);
		/*final LinearLayout wish_lay =(LinearLayout)rowView.  findViewById(R.id.wish_lay);*/
		/*final LinearLayout share =(LinearLayout)rowView.  findViewById(R.id.share_lay);*/

		name.setText(productlist.get(position).getProname());
		sprice.setText(""+(int) (Double.parseDouble(productlist.get(position).getDescription()))+".00");
		stores.setText(productlist.get(position).getStoreslist()+" Stores");
		name.setTextColor(Color.parseColor("#3B5998"));
		if(productlist.get(position).getwish().equalsIgnoreCase("true")){
			
			/*	wishlisted.setVisibility(View.VISIBLE);
				wishlist.setVisibility(View.GONE);*/
				/*wish_name.setText("Wishlist");*/
			}
			else{
				/*wishlisted.setVisibility(View.GONE);
				wishlist.setVisibility(View.VISIBLE);*/
			/*	wish_name.setText("Wishlist");*/
			}
		price.setText(price.getText().toString()+((int) (Double.parseDouble(productlist.get(position).getId())))+".00");
		price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		imageLoader.displayImage(productlist.get(position).getImgurl(), imageView, options, new SimpleImageLoadingListener() {
			public void onLoadingStarted() {
				spinner.setVisibility(View.VISIBLE);
			}

			public void onLoadingFailed(FailReason failReason) {
				String message = null;
				switch (failReason) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case OUT_OF_MEMORY:
						message = "Out Of Memory error";
						break;
					case UNKNOWN:
						message = "Unknown error";
						break;
				}
				

				spinner.setVisibility(View.GONE);
				imageView.setImageResource(R.drawable.hidescreen);
			}

			public void onLoadingComplete(Bitmap loadedImage) {
			/*	share.setVisibility(View.VISIBLE);*/
				spinner.setVisibility(View.GONE);
			}
		});

	/*	wish_lay.setOnClickListener(new OnClickListener() {
	         
	         public void onClick(View v) {
	        	  wishlisted1=(ImageView)rowView.findViewById(R.id.wishlisted);
	              wishlist1=(ImageView)rowView.findViewById(R.id.wishlist);
	              wish_name1=(TextView)rowView.findViewById(R.id.wish_name);
	              productid=(productlist.get(position).getPid());
	              wish_pos=position;
	             
	             DBLogin	db1 = new DBLogin(getApplicationContext());
					
	              db1.open();
	              
					 try {
						 
						 if (isNetworkAvailable()){
							 if (db1.getAllContactscount(1)){
							if (cachetoken.token.size() > 0)

							{
								Cursor c1 = db1.getAllContacts();
								 c1 = db1.getContact(1);
								emailid=c1.getString(2);
								token=cachetoken.token.get("token");
								boolean status = Wish_flag.flag_features("featuresflag","1");
								if(status){
								if(wishlist.getVisibility() == View.VISIBLE){
									new wishlist().execute();
								}
								else{
									 new Removewishlist().execute(); 
								}
								}
							}
							else{
								 Intent intent = new Intent(
											ProductlistActivity.this.getApplicationContext(),
											RetailerMain.class);
									intent.putExtra("dir", "session");
									intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									startActivity(intent);
							 }
							 } else {
								 
								if (isNetworkAvailable())
								{
								
										Intent intent = new Intent(getApplicationContext(), RetailerMain.class);
										intent.putExtra("dir", "home");
										intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
										 startActivity(intent);
								   
										
									
								}
								else {
									
									
									showToast("No Network Connection!!!");
								}
								
							}
						 
						 }
						 else {
								
								// if false show msg
								showToast("No Network Connection!!!");
							}
						} catch (Exception e) {
							Log.d("error", "" + e.toString());

						}
					 
					 
					   
					    db1.close();
	            
	         }
	         });
*/
		
		//imageLoader.displayImage(imageUrls[position], imageView, options);
	/*	share.setOnClickListener(new OnClickListener() {
	         
	         public void onClick(View v) {
	        	 Bitmap bitmap;
		 			OutputStream output;
		 			view.setDrawingCacheEnabled(true);
		 			// Retrieve the image from the res folder
		 			//bitmap = BitmapFactory.decodeResource(getResources(),
		 			//		R.drawable.view);
		 			 bitmap = view.getDrawingCache();
		 			// Find the SD Card path
		 			File filepath = Environment.getExternalStorageDirectory();
		  
		 			// Create a new folder AndroidBegin in SD Card
		 			File dir = new File(filepath.getAbsolutePath() + "/VSOnline/");
		 			dir.mkdirs();
		  
		 			// Create a name for the saved image
		 			File file = new File(dir, "VBuy.jpg");
		 			try{
		        	 Intent share = new Intent(Intent.ACTION_SEND);
		        	 
						// Type of file to share
						share.setType("image/jpeg");
		 
						output = new FileOutputStream(file);
		 
						// Compress into png format image from 0% - 100%
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
						output.flush();
						output.close();
		 
						// Locate the image to Share
						Uri uri = Uri.fromFile(file);

						share.putExtra(android.content.Intent.EXTRA_TEXT, "VSOnline Service Testing.");
					    share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Try out VBuy.in app");
						// Pass the image into an Intnet
						share.putExtra(Intent.EXTRA_STREAM, uri);
		 
						// Show the social share chooser list
						startActivity(Intent.createChooser(share, "Share Image Tutorial")); 
						
		         } 
		 			
		 			catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         }
	        });*/
		return rowView;
	}
	
	
	class wishlist extends AsyncTask<String, String, String> {

	 	/**
	 	 * Before starting background thread Show Progress Dialog
	 	 * */
		 private final ProgressDialog pDialog1 = new ProgressDialog(ProductlistActivity.this);
	 	@Override
	 	protected void onPreExecute() {
	 		super.onPreExecute();
	 		pDialog1.setMessage("Adding To Wishlist...");
	 		pDialog1.setIndeterminate(false);
	 		pDialog1.setCancelable(false);
	 		pDialog1.show();
	 	}

	 	
	 	protected String doInBackground(String... args) {
	 		String result = null;
	 		 try {
	 		
	 		 HttpParams myParams = new BasicHttpParams();
		 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
		 	    //HttpClient httpclient = new DefaultHttpClient(myParams );
	 	    String url=getString(R.string.addtowishlist)+productid+"&userName="+emailid;

	 	    HttpClient client = new DefaultHttpClient(new BasicHttpParams());
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Content-type", "application/json");
			httpGet.addHeader("Authorization",  "Bearer"+" "+token );
			    
				  HttpResponse response = client.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());
				Log.d("Reply Response", result);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClientProtocolException e) {
	 	    	return "Failed";
	 	    } catch (IOException e) {
	 	    	return "Failed";
	 	    }
	 	    catch(Exception e){
	 	    	return "Failed";
	 	    }
	 	   
	 		return result;
		}
	 	protected void onPostExecute(String result) {
	 		if(result.equalsIgnoreCase("Failed")){
	 			pDialog1.dismiss();
	 			Toast.makeText(ProductlistActivity.this,
	 					"Check your network connection", Toast.LENGTH_LONG)
	 					.show();
	 		}
	 		else{
	 		pDialog1.dismiss();
	 		wishlist1.setVisibility(View.GONE);
	 		wishlisted1.setVisibility(View.VISIBLE);
	 		productlist.get(wish_pos).setwish("true");
	 		wish_name1.setText("Wishlist");
	 	}
	 
	 	}
 }
	class Removewishlist extends AsyncTask<String, String, String> {

	 	/**
	 	 * Before starting background thread Show Progress Dialog
	 	 * */
		 private final ProgressDialog pDialog1 = new ProgressDialog(ProductlistActivity.this);
	 	@Override
	 	protected void onPreExecute() {
	 		super.onPreExecute();
	 		pDialog1.setMessage("Removing From Wishlist...");
	 		pDialog1.setIndeterminate(false);
	 		pDialog1.setCancelable(false);
	 		pDialog1.show();
	 	}

	 	
	 	protected String doInBackground(String... args) {
	 		String result = null;
	 		 try {
	 		
	 		 HttpParams myParams = new BasicHttpParams();
		 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
		 	    //HttpClient httpclient = new DefaultHttpClient(myParams );
	 	    String url=getString(R.string.removewishlist)+productid+"&userName="+emailid;

	 	    HttpClient client = new DefaultHttpClient(new BasicHttpParams());
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Content-type", "application/json");
			httpGet.addHeader("Authorization",  "Bearer"+" "+token );
			    
				  HttpResponse response = client.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());
				Log.d("Reply Response", result);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClientProtocolException e) {
	 	    	return "Failed";
	 	    } catch (IOException e) {
	 	    	return "Failed";
	 	    }
	 	    catch(Exception e){
	 	    	return "Failed";
	 	    }
	 	   
	 		return result;
		}
	 	protected void onPostExecute(String result) {
	 		if(result.equalsIgnoreCase("Failed")){
	 			pDialog1.dismiss();
	 			Toast.makeText(ProductlistActivity.this,
	 					"Check your network connection", Toast.LENGTH_LONG)
	 					.show();
	 		}
	 		else{
	 		pDialog1.dismiss();
	 		wishlist1.setVisibility(View.VISIBLE);
	 		wishlisted1.setVisibility(View.GONE);
	 		productlist.get(wish_pos).setwish("null");
	 		wish_name1.setText("Wishlist");
	 	}
	 
	 	}
 }
}
public class AreaAdapter extends ArrayAdapter<String> {

	protected static final String TAG="AreaAdapter";
	
	public AreaAdapter(Activity context,String Name)
	{
		super(context,android.R.layout.simple_dropdown_item_1line);
		
	}
	@Override
public int getCount()
{
	return filterproduct.size();
}
	@Override
public String getItem(int index)
{
	return filterproduct.get(index);
}

@Override
public Filter getFilter() {
    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
          AreaHttpConection jsonparseobject=new AreaHttpConection();
            if (constraint != null) {
                List<GetSetArea> new_suggestions =jsonparseobject.getParseJsonWCF(constraint.toString());
                filterproduct.clear();
                filterproduct.add("Current Location");
                for (int i=0;i<new_suggestions.size();i++) {
                constraint=Character.toString(constraint.charAt(0)).toUpperCase()+constraint.toString().substring(1);        	  	
           if(new_suggestions.get(i).getAreaName().contains(constraint))
                filterproduct.add(new_suggestions.get(i).getAreaName());
           	arealist.add(new_suggestions.get(i).getAreaName());
           	latList.add(new_suggestions.get(i).getLatitude());
           	lonList.add(new_suggestions.get(i).getLongitude());
                }
                filterResults.values = filterproduct;
                filterResults.count = filterproduct.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence contraint,
                FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };
    return myFilter;
}

}
public class FilterproductAdapter extends ArrayAdapter<String> {

	protected static final String TAG="FilterproductAdapter";
	private List<String> filterproduct;
	public FilterproductAdapter(Activity context,String Name)
	{
		super(context,android.R.layout.simple_dropdown_item_1line);
		filterproduct=new ArrayList<String>();
	}
	@Override
public int getCount()
{
	return filterproduct.size();
}
	@Override
public String getItem(int index)
{
	return filterproduct.get(index);
}
@Override
public Filter getFilter() {
    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            JsonParse jsonparseobject=new JsonParse();
            if (constraint != null) {
                List<GetProduct> new_suggestions =jsonparseobject.getParseJsonWCF(constraint.toString());
                filterproduct.clear();
                for (int i=0;i<new_suggestions.size();i++) {
                filterproduct.add(new_suggestions.get(i).getName());
                product.add(new_suggestions.get(i).getName());
                pid.add(new_suggestions.get(i).getProductId());
                img_url.add(new_suggestions.get(i).getPictureName());
                }
                filterResults.values = filterproduct;
                filterResults.count = filterproduct.size();
            }
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence contraint,
                FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };
    return myFilter;
}
}
}