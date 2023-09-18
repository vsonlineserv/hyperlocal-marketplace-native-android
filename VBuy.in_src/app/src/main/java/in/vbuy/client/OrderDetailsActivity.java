package in.vbuy.client;

import in.vbuy.client.util.OrderTrackProduct;

import java.io.IOException;
import java.util.ArrayList;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OrderDetailsActivity extends Activity {
	ListView track; 
	final Context context = this;
	TrackOrderAdapter adapter;
	String authen;
	String orderid;
	ArrayList<OrderTrackProduct>order=new ArrayList<OrderTrackProduct>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderlist);
		track=(ListView) findViewById(R.id.order_list);
		order = new ArrayList<OrderTrackProduct>();
		adapter = new TrackOrderAdapter(getApplicationContext(), R.layout.order_row, order);
		track.setAdapter(adapter);
		Intent in=getIntent();
		orderid = in.getStringExtra("id");
		DBLogin db = new DBLogin(getApplicationContext());
		db = new DBLogin(getApplicationContext());
		 db.open();
		
			if (db.getAllContactscount(1)){
		    	 
		    	 if (cachetoken.token.size() > 0){
			 authen = cachetoken.token.get("token");
			}
		    	 else{
		    		 Intent intent = new Intent(
								OrderDetailsActivity.this.getApplicationContext(),
								RetailerMain.class);
						intent.putExtra("dir", "session");
						startActivity(intent);
		    	 }
		    	 }
			new Getorder().execute();
	}
	
	// Getting Track order items
	
			class Getorder extends AsyncTask<String, String, String> {

			 	/**
			 	 * Before starting background thread Show Progress Dialog
			 	 * */
				 
				 private final ProgressDialog pDialog1 = new ProgressDialog(OrderDetailsActivity.this);
				 	@Override
				 	protected void onPreExecute() {
				 		super.onPreExecute();
				 		pDialog1.setMessage("Order Details...");
				 		pDialog1.setIndeterminate(false);
				 		pDialog1.setCancelable(true);
				 		pDialog1.show();
				 	}

			 	
			 	protected String doInBackground(String... args) {
			 		String result = null;
			 		
			 		
			 		 HttpParams myParams = new BasicHttpParams();
				 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
				 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
				 	    //HttpClient httpclient = new DefaultHttpClient(myParams );
			 	    String url=getString(R.string.getorderdetails)+orderid;

			 	    try {
			 	    	HttpClient client = new DefaultHttpClient(new BasicHttpParams());
			 	    	HttpGet httpGet = new HttpGet(url);
			 	    	httpGet.setHeader("Content-type", "application/json");
			 	    	httpGet.addHeader("Authorization",  "Bearer"+" "+authen );

			 	    	    HttpResponse response = client.execute(httpGet);
			 	    	  result = EntityUtils.toString(response.getEntity());
			 	    	 JSONArray array = new JSONArray(result);
			 			for (int i = 0; i <= array.length()-1; i++)
			 			{
			 				JSONObject j = array.getJSONObject(i);
							String ordernum=(j.optString("Id", ""));
							String ordate=(j.optString("Name", ""));
							String totalorder=(j.optString("PriceInclTax", ""));
							String quantitypro=(j.optString("Quantity", ""));
							String orderstatus=(j.optString("OrderItemStatus", ""));
							String payment=j.optString("BranchName", "");
							
							order.add(new OrderTrackProduct(ordernum,ordate,totalorder,quantitypro,orderstatus,payment));
			 			}
			 			
			 			
			 	    } catch (ClientProtocolException e) {

			 	    } catch (IOException e) {
			 	    } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	   
			 		return "SUCCESS";
				}

			 	
				/**
			 	 * After completing background task Dismiss the progress dialog
			 	 * **/
			 	protected void onPostExecute(String result) {
			 		pDialog1.dismiss();
			 		adapter.notifyDataSetChanged();
			 	}
			 		}
			
	
	public class TrackOrderAdapter extends ArrayAdapter<OrderTrackProduct> {
		ArrayList<OrderTrackProduct> productlist;
		LayoutInflater vi;
		int Resource;
		ViewHolder holder;

		public TrackOrderAdapter(Context context, int resource, ArrayList<OrderTrackProduct> objects) {
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
				v.setOnClickListener(null);
				 v.setOnLongClickListener(null);
				 v.setLongClickable(false);
				holder. orderno=(TextView)v.findViewById(R.id.textView7);
				holder. proname=(TextView)v.findViewById(R.id.textView8);
				holder. status=(TextView)v.findViewById(R.id.textView12);
				holder. seller=(TextView)v.findViewById(R.id.textView9);
				holder. quantity=(TextView)v.findViewById(R.id.textView10);
				holder. order_total=(TextView)v.findViewById(R.id.textView11);
				
				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}
			
			if((productlist.get(position).getorderstatus()).equalsIgnoreCase("null")){
				holder.status.setText("");
			}
			else{
				holder.status.setText(productlist.get(position).getorderstatus());
			}
			holder.orderno.setText(productlist.get(position).getordernumber());
			holder.proname.setText(productlist.get(position).getorderdate());
			
			holder.seller.setText(productlist.get(position).getpayment());
			holder.quantity.setText(productlist.get(position).getquantity());
			holder.order_total.setText(productlist.get(position).gettotalorder());
			
			return v;

		}

	}
	static class ViewHolder {
		public TextView orderno;
		public TextView proname;
		public TextView status;
		public TextView seller;
		public TextView quantity;
		public TextView order_total;
		}

}
