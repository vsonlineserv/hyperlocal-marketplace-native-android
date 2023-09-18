package in.vbuy.client;

import in.vbuy.client.util.OrderTrackProduct;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrackOrderActivity extends Activity {
	ListView track; 
	final Context context = this;
	TrackOrderAdapter adapter;
	String authen;
	TextView count;
	String orderid;
	static Button cancel_btn;
	int pos;
	String count_name;
	ArrayList<OrderTrackProduct>order=new ArrayList<OrderTrackProduct>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackorder);
		track=(ListView) findViewById(R.id.orderlist);
		count=(TextView) findViewById(R.id.counts);
		order = new ArrayList<OrderTrackProduct>();
		count_name=count.getText().toString();
		adapter = new TrackOrderAdapter(getApplicationContext(), R.layout.trackorder_row, order);
		track.setAdapter(adapter);
		DBLogin db = new DBLogin(getApplicationContext());
		db = new DBLogin(getApplicationContext());
		 db.open();
		
			if (db.getAllContactscount(1)){
		    	 
		    	 if (cachetoken.token.size() > 0){
			 authen = cachetoken.token.get("token");
			 new Getorder().execute();
			}
		    	 else{
		    		 Intent intent = new Intent(
								TrackOrderActivity.this.getApplicationContext(),
								RetailerMain.class);
						intent.putExtra("dir", "session");
						startActivity(intent);
		    	 }
		    	 }
			db.close();
			
	}
	
	// Getting Track order items
	
			class Getorder extends AsyncTask<String, String, String> {

			 	/**
			 	 * Before starting background thread Show Progress Dialog
			 	 * */
				 
				 private final ProgressDialog pDialog1 = new ProgressDialog(TrackOrderActivity.this);
				 	@Override
				 	protected void onPreExecute() {
				 		super.onPreExecute();
				 		pDialog1.setMessage("Track Order...");
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
			 	    String url=getString(R.string.getorder);

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
							String ordate=(j.optString("OrderDateUtc", ""));
							String totalorder=(j.optString("OrderTotal", ""));
							String quantitypro=(j.optString("NumberOfProducts", ""));
							String orderstatus=(j.optString("OrderStatus", ""));
							String payment=j.optString("PaymentStatus", "");
							
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
			 		count.setText(count.getText().toString()+order.size());
			 	}
			 		}
			// Cancel Track order items
			
					class Cancelorder extends AsyncTask<String, String, String> {

					 	/**
					 	 * Before starting background thread Show Progress Dialog
					 	 * */
						 
						 private final ProgressDialog pDialog1 = new ProgressDialog(TrackOrderActivity.this);
						 	@Override
						 	protected void onPreExecute() {
						 		super.onPreExecute();
						 		pDialog1.setMessage("Cancel Order...");
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
					 	    String url=(getString(R.string.cancelorder))+orderid;

					 	    try {
					 	    	HttpClient client = new DefaultHttpClient(new BasicHttpParams());
					 	    	HttpGet httpGet = new HttpGet(url);
					 	    	httpGet.setHeader("Content-type", "application/json");
					 	    	httpGet.addHeader("Authorization",  "Bearer"+" "+authen );

					 	    	    HttpResponse response = client.execute(httpGet);
					 	    	  result = EntityUtils.toString(response.getEntity());
					 	    	 
					 			
					 	    } catch (ClientProtocolException e) {

					 	    } catch (IOException e) {
					 	    }
					 		return result;
						}

					 	
						/**
					 	 * After completing background task Dismiss the progress dialog
					 	 * **/
					 	protected void onPostExecute(String result) {
					 		pDialog1.dismiss();
					 		if(result.equalsIgnoreCase("true")){
					 			order.get(pos).setorderstatus("Cancelled");
					 			adapter.notifyDataSetChanged();
					 			cancel_btn.setVisibility(View.INVISIBLE);
						 		count.setText(count_name+order.size());
					 		}
					 		else{
					 			Toast.makeText(getApplicationContext(),"Error Cancelled "+orderid, 
						                Toast.LENGTH_SHORT).show();
					 		}
					 		
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
				 holder.arg=v;
				holder.date=(TextView)v.findViewById(R.id.date);
				holder. order_number=(TextView)v.findViewById(R.id.order_number);
				holder. productnumber=(TextView)v.findViewById(R.id.productnumber);
				holder. status=(TextView)v.findViewById(R.id.status);
				holder. payment_method=(TextView)v.findViewById(R.id.payment_method);
				holder. paid_amount=(TextView)v.findViewById(R.id.paid_amount);
				holder. order_total=(TextView)v.findViewById(R.id.order_total);
				holder.cancel = (Button) v.findViewById(R.id.cancel);
				holder.details = (Button) v.findViewById(R.id.details);
				
				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				 SimpleDateFormat FORMATTER = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				 Date date1 = sdf.parse(productlist.get(position).getorderdate()+"+00:00");
				 holder.date.setText(FORMATTER.format(date1));
		     } catch (ParseException e) {
		         e.printStackTrace();
		     } catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			holder.order_number.setText(productlist.get(position).getordernumber());
			holder.productnumber.setText(productlist.get(position).getquantity());
			holder.status.setText(productlist.get(position).getorderstatus());
			holder.payment_method.setText(productlist.get(position).getpayment());
			holder.paid_amount.setText(productlist.get(position).gettotalorder());
			holder.order_total.setText(productlist.get(position).gettotalorder());
			if(productlist.get(position).getorderstatus().equalsIgnoreCase("Cancelled")){
				holder.cancel.setVisibility(View.INVISIBLE);
			}
			else{
				holder.cancel.setVisibility(View.VISIBLE);
			}
			
			holder.details.setOnClickListener(new OnClickListener() {
		         
		         public void onClick(View v) {
		        	 Intent intent = new Intent(context,OrderDetailsActivity.class);
		        	 intent.putExtra("id", productlist.get(position).getordernumber());
		             context.startActivity(intent);  
		        		
		         }
		     });
			holder.cancel.setOnClickListener(new OnClickListener() {
		         
		         public void onClick(View v) {
		        	 cancel_btn = (Button) holder.arg.findViewById(R.id.cancel);
		        	pos=position;
		        	orderid=productlist.get(position).getordernumber(); 
		        	 new Cancelorder().execute();
		         }
		     });
			
			return v;

		}

	}
	static class ViewHolder {
		public TextView date;
		public TextView order_number;
		public TextView productnumber;
		public TextView status;
		public TextView payment_method;
		public TextView paid_amount;
		public TextView order_total;
		public Button cancel;
		public Button details;
		public View arg;
		}

}
