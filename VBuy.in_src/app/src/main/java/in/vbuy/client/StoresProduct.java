package in.vbuy.client;

import in.vbuy.client.SellerListActivity.ViewHolder;
import in.vbuy.client.UILApplication.TrackerName;
import in.vbuy.client.util.AdapterclassCategory1;
import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.Product;
import in.vbuy.client.util.Productpojo;
import in.vbuy.client.util.ShopCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class StoresProduct extends ActionBarActivity {

    String productid,storeid,store_name,branch_id,branchname;
    String id,sub_id,cat_name;
    Hashtable<String, String> ht = new Hashtable<String, String>();
    ArrayList<String> catarray;
    ArrayList<String> brancharray;
    public String Categoryname1[];
    public String addstring[];
    ImageView wishlisted1,wishlist1;
    String emailid,token,Username,buy_enable;
    int wish_pos;
    Spinner cat;
    Spinner branch;
    GridView gridView ;
    static gridviewAdapter1 gridadapter;
    final Context context = this;
    ArrayList<Productpojo>users=new ArrayList<Productpojo>();
    public String subcategory[];
    public String subcategory1[];
    ArrayList<String> subcategoryname1;
    ArrayList<String> subcategoryname;
    ProgressBar pb;
    Product p = new Product();
    String actiontitle;
    String finallati,finallongi;
    DBAdapter db = new DBAdapter(this);
    private Toolbar toolbar;
    TextView branch_nam,add1,add2,state,city,store_nam;
    private TextView mCounter;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.store_details);
        subcategoryname = new ArrayList<String>();
        subcategoryname1 = new ArrayList<String>();
        brancharray = new ArrayList<String>();
        catarray = new ArrayList<String>();
        pb =(ProgressBar) findViewById(R.id.loading);
        store_nam=(TextView)findViewById(R.id.storename);
        branch_nam=(TextView)findViewById(R.id.branch);
        add1=(TextView)findViewById(R.id.address1);
        add2=(TextView)findViewById(R.id.address2);
        city=(TextView)findViewById(R.id.city);
        state=(TextView)findViewById(R.id.state);
        gridView = (GridView) findViewById(R.id.gridView1);
        Intent in=getIntent();
        productid=in.getStringExtra("Pro_id");
        storeid=in.getStringExtra("store_id");
        gridView.setOnScrollListener(new PauseOnScrollListener(true, true));
        users = new ArrayList<Productpojo>();
        gridadapter = new gridviewAdapter1(getApplicationContext(), R.layout.stores_productrow, users);
        gridView.setAdapter(gridadapter);
        Tracker t = ((UILApplication) getApplication()).getTracker(TrackerName.APP_TRACKER);
        t.setScreenName("Store Page :ID "+storeid);
       t.send(new HitBuilders.AppViewBuilder().build());
        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        //toolbartitle = (TextView) findViewById(R.id.titletool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        db.open();
        Cursor c = db.getAllContacts();
        if (db.getAllContactscount(1)){
            c = db.getContact(1);
            actiontitle=c.getString(1);
            finallati=c.getString(2);
            finallongi=c.getString(3);
        }
        else {

            actiontitle="Chennai";
            finallati="13.062657742091597";
            finallongi="80.22018974609375";
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
        toolbar.setTitle("  "+actiontitle);

        executeAsyncTask();



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
            String storedetails=	getString(R.string.GetStoreInfo)+storeid;
            String productfilter=getString(R.string.GetStoresProductFilter)+productid+"&storeId="+storeid;

            String json = HelperHttp.getJSONResponseFromURL(storedetails
                    , ht);
            String json_offer = HelperHttp.getJSONResponseFromURL(productfilter
                    , ht);
            if (json != null)
                parseJsonString(json,json_offer);
            else {
                return "Invalid Company Id";
            }
            return "SUCCESS";
        }
        protected void parseJsonString(String json , String json_offer ) {
            try {

                JSONObject address=new JSONObject(json);
                store_name=address.optString("StoreName");
                JSONArray branchjson=address.getJSONArray("Branches");
                for(int a=0; a<branchjson.length(); a++ ){
                    JSONObject j = branchjson.getJSONObject(a);
                    String BranchId=j.optString("BranchId");
                    String BranchName=j.optString("BranchName");
                    String Address1=j.optString("Address1");
                    String Address2=j.optString("Address2");
                    String City=j.optString("City");
                    String State=j.optString("State");
                    String PhoneNumber=j.optString("PhoneNumber");
                    String Email=j.optString("Email");
                    String buy=j.optString("EnableBuy");
                    String fulladdress=BranchName+"~"+BranchId+"@"+Address1+"@"+Address2+"@"+City+"@"+State+"@"+buy+"@"+PhoneNumber+"@"+Email;
                    brancharray.add(fulladdress);
                }

                JSONObject jsonobj=new JSONObject(json_offer);
                JSONArray array=jsonobj.getJSONArray("CategoryFilter");
                for (int i = 0; i <= array.length() - 1; i++) {
                    JSONObject j = array.getJSONObject(i);


                    String catename,cateid;
                    String  temp,temp1;

                    Log.d("id", j.optString("CategoryId", ""));
                    cateid=j.optString("CategoryId", "");
                    catename=j.optString("Name", "");

                    catarray.add(catename+"~"+cateid);

                }
                JSONArray array_offer=jsonobj.getJSONArray("SubCategoryFilter");
                String CategoryId,Name,ParentCategoryId;
                String  temp3;
                for (int i = 0; i <= array_offer.length() - 1; i++) {
                    JSONObject j = array_offer.getJSONObject(i);
                    CategoryId=j.optString("CategoryId", "");
                    Name=j.optString("Name", "");
                    ParentCategoryId=j.optString("ParentCategoryId", "");
                    temp3=Name+"~"+CategoryId+"~"+ParentCategoryId;
                    subcategoryname.add(temp3);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        protected void onPostExecute(String result) {

            if (result == "SUCCESS") {
                if(catarray.isEmpty()){
                    pb.setVisibility(View.GONE);
                    finish();
                }
                else{

                    addstring = brancharray.toArray(new String[0]);

                    AdapterclassCategory1 addadapter = new AdapterclassCategory1(StoresProduct.this,
                            addstring);
                    branch=(Spinner) findViewById(R.id.spinner1);
                    branch.setAdapter(addadapter);
                    branch
                            .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                public void onItemSelected(AdapterView<?> arg0,
                                                           View arg1, int position, long arg3) {
                                    branchname=((TextView) arg1.findViewById(R.id.code))
                                            .getText().toString();
                                    String branchs_nam=((TextView) arg1.findViewById(R.id.code))
                                            .getTag().toString();
                                    String [] branchs_name=branchs_nam.split("@");
                                    branch_id=branchs_name[0];
                                    store_nam.setText(store_name);
                                    branch_nam.setText(((TextView) arg1.findViewById(R.id.code)).getText().toString());
                                    add1.setText(branchs_name[1]);
                                    add2.setText(branchs_name[2]);
                                    state.setText(branchs_name[4]);
                                    city.setText(branchs_name[3]);
                                    buy_enable=branchs_name[5];

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });


                    Categoryname1 = catarray.toArray(new String[0]);

                    AdapterclassCategory1 adapter = new AdapterclassCategory1(StoresProduct.this,
                            Categoryname1);
                    cat=(Spinner) findViewById(R.id.spinner2);
                    cat.setAdapter(adapter);
                    cat
                            .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @SuppressWarnings("unchecked")
                                @Override
                                public void onItemSelected(AdapterView<?> arg0,
                                                           View arg1, int position, long arg3) {
                                    // TODO Auto-generated method stub
                                    // Locate the textviews in activity_main.xml

                                    id=((TextView) arg1.findViewById(R.id.code))
                                            .getTag().toString();
                                    cat_name=((TextView) arg1.findViewById(R.id.code))
                                            .getText().toString();
                                    new GetDeptAyncTask1().execute();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });

                }
            }
        }
    }
    private   class GetDeptAyncTask1 extends
            AsyncTask<Hashtable<String, String>, Void, String> {


        protected void onPreExecute() {

        }

        protected String doInBackground(Hashtable<String, String>... params){
            subcategory = subcategoryname.toArray(new String[0]);
            subcategoryname1.clear();
            for(int i=0;i<subcategoryname.size(); i++){
                String[] iid=subcategory[i].split("~");
                if(id.equalsIgnoreCase(iid[2])){
                    subcategoryname1.add(subcategory[i]);
                }
            }

            {
            }
            return "SUCCESS";
        }
        protected void onPostExecute(String result) {
            subcategory1=null;
            subcategory1 = subcategoryname1.toArray(new String[0]);
            AdapterclassCategory1 adapter1 = new AdapterclassCategory1(StoresProduct.this,
                    subcategory1);
            Spinner  subcat=(Spinner) findViewById(R.id.spinner3);
            subcat.setAdapter(adapter1);
            subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @SuppressWarnings("unchecked")
                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {

                    sub_id=((TextView) arg1.findViewById(R.id.code))
                            .getTag().toString();
                    gridadapter.clear();
                    GetStoreProducts();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }

    private void GetStoreProducts() {
        GetStoreProduct async = new GetStoreProduct();
        // ht.put("company_id", "1");
        Hashtable[] ht_array = { ht };
        async.execute(ht_array);
    }
    private class GetStoreProduct extends
            AsyncTask<Hashtable<String, String>, Void, String> {


        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Hashtable<String, String>... params) {
            Hashtable ht = params[0];


            String url=getString(R.string.GetStoreProducts)+branch_id+"&selectedCategory="+id+"&selectedSubCategory="+sub_id+"&storeId="+storeid;


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
                    users.add(new Productpojo(id,pid,name,imageurl,price,stores,imagename,wish));
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String result) {

            if (result == "SUCCESS") {
                if(users.isEmpty()){


                }

                else{

                    gridadapter.notifyDataSetChanged();

                    getListViewSize(gridView);


                }

            }else {
                Toast.makeText(StoresProduct.this,
                        "Check your network connection", Toast.LENGTH_LONG)
                        .show();



            }

        }

    }
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        GoogleAnalytics.getInstance(StoresProduct.this).reportActivityStart(this);
    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        GoogleAnalytics.getInstance(StoresProduct.this).reportActivityStop(this);
    }
    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        GoogleAnalytics.getInstance(StoresProduct.this).reportActivityStart(this);
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


        subMenu.add(0, 6, 3, "Wishlist").setIcon(R.drawable.wish_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        subMenu.add(0, 3, 4, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        subMenu.add(0, 4, 5, "Rate Us").setIcon(R.drawable.rate).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
        mCounter = (TextView) badgeLayout.findViewById(R.id.counter);
        if(ShopCart.totalQuantity(getApplicationContext())>0){
            mCounter.setVisibility(View.VISIBLE);
            mCounter.setText(""+ShopCart.totalQuantity(getApplicationContext()));
        }
        else{
            mCounter.setVisibility(View.GONE);
        }

        final MenuItem item = menu.findItem(R.id.badge);
        item.getActionView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item);
                Intent in=new Intent(getApplicationContext(),ShopCartActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(in);
            }
        });
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
                                    StoresProduct.this.getApplicationContext(),
                                    LoginMain.class);
                            intent.putExtra("dir", "session");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                    } else {

                        if (isNetworkAvailable())
                        {

                            Intent intent = new Intent(getApplicationContext(), LoginMain.class);
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
            Intent in=new Intent(getApplicationContext(),LoginMain.class);
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

    public static void getListViewSize(GridView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int single = 0, totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount()/2; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            listItem.measure(0, 0);
            single=listItem.getMeasuredHeight();
            Log.i("height of single:", String.valueOf(listItem.getMeasuredHeight()));
            totalHeight +=( listItem.getMeasuredHeight());
        }
        if(myListAdapter.getCount()%2!=0){
            totalHeight=totalHeight+single;
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + ((myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
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
        Toast.makeText(StoresProduct.this, msg, Toast.LENGTH_LONG).show();
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
            final TextView price=(TextView)rowView.findViewById(R.id.code);
            final LinearLayout wish_lay =(LinearLayout)rowView.  findViewById(R.id.wish_lay);
            //final LinearLayout contact =(LinearLayout)rowView.  findViewById(R.id.share_lay);
            final ImageView wishlisted=(ImageView)rowView.findViewById(R.id.wishlisted);
            final ImageView wishlist=(ImageView)rowView.findViewById(R.id.wishlist);
            final Button cart=(Button)rowView.findViewById(R.id.buy);

            name.setText(productlist.get(position).getProname());
            price.setText(""+((int) (Double.parseDouble(productlist.get(position).getId())))+".00");
            name.setTextColor(this.cnt.getResources()
                    .getColorStateList(R.color.Rcolor));
            if(buy_enable.equalsIgnoreCase("true")){
                cart.setVisibility(View.VISIBLE);
            }
            else{
                cart.setVisibility(View.GONE);
            }
            if(productlist.get(position).getwish().equalsIgnoreCase("true")){

                wishlisted.setVisibility(View.VISIBLE);
                wishlist.setVisibility(View.GONE);
            }
            else{
                wishlisted.setVisibility(View.GONE);
                wishlist.setVisibility(View.VISIBLE);
            }
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
                    spinner.setVisibility(View.GONE);
                }
            });

            /*contact.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {

                    Intent intent = new Intent(context,ContactSeller.class);
                    intent.putExtra("imageurls", productlist.get(position).getImgurl());
                    intent.putExtra("productname", productlist.get(position).getProname());
                    intent.putExtra("productid", productlist.get(position).getPid());
                    intent.putExtra("area", actiontitle);
                    intent.putExtra("storename", store_name);
                    intent.putExtra("branchid", branch_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);

                }
            });*/
            /*cart.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {

                    double rate = Double.parseDouble(productlist.get(position).getId());
                    p.setImageId(productlist.get(position).getimage());
                    p.setItemDesc(Double.parseDouble("0.00"));
                    p.setbranch(branchname);
                    p.setbranchid(branch_id);
                    p.setstoreId(storeid);
                    p.setunitprice(Double.parseDouble(productlist.get(position).getId()));
                    p.setItemName(productlist.get(position).getProname());
                    p.setproductId(productlist.get(position).getPid());
                    p.setPrice(rate);
                    p.setQty(1);

                    DBLogin db = new DBLogin(getApplicationContext());
                    db.open();
                    Cursor c = db.getAllContacts();
                    if (db.getAllContactscount(1)){
                        if(cachetoken.token.size()>0){
                            token = cachetoken.token.get("token");
                            ShopCart.addProduct(getApplicationContext(),p.getbranchid(),p.getproductId(), p);
                            c = db.getContact(1);
                            Username=(c.getString(2));
                            new AddProduct().execute();

                        }
                        else{
                            Intent intent = new Intent(
                                    StoresProduct.this.getApplicationContext(),
                                    LoginMain.class);
                            intent.putExtra("dir", "session");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                    }
                    else{
                        ShopCart.addProduct(getApplicationContext(),p.getbranchid(),p.getproductId(), p);
                        if(ShopCart.totalQuantity(getApplicationContext())>0){
                            mCounter.setVisibility(View.VISIBLE);
                            mCounter.setText(""+ShopCart.totalQuantity(getApplicationContext()));
                        }
                        else{
                            mCounter.setVisibility(View.GONE);
                        }
                        Toast.makeText(StoresProduct.this,
                                "Successfully Added...", Toast.LENGTH_SHORT)
                                .show();
                    }

                }
            });*/
            wish_lay.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    wishlisted1=(ImageView)rowView.findViewById(R.id.wishlisted);
                    wishlist1=(ImageView)rowView.findViewById(R.id.wishlist);
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
                                            StoresProduct.this.getApplicationContext(),

                                            LoginMain.class);
                                    intent.putExtra("dir", "session");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                }
                            } else {

                                if (isNetworkAvailable())
                                {

                                    Intent intent = new Intent(getApplicationContext(), LoginMain.class);
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
            rowView.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {

                    Intent int1 = new Intent(context,SellerListActivity.class);
                    int1.putExtra("productname",productlist.get(position).getProname()  );
                    int1.putExtra("productid", productlist.get(position).getPid());
                    int1.putExtra("mapvalue","5");
                    int1.putExtra("imageurl", productlist.get(position).getImgurl());
                    int1.putExtra("image", users.get(position).getimage());
                    int1.putExtra("latitude", finallati);
                    int1.putExtra("longitude", finallongi);
                    int1.putExtra("area", actiontitle);
                    int1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(int1);

                }
            });
            return rowView;
        }
        class AddProduct extends AsyncTask<String, String, String> {



            /**
             * Before starting background thread Show Progress Dialog
             * */

            private final ProgressDialog progressDialog1 = new ProgressDialog(StoresProduct.this);

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


                HttpParams myParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(myParams, 10000);
                HttpConnectionParams.setSoTimeout(myParams, 10000);
                HttpClient httpclient = new DefaultHttpClient(myParams );


                try {
                    JSONObject cart=new JSONObject();
                    cart.put("BranchId", p.getbranchid());
                    cart.put("Quantity", p.getQty());
                    cart.put("UnitPrice", p.getunitprice());
                    cart.put("userName", Username);
                    cart.put("ProductId", p.getproductId());
                    String json=cart.toString();
                    Log.d("Add to cart", json);
                    HttpPost httppost = new HttpPost(getString(R.string.AddShoppingCartItem));
                    httppost.setHeader("Content-type", "application/json");
                    httppost.addHeader("Authorization",  "Bearer"+" "+token );
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
                    Toast.makeText(StoresProduct.this,
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
                            if(j.optString("AdditionalShippingCharge", "").equalsIgnoreCase("null")){

                            }
                            else{
                                p.setItemDesc(Double.parseDouble(j.optString("AdditionalShippingCharge", "")));
                            }
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
                    if(ShopCart.totalQuantity(getApplicationContext())>0){
                        mCounter.setVisibility(View.VISIBLE);
                        mCounter.setText(""+ShopCart.totalQuantity(getApplicationContext()));
                    }
                    else{
                        mCounter.setVisibility(View.GONE);
                    }
                    Toast.makeText(StoresProduct.this,
                            "Successfully Added...", Toast.LENGTH_SHORT)
                            .show();
                }

            }

        }
        class wishlist extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread Show Progress Dialog
             * */
            private final ProgressDialog pDialog1 = new ProgressDialog(StoresProduct.this);
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
                    Toast.makeText(StoresProduct.this,
                            "Check your network connection", Toast.LENGTH_LONG)
                            .show();
                }
                else{
                    pDialog1.dismiss();
                    wishlist1.setVisibility(View.GONE);
                    wishlisted1.setVisibility(View.VISIBLE);
                    productlist.get(wish_pos).setwish("true");
                }

            }
        }
        class Removewishlist extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread Show Progress Dialog
             * */
            private final ProgressDialog pDialog1 = new ProgressDialog(StoresProduct.this);
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
                    Toast.makeText(StoresProduct.this,
                            "Check your network connection", Toast.LENGTH_LONG)
                            .show();
                }
                else{
                    pDialog1.dismiss();
                    wishlist1.setVisibility(View.VISIBLE);
                    wishlisted1.setVisibility(View.GONE);
                    productlist.get(wish_pos).setwish("null");
                }

            }
        }
    }
}


