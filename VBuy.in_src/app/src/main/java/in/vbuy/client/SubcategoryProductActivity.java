package in.vbuy.client;


import in.vbuy.client.util.Categorypojo;
import in.vbuy.client.util.ExpandableListAdapter;
import in.vbuy.client.util.Item;
import in.vbuy.client.util.Productpojo;
import in.vbuy.client.util.SubCategorySupportfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class SubcategoryProductActivity extends ActionBarActivity {
/** Called when the activity is first created. */
ListView list;
TextView error;
DBAdapter db = new DBAdapter(this);
ArrayList<Productpojo> itemlist=new ArrayList<Productpojo>();
static final String category = "category";
String url;
String catid;

String area=" ";

public String mainCategoryname[];
public String Categoryname[];
public String splitarray[];
private ExpandableListView mExpandablelistView;
private ArrayList<String> parentItems = new ArrayList<String>();
private ArrayList<Object> childItems = new ArrayList<Object>();
private HashMap<String, List<String>> listDataChild;
private ExpandableListAdapter mExpandableListAdapter;
ArrayList<String> splited = new ArrayList<String>();
ArrayList<String> groupvalue = new ArrayList<String>();
ArrayList<String> childarray = new ArrayList<String>();
ArrayList<Item> items = new ArrayList<Item>();
ArrayList<String> itemnameadapter = new ArrayList<String>();
ArrayList<String> maincatarray = new ArrayList<String>();
//GPSTracker gps; sri
SharedPreferences preferences;
String title ;
HashMap<String, ArrayList<Productpojo>> temp = SubCategorySupportfile.productdetials;
String hashmapkeyname;
String catID;
Animation animation = null;

ListView lview;

RelativeLayout mainView;
private DrawerLayout mDrawerLayout;
   private ListView mDrawerList;
   private ActionBarDrawerToggle mDrawerToggle;
   TextView nooffers,nofeatures;
   private RelativeLayout mDrawerContent;
private Toolbar toolbar;

ProgressBar pb1,pb2;


public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//getActionBar().setDisplayHomeAsUpEnabled(true);

setContentView(R.layout.subcategory);

toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//toolbartitle = (TextView) findViewById(R.id.titletool);

setSupportActionBar(toolbar);
getSupportActionBar().setDisplayShowTitleEnabled(false);

mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
mDrawerList = (ListView) findViewById(R.id.drawer_list);
mDrawerContent = (RelativeLayout) findViewById(R.id.relative_layout);
toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//toolbartitle = (TextView) findViewById(R.id.titletool);
//hide=(Button)findViewById(R.id.show1);
mainView = (RelativeLayout) findViewById(R.id.content_frame);

//gps = new GPSTracker(SubcategoryProductActivity.this);sri
error=(TextView) findViewById(R.id.error);
Intent in = getIntent();
title = in.getStringExtra("category");
catid=in.getStringExtra("id");
hashmapkeyname=title;
catID=catid;
Log.d("productname", hashmapkeyname);
String link = catid.replaceAll("\\s+", "%20");
String urladdress=	getString(R.string.subcategorys);
url =urladdress + link;
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
  
   mDrawerToggle = new ActionBarDrawerToggle(
           this,                  /* host Activity */
           mDrawerLayout,         /* DrawerLayout object */
             /* nav drawer image to replace 'Up' caret */
           R.string.drawer_open,  /* "open drawer" description for accessibility */
           R.string.drawer_close  /* "close drawer" description for accessibility */
   ) {
       public void onDrawerClosed(View view) {
           //toolbar.setTitle(mTitle);
          // toolbartitle.setText(mTitle);
       	//hide.setVisibility(View.VISIBLE);
           invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
       }

       public void onDrawerOpened(View drawerView) {
       //	hide.setVisibility(View.GONE);
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

   lview=(ListView)findViewById (R.id.drawer_list1);
   maincatarray = SubCategorySupportfile.Categorydata.get("mainarray");
   mainCategoryname = maincatarray.toArray(new String[0]);
   FragmentAdapter fada=new FragmentAdapter(SubcategoryProductActivity.this, mainCategoryname);
   lview.setAdapter(fada);
   lview.setOnItemClickListener(new OnItemClickListener() {

		
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
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			startActivity(in);
			
		}
	});	
getmaincategorydetials();
mExpandablelistView = (ExpandableListView) findViewById(R.id.expandablelistview);


mExpandableListAdapter = new ExpandableListAdapter(SubcategoryProductActivity.this,groupvalue, childItems , title);

// setting list adapter
mExpandablelistView.setAdapter(mExpandableListAdapter);
for(int i=0; i < mExpandableListAdapter.getGroupCount(); i++)
	mExpandablelistView.expandGroup(i);
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
									SubcategoryProductActivity.this.getApplicationContext(),
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
	Toast.makeText(SubcategoryProductActivity.this, msg, Toast.LENGTH_LONG).show();
	finish();
}


public static Set<String> sortList (List<String> myList){
    Set<String> hashsetList = new HashSet<String>(myList);

    Set<String> treesetList = new TreeSet<String>(myList);
	return treesetList;
}

public void getmaincategorydetials() {
	Categoryname = null;
	splitarray = null;

	itemnameadapter = SubCategorySupportfile.Categorydata.get(catid);
	
     int size = itemnameadapter.size();
     
	   Categorypojo cpojo= new Categorypojo();
	for (int i=0;i<itemnameadapter.size();i++){
	String listvalue=	itemnameadapter.get(i);
	String[]splittemp= listvalue.split("~");
		if(splittemp.length==3) {
			splited.add(splittemp[2]);
		}
		else
		{
			splited.add("empt");
			//splited.add(splittemp[2]);
		}



	
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
		if(!groupvalue.get(j).equals("empt")) {
			if (splittemp.length == 3) {

				if (groupvalue.get(j).equalsIgnoreCase(splittemp[2])) {
					child.add(splittemp[0] + "~" + splittemp[1]);

					// items.add(new EntryItem(splittemp[0], splittemp[1]));
				}
			}
		}
		else {
			if (splittemp.length != 3) {
				child.add(splittemp[0] + "~" + splittemp[1]);
			}
		}

	}
	childItems.add(child);

	}
	Categoryname = itemnameadapter.toArray(new String[0]);

		
}
	
		
}	

