package in.vbuy.client;



import in.vbuy.client.ProductlistActivity.gridviewAdapter1.wishlist;
import in.vbuy.client.SellerListActivity.ViewHolder;
import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.Productpojo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;



public class WishListPage extends BaseActivity {
/** Called when the activity is first created. */
GridView gridView ;
static final String category = "category";
ProgressBar pb1,pb2;
String url;
String proname,hash;
String productid;
String lati,longi;
String wish_row;
int sort=0;
static gridviewAdapter1 gridadapter;
ArrayList<Productpojo>users=new ArrayList<Productpojo>();
Hashtable<String, String> ht = new Hashtable<String, String>();
SeekBar MapRadius;
int MapRadiusval;
GPSTracker gps;
double latitude;
double longitude;
int pos;
long id;
DBAdapter db = new DBAdapter(this);
DisplayImageOptions options;
String hashmapkeyname;
String finallati,finallongi;
TextView km,prname;
String actiontitle;
String areaname;
String emailid;
int countstart=1;
double totalcount;
String selection,lat,lon;
String token;
private Toolbar toolbar;



public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//getActionBar().setDisplayHomeAsUpEnabled(true);
setContentView(R.layout.wishlist);


toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//toolbartitle = (TextView) findViewById(R.id.titletool);
setSupportActionBar(toolbar);
getSupportActionBar().setDisplayShowTitleEnabled(false);


MapRadius=(SeekBar)findViewById(R.id.seekBar1);
km=(TextView)findViewById(R.id.km);
prname=(TextView)findViewById(R.id.categoryname);
gridView = (GridView) findViewById(R.id.grid_view);
pb1=(ProgressBar) findViewById(R.id.loadmore);
pb2=(ProgressBar) findViewById(R.id.progress);
gridView.setOnScrollListener(new PauseOnScrollListener(true, true));


token = cachetoken.token.get("token");

 MapRadiusval= 25;
 MapRadius.setProgress(MapRadiusval);
 km.setText("Search within"+" "+25+" "+"Kms");//sri changed 25 instead of 5
options = new DisplayImageOptions.Builder()
.showImageForEmptyUri(R.drawable.hidescreen)
.cacheInMemory()
.cacheOnDisc()
.bitmapConfig(Bitmap.Config.RGB_565)
.build();

//create class object
gps = new GPSTracker(WishListPage.this);

users = new ArrayList<Productpojo>();
gridadapter = new gridviewAdapter1(getApplicationContext(), R.layout.wishlist_row, users);
gridView.setAdapter(gridadapter);


MapRadius.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	
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
		
		km.setText("Search within"+" "+String.valueOf(progress+1)+" "+"Kms");
		MapRadiusval=progress+1;
		gridadapter.clear();
			
	}
});

db.open();
Cursor c = db.getAllContacts();
if (db.getAllContactscount(1)){
	 c = db.getContact(1);
	 actiontitle=c.getString(1);
	 hash=c.getString(2)+c.getString(3);
	 finallati=c.getString(2);
	 finallongi=c.getString(3);
}
else {
	
		actiontitle="Chennai";
		hash= "13.062657742091597"+"80.22018974609375";
		 finallati="13.062657742091597";
		 finallongi="80.22018974609375";
		}
	
db.close();
hashmapkeyname=actiontitle+hash+"wishlist";

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
	
			if (isNetworkAvailable())
			{
				executeAsyncTask();
			}
			else {
				
				// if false show msg
				showToast("No Network Connection!!!");
			}
		
	
	}
	catch(Exception e)
	{
		Log.d("error",""+e.toString());
	}
	


}	
    

//Custom Title
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
	 // search();
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
	 finish();
        return true;
      }
   
     
  return true;
}



private class GetDeptAyncTask extends
	AsyncTask<Hashtable<String, String>, Void, String> {


protected void onPreExecute() {
	
}

@Override
protected String doInBackground(Hashtable<String, String>... params) {
	Hashtable ht = params[0];
	
	// Get sort id
	
	
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
		
	
		url=getString(R.string.Getwishlist)+finallati+"&lng="+finallongi+"&mapRadius="+MapRadiusval+"&pageSize=20&pageStart="+countstart;
				
	
	ht.clear();
	Log.d("url address",url);
	
	String json = HelperHttp.getJSONResponseFromURL(url,ht);
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
		wish_row=json;
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

	   Toast toast = Toast.makeText(WishListPage.this, 
	     "Error connecting to Server", Toast.LENGTH_LONG);
	   toast.setGravity(Gravity.TOP, 25, 400);
	   toast.show();
	 
	  }
@Override
protected void onPostExecute(String result) {
	
	if (result == "SUCCESS") {
		if(users.isEmpty()){
			MapRadius.setVisibility(View.INVISIBLE);
			km.setVisibility(View.INVISIBLE);
			prname.setVisibility(View.VISIBLE);
			prname.setText("There are no products matching the selection");
			pb2.setVisibility(View.GONE);
			
		}
		
		else{
			
			km.setVisibility(View.VISIBLE);
			pb1.setVisibility(View.GONE);
			pb2.setVisibility(View.GONE);
			MapRadius.setVisibility(View.VISIBLE);
			prname.setVisibility(View.VISIBLE);
			
			
		final GridView gridView = (GridView) findViewById(R.id.grid_view);
		gridView.setVisibility(View.VISIBLE);
		
		
		gridadapter.notifyDataSetChanged();
			
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
		
		
		}

	}else {
		Toast.makeText(WishListPage.this,
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
	Toast.makeText(WishListPage.this, msg, Toast.LENGTH_LONG).show();
	finish();
}


public void onBackPressed(){
	
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
		final TextView price=(TextView)rowView.findViewById(R.id.price);
		final TextView stores=(TextView)rowView.findViewById(R.id.store);
		final LinearLayout wish_lay =(LinearLayout)rowView.  findViewById(R.id.wish_lay);
		final LinearLayout share =(LinearLayout)rowView.  findViewById(R.id.share_lay);
		final View view =(View)rowView.  findViewById(R.id.RelativeLayout1);

		
		name.setText(productlist.get(position).getProname());
		sprice.setText(""+(int) (Double.parseDouble(productlist.get(position).getDescription())));
		stores.setText(productlist.get(position).getStoreslist()+" Stores");
		price.setText(price.getText().toString()+((int) (Double.parseDouble(productlist.get(position).getId())))+".00");
		price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		name.setTextColor(this.cnt.getResources()
				.getColorStateList(R.color.green));
		
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
				share.setVisibility(View.VISIBLE);
				spinner.setVisibility(View.GONE);
			}
		});

		

		wish_lay.setOnClickListener(new OnClickListener() {
	         
	         public void onClick(View v) {
	        	  pos=position;
	              productid=(productlist.get(position).getPid());
	              
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
								boolean flag_products_status = Wish_flag.flag_products("productsflag","1");
								boolean flag_features_status = Wish_flag.flag_features("featuresflag","1");
								if(flag_products_status && flag_features_status ){
								
								new Removewishlist().execute(); 
								
								}
								
							}
							else{
								 Intent intent = new Intent(
											WishListPage.this.getApplicationContext(),
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
		
		
		//imageLoader.displayImage(imageUrls[position], imageView, options);
		share.setOnClickListener(new OnClickListener() {
	         
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
	        });
		return rowView;
	}
	
	
	
	class Removewishlist extends AsyncTask<String, String, String> {

	 	/**
	 	 * Before starting background thread Show Progress Dialog
	 	 * */
		 private final ProgressDialog pDialog1 = new ProgressDialog(WishListPage.this);
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
	 			Toast.makeText(WishListPage.this,
	 					"Check your network connection", Toast.LENGTH_LONG)
	 					.show();
	 		}
	 		else{
	 			 Intent in=new Intent(getApplicationContext(),WishListPage.class);
	 			  in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	 			  finish();
	 				startActivity(in);
	 		
	 	}
	 
	 	}
 }
}

}