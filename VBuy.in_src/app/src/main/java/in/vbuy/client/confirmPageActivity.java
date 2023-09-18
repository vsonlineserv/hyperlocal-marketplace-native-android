package in.vbuy.client;



import in.vbuy.client.util.ListHeight;
import in.vbuy.client.util.Productpojo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.http.HttpResponse;
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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class confirmPageActivity extends Activity {
	public static ConfirmAdapter adapter;
	public static ListView menulist = null;
	HashMap<String, String> customer = new HashMap<String, String>();
	HashMap<String, String> order = new HashMap<String, String>();
	HashMap<String, String> shipping = new HashMap<String, String>();
	ArrayList<Productpojo>list=new ArrayList<Productpojo>();
	RelativeLayout rl;
	ProgressBar pb;
	String id,username;
	DBLogin db;
	String token;
	TextView orderid,date,ordertot,shippingamount,coupondis,netpay,cus_name,cus_email,address1,address2,city,state,pin,phno;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirmation);
        menulist = (ListView) findViewById(R.id.listView1);
        orderid=(TextView) findViewById(R.id.oredrno);
        date=(TextView) findViewById(R.id.date);
        ordertot=(TextView) findViewById(R.id.total);
        shippingamount=(TextView) findViewById(R.id.shipping);
        coupondis=(TextView) findViewById(R.id.coupen);
        netpay=(TextView) findViewById(R.id.netpay);
        cus_name=(TextView) findViewById(R.id.name);
        cus_email=(TextView) findViewById(R.id.email);
        address1=(TextView) findViewById(R.id.addressname);
        address2=(TextView) findViewById(R.id.nearby);
        city=(TextView) findViewById(R.id.city);
        state=(TextView) findViewById(R.id.state);
        pin=(TextView) findViewById(R.id.pincode);
        phno=(TextView) findViewById(R.id.phoneno);
        rl=(RelativeLayout) findViewById(R.id.rl);
        pb=(ProgressBar) findViewById(R.id.confirm_pb);
        Intent getin = getIntent();
		id = getin.getStringExtra("id");
        adapter=(new ConfirmAdapter(getApplicationContext(), R.layout.order_list_row, list));
		menulist.setAdapter(adapter);
		getListViewSize(menulist);
		token = cachetoken.token.get("token");
		db=new DBLogin(getApplicationContext());
		 db.open();
		 Cursor c = db.getAllContacts();
		if (db.getAllContactscount(1)){
	    	 c = db.getContact(1);
	    	
	    	username=(c.getString(2));
		}
		db.close();
		try{
			
			
			if (isNetworkAvailable())
			{
				new GetCart().execute();
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
    public static void getListViewSize(ListView myListView) {
		 ListAdapter myListAdapter = myListView.getAdapter();  
		 if (myListAdapter == null) {
	            //do nothing return null
	            return;
	        }
	        //set listAdapter in loop for getting final size
	        int totalHeight = 0;
	        for (int size = 0; size < myListAdapter.getCount(); size++) {
	            View listItem = myListAdapter.getView(size, null, myListView);
	            if (listItem instanceof ViewGroup) {
	                listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.FILL_PARENT));
	             }
	            listItem.measure(0, 0);
	            Log.i("height of single:", String.valueOf(listItem.getMeasuredHeight()));
	            totalHeight +=( listItem.getMeasuredHeight());
	        }
	      //setting listview item in adapter
	        ViewGroup.LayoutParams params = myListView.getLayoutParams();
	        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
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
		Toast.makeText(confirmPageActivity.this, msg, Toast.LENGTH_LONG).show();
	}
	class GetCart extends AsyncTask<String, String, String> {

	 	/**
	 	 * Before starting background thread Show Progress Dialog
	 	 * */
		 
	 	protected void onPreExecute() {
	 		super.onPreExecute();
	 		
	 	}

	 	
	 	protected String doInBackground(String... args) {
	 		String result = null;
	 		
	 		
	 		 HttpParams myParams = new BasicHttpParams();
		 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
		 	    //HttpClient httpclient = new DefaultHttpClient(myParams );
	 	    String url=getString(R.string.confirmorderlist)+id+"&userName="+username;
	 	    try {
	 	    	HttpClient client = new DefaultHttpClient(new BasicHttpParams());
	 	    	HttpGet httpGet = new HttpGet(url);
	 	    	httpGet.setHeader("Content-type", "application/json");
	 	    	httpGet.addHeader("Authorization",  "Bearer"+" "+token );

	 	    	    HttpResponse response = client.execute(httpGet);
	 	    	  result = EntityUtils.toString(response.getEntity());
   
		
		JSONObject jsonobj=new JSONObject(result);

		customer.put("Name",jsonobj.optString("CustomerName",""));
		customer.put("email",jsonobj.optString("CustomerEmail",""));
		
	      System.out.println(customer);
	      JSONObject ord=jsonobj.getJSONObject("OrderDetails");
	      order.put("order_id",ord.optString("Id",""));
	      order.put("ordersubtotal",ord.optString("OrderSubtotalInclTax",""));
	      order.put("OrderShippingCharges",ord.optString("OrderShippingCharges",""));
	      order.put("cupon",ord.optString("OrderDiscount",""));
	      order.put("OrderTotal",ord.optString("OrderTotal",""));
	      order.put("date",ord.optString("UpdatedOnUtc",""));
			
			
			 JSONObject address=jsonobj.getJSONObject("ShippingAddress");
			 shipping.put("State",address.optString("State",""));
			 shipping.put("City",address.optString("City",""));
			 shipping.put("Address1",address.optString("Address1",""));
			 shipping.put("Address2",address.optString("Address2",""));
			 shipping.put("PostalCode",address.optString("PostalCode",""));
			 shipping.put("PhoneNumber",address.optString("PhoneNumber",""));
	      
		JSONArray array=jsonobj.getJSONArray("OrderItemDetails");
		
		for (int i = 0; i <= array.length()-1; i++) {
			JSONObject j = array.getJSONObject(i);
			String a=(j.optString("ProductId", ""));
			String b=(j.optString("Quantity", ""));
			String c=(j.optString("UnitPrice", ""));
			String d=(j.optString("PictureName", ""));
			String e=(j.optString("Name", ""));
			String f=(j.optString("Price", ""));
			String g=(j.optString("Branch", ""));
			
			
			list.add(new Productpojo(a,b,c,d,e,f,g,g));
		}
		
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
	 		return "SUCCESS";
		}

protected void onPostExecute(String result) {
	if(result.equalsIgnoreCase("Failed")){
		pb.setVisibility(View.INVISIBLE);
			Toast.makeText(getApplicationContext(),
					"Check your network connection", Toast.LENGTH_LONG)
					.show();
		}
	else{
	 try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		 SimpleDateFormat FORMATTER = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		 Date date1 = sdf.parse(order.get("date")+"+00:00");
		 date.setText(FORMATTER.format(date1));
     } catch (ParseException e) {
         e.printStackTrace();
     } catch (java.text.ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	pb.setVisibility(View.INVISIBLE);
	rl.setVisibility(View.VISIBLE);
	orderid.setText(""+(order.get("order_id")));
	ordertot.setText(order.get("ordersubtotal"));
	shippingamount.setText(order.get("OrderShippingCharges"));
	coupondis.setText(order.get("cupon"));
	netpay.setText(order.get("OrderTotal"));
	cus_name.setText(customer.get("Name"));
	cus_email.setText(customer.get("email"));
	address1.setText(shipping.get("Address1"));
	phno.setText(shipping.get("PhoneNumber"));
	address2.setText(shipping.get("Address2"));
	city.setText(shipping.get("City"));
	state.setText(shipping.get("State"));
	pin.setText(shipping.get("PostalCode"));
	getListViewSize(menulist);
	adapter.notifyDataSetChanged();
}
    
}
	}
	
    public class ConfirmAdapter extends ArrayAdapter<Productpojo> {
    	ArrayList<Productpojo> productlist;
    	LayoutInflater vi;
    	int Resource;
    	ViewHolder holder;
    	protected ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options;
		
    	public ConfirmAdapter(Context context, int resource, ArrayList<Productpojo> objects) {
    		super(context, resource, objects);
    		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		Resource = resource;
    		productlist = objects;
    		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.hidescreen)
			.cacheInMemory()
			.cacheOnDisc()
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
    	}
    	@Override
    	public View getView(final int position, View convertView, ViewGroup parent) {
    		//convert view = design
    		View v = convertView;
    		if (v == null) {
    			holder = new ViewHolder();
    			v = vi.inflate(Resource, null);
    			holder. name=(TextView)v.findViewById(R.id.productname);
    			holder.branch=(TextView)v.findViewById(R.id.store);
    			holder. qty=(TextView)v.findViewById(R.id.qty);
    			holder. unitprice=(TextView)v.findViewById(R.id.unitprice);
    			holder. subtotal=(TextView)v.findViewById(R.id.subtotal);
    			holder. img = (ImageView) v.findViewById(R.id.imageView1);
    			holder. hide = (ImageView) v.findViewById(R.id.hide_img);
    			v.setTag(holder);
    		} else {
    			holder = (ViewHolder) v.getTag();
    		}
    		holder.name.setText(productlist.get(position).getDescription());
    		holder.branch.setText(productlist.get(position).getimage());
    		holder.qty.setText(productlist.get(position).getPid());
    		holder.unitprice.setText(productlist.get(position).getProname());
    		holder.subtotal.setText(productlist.get(position).getStoreslist());
    		String  temp="http://images.vbuy.in/VBuyImages/large/";
			String image = productlist.get(position).getImgurl();
			String pic=temp+image;
			String img=pic.replaceAll("\\s", "%20");
    		imageLoader.displayImage(img,holder. img, options, new SimpleImageLoadingListener() {
				public void onLoadingStarted() {
					//spinner.setVisibility(View.VISIBLE);
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
					holder. img.setImageResource(R.drawable.hidescreen);
					
				}
				public void onLoadingComplete(Bitmap loadedImage) {
					holder.hide.setVisibility(View.GONE);
					
				}
				
			});
    		return v;

    	}

    }
    static class ViewHolder {
    	public TextView name;
    	public TextView branch;
    	public TextView qty;
    	public TextView unitprice;
    	public TextView subtotal;
    	public ImageView img;
    	public ImageView hide;
    	}

    public void onBackPressed(){
    	
    	Intent intent = new Intent(getApplicationContext(), CategoryProductActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
    	startActivity(intent);
        }
}
