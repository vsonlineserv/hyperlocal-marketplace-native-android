package in.vbuy.client;



import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.HorizontalListView;
import in.vbuy.client.util.ImageLoader;
import in.vbuy.client.util.ImageLoader1;
import in.vbuy.client.util.Product;
import in.vbuy.client.util.ProductModel;
import in.vbuy.client.util.Productpojo;
import in.vbuy.client.util.ShopCart;
import in.vbuy.client.util.SubCategorySupportfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;




public class SellerListActivity extends BaseActivity implements
android.view.View.OnClickListener{

	SharedPreferences preferences;
	TextView textView,hints;
	RelativeLayout lay_buy;
	int priceint;
	//String information="";
	Button buy,availability;
	TextView more;
	String Username;
	private Toolbar toolbar;
	ImageView image;
	ImageView name_up,name_down,price_up,price_down,km_up,km_down;
	TextView proname,keyfeaturename,errorpage,km,sorting,sortprice,sortkm,sortprice_down,sortkm_down;
	//private Context context;
	final Context context = this;
	// These matrices will be used to move and zoom image
		Matrix matrix = new Matrix();
		Matrix savedMatrix = new Matrix();

		ArrayList<Productpojo> itemlist=new ArrayList<Productpojo>();
		static final String category = "category";
		String url,keyurl;
		// We can be in one of these 3 states
		static final int NONE = 0;
		static final int DRAG = 1;
		static final int ZOOM = 2;
		int mode = NONE;
		ArrayList<String>number=new ArrayList<String>();
		// Remember some things for zooming
		PointF start = new PointF();
		PointF mid = new PointF();
		float oldDist = 1f;
		Object content = null;
		int imageId = 0;
		ProgressBar progressbar;
		 String	itemName = null;
		 HashMap<String, ArrayList<Productpojo>> temp = SubCategorySupportfile.productdetials;
		 String hashmapkeyname;
			String 	imageurls,price,productid,mapvalue,imagename;
			
			String actiontitle;
			
			String Desc=null;
			Double finalans;
			String keyname,itemname;
			String pic1,pic2,pic3;
			String latitude,longitude;
			String allpic;
			int position;
			GPSTracker gps;
			/*SeekBar MapRadius;*/
			DisplayImageOptions options;
			ImageLoader imgLoader;
			ImageLoader1 imageLoad;
		   // TextView locationDescription;
			//private HorizontalListView gallery;
			String change="change";
			ArrayList<ProductModel>users=new ArrayList<ProductModel>();
			ProductModelAdapter selleradapter;
			String key="";
			ListView listview;
			String description;
			TextView buyprice,buystore;
			HashMap<String, String> basic = new HashMap<String, String>();
			HashMap<String, String> basics = new HashMap<String, String>();
			ArrayList<HashMap<String, String>> specification = new ArrayList<HashMap<String, String>>();
			List<String> stockList = new ArrayList<String>();
			List<String> stockList1 = new ArrayList<String>();
			String[] stockArr = new String[stockList1.size()];
			ArrayList<String> parameterlist = new ArrayList<String>();
			ArrayList<String> keyfeaturelist = new ArrayList<String>();
			DBLogin db = new DBLogin(this);
			Product p = new Product();
			String authen;
			
			private TextView mCounter;
			private int count=0;
			
			//int key1=0;
			private Animation animShow, animHide;

			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.productwithseller);
		
		initPopup();
		
		toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		//toolbartitle = (TextView) findViewById(R.id.titletool);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		 imgLoader = new ImageLoader(this);
		 imageLoad = new ImageLoader1(this);
        proname=(TextView) findViewById(R.id.hints);
       /* MapRadius=(SeekBar)findViewById(R.id.seekBar1);*/
        keyfeaturename=(TextView) findViewById(R.id.keyfeature);
        errorpage=(TextView) findViewById(R.id.errorpage);
        km=(TextView) findViewById(R.id.km);
        sortprice=(TextView) findViewById(R.id.sort_price);
        sortprice_down=(TextView) findViewById(R.id.sort_price_down);
        sorting=(TextView) findViewById(R.id.sort_name);
        sortkm=(TextView) findViewById(R.id.sort_direction);
        sortkm_down=(TextView) findViewById(R.id.sort_direction_down);
        buystore=(TextView) findViewById(R.id.shop);
        buyprice=(TextView) findViewById(R.id.price);
        lay_buy=(RelativeLayout) findViewById(R.id.lay_buy);
        
        /*final Transparent popup = (Transparent) findViewById(R.id.popup_window);
        popup.setVisibility(View.GONE);
 
	    final Button btn=(Button)findViewById(R.id.handle);*/
        
		Intent in = getIntent();
		image = (ImageView) findViewById(R.id.imginactivity);
		name_up = (ImageView) findViewById(R.id.name_up);
		name_down = (ImageView) findViewById(R.id.name_down);
		price_up = (ImageView) findViewById(R.id.price_up);
		price_down = (ImageView) findViewById(R.id.price_down);
		km_up = (ImageView) findViewById(R.id.km_up);
		km_down = (ImageView) findViewById(R.id.km_down);
		more=(TextView) findViewById(R.id.more);
		buy=(Button) findViewById(R.id.buy);
		//availability=(Button) findViewById(R.id.availability);
		productid = in.getStringExtra("productid");
		mapvalue = in.getStringExtra("mapvalue");
		imageurls = in.getStringExtra("imageurl");
		itemname = in.getStringExtra("productname");
		latitude = in.getStringExtra("latitude");
		longitude = in.getStringExtra("longitude");
		actiontitle = in.getStringExtra("area");
		imagename = in.getStringExtra("image");
		Log.d("test", imagename);
		
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
			   
	    int	MapRadiusval= Integer.parseInt(mapvalue);
/*	 MapRadius.setProgress(MapRadiusval);
		 km.setText("Search within"+" "+Html.fromHtml("<b>"+mapvalue+"</b>")+" "+"Kms");*/
		gps = new GPSTracker(SellerListActivity.this);
		
			proname.setText(itemname);
		
		
		Log.d("image urls", imageurls);
		
        image.setOnClickListener(this);
       
		 imageLoader.displayImage(this.imageurls, image, options, new SimpleImageLoadingListener());
		
		Drawable drawable = LoadImageFromWebOperations(imageurls);
		image.setImageDrawable(drawable);
		hashmapkeyname=itemname+mapvalue;
		
		LoadImageFromWebOperations(imageurls);
		users = new ArrayList<ProductModel>();
		 listview = (ListView)findViewById(R.id.shoplist);
		selleradapter = new ProductModelAdapter(getApplicationContext(), R.layout.shopdetails, users);
		listview.setAdapter(selleradapter);
		
		
		
		/*btn.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				if(key1==0){
					key1=1;
					popup.setVisibility(View.VISIBLE);
				    btn.setBackgroundResource(R.drawable.left);
				    
				}
				else if(key1==1){
					key1=0;
					popup.setVisibility(View.GONE);
					btn.setBackgroundResource(R.drawable.right);
					
				}
			}
		});
	    */

		
		
		
		
		
		
		
		
		
		sorting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Collections.sort(users, new Comparator<ProductModel>(){
				      public int compare(ProductModel obj1, ProductModel obj2)
				      {
				            // TODO Auto-generated method stub
				            return obj1.getstorename().compareToIgnoreCase(obj2.getstorename());
				      }
				});
				selleradapter.notifyDataSetChanged();
				listview.setSelection(0);
				sortkm.setVisibility(View.VISIBLE);
				sortprice.setVisibility(View.VISIBLE);
				sortprice_down.setVisibility(View.INVISIBLE);
				sortkm_down.setVisibility(View.INVISIBLE);
				price_down.setVisibility(View.INVISIBLE);
				price_up.setVisibility(View.INVISIBLE);
				km_down.setVisibility(View.INVISIBLE);
				km_up.setVisibility(View.INVISIBLE);
				name_up.setVisibility(View.VISIBLE);
			}

		});
		
		// Buying Product
		try {
		      String destPath = "/data/data/" + getPackageName()
		          + "/databases/MyDB1";
		      File f = new File(destPath);
		      if (!f.exists()) {
		        CopyDB(getBaseContext().getAssets().open("mydb1"),
		            new FileOutputStream(destPath));
		      }
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
		buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				if (mCounter!=null) {
					count++;
					mCounter.setText(""+Integer.toString(count));
				}
				
				double rate = Double.parseDouble(users.get(0).getspecialprice());
				p.setImageId(imagename);
				p.setItemDesc(users.get(0).getadditional());
				p.setbranch(users.get(0).getstorename());
				p.setbranchid(users.get(0).getbranchid());
				p.setstoreId(users.get(0).getbranchid());
				p.setunitprice(Double.parseDouble(users.get(0).getspecialprice()));
				p.setItemName(itemname);
				p.setproductId(productid);
				p.setPrice(rate);
				p.setQty(1);
				
				
				
				db = new DBLogin(getApplicationContext());
				 db.open();
				 Cursor c = db.getAllContacts();
				 if (db.getAllContactscount(1)){
					 if(cachetoken.token.size()>0){
					 authen = cachetoken.token.get("token");
					 ShopCart.addProduct(getApplicationContext(),p.getbranchid(),p.getproductId(), p);
			    	 c = db.getContact(1);
			    	Username=(c.getString(2));
			    	new AddProduct().execute();
					
					 }
					 else{
						 Intent intent = new Intent(
									SellerListActivity.this.getApplicationContext(),
									RetailerMain.class);
							intent.putExtra("dir", "session");
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivity(intent);
					 }
				 }
				 else{
					 ShopCart.addProduct(getApplicationContext(),p.getbranchid(),p.getproductId(), p);
					 Intent intent = new Intent(
								SellerListActivity.this.getApplicationContext(),
								ShopCartActivity.class);
					    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(intent);
				 }
				
				 }
			});
		/*availability.setOnClickListener(new OnClickListener() {

	         public void onClick(View v) {
	        	Intent intent = new Intent(context,ContactSeller.class);
	        	 intent.putExtra("imageurls", imageurls);
	        	 intent.putExtra("productname", itemname);
	        	 intent.putExtra("productid", productid);
	        	 intent.putExtra("area", actiontitle);
	     		intent.putExtra("storename", users.get(0).getstorename());
	     		intent.putExtra("branchid", users.get(0).getbranchid());
	     		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	             context.startActivity(intent);

	         }
	     });
		*/
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(
						SellerListActivity.this.getApplicationContext(),
						ProductDescription.class);
				intent.putExtra("id", productid);
				intent.putExtra("description", description);
				intent.putExtra("basicdes", specification);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				}
			});
		sortprice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Collections.sort(users, new Comparator<ProductModel>(){
				    
					public int compare(ProductModel obj1, ProductModel obj2) {
						  int value = 0;
						  if (Double.parseDouble(obj1.getspecialprice()) > Double.parseDouble(obj2.getspecialprice()))
						   value = 1;
						  else if (Double.parseDouble(obj1.getspecialprice()) < Double.parseDouble(obj2.getspecialprice()))
						   value = -1;
						  else if ((Double.parseDouble(obj1.getspecialprice()) == Double.parseDouble(obj2.getspecialprice())))
						   value = 0;

						  return value;
						 }
				});
				selleradapter.notifyDataSetChanged();
				listview.setSelection(0);
				sortkm.setVisibility(View.VISIBLE);
				sortprice.setVisibility(View.INVISIBLE);
				sortprice_down.setVisibility(View.VISIBLE);
				sortkm_down.setVisibility(View.INVISIBLE);
				price_down.setVisibility(View.INVISIBLE);
				price_up.setVisibility(View.VISIBLE);
				km_down.setVisibility(View.INVISIBLE);
				km_up.setVisibility(View.INVISIBLE);
				name_up.setVisibility(View.INVISIBLE);
			}

		});
		
		
		sortkm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Collections.sort(users, new Comparator<ProductModel>(){
				      public int compare(ProductModel obj1, ProductModel obj2)
				      {
				    	  if (obj1.getstorename() == obj2.getstorename())
				              return obj1.getstorename().compareTo(obj2.getstorename());  
				    	 return (obj1.getkm() < obj2.getkm()) ? -1: (obj1.getkm() > obj2.getkm()) ? 1:0 ;
				      }
				});
				selleradapter.notifyDataSetChanged();
				listview.setSelection(0);
				sortprice.setVisibility(View.VISIBLE);
				sortkm.setVisibility(View.INVISIBLE);
				sortprice_down.setVisibility(View.INVISIBLE);
				sortkm_down.setVisibility(View.VISIBLE);
				price_down.setVisibility(View.INVISIBLE);
				price_up.setVisibility(View.INVISIBLE);
				km_down.setVisibility(View.INVISIBLE);
				km_up.setVisibility(View.VISIBLE);
				name_up.setVisibility(View.INVISIBLE);
			}

		});
		sortprice_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Collections.sort(users, new Comparator<ProductModel>(){
				     /* public int compare(ProductModel obj1, ProductModel obj2)
				      {
				    	  if (obj1.getstorename() == obj2.getstorename())
				              return obj1.getstorename().compareTo(obj2.getstorename());  
				    	 return ( Double.parseDouble(obj1.getspecialprice()) <  Double.parseDouble(obj2.getspecialprice())) ? -1: ( Double.parseDouble(obj1.getspecialprice()) >  Double.parseDouble(obj2.getspecialprice())) ? 1:0 ;
				      }*/
					public int compare(ProductModel obj1, ProductModel obj2) {
						  int value = 0;
						  if (Double.parseDouble(obj1.getspecialprice()) < Double.parseDouble(obj2.getspecialprice()))
						   value = 1;
						  else if (Double.parseDouble(obj1.getspecialprice()) > Double.parseDouble(obj2.getspecialprice()))
						   value = -1;
						  else if ((Double.parseDouble(obj1.getspecialprice()) == Double.parseDouble(obj2.getspecialprice())))
						   value = 0;

						  return value;
						 }
				});
				selleradapter.notifyDataSetChanged();
				listview.setSelection(0);
				sortprice.setVisibility(View.VISIBLE);
				sortkm.setVisibility(View.VISIBLE);
				sortprice_down.setVisibility(View.INVISIBLE);
				sortkm_down.setVisibility(View.INVISIBLE);
				price_down.setVisibility(View.VISIBLE);
				price_up.setVisibility(View.INVISIBLE);
				km_down.setVisibility(View.INVISIBLE);
				km_up.setVisibility(View.INVISIBLE);
				name_up.setVisibility(View.INVISIBLE);
			}

		});
		
		
		sortkm_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Collections.sort(users, new Comparator<ProductModel>(){
				      public int compare(ProductModel obj1, ProductModel obj2)
				      {
				    	 // int i=(int) Double.parseDouble(obj1.getpricelist());
				    	//  int j=(int) Double.parseDouble(obj2.getpricelist());
				    	  if (obj1.getstorename() == obj2.getstorename())
				              return obj1.getstorename().compareTo(obj2.getstorename());  
				    	 return (obj1.getkm() > obj2.getkm()) ? -1: (obj1.getkm() < obj2.getkm()) ? 1:0 ;
				      }
				});
				selleradapter.notifyDataSetChanged();
				listview.setSelection(0);
				sortprice.setVisibility(View.VISIBLE);
				sortkm.setVisibility(View.VISIBLE);
				sortprice_down.setVisibility(View.INVISIBLE);
				sortkm_down.setVisibility(View.INVISIBLE);
				price_down.setVisibility(View.INVISIBLE);
				price_up.setVisibility(View.INVISIBLE);
				km_down.setVisibility(View.VISIBLE);
				km_up.setVisibility(View.INVISIBLE);
				name_up.setVisibility(View.INVISIBLE);
			}

		});

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
	
		

	
	/*
	MapRadius.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (isNetworkAvailable())
			{
				lay_buy.setVisibility(View.INVISIBLE);
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
			change="changelist";
			km.setText("Search within"+" "+String.valueOf(progress)+" "+"Kms");
			mapvalue=""+progress;
			selleradapter.clear();
			errorpage.setVisibility(View.GONE);
			
				
		}
	});*/
	}
	 private void initPopup() {
		// TODO Auto-generated method stub
		 
		 /*final SlidingPanel popup = (SlidingPanel) findViewById(R.id.popup_window);

	    	// Hide the popup initially.....
	    	popup.setVisibility(View.GONE);
	    	
	    	animShow = AnimationUtils.loadAnimation( this, R.anim.popup_show);
	    	animHide = AnimationUtils.loadAnimation( this, R.anim.popup_hide);*/
	    	
	    	/*final ImageButton   showButton = (ImageButton) findViewById(R.id.show_popup_button);
	    	final ImageButton   hideButton = (ImageButton) findViewById(R.id.hide_popup_button);
	    	showButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					popup.setVisibility(View.VISIBLE);
					popup.startAnimation( animShow );
					showButton.setEnabled(false);
					hideButton.setEnabled(true);
					showButton.setVisibility(View.INVISIBLE);
	        }});
	        
	        hideButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					popup.startAnimation( animHide );
					showButton.setEnabled(true);
					hideButton.setEnabled(false);
					popup.setVisibility(View.GONE);
					showButton.setVisibility(View.VISIBLE);
	        }});

	    	final TextView locationName = (TextView) findViewById(R.id.site_name);
	         locationDescription = (TextView) findViewById(R.id.site_description);
	        
	        locationName.setText("Information");
	       */
		}
		 
		 
		public void onResume() {
			super.onResume();
		    invalidateOptionsMenu();
		}
	
		public boolean onCreateOptionsMenu(Menu menu) {
			
			toolbar.inflateMenu(R.menu.main);
			RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
			mCounter = (TextView) badgeLayout.findViewById(R.id.counter);
			mCounter.setText(""+Integer.toString(count));
			
			 
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
				   
				    
				    subMenu.add(0, 5, 2, "Cart")
				    .setIcon(R.drawable.cart)
				    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				
				/*subMenu.add(0, 6, 3, "Wishlist").setIcon(R.drawable.wish_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
				/*subMenu.add(0, 3, 4, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
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
											SellerListActivity.this.getApplicationContext(),
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
	
	private Drawable LoadImageFromWebOperations(String uRL) {
		try {
			InputStream is = (InputStream) new URL(uRL).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			System.out.println("Exc=" + e);
			return null;
		}
	}

	public void toloadimage()
	{
			
		imageLoader.displayImage(imageurls, image, options, new SimpleImageLoadingListener() {
			public void onLoadingStarted() {
				progressbar.setVisibility(View.VISIBLE);
			}

			@Override
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
				

				progressbar.setVisibility(View.GONE);
				image.setImageResource(R.drawable.hidescreen);
			}

			
			public void onLoadingComplete(Bitmap loadedImage) {
				progressbar.setVisibility(View.GONE);
			}
		});
	}


private class GetDeptAyncTask extends
	AsyncTask<Hashtable<String, String>, Void, String> {

private final ProgressDialog progressDialog = new ProgressDialog(SellerListActivity.this);

protected void onPreExecute() {
	progressDialog.setCancelable(false);
	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	progressDialog.setIndeterminate(true);
	progressDialog.setMessage("Loading...");
	progressDialog.show();
}

@Override
protected String doInBackground(Hashtable<String, String>... params) {
	@SuppressWarnings("rawtypes")
	Hashtable ht = params[0];
	url= getString(R.string.seller)+"true&id="+productid+"&lat="+latitude+"&lng="+longitude+"&mapRadius="+mapvalue+"%E2%80%8F";
	keyurl=getString(R.string.keyfeature)+productid;
	Log.d("url address",url);
	String jsonfile = HelperHttp.getJSONResponseFromURL(url,
			ht);
	String jsonkey = HelperHttp.getJSONResponseFromURL(keyurl,
			ht);
	if (jsonfile != null  ){
		parseJsonString( jsonfile,jsonkey);
	}
	else {
		return "FAILED";
	}
	return "SUCCESS";
}

protected void parseJsonString(
		String jsonfile,String jsonkey) {
	try {
		if(change.equalsIgnoreCase("change")){
		JSONArray arraykey = new JSONArray(jsonkey);
		
		for (int i = 0; i <= arraykey.length()-1; i++) {
			JSONObject jkey = arraykey.getJSONObject(i);
			key=key+jkey.optString("Parameter", "")+":"+jkey.optString("Keyfeature", "")+"\n";
			}
		Log.d("Key Feature",key);
		}
		JSONObject jsonobj=new JSONObject(jsonfile);

		basic.put("Name",jsonobj.optString("Name",""));
		description=jsonobj.optString("FullDescription","");
		basic.put("Brand",jsonobj.optString("BrandName",""));
		basic.put("Model Number",jsonobj.optString("ManufacturerPartNumber",""));
		basic.put("Weight",jsonobj.optString("Weight",""));
		basic.put("Length",jsonobj.optString("Length",""));
		basic.put("Width",jsonobj.optString("Width",""));
		basic.put("Height",jsonobj.optString("Height",""));
		basic.put("Color",jsonobj.optString("Color",""));
		
	     basics.putAll(basic);

	      Set<String> keys = basic.keySet();
	      for(String key: keys)
	      {
	    	  if(basic.get(key).equalsIgnoreCase("null")){
	    		  System.out.println("success");
		        	 basics.remove(key);
		         }
		         else{
		        	 specification.add(putData((key)+" : "+basics.get(key),basics.get(key)));
	          System.out.println(key);
	      }
	      }
	      System.out.println(basics);
		specification.add(basics);
		//information=jsonobj.optString("AndroidInformation1","");
		String  temp="http://images.vbuy.in/VBuyImages/large/";
		String imagename1=jsonobj.optString("PictureName","");
		String image = imagename1.replaceAll("\\s+", "%20");
		pic1=temp+image;
		String imagename2=jsonobj.optString("PictureName1","");
		String image1 = imagename2.replaceAll("\\s+", "%20");
		pic2=temp+image1;
		String imagename3=jsonobj.optString("PictureName2","");
		String image2 = imagename3.replaceAll("\\s+", "%20");
		pic3=temp+image2;
		stockList.add(image);
		stockList.add(image1);
		stockList.add(image2);
		 for ( int i = 0;  i < stockList.size(); i++){
	            String tempName = stockList.get(i);
	            if(tempName.equals("null"))
	            		{
	            	stockList.remove(i);
	            }
	            else{
	            	
	            	stockList1.add((temp+stockList.get(i)));
	            	
	            }
	        }
		
		Log.d("picture", imagename3);
		JSONArray array=jsonobj.getJSONArray("StorePricingModel");
		 String str_origin = "origin="+latitude+","+longitude;
		 String km = "K";
		 Double ans;
		 Double finals;
		for (int i = 0; i <= array.length()-1; i++) {
			JSONObject j = array.getJSONObject(i);
			Productpojo items =new Productpojo();
			String a=(j.optString("StoreName", ""));
			String b=(j.optString("BranchName", ""));
			String c=(j.optString("BranchAddress1", ""));
			String d=(j.optString("BranchAddress2", ""));
			String e=(j.optString("Price", ""));
			String f=(j.optString("SpecialPrice", ""));
			String g=(j.optString("Latitude", ""));
			String h=(j.optString("Longitude", ""));
			String value=(j.optString("AdditionalShippingCharge", ""));
			String l=(j.optString("DeliveryTime", ""));
			String m=(j.optString("BranchId", ""));
			String y=(j.optString("EnableBuy", ""));
			Log.d("productname", j.optString("BranchName", ""));
			double k = Double.parseDouble(value);
			ans= calculateDistance(Double.parseDouble(latitude),Double.parseDouble(longitude),Double.parseDouble(g),Double.parseDouble(h),km);
			 finals=round(ans, 2);
			users.add(new ProductModel(finals,a,c,d,e,f,g,h,k,l,m,y));
		}
		
		
	} catch (JSONException e) {
		
		e.printStackTrace();
	}
	catch (NumberFormatException e) {
		e.printStackTrace();
	}
	
}

protected void onPostExecute(String result) {
	progressDialog.dismiss();
if(result.equalsIgnoreCase("FAILED")){
		
			
		
		Toast.makeText(SellerListActivity.this,
				"Check your network connection", Toast.LENGTH_SHORT)
				.show();
	}
else {
	if (result.equalsIgnoreCase("SUCCESS")) {

	
	if(change.equalsIgnoreCase("change")){

		// locationDescription.setText(information);
	keyfeaturename.setText(key);
	HorizontalListView gallery = (HorizontalListView) findViewById(R.id.Gallery);
	 stockArr = new String[stockList1.size()];
	 stockArr = stockList1.toArray(stockArr);
	 
	 gallery.setAdapter(new AddImgAdp(SellerListActivity.this ,stockArr));
	 gallery.setOnItemClickListener(new OnItemClickListener() {
    	 public void onItemClick(AdapterView<?> parent, View v, int position,long id)
    	 {
    		 imageLoad.DisplayImage(stockArr[position], image);
              image.setScaleType(ImageView.ScaleType.FIT_CENTER);
         }
    	 });
	}
	
	if(users.isEmpty()){
		errorpage.setVisibility(View.VISIBLE);
		
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
	}
	else{
		
		Collections.sort(users, new Comparator<ProductModel>(){
		     /* public int compare(ProductModel obj1, ProductModel obj2)
		      {
		    	  if (obj1.getstorename() == obj2.getstorename())
		              return obj1.getstorename().compareTo(obj2.getstorename());  
		    	 return ( Double.parseDouble(obj1.getspecialprice()) <  Double.parseDouble(obj2.getspecialprice())) ? -1: ( Double.parseDouble(obj1.getspecialprice()) >  Double.parseDouble(obj2.getspecialprice())) ? 1:0 ;
		      }*/
			public int compare(ProductModel obj1, ProductModel obj2) {
				  int value = 0;
				  if (Double.parseDouble(obj1.getspecialprice()) > Double.parseDouble(obj2.getspecialprice()))
				   value = 1;
				  else if (Double.parseDouble(obj1.getspecialprice()) < Double.parseDouble(obj2.getspecialprice()))
				   value = -1;
				  else if ((Double.parseDouble(obj1.getspecialprice()) == Double.parseDouble(obj2.getspecialprice())))
				   value = 0;

				  return value;
				 }
		});
		selleradapter.notifyDataSetChanged();
		listview.setSelection(0);
		sortkm.setVisibility(View.VISIBLE);
		sortprice.setVisibility(View.INVISIBLE);
		sortprice_down.setVisibility(View.VISIBLE);
		sortkm_down.setVisibility(View.INVISIBLE);
		price_down.setVisibility(View.INVISIBLE);
		price_up.setVisibility(View.VISIBLE);
		km_down.setVisibility(View.INVISIBLE);
		km_up.setVisibility(View.INVISIBLE);
		name_up.setVisibility(View.INVISIBLE);
		lay_buy.setVisibility(View.VISIBLE);
		buystore.setText(users.get(0).getstorename());
		buyprice.setText(getString(R.string.rs)+users.get(0).getspecialprice());
		/*if(users.get(0).getparameter().equalsIgnoreCase("true")){
			buy.setVisibility(View.VISIBLE);
			availability.setVisibility(View.INVISIBLE);
		}
		else{
			availability.setVisibility(View.VISIBLE);
			buy.setVisibility(View.INVISIBLE);
		}*/
	}
	
}
}
}
}
private HashMap<String, String> putData(String name, String value) {

    HashMap<String, String> item = new HashMap<String, String>();
    item.put("aktivite", name);
    item.put("sayi", value);
    return item;
  }
public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
}
private double calculateDistance(double lat1, double lon1, double lat2, double lon2, String unit) 
{
      double theta = lon1 - lon2;
      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
      dist = Math.acos(dist);
      dist = rad2deg(dist);
      dist = dist * 60 * 1.1515;
      if (unit == "K") {
        dist = dist * 1.609344;
      } else if (unit == "M") {
        dist = dist * 0.8684;
        }
      return (dist);
}
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts decimal degrees to radians             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private double deg2rad(double deg) 
{
  return (deg * Math.PI / 180.0);
}

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts radians to decimal degrees             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private double rad2deg(double rad)
{
  return (rad * 180.0 / Math.PI);
}
private String downloadUrl(String strUrl) throws IOException{
    String data = "";
    InputStream iStream = null;
    HttpURLConnection urlConnection = null;
    try{
        URL url = new URL(strUrl);

        // Creating an http connection to communicate with url
        urlConnection = (HttpURLConnection) url.openConnection();

        // Connecting to url
        urlConnection.connect();

        // Reading data from url
        iStream = urlConnection.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

        StringBuffer sb  = new StringBuffer();

        String line = "";
        while( ( line = br.readLine())  != null){
            sb.append(line);
        }

        data = sb.toString();

        br.close();

    }catch(Exception e){
        /*Log.d("Exception while downloading url", e.toString());*/
    }finally{
        iStream.close();
        urlConnection.disconnect();
    }
    return data;
}	

protected  String  dis(List<List<HashMap<String, String>>> result) {
    ArrayList<LatLng> points = null;
    PolylineOptions lineOptions = null;
    MarkerOptions markerOptions = new MarkerOptions();
    String distance = "";
    String duration = "";

    if(result.size()<1){
        Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
       
    }

    // Traversing through all the routes
    for(int i=0;i<result.size();i++){
        points = new ArrayList<LatLng>();
        lineOptions = new PolylineOptions();

        // Fetching i-th route
        List<HashMap<String, String>> path = result.get(i);

        // Fetching all the points in i-th route
        for(int j=0;j<path.size();j++){
            HashMap<String,String> point = path.get(j);

            if(j==0){    // Get distance from the list
                distance = (String)point.get("distance");
                continue;
            }else if(j==1){ // Get duration from the list
                duration = (String)point.get("duration");
                continue;
            }}}
 return distance+duration;
    }
private void executeAsyncTask() {
Hashtable<String, String> ht = new Hashtable<String, String>();
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
	Toast.makeText(SellerListActivity.this, msg, Toast.LENGTH_LONG).show();
}

public void onClick(View v) {
	 
	if (v.getId() == R.id.imginactivity) {
		Intent intent = new Intent(
				SellerListActivity.this.getApplicationContext(),
				galleryView.class);
		intent.putExtra("picture", stockArr);
		intent.putExtra("area", actiontitle);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		 }

}



public class ProductModelAdapter extends ArrayAdapter<ProductModel> {
	ArrayList<ProductModel> productlist;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public ProductModelAdapter(Context context, int resource, ArrayList<ProductModel> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		productlist = objects;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder. name=(TextView)v.findViewById(R.id.shopname);
			holder.branch1=(TextView)v.findViewById(R.id.branceaddress1);
			holder. branch2=(TextView)v.findViewById(R.id.branchaddress2);
			holder. sprice=(TextView)v.findViewById(R.id.shopspecialprice);
			holder. addtional=(TextView)v.findViewById(R.id.addition);
			holder. timing=(TextView)v.findViewById(R.id.time);
			holder. kms=(TextView)v.findViewById(R.id.kms);
			holder. buy=(TextView)v.findViewById(R.id.buy);
			/*holder. map = (ImageView) v.findViewById(R.id.map);*/
			holder. store_buy = (Button) v.findViewById(R.id.store_buy);
			holder. ln = (LinearLayout) v.findViewById(R.id.sellerlist);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.name.setText(productlist.get(position).getstorename());
		holder.branch1.setText(productlist.get(position).getbranchadd1());
		holder.branch2.setText(productlist.get(position).getbranchadd2());
		holder.kms.setText(""+productlist.get(position).getkm()+" KM");
		holder.sprice.setText(getString(R.string.rs)+productlist.get(position).getspecialprice());
		if(productlist.get(position).getparameter().equalsIgnoreCase("true")){
			holder. store_buy.setVisibility(View.VISIBLE);
		}
		else{
			holder. store_buy.setVisibility(View.INVISIBLE);
		}
		if(productlist.get(position).gettime().equalsIgnoreCase("null"))
		{
			holder.addtional.setText(getString(R.string.bullet)+" "+"Contact store to know delivery details");
			holder.timing.setText("");
		}
		else 
		{
			holder.addtional.setText(getString(R.string.bullet)+" "+"Delivered within"+" "+productlist.get(position).gettime()+" "+"business day");
		double shipping=productlist.get(position).getadditional();
		 if(shipping==0)
		{
			holder.timing.setText(getString(R.string.bullet)+" "+"Free Shipping");
		}
		else{
			holder.timing.setText(getString(R.string.bullet)+" "+"Shipping Amount :"+productlist.get(position).getadditional());
		}

		}
		/*holder.map.setOnClickListener(new OnClickListener() {
	         
	         public void onClick(View v) {
	        	 Intent intent = new Intent(context,MapActivity.class);
	        	 intent.putExtra("storename", productlist.get(position).getstorename());
	        	 intent.putExtra("storeaddress1", productlist.get(position).getbranchadd1());
	        	 intent.putExtra("storeaddress2", productlist.get(position).getbranchadd2());
	        	 intent.putExtra("productname", itemname);
	        	 intent.putExtra("price", productlist.get(position).getspecialprice());
	        	 intent.putExtra("mylatitude", latitude);
	        	 intent.putExtra("mylongitude", longitude);
	        	 intent.putExtra("storelatitude", productlist.get(position).getlat());
	        	 intent.putExtra("storelongitude", productlist.get(position).getlon());
	             context.startActivity(intent);  
	        		
	         }
	     });*/
		holder.ln.setOnClickListener(new OnClickListener() {

	         public void onClick(View v) {
	        	Intent intent = new Intent(context,StoresProduct.class);
	        	 intent.putExtra("Pro_id", productid);
	        	 intent.putExtra("store_id", storeid);

	             context.startActivity(intent);

	         }
	     });
		
		/*holder.ln.setOnClickListener(new OnClickListener() {
	         
	         public void onClick(View v) {
	        	Intent intent = new Intent(context,ContactSeller.class);
	        	 intent.putExtra("imageurls", imageurls);
	        	 intent.putExtra("productname", itemname);
	        	 intent.putExtra("productid", productid);
	        	 intent.putExtra("area", actiontitle);
	     		intent.putExtra("storename", productlist.get(position).getstorename());
	     		intent.putExtra("branchid", productlist.get(position).getbranchid());
	     		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	             context.startActivity(intent); 
	        		 
	         }
	     });*/
		holder.store_buy.setOnClickListener(new OnClickListener() {
	         
	         public void onClick(View v) {
	        	 
	        	 double rate = Double.parseDouble(users.get(position).getspecialprice());
					p.setImageId(imagename);
					p.setItemDesc(productlist.get(position).getadditional());
					p.setbranch(users.get(position).getstorename());
					p.setbranchid(users.get(position).getbranchid());
					p.setstoreId(users.get(position).getbranchid());
					p.setunitprice(Double.parseDouble(users.get(position).getspecialprice()));
					p.setItemName(itemname);
					p.setproductId(productid);
					p.setPrice(rate);
					p.setQty(1);
					
					db = new DBLogin(getApplicationContext());
					 db.open();
					 Cursor c = db.getAllContacts();
					 if (db.getAllContactscount(1)){
						 if(cachetoken.token.size()>0){
						 authen = cachetoken.token.get("token");
						 ShopCart.addProduct(getApplicationContext(),p.getbranchid(),p.getproductId(), p);
				    	 c = db.getContact(1);
				    	Username=(c.getString(2));
				    	new AddProduct().execute();
						
						 }
						 else{
							 Intent intent = new Intent(
										SellerListActivity.this.getApplicationContext(),
										RetailerMain.class);
								intent.putExtra("dir", "session");
								intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
								startActivity(intent);
						 }
					 }
					 else{
						 ShopCart.addProduct(getApplicationContext(),p.getbranchid(),p.getproductId(), p);
						 Intent intent = new Intent(
									SellerListActivity.this.getApplicationContext(),
									ShopCartActivity.class);
						 intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivity(intent);
					 }
					
					 }
				});
		return v;

	}

}
static class ViewHolder {
	public TextView name;
	public TextView branch1;
	public TextView branch2;
	public TextView sprice;
	public TextView addtional;
	public TextView timing;
	public TextView kms;
	public TextView buy;
	public LinearLayout ln;
	public Button contact;
	public ImageView map;
	public Button store_buy;
	}

public class AddImgAdp extends BaseAdapter {
    int GalItemBg;
    private Context cont;
    private Activity activity;  
    private  LayoutInflater inflater=null; 
    private String[] image_urls;
   
    public AddImgAdp(Activity a,String[] image_id) { 
    	this.image_urls=image_id;
        activity = a;  
             inflater = (LayoutInflater)activity.getSystemService
                     (Context.LAYOUT_INFLATER_SERVICE);          } 
    public int getCount() {
        return image_urls.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public  class ViewHolder{     
        public ImageView image;         } 
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View vi=convertView;   
        ViewHolder holder;   
        if(convertView==null){  
            vi = inflater.inflate(R.layout.category_gallery1, null); 
            holder=new ViewHolder();  
            holder.image=(ImageView)vi.findViewById(R.id.img);   
            vi.setTag(holder);             }  
        else    

            holder=(ViewHolder)vi.getTag(); 
        imgLoader.DisplayImage(image_urls[position],0, holder.image);
            return vi;     
    	
    	
        
    }
    
}
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	this.finish();
	overridePendingTransition(0, 0);
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


class AddProduct extends AsyncTask<String, String, String> {

	
	
 	/**
 	 * Before starting background thread Show Progress Dialog
 	 * */
	
	private final ProgressDialog progressDialog1 = new ProgressDialog(SellerListActivity.this);

	protected void onPreExecute() {
		progressDialog1.setCancelable(false);
		progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog1.setIndeterminate(true);
		progressDialog1.setMessage("Adding To Cart...");
		progressDialog1.show();
	}

 	/**
 	 * add product
 	 * */
 	protected String doInBackground(String... args) {
 		String temp = null;
 		JSONArray jsonfilter=new JSONArray();
		 try {
		 JSONObject jsonob=new JSONObject();
			jsonob.put("Id", p.getId()) ;
			jsonob.put("StoreId", p.getstoreId()) ;
			jsonob.put("CustomerId", p.getcustomerId()) ;
			jsonob.put("ProductId", p.getproductId()) ;
			jsonob.put("Quantity", p.getQty()) ;
			jsonob.put("UnitPrice", p.getunitprice()) ;
			jsonob.put("SpecialPrice", p.getPrice()) ;
			jsonob.put("Name", p.getItemName()) ;
			jsonob.put("PictureName", p.getImageId()) ;
			jsonob.put("BranchId", p.getbranchid()) ;
			jsonob.put("Branch", p.getbranch()) ;
			jsonob.put("AdditionalShippingCharge", p.getItemDesc()) ;
			jsonfilter.put(jsonob);
			
		 }
		catch(JSONException ex) {
			
	        ex.printStackTrace();

	    }
 		
 		HttpParams myParams = new BasicHttpParams();
 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
 	    HttpClient httpclient = new DefaultHttpClient(myParams );
 	    

 	    try {
 	    	JSONObject cart=new JSONObject();
 	    	cart.put("shoppingCartDTOList", jsonfilter);
 	    	cart.put("userName", Username);
 	    	String json=cart.toString();
 	    	Log.d("Add to cart", json);
 	        HttpPost httppost = new HttpPost(getString(R.string.addcart));
 	        httppost.setHeader("Content-type", "application/json");
 	        httppost.addHeader("Authorization",  "Bearer"+" "+authen );
 	        StringEntity se = new StringEntity(json); 
 	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
 	        httppost.setEntity(se); 

 	        HttpResponse response = httpclient.execute(httppost);
 	        temp = EntityUtils.toString(response.getEntity());
 	        Log.i("Response", temp);
 	       
 	    } catch (ClientProtocolException e) {
 	    	return "Failed";
 	    } catch (IOException e) {
 	    	return "Failed";
 	    } catch (JSONException e) {
 	    	return "Failed";
		}
 	    catch(Exception e){
 	    	return "Failed";
 	    }
 		return temp;
	}

 	
	/**
 	 * After completing background task Dismiss the progress dialog
 	 * **/
 	protected void onPostExecute(String result) {
 		if(result.equalsIgnoreCase("Failed")){
 			progressDialog1.dismiss();
 			Toast.makeText(SellerListActivity.this,
 					"Check your network connection", Toast.LENGTH_LONG)
 					.show();
 		}
 		else{
 		ShopCart.clearall(getApplicationContext());
 		boolean status = true;
		try {
			JSONArray array;
			array = new JSONArray(result);
		
		for (int i = 0; i <= array.length()-1; i++)
		{
			JSONObject j = array.getJSONObject(i);
			Product p = new Product();
			p.setImageId(j.optString("PictureName", ""));
			p.setItemDesc(Double.parseDouble(j.optString("AdditionalShippingCharge", "")));
			p.setbranch(j.optString("Branch", ""));
			p.setbranchid(j.optString("BranchId", ""));
			p.setstoreId(j.optString("StoreId", ""));
			p.setunitprice(Double.parseDouble(j.optString("UnitPrice", "")));
			p.setItemName(j.optString("Name", ""));
			p.setproductId(j.optString("ProductId", ""));
			p.setPrice(Double.parseDouble(j.optString("SpecialPrice", "")));
			p.setQty(Integer.parseInt(j.optString("Quantity", "")));
			status = ShopCart.addProduct(getApplicationContext(),p.getbranchid(),p.getproductId(), p);
			
		}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		progressDialog1.dismiss();
 		if(status){
 		Intent intent = new Intent(
				SellerListActivity.this.getApplicationContext(),
				ShopCartActivity.class);
 		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
 		}
 	}

}

	}
	
}
	
	
	
	
	