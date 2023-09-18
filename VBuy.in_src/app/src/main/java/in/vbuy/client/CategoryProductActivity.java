package in.vbuy.client;



import in.vbuy.client.ProductlistActivity.gridviewAdapter1.Removewishlist;
import in.vbuy.client.ProductlistActivity.gridviewAdapter1.wishlist;
import in.vbuy.client.util.AdapterclassCategory;
import in.vbuy.client.util.Categorypojo;
import in.vbuy.client.util.GridHeightView;
import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.HomeOfferAdapter;
import in.vbuy.client.util.ListHeight;
import in.vbuy.client.util.Productpojo;
import in.vbuy.client.util.SubCategorySupportfile;
import in.vbuy.client.util.TopCategoryAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class CategoryProductActivity extends ActionBarActivity {
	ArrayList<String> itemnameadapter = new ArrayList<String>();
	ArrayList<String> itemnameadapter1 = new ArrayList<String>();
	ArrayList<String> itemnameadapter2 = new ArrayList<String>();
	ArrayList<String> categorynamedata = new ArrayList<String>();
	ArrayList<String> categoryiddata = new ArrayList<String>();
	ArrayList<String> today_off = new ArrayList<String>();
	ArrayList<String> top_cate = new ArrayList<String>();
	ArrayList<Productpojo> itemlist=new ArrayList<Productpojo>();
	HashMap<String, ArrayList<Productpojo>> temp = SubCategorySupportfile.productdetials;
	ArrayList<String> categoryname;
	List<String> subcategory;
	String url;
	int wish_pos;
	String emailid,token;
	String wish_proid;
	String area;
	ImageView wishlisted1,wishlist1;
	TextView offer,notop,wish_name1;
	GridView featurepro;
	RelativeLayout rl;
	ImageView noconnection;
	ProgressBar pb;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	/*public static GridView menulist ;*/
	public static GridView home_offers;
	private String imageIds[] = null;
	private String itemName[] = null;
	private String itemdesc[]=null;
	private String itemstores[]=null;
	private String productid[]=null;
	private String image[]=null;
	private String price[]=null;
	private String wish[]=null;
	public String Categoryname[];
	public String splitarray[];
	public String home_offer[];
	public String top_string[];
	String log_button;
	DisplayImageOptions options;
	SharedPreferences preferences;
	GPSTracker gps;
	DBAdapter db = new DBAdapter(this);
	Button retry;
	/*Button hide;*/
	RelativeLayout mainView;
	 private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;
	    TextView nooffers,nofeatures;
	    private RelativeLayout mDrawerContent;
	    private CharSequence mDrawerTitle;
	    private CharSequence mTitle;
	    private Toolbar toolbar;
	    TextView toolbartitle;
	    private static final int NEW_MENU_ITEM = Menu.FIRST;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		 
		super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.home);
		
		
		
		// mTitle = mDrawerTitle = getTitle();
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.drawer_list);
	        mDrawerContent = (RelativeLayout) findViewById(R.id.relative_layout);
	        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
	       //toolbartitle = (TextView) findViewById(R.id.titletool);
	      /*hide=(Button)findViewById(R.id.show1);*/
	      mainView = (RelativeLayout) findViewById(R.id.content_frame);
	      
	      setSupportActionBar(toolbar);
	      getSupportActionBar().setDisplayShowTitleEnabled(false);
	     
		itemnameadapter = new ArrayList<String>();
		categoryname = new ArrayList<String>();
		categorynamedata = new ArrayList<String>();
		categoryiddata = new ArrayList<String>();
		subcategory = new ArrayList<String>();
		offer =(TextView) findViewById(R.id.offer_view);	
		nooffers =(TextView) findViewById(R.id.nooffers);
		notop =(TextView) findViewById(R.id.notop);
		nofeatures =(TextView) findViewById(R.id.feature);
		noconnection =(ImageView) findViewById(R.id.noconnection);
		rl =(RelativeLayout) findViewById(R.id.rl);
		pb =(ProgressBar) findViewById(R.id.loading);
		featurepro =(GridView) findViewById(R.id.featured_product);
		/*top_catlay =(GridView) findViewById(R.id.top_categories);*/
		retry=(Button) findViewById(R.id.img1);
		gps = new GPSTracker(CategoryProductActivity.this);
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
		    
		    // Toolbar Title and Logo
		    
		    toolbar.setLogo(R.drawable.vbuy);
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

		       
		    
		       
		       
		
		
		retry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent int1=new Intent(getApplicationContext(), CategoryProductActivity.class);
				int1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				int1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |IntentCompat.FLAG_ACTIVITY_CLEAR_TASK); 			
    			startActivity(int1);
				
			}
		});
		// Fragment
		
		  // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //toolbar.setTitle(mTitle);
               // toolbartitle.setText(mTitle);
            /*	hide.setVisibility(View.VISIBLE);*/
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
            	/*hide.setVisibility(View.GONE);*/
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            	// TODO Auto-generated method stub
            	super.onDrawerSlide(drawerView, slideOffset);
            	 mainView.setTranslationX(slideOffset * drawerView.getWidth());
                 mDrawerLayout.bringChildToFront(drawerView);
                 mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
       
       // mDrawerToggle.syncState();

        /*if (savedInstanceState == null) {
            selectItem(0);
        }*/
	 
		
		
		
		
		offer.setOnClickListener(new OnClickListener() {

			
			public void onClick(View view) {
				Intent in = new Intent(getApplicationContext(),
						OffersActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(in);
			}
		});

	}
	
	
	
	    private void dataloadedfromhashmap() {
		// TODO Auto-generated method stub
	    	pb.setVisibility(View.GONE);
			noconnection.setVisibility(View.INVISIBLE);
			retry.setVisibility(View.INVISIBLE);
			rl.setVisibility(View.VISIBLE);
		getmaincategorydetials();
		FragmentAdapter fada=new FragmentAdapter(CategoryProductActivity.this, Categoryname);
		mDrawerList.setAdapter(fada);
		
		AdapterclassCategory adapter = new AdapterclassCategory(CategoryProductActivity.this,
				Categoryname);
		
		/*menulist = (GridView) findViewById(R.id.category);*/
		home_offers = (GridView) findViewById(R.id.today_offers);
		//fragmentadapter
		
		
		
		// Category Adapter
	/*	menulist.setAdapter(adapter);
		ListAdapter myListAdapter1 = menulist.getAdapter();
		int size1=myListAdapter1.getCount();
		ListHeight.gridViewSetting(CategoryProductActivity.this,menulist);
		menulist.setNumColumns(size1);*/
		
		// Home Offer Adapter
		if(SubCategorySupportfile.Categorydata.get("today_offer").isEmpty()){
			nooffers.setVisibility(View.VISIBLE);
		}
		else{
			Log.d("Offer", ""+home_offer);
		HomeOfferAdapter adapter_offer = new HomeOfferAdapter(CategoryProductActivity.this,
				home_offer);
		home_offers.setAdapter(adapter_offer);
		ListAdapter myListAdapter = home_offers.getAdapter();
		int size=myListAdapter.getCount();
		ListHeight.gridViewSetting(CategoryProductActivity.this,home_offers);
		home_offers.setNumColumns(size);
		}
		// Top Category Adapter
				if(SubCategorySupportfile.Categorydata.get("top_category").isEmpty()){
					notop.setVisibility(View.VISIBLE);
				}
				else{
					TopCategoryAdapter top_cat_adapter = new TopCategoryAdapter(CategoryProductActivity.this,
						top_string);
			/*	top_catlay.setAdapter(top_cat_adapter);
				ListAdapter myListAdapter = top_catlay.getAdapter();
				int size=myListAdapter.getCount();
				ListHeight.gridViewSetting(CategoryProductActivity.this,top_catlay);
				top_catlay.setNumColumns(size);*/
				}
		
		getproductdetials();
		 
		  
		    boolean ifavilable = temp.get("features").isEmpty();
		   
		  if(ifavilable==true){
			nofeatures.setVisibility(View.VISIBLE);
		}
		else{
		homePageFeaturedAdapter feature_adapter = new homePageFeaturedAdapter(CategoryProductActivity.this,
				itemName,imageIds,itemdesc,itemstores,productid,image,price,wish);
		featurepro.setAdapter(feature_adapter);
		//ListHeight.getListViewSize1(featurepro);
		}

		// Setting item click listener for the listview mDrawerList
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent,
							View view,
							int position,
							long id) {			
				
				String title = ((TextView) view.findViewById(R.id.price))
						.getText().toString();
				String tag=((TextView) view.findViewById(R.id.price))
						.getTag().toString();
				
				Intent in = new Intent(getApplicationContext(),
						SubcategoryProductActivity.class);
				
				in.putExtra("category", title);
				in.putExtra("id", tag);
				in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				
				startActivity(in);
				
				// Closing the drawer
				
				
				mDrawerLayout.closeDrawer(mDrawerContent);			
				
			}
		});	
		
		
		
		
	/*	menulist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String title = ((TextView) view.findViewById(R.id.price))
						.getText().toString();
				String tag=((TextView) view.findViewById(R.id.price))
						.getTag().toString();
				
				Intent in = new Intent(getApplicationContext(),
						SubcategoryProductActivity.class);
				
				in.putExtra("category", title);
				in.putExtra("id", tag);
				in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				
				startActivity(in);
				

			}
		});*/

	}
	



		@Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }
		
		public void onRestart()
		{
		    super.onRestart();
		    if(Wish_flag.feature.get("featuresflag").equalsIgnoreCase("1")){
		    	finish();
		        startActivity(getIntent());
		    }
		    
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
	    		/*subMenu.add(0, 6, 3, "Wishlist").setIcon(R.drawable.wish_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	    		subMenu.add(0, 3, 4, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
	    	    subMenu.add(0, 4, 5, "Rate Us").setIcon(R.drawable.rate).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
	    									CategoryProductActivity.this.getApplicationContext(),
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
String urladdress=	getString(R.string.categorys);


	String todayoffer=getString(R.string.GetTopOffers)+"lat=13.05831&limit=12&lng=80.21195&radius=20";
String feature_url=	getString(R.string.featuredproduct);
String top_cat=getString(R.string.Topcategory);
String json = HelperHttp.getJSONResponseFromURL(urladdress
			, ht);
	String json_offer = HelperHttp.getJSONResponseFromURL(todayoffer
			, ht);
	String feature_json = HelperHttp.getJSONResponseFromURL(feature_url
			, ht);
	String topcat = HelperHttp.getJSONResponseFromURL(top_cat
			, ht);
	if (json != null)
		parseJsonString(json,json_offer,feature_json,topcat);
	else {
		return "Invalid Company Id";
	}
	return "SUCCESS";
}

protected void parseJsonString(String json , String json_offer , String feature_json,String topcat) {
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
		
		JSONArray array_offer = new JSONArray(json_offer);	
		String ProductId,Name,PictureName,SpecialPrice,Price;
		String  temp3;
		for (int i = 0; i <= array_offer.length() - 1; i++) {
			JSONObject j = array_offer.getJSONObject(i);
			ProductId=j.optString("ProductId", "");
			Name=j.optString("Name", "");
			PictureName=j.optString("PictureName", "");
			SpecialPrice=j.optString("SpecialPrice", "");
			Price=j.optString("Price", "");
			temp3=ProductId+"~"+Name+"~"+PictureName+"~"+SpecialPrice+"~"+Price;
			today_off.add(temp3);
		}
		boolean status1 = SubCategorySupportfile.addcategorydata(
				"today_offer", today_off);

		if (status1) {

			Log.d("Added to hash map ", "added Ok");
		} else {
			Log.d("Added to hash map ", "not add to hashmap ");
		}
		
		JSONArray feature_array = new JSONArray(feature_json);
		for (int i = 0; i <= feature_array.length()-1; i++) {
			JSONObject j = feature_array.getJSONObject(i);
			Productpojo items =new Productpojo();
			String  temp="http://images.vbuy.in/VBuyImages/small/";
			items.setCategory(j.optString("StoresCount", ""));
			items.setId(j.optString("Price", ""));
			items.setPid(j.optString("ProductId", ""));
			items.setimage(j.optString("PictureName", ""));
			String imagename=j.optString("PictureName", "");
			String image = imagename.replaceAll("\\s+", "%20");
			items.setImgurl(temp+image);
			items.setProname(j.optString("Name", ""));
			items.setwishlist(j.optString("FlagWishlist", ""));
			items.setDescription(j.optString("Price", ""));
			items.setStoreslist(j.optString("StoresCount", ""));
			Log.d("productname", j.optString("Name", ""));
			itemlist.add(items);
			
		}
		
		
		boolean status2 = SubCategorySupportfile
				.addProducts(
						"features", itemlist);
		
		if (status2) {
			
			Log.d("Added to hash map ","added Ok");
		}
			else
			{
				Log.d("Added to hash map ","not add to hashmap ");
			}
		JSONArray top_array = new JSONArray(topcat);
		for (int i = 0; i <= top_array.length()-1; i++) {
			JSONObject j = top_array.getJSONObject(i);
			String cat_id=j.optString("CategoryId", "");
			String	top_Name=j.optString("Name", "");
			String group_tag=j.optString("CategoryGroupTag", "");
			temp3=cat_id+"~"+top_Name+"~"+group_tag;
			top_cate.add(temp3);
		}
		boolean status5 = SubCategorySupportfile.addcategorydata(
				"top_category", top_cate);

		if (status5) {

			Log.d("Added to hash map ", "added Ok");
		} else {
			Log.d("Added to hash map ", "not add to hashmap ");
		}
		
	} catch (JSONException e) {
		e.printStackTrace();
	}

}

protected void onPostExecute(String result) {

	if (result == "SUCCESS") {
		if(categoryname.isEmpty()){
			pb.setVisibility(View.GONE);
			finish();
		}
		else{
			 Wish_flag.flag_features("featuresflag","0");
			pb.setVisibility(View.GONE);
			noconnection.setVisibility(View.INVISIBLE);
			retry.setVisibility(View.INVISIBLE);
			rl.setVisibility(View.VISIBLE);
		getmaincategorydetials();
		FragmentAdapter fada=new FragmentAdapter(CategoryProductActivity.this, Categoryname);
		mDrawerList.setAdapter(fada);
		
		AdapterclassCategory adapter = new AdapterclassCategory(CategoryProductActivity.this,
				Categoryname);
			home_offers = (GridView) findViewById(R.id.today_offers);
		/*menulist = (GridView) findViewById(R.id.category);

		//fragmentadapter
		
		
		
		// Category Adapter
		menulist.setAdapter(adapter);
		ListAdapter myListAdapter1 = menulist.getAdapter();
		int size1=myListAdapter1.getCount();
		ListHeight.gridViewSetting(CategoryProductActivity.this,menulist);
		menulist.setNumColumns(size1);*/
		
		// Home Offer Adapter
		if(today_off.isEmpty()){
			nooffers.setVisibility(View.VISIBLE);
		}
		else{
		HomeOfferAdapter adapter_offer = new HomeOfferAdapter(CategoryProductActivity.this,
				home_offer);
		home_offers.setAdapter(adapter_offer);
		ListAdapter myListAdapter = home_offers.getAdapter();
		int size=myListAdapter.getCount();
		ListHeight.gridViewSetting(CategoryProductActivity.this,home_offers);
		home_offers.setNumColumns(size);
		}
		// Top Category Adapter
				if(top_cate.isEmpty()){
					notop.setVisibility(View.VISIBLE);
				}
				else{
					/*TopCategoryAdapter top_cat_adapter = new TopCategoryAdapter(CategoryProductActivity.this,
						top_string);
				top_catlay.setAdapter(top_cat_adapter);
				ListAdapter myListAdapter = top_catlay.getAdapter();
				int size=myListAdapter.getCount();
				ListHeight.gridViewSetting(CategoryProductActivity.this,top_catlay);
				top_catlay.setNumColumns(size);*/
				}
		
		getproductdetials();
		if(itemlist.isEmpty()){
			nofeatures.setVisibility(View.VISIBLE);
		}
		else{
		homePageFeaturedAdapter feature_adapter = new homePageFeaturedAdapter(CategoryProductActivity.this,
				itemName,imageIds,itemdesc,itemstores,productid,image,price,wish);
		featurepro.setAdapter(feature_adapter);
		ListHeight.getListViewSize1(featurepro);
		}

		// Setting item click listener for the listview mDrawerList
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent,
							View view,
							int position,
							long id) {			
				
				String title = ((TextView) view.findViewById(R.id.price))
						.getText().toString();
				String tag=((TextView) view.findViewById(R.id.price))
						.getTag().toString();
				
				Intent in = new Intent(getApplicationContext(),
						SubcategoryProductActivity.class);
				
				in.putExtra("category", title);
				in.putExtra("id", tag);
				in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				
				startActivity(in);
				
				// Closing the drawer
				
				
				mDrawerLayout.closeDrawer(mDrawerContent);			
				
			}
		});	
		
		
		
		
	/*	menulist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String title = ((TextView) view.findViewById(R.id.price))
						.getText().toString();
				String tag=((TextView) view.findViewById(R.id.price))
						.getTag().toString();
				
				Intent in = new Intent(getApplicationContext(),
						SubcategoryProductActivity.class);
				
				in.putExtra("category", title);
				in.putExtra("id", tag);
				in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				
				startActivity(in);
				

			}
		});*/

	}} else {
		pb.setVisibility(View.GONE);
		Toast.makeText(CategoryProductActivity.this,
				"Check your network connection", Toast.LENGTH_LONG)
				.show();
		noconnection.setVisibility(View.VISIBLE);
		retry.setVisibility(View.VISIBLE);
		

	}

}

}
	
	public void getproductdetials() {
		itemName = null;
		itemdesc=null;	
		itemstores = null;
		productid=null;
		imageIds = null;
		image=null;
		price=null;
		wish=null;
		System.out.print(temp.get("features"));
		if (temp != null) {
		
			ArrayList<Productpojo> si =temp.get("features");
			int size = si.size();
			
			Log.d("return size is ", "ArrayList size is "+size);
			itemName = new String[size];
			imageIds = new String[size];
			itemdesc=new String[size];
			itemstores=new String[size];
			productid=new String[size];
			image=new String[size];
			price=new String[size];
			wish=new String[size];
			for(int i=0 ;i< si.size();i++)
			{
				
				Productpojo spl =si.get(i);
				productid[i]=spl.getPid();
				itemName[i]=spl.getProname();
				imageIds[i]=spl.getImgurl();
				itemstores[i]=spl.getStoreslist();
				itemdesc[i]=spl.getDescription();
				image[i]=spl.getimage();
				price[i]=spl.getId();
				wish[i]=spl.getwishlist();
				Log.d("itemname ", itemName[i]=spl.getProname());
				Log.d("imageurls ", imageIds[i]=spl.getImgurl());
				
				/*Log.i("Loaded From Shopping HashMap", "" + i);*/
			}		
			
		} else {
			Log.i("Hashmap not set", "yes");
		}	
	}
	public void getmaincategorydetials() {
		Categoryname = null;
		home_offer=null;
		top_string=null;
		itemnameadapter = SubCategorySupportfile.Categorydata.get("mainarray");
		itemnameadapter1 = SubCategorySupportfile.Categorydata.get("today_offer");
		itemnameadapter2 = SubCategorySupportfile.Categorydata.get("top_category");
		System.out.println(itemnameadapter);
		System.out.println(itemnameadapter1);
		System.out.println(itemnameadapter2);
		if(itemnameadapter.isEmpty()){}
		else{
        Categoryname = itemnameadapter.toArray(new String[0]);
		}
		if(itemnameadapter2.isEmpty()){}
		else{
        top_string = itemnameadapter2.toArray(new String[0]);
		}
		if(today_off.isEmpty()){}
		else{
        home_offer=	itemnameadapter1.toArray(new String[0]);
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
	
	public void showToast(String msg) {
		noconnection.setVisibility(View.VISIBLE);
		retry.setVisibility(View.VISIBLE);
		pb.setVisibility(View.INVISIBLE);
		rl.setVisibility(View.INVISIBLE);
		Toast.makeText(CategoryProductActivity.this, msg, Toast.LENGTH_LONG).show();
	}
	
	public class homePageFeaturedAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater;
		private Context context;
		String []category=null;
		String []description=null;
		String []storeslist=null;
		String []image=null;
		String []wish=null;
		String []price=null;
		DisplayImageOptions options;
		String []imageurl=null;
		String []id=null;
		protected ImageLoader imageLoader = ImageLoader.getInstance();
	String imgUrl;
		
	public homePageFeaturedAdapter(Context context, String[] dataObject,String[] imageurl,String[] description,String[] storeslist , String[] productid,String[] image,String[] price,String[] wish ) {
			
			this.context = context;
			this.mInflater = LayoutInflater.from(this.context);
			this.category=dataObject;
			this.description=description;
			this.storeslist=storeslist;
			this.imageurl=imageurl;
			this.image=image;
			this.wish=wish;
			this.price=price;
			this.id=productid;
			options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.hidescreen)
			.cacheInMemory()
			.cacheOnDisc()
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();

		
			
		}
		@Override
		public int getCount() {
			return this.imageurl.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			final View rowView = inflater.inflate(R.layout.home_features, parent, false);
			
			final ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
			final ImageView hideView = (ImageView) rowView.findViewById(R.id.hide_image);
			final TextView name=(TextView)rowView.findViewById(R.id.name);
			final TextView sprice=(TextView)rowView.findViewById(R.id.code);
			final TextView stores=(TextView)rowView.findViewById(R.id.store);
			final TextView wish_name=(TextView)rowView.findViewById(R.id.wish_name);
			/*final ImageView wishlisted=(ImageView)rowView.findViewById(R.id.wishlisted);*/
			/*final ImageView wishlist=(ImageView)rowView.findViewById(R.id.wishlist);*/
			/*final LinearLayout wish_lay =(LinearLayout)rowView.  findViewById(R.id.wish_lay);*/
			/*final LinearLayout share =(LinearLayout)rowView.  findViewById(R.id.share_lay);*/
			final View view =(View)rowView.  findViewById(R.id.RelativeLayout1);
			
			
			String checkname=(this.category[position]);
			if(checkname.length()>45){
			String result = checkname.substring(0, 45) + "...";

			name.setText(result);
			}
			else{
			name.setText(checkname);
			}
			sprice.setText(""+(int) (Double.parseDouble(this.description[position]))+".00");
			stores.setText(this.storeslist[position]+" Stores");
			Log.i("image name", this.category[position]);
			Log.i("image url", this.imageurl[position]);
			/*name.setTextColor(this.context.getResources()
					.getColorStateList(R.color.red));*/
			name.setTextColor(Color.parseColor("#3B5998"));
			if(wish[position].equalsIgnoreCase("true")){
			/*	wishlisted.setVisibility(View.VISIBLE);*/
			/*	wishlist.setVisibility(View.GONE);*/
			/*	wish_name.setText("Wishlist");*/
			}
			else{
			/*	wishlisted.setVisibility(View.GONE);*//*
				wishlist.setVisibility(View.VISIBLE);
				wish_name.setText("Wishlist");*/
			}
			
			imageLoader.displayImage(this.imageurl[position], imageView, options, new SimpleImageLoadingListener() {
				public void onLoadingStarted() {
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
					

					//spinner.setVisibility(View.GONE);
					imageView.setImageResource(R.drawable.hidescreen);
					
				}

				public void onLoadingComplete(Bitmap loadedImage) {
					hideView.setVisibility(View.INVISIBLE);
				}
			});


			 rowView.setOnClickListener(new OnClickListener() {
				 
	             public void onClick(View view) {

	            	 Intent int1 = new Intent(context, SellerListActivity.class);

	            	 int1.putExtra("productname",category[position]  );
		            	int1.putExtra("productid", id[position]);
		            	int1.putExtra("mapvalue","5");
						int1.putExtra("imageurl", imageurl[position]);
						int1.putExtra("image", image[position]);
						int1.putExtra("latitude", "13.062657742091597");
						int1.putExtra("longitude", "80.22018974609375");
						int1.putExtra("area", "Chennai");
						int1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						 context.startActivity(int1);
	             }

	         });
		/*	 wish_lay.setOnClickListener(new OnClickListener() {
		         
		         public void onClick(View v) {
		        	  wishlisted1=(ImageView)rowView.findViewById(R.id.wishlisted);
		              wishlist1=(ImageView)rowView.findViewById(R.id.wishlist);
		              wish_name1=(TextView)rowView.findViewById(R.id.wish_name);
		              wish_proid= id[position];
		              wish_pos=position;
		             
		             DBLogin	db1 = new DBLogin(context);
						
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
									if(wishlist.getVisibility() == View.VISIBLE){
										new wishlist().execute();
									}
									else{
										 new Removewishlist().execute(); 
									}
									
								}
								else{
									 Intent intent = new Intent(
											 context,
												RetailerMain.class);
										intent.putExtra("dir", "session");
										intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
										context.startActivity(intent);
								 }
								 } else {
									 
									if (isNetworkAvailable())
									{
									
											Intent intent = new Intent(context, RetailerMain.class);
											intent.putExtra("dir", "home");
											intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
											context.startActivity(intent);
									   
											
										
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
			/* share.setOnClickListener(new OnClickListener() {
		         
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
			 private final ProgressDialog pDialog1 = new ProgressDialog(CategoryProductActivity.this);
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
		 	    String url=getString(R.string.addtowishlist)+wish_proid+"&userName="+emailid;
		 	    Log.d("url==", url);
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
		 			Toast.makeText(CategoryProductActivity.this,
		 					"Check your network connection", Toast.LENGTH_LONG)
		 					.show();
		 		}
		 		else{
		 		pDialog1.dismiss();
		 		wishlist1.setVisibility(View.GONE);
		 	/*	wishlisted1.setVisibility(View.VISIBLE);*/
		 		wish_name1.setText("Wishlist");
		 		wish[wish_pos]="true";
		 	}
		 
		 	}
	 }
		class Removewishlist extends AsyncTask<String, String, String> {

		 	/**
		 	 * Before starting background thread Show Progress Dialog
		 	 * */
			 private final ProgressDialog pDialog1 = new ProgressDialog(CategoryProductActivity.this);
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
		 	    String url=getString(R.string.removewishlist)+wish_proid+"&userName="+emailid;

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
		 			Toast.makeText(CategoryProductActivity.this,
		 					"Check your network connection", Toast.LENGTH_LONG)
		 					.show();
		 		}
		 		else{
		 		pDialog1.dismiss();
		 		wishlist1.setVisibility(View.VISIBLE);
		 		/*wishlisted1.setVisibility(View.GONE);*/
		 		wish_name1.setText("Wishlist");
		 		wish[wish_pos]=("null");
		 	}
		 
		 	}
	 }
		
		
		
			
		
	}
				}



