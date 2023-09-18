package in.vbuy.client;

import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.Productpojo;
import in.vbuy.client.util.SubCategorySupportfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FilterPrice  extends Activity implements OnClickListener{
	SeekBar minprice,maxprice;
	TextView min,max;
	int minimumvalue,total;
	String productid,url,productname,search;
	String minijson,maxjson;
	Button ok;
	TextView title1,title2;
	String areaname;
	HashMap<String, ArrayList<Productpojo>> temp = SubCategorySupportfile.productdetials;
	 String hashmapkeyname;
	 private String minimum[] = null;
	 private String maximum[] = null;
	 ArrayList<Productpojo> itemlist=new ArrayList<Productpojo>();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.filter_price);
		minprice=(SeekBar)findViewById(R.id.seekBar1);
		title1=(TextView)findViewById(R.id.textView1);
		title2=(TextView)findViewById(R.id.textView2);
		 maxprice = (SeekBar) findViewById(R.id.seekBar2);
		 min = (TextView) findViewById(R.id.min);
		 max = (TextView) findViewById(R.id.max);

        ok=(Button) findViewById(R.id.ok);
        ok.setOnClickListener(this);
		 //  Get the data using intent
		 
		 Intent in = getIntent();
			productid = in.getStringExtra("productid");
			productname = in.getStringExtra("productname");
			areaname = in.getStringExtra("areaname");
			search = in.getStringExtra("search");
		 
		 hashmapkeyname=productid+"price";
		 if(productid !=null){
		url=getString(R.string.pricerate)+productid;	
		 }
		 else{
			 url=getString(R.string.search_pricerate)+search;
		 }
		
		 try{
				if(temp.size()>0)
				
				{
					
				ArrayList<Productpojo> si =temp.get(productid);
					
					
					  boolean ifavilable = temp.get(hashmapkeyname) != null;
						
					if(ifavilable==true)
					{
						dataloadedfromhashmap();
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
				
				
				}
				catch(Exception e)
				{
					Log.d("error",""+e.toString());
				}
				
}
	

private class GetDeptAyncTask extends
	AsyncTask<Hashtable<String, String>, Void, String> {

private final ProgressDialog progressDialog = new ProgressDialog(FilterPrice.this);

protected void onPreExecute() {
	progressDialog.setCancelable(true);
	
	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	progressDialog.setIndeterminate(true);
	progressDialog.setMessage("Loading...");
	progressDialog.show();
}

@Override
protected String doInBackground(Hashtable<String, String>... params) {
	Hashtable ht = params[0];

	Log.d("url address",url);
	String json = HelperHttp.getJSONResponseFromURL(url, ht);
	if (json != null)
		parseJsonString( json);
	else {
		return "Invalid Company Id";
	}
	return "SUCCESS";
}

protected void parseJsonString(
		String json) {
	try {
		JSONObject jsonobj=new JSONObject(json);
			Productpojo items =new Productpojo();
			items.setminimum(jsonobj.optString("Min", ""));
			items.setmaximum(jsonobj.optString("Max", ""));
			 minijson=jsonobj.optString("Min", "");
			maxjson=jsonobj.optString("Max", "");
			Log.d("Maximum Price", jsonobj.optString("Max", ""));
			
			itemlist.add(items);
		
		
		
		
		boolean status = SubCategorySupportfile
				.addProducts(
						hashmapkeyname, itemlist);
		
		if (status) {
			
			Log.d("Added to hash map ","added Ok");
		}
			else
			{
				Log.d("Added to hash map ","not add to hashmap ");
			}
	
		
	} catch (JSONException e) {
		
		e.printStackTrace();
	}
	
}
@Override
protected void onPostExecute(String result) {

	if (result == "SUCCESS") {
		if(itemlist.isEmpty()){
			Toast.makeText(FilterPrice.this,
					"Threre are No Datas", Toast.LENGTH_LONG)
					.show();
			
			if (this.progressDialog.isShowing()) {
				this.progressDialog.dismiss();
			}
		}
		else{
		getproductdetials();
		  minprice.setVisibility(View.VISIBLE); 
			maxprice.setVisibility(View.VISIBLE); 
		  max.setVisibility(View.VISIBLE); 
		  min.setVisibility(View.VISIBLE); 
		  ok.setVisibility(View.VISIBLE); 
		  title1.setVisibility(View.VISIBLE); 
		  title2.setVisibility(View.VISIBLE); 
		String mini=minimum[0];
		String maxi=maximum[0];
		double maxans = Double.parseDouble(maxi);
		double minians = Double.parseDouble(mini);
		final int maxiint=(int)maxans;
		Log.d("Maximum jsonPrice", maxi);
		final int miniint=(int)minians;
		int progresslength=maxiint-miniint;
		minprice.setMax(progresslength);
		min.setText(String.valueOf(miniint));
		max.setText(String.valueOf(maxiint));
		minimumvalue=miniint;
		maxprice.setMax(maxiint-(minimumvalue+1));
		maxprice.setProgress(1000000);
		minprice.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
	    {
	       public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	       {
	    	    minimumvalue=progress+miniint;
	    	   min.setText(String.valueOf(minimumvalue));
	    	 
	       }

	      public void onStartTrackingTouch(SeekBar seekBar) {
	    	  
	      }

	      public void onStopTrackingTouch(SeekBar seekBar) {
	    	 maxprice.setMax(maxiint-(minimumvalue+1));
	    	 maxprice.setProgress(1000000);
	      }
	    });
	 
		 maxprice.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
		    {
		       public void onProgressChanged(SeekBar seekBar, int progress1, boolean fromUser)
		       {
		    	   total=progress1+minimumvalue+1;
		    	   max.setText(String.valueOf(total));
		    	  
		       }

		      public void onStartTrackingTouch(SeekBar seekBar) {}

		      public void onStopTrackingTouch(SeekBar seekBar) {
		    	  
		      }
		    });
		
		
		//gridView.setOnScrollListener(new PauseOnScrollListener(true, true));
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
		}
	} else {
		Toast.makeText(FilterPrice.this,
				"Check your network connection", Toast.LENGTH_SHORT)
				.show();
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
			
		}
	}

}

}



private void executeAsyncTask() {
Hashtable<String, String> ht = new Hashtable<String, String>();
GetDeptAyncTask async = new GetDeptAyncTask();
// ht.put("company_id", "1");
Hashtable[] ht_array = { ht };
async.execute(ht_array);
}


public void getproductdetials() {
	minimum = null;
	maximum=null;	
	
	
	
	System.out.print(temp.get(hashmapkeyname));
	if (temp != null) {
	
		ArrayList<Productpojo> si =temp.get(hashmapkeyname);
		int size = si.size();
		
		Log.d("return size is ", "ArrayList size is "+size);
		minimum = new String[size];
		maximum = new String[size];
		
		
		for(int i=0 ;i< si.size();i++)
		{
			
			Productpojo spl =si.get(i);
			
			minimum[i]=spl.getminimum();
			maximum[i]=spl.getmaximum();
			
			Log.d("Maximum Price ", maximum[i]=spl.getmaximum());
			
			Log.i("Loaded From Shopping HashMap", "" + i);
		}		
		
	} else {
		Log.i("Hashmap not set", "yes");
	}	
}

public void dataloadedfromhashmap()
{
	
	getproductdetials();
	  minprice.setVisibility(View.VISIBLE); 
		maxprice.setVisibility(View.VISIBLE); 
	  max.setVisibility(View.VISIBLE); 
	  min.setVisibility(View.VISIBLE); 
	  ok.setVisibility(View.VISIBLE); 
	  title1.setVisibility(View.VISIBLE); 
	  title2.setVisibility(View.VISIBLE); 
	String mini=minimum[0];
	String maxi=maximum[0];
	double maxans = Double.parseDouble(maxi);
	double minians = Double.parseDouble(mini);
	final int maxiint=(int)maxans;
	Log.d("Maximum jsonPrice", maxi);
	final int miniint=(int)minians;
	int progresslength=maxiint-miniint;
	minprice.setMax(progresslength);
	min.setText(String.valueOf(miniint));
	max.setText(String.valueOf(maxiint));
	minimumvalue=miniint;
	maxprice.setMax(maxiint-(minimumvalue+1));
	maxprice.setProgress(1000000);
	
	minprice.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
  {
     public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
     {
  	    minimumvalue=progress+miniint;
  	   min.setText(String.valueOf(minimumvalue));
  	 
     }

    public void onStartTrackingTouch(SeekBar seekBar) {}

    public void onStopTrackingTouch(SeekBar seekBar) {
  	 maxprice.setMax(maxiint-(minimumvalue+1));
  	 maxprice.setProgress(1000000);
    }
  });

	 maxprice.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
	    {
	       public void onProgressChanged(SeekBar seekBar, int progress1, boolean fromUser)
	       {
	    	   total=progress1+minimumvalue+1;
	    	   max.setText(String.valueOf(total));
	    	  
	       }

	      public void onStartTrackingTouch(SeekBar seekBar) {}

	      public void onStopTrackingTouch(SeekBar seekBar) {
	    	  
	      }
	    });
	
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
	Toast.makeText(FilterPrice.this, msg, Toast.LENGTH_LONG).show();
}


public void onClick(View v) {
	 
	if (v.getId() == R.id.ok) {
		Intent intent = new Intent(
				FilterPrice.this.getApplicationContext(),
				ProductlistActivity.class);
		intent.putExtra("productid", productid);
		intent.putExtra("productname", productname);
		intent.putExtra("mapradius", "5");
		intent.putExtra("areaname", areaname);
		intent.putExtra("productname", search);
		intent.putExtra("from",  min.getText().toString());
		intent.putExtra("to", max.getText().toString());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
		startActivity(intent);
		 }

}
	
}