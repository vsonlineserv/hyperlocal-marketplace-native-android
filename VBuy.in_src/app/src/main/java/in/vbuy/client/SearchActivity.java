package in.vbuy.client;

import in.vbuy.client.util.SubCategorySupportfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class SearchActivity extends Activity implements OnClickListener{
	AutoCompleteTextView autocompletetext,areafiltertext;//search product and area name
	ImageView clear1,clear2;
	String autocomtextval,areanameval;
	String lat,lon;
	String pro_id,pro_img;
	GPSTracker gps;
	double latitude;
	double longitude;
	String selection,selectpid;
	 String lati,longi;
	 long id;
	 DBAdapter db = new DBAdapter(this);
	private List<String>filterproduct=new ArrayList<String>();
	List<String> latList = new ArrayList<String>();
	List<String> lonList = new ArrayList<String>();
	List<String> pid = new ArrayList<String>();
	List<String> img_url = new ArrayList<String>();
	List<String> product = new ArrayList<String>();
	List<String> arealist = new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchproductarea);
		clear1=(ImageView) findViewById(R.id.clear1);
		clear2=(ImageView) findViewById(R.id.clear2);
		autocompletetext=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		areafiltertext=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
		areafiltertext.setThreshold(1);
		autocompletetext.setThreshold(1);
		clear1.setOnClickListener(this);
		clear2.setOnClickListener(this);
		
		 try {
		      String destPath = "/data/data/" + getPackageName()
		          + "/databases/MyDB";
		      File f = new File(destPath);
		      if (!f.exists()) {
		        CopyDB(getBaseContext().getAssets().open("mydb"),
		            new FileOutputStream(destPath));
		      }
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  db = new DBAdapter(this);
		 db.open();
		 Cursor c = db.getAllContacts();
		   
		   
		   
		    if (db.getAllContactscount(1)){
		    	 c = db.getContact(1);
		    	areafiltertext.setText(c.getString(1));
		    }
		    else{
		    	 id = db.insertContact("Chennai",
		    	        "13.062657742091597","80.22018974609375");
		    	 c = db.getContact(1);
		    	 areafiltertext.setText(c.getString(1));
		    }
		    db.close();
	//product and area filter autocomplete text views
    autocompletetext.setAdapter(new FilterproductAdapter(this,autocompletetext.getText().toString()));
    areafiltertext.setAdapter(new AreaAdapter(this,areafiltertext.getText().toString()));
    
    autocompletetext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
     
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        }
    });
    
    
    autocompletetext.setOnItemClickListener(new OnItemClickListener(){

        public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
        	autocomtextval=autocompletetext.getText().toString();	
    		areanameval=areafiltertext.getText().toString();
    		selectpid = (String) parent.getItemAtPosition(position);
    		 int pos=product.indexOf(selectpid);
             pro_id=pid.get(pos);
            pro_img=img_url.get(pos);
    		if(autocomtextval.length()>0 && areanameval.length()>0 ){
    			db.open();
    			Cursor c = db.getAllContacts();
    		    c = db.getContact(1);
    		    String areadata=c.getString(1);
    			String latdata=c.getString(2);
    			String londata=c.getString(3);
    			db.close();
    			Intent int1 = new Intent(getApplicationContext(), SellerListActivity.class);

	        	int1.putExtra("productname",autocomtextval );
	        	int1.putExtra("productid", pro_id);
	        	int1.putExtra("mapvalue", "5");
				int1.putExtra("imageurl", "http://images.vbuy.in/VBuyImages/small/"+pro_img);
				int1.putExtra("image", pro_img);
				int1.putExtra("latitude", latdata);
				int1.putExtra("longitude", londata);
				int1.putExtra("area", areadata);
				int1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			int1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			SubCategorySupportfile.mylist.clear();
    			SubCategorySupportfile.mybrand.clear();
    			SubCategorySupportfile.filterprice.clear();
    	    	SubCategorySupportfile.sort.clear();
    	    	SubCategorySupportfile.filterhash.clear();
    			finish();
				startActivity(int1);
    			
    			
    			
    			}
    		else{
    		
    		Toast.makeText(SearchActivity.this, "Enter The Search Value", Toast.LENGTH_LONG).show();
    		}
        	
        }
        });
    areafiltertext.setOnItemClickListener(new OnItemClickListener(){

        public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
        	gps = new GPSTracker(SearchActivity.this);
        	
         selection = (String) parent.getItemAtPosition(position);
           if(selection.equalsIgnoreCase("Current Location")){
       	if(gps.canGetLocation()== false){
       		gps.showSettingsAlert();
       		areafiltertext.setText("");
    		}
    	else{
    		
    		 new LongOperation().execute("");
    		
    	}
       	   
       	   
          }
          else{
        	  int pos=arealist.indexOf(selection);
              lat=latList.get(pos);
             lon=lonList.get(pos);
             db.open();
 			db.updateContact(1, selection,lat,lon);
 			db.close();
          }
          
        }
    });
    autocompletetext.addTextChangedListener(new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			if(s.length()>0)
			{
				clear1.setVisibility(View.VISIBLE);
			}
			else{
				clear1.setVisibility(View.INVISIBLE);
			}
		}
    	
    });
    areafiltertext.addTextChangedListener(new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			if(areafiltertext.length()>0)
			{
				clear2.setVisibility(View.VISIBLE);
			}
			else{
				clear2.setVisibility(View.INVISIBLE);
			}
		}
    	
    });
	
    areafiltertext.setOnFocusChangeListener(new OnFocusChangeListener() {          
    	
        public void onFocusChange(View v, boolean hasFocus) {
        	
           	 db.open();
   		 Cursor c = db.getAllContacts();
   		   
   		if(!hasFocus)
   		{
   		    if (db.getAllContactscount(1)){
   		    	 c = db.getContact(1);
   		    	areafiltertext.setText(c.getString(1));
   		    }
   		    else{
   		    	 id = db.insertContact("Chennai",
   		    	        "13.062657742091597","80.22018974609375");
   		    	 c = db.getContact(1);
   		    	 areafiltertext.setText(c.getString(1));
   		    }
   		}
   		    db.close();
        }
    });
    
  }
	public void search(){
		 
        autocomtextval=autocompletetext.getText().toString();	
		areanameval=areafiltertext.getText().toString();
		if(autocomtextval.length()>0 && areanameval.length()>0 ){
			db.open();
			Cursor c = db.getAllContacts();
		    c = db.getContact(1);
		    String areadata=c.getString(1);
			String latdata=c.getString(2);
			String londata=c.getString(3);
			db.close();
			String temp=autocomtextval.replaceAll("\\s+", "%20");
			Intent intent = new Intent(
					SearchActivity.this.getApplicationContext(),
					ProductlistActivity.class);
			intent.putExtra("productname", temp);
			intent.putExtra("areaname", areadata);
			intent.putExtra("mapradius", "5");
			intent.putExtra("from",  "0");
			intent.putExtra("to", "100000");
			intent.putExtra("lati", latdata);
			intent.putExtra("longi", londata);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			System.out.println("position " + latdata+" "+londata); //check it now in Logcat
			SubCategorySupportfile.mylist.clear();
			SubCategorySupportfile.mybrand.clear();
			SubCategorySupportfile.filterprice.clear();
	    	SubCategorySupportfile.sort.clear();
	    	SubCategorySupportfile.filterhash.clear();
	        finish();
			startActivity(intent);
			 
		
			}
		else{
		
		Toast.makeText(SearchActivity.this, "Enter The Search Value", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onClick(View v) {
		 if(v.getId()==R.id.clear1){
			autocompletetext.setText("");
		}
		else if(v.getId()==R.id.clear2){
			areafiltertext.setText("");
		}
		
		
		
	}
	
	private class LongOperation extends
	AsyncTask<String, Void, String> {

private final ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

protected void onPreExecute() {
	progressDialog.setCancelable(false);
	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	progressDialog.setIndeterminate(true);
	progressDialog.setMessage("Fetching Location");
	progressDialog.show();
}
	
protected String doInBackground(String... params) {
	 try {
		 latitude = gps.getLatitude();
		longitude = gps.getLongitude();

        lati=Double.toString(latitude);
        longi=Double.toString(longitude);
        
            	  for(int i=1000; i<10000;){
            		  Thread.sleep(i);
            		  if(lati.length() < 5 && longi.length() < 5){
            			  i=i+1000;
            		  }
            		  else{
            			  i=i+5000;
            		  }
            	  }
            			
            	              
            	  
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        
   
    
    return null;
}

@Override
protected void onPostExecute(String result) {
	 if(lati.length() < 5 && longi.length() < 5){
		 if (this.progressDialog.isShowing()) {
				this.progressDialog.dismiss();
			}
		 areafiltertext.setText("");
	Toast.makeText(SearchActivity.this, "Try Again...", Toast.LENGTH_LONG).show();
	 }
	 else {
	latitude = gps.getLatitude();
	longitude = gps.getLongitude();
	lat=Double.toString(latitude);
	lon=Double.toString(longitude);
	System.out.println("position " + lat+" "+lon);
	db.open();
	db.updateContact(1, selection,lat,lon);
	db.close();
	if (this.progressDialog.isShowing()) {
		this.progressDialog.dismiss();
	}
	}
}

	}


	public class AreaAdapter extends ArrayAdapter<String> {

		protected static final String TAG="AreaAdapter";
		
		public AreaAdapter(Activity context,String Name)
		{
			super(context,android.R.layout.simple_dropdown_item_1line);
			
		}
		@Override
	public int getCount()
	{
		return filterproduct.size();
	}
		@Override
	public String getItem(int index)
	{
		return filterproduct.get(index);
	}

	@Override
	public Filter getFilter() {
	    Filter myFilter = new Filter() {
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	            FilterResults filterResults = new FilterResults();
	          AreaHttpConection jsonparseobject=new AreaHttpConection();
	            if (constraint != null) {
	                List<GetSetArea> new_suggestions =jsonparseobject.getParseJsonWCF(constraint.toString());
	                filterproduct.clear();
	                filterproduct.add("Current Location");
	                for (int i=0;i<new_suggestions.size();i++) {
	                constraint=Character.toString(constraint.charAt(0)).toUpperCase()+constraint.toString().substring(1);        	  	
	           if(new_suggestions.get(i).getAreaName().contains(constraint))
	                filterproduct.add(new_suggestions.get(i).getAreaName());
	           	arealist.add(new_suggestions.get(i).getAreaName());
	           	latList.add(new_suggestions.get(i).getLatitude());
	           	lonList.add(new_suggestions.get(i).getLongitude());
	                }
	                filterResults.values = filterproduct;
	                filterResults.count = filterproduct.size();
	            }
	            return filterResults;
	        }

	        @Override
	        protected void publishResults(CharSequence contraint,
	                FilterResults results) {
	            if (results != null && results.count > 0) {
	                notifyDataSetChanged();
	            } else {
	                notifyDataSetInvalidated();
	            }
	        }
	    };
	    return myFilter;
	}

	}

public class FilterproductAdapter extends ArrayAdapter<String> {

	protected static final String TAG="FilterproductAdapter";
	private List<String> filterproduct;
	public FilterproductAdapter(Activity context,String Name)
	{
		super(context,android.R.layout.simple_dropdown_item_1line);
		filterproduct=new ArrayList<String>();
	}
	@Override
public int getCount()
{
	return filterproduct.size();
}
	@Override
public String getItem(int index)
{
	return filterproduct.get(index);
}
@Override
public Filter getFilter() {
    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            JsonParse jsonparseobject=new JsonParse();
            if (constraint != null) {
                List<GetProduct> new_suggestions =jsonparseobject.getParseJsonWCF(constraint.toString());
                filterproduct.clear();
                for (int i=0;i<new_suggestions.size();i++) {
                filterproduct.add(new_suggestions.get(i).getName());
                product.add(new_suggestions.get(i).getName());
                pid.add(new_suggestions.get(i).getProductId());
                img_url.add(new_suggestions.get(i).getPictureName());
                }
                filterResults.values = filterproduct;
                filterResults.count = filterproduct.size();
            }
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence contraint,
                FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };
    return myFilter;
}
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
}
 