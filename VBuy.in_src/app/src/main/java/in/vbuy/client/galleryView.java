package in.vbuy.client;

import in.vbuy.client.util.HorizontalListView;
import in.vbuy.client.util.ImageLoader;
import in.vbuy.client.util.ImageLoader1;
import in.vbuy.client.util.TouchImageView;
import in.vbuy.client.util.TouchImageView.OnTouchImageViewListener;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class galleryView extends ActionBarActivity {
    
	private TouchImageView image;
	ImageLoader1 imageLoader;
    ImageLoader imageLoader1;
    private HorizontalListView gallery;
    String actiontitle;
    GPSTracker gps;
    String  []strings=null;
    private Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.seller);
        
        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
      //toolbartitle = (TextView) findViewById(R.id.titletool);

      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
        
        gps = new GPSTracker(galleryView.this);
        Intent in = getIntent();
        strings = in.getStringArrayExtra("picture");
		actiontitle = in.getStringExtra("area");
       // final String [] strings={pic1,pic2,pic3};
         imageLoader=new ImageLoader1(galleryView.this);
         imageLoader1=new ImageLoader(galleryView.this);
         gallery = (HorizontalListView) findViewById(R.id.Gallery);
         gallery.setAdapter(new AddImgAdp(this));
         image = (TouchImageView) findViewById(R.id.img);
         imageLoader.DisplayImage(strings[0],  image);
        gallery.setOnItemClickListener(new OnItemClickListener() {
        	 public void onItemClick(AdapterView<?> parent, View v, int position,long id)
        	 {
        		    image.resetZoom();
                  imageLoader.DisplayImage(strings[position],  image); 
                 
             }
        	 });
      
image.setOnTouchImageViewListener(new OnTouchImageViewListener() {
			
			
			public void onMove() {
				PointF point = image.getScrollPosition();
				RectF rect = image.getZoomedRect();
				float currentZoom = image.getCurrentZoom();
				boolean isZoomed = image.isZoomed();
				}
		});
       
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
        
    }
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		overridePendingTransition(0, 0);
	}
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	/** Create an option menu from res/menu/items.xml */
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
    
    public class AddImgAdp extends BaseAdapter {
        int GalItemBg;
        private Context cont;
        private Activity activity;  
        private  LayoutInflater inflater=null; 
       
        public AddImgAdp(Activity a) {   
            activity = a;  
                 inflater = (LayoutInflater)activity.getSystemService
                         (Context.LAYOUT_INFLATER_SERVICE);          } 
        public int getCount() {
            return strings.length;
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
                imageLoader1.DisplayImage(strings[position],0, holder.image);
                return vi;     
        	
        	
            
        }
        
    }
 }