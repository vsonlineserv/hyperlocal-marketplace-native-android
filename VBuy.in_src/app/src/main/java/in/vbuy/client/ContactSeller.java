package in.vbuy.client;


import in.vbuy.client.util.Validation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ContactSeller extends BaseActivity implements OnClickListener{
	private ProgressDialog pDialog;
	Button cancel,send;
	ImageView image;
	String actiontitle;
	String storename,imageurls,product,productid,branchid;
	ProgressBar progressbar;
	DisplayImageOptions options;
	TextView productname,storenametext;
	EditText username,password,company,person,address,city,country,mobile,email,query;
	static String usernamevalue,mobilevalue,emailvalue,queryvalue;
	JSONParser jsonParser = new JSONParser();
	
	final Context context = this;
	// These matrices will be used to move and zoom image
		Matrix matrix = new Matrix();
		Matrix savedMatrix = new Matrix();

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
	private ProgressDialog progressDialog;
	static final int CUSTOM_DIALOG_ID = 0;
	GPSTracker gps;
	
	ProgressBar pb1,pb2;
	
	// JSON Node names
		private static final String TAG_SUCCESS = "success";
		
		private Toolbar toolbar;
		
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//getActionBar().setDisplayHomeAsUpEnabled(true);
			setContentView(R.layout.contact);
			
			toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
			//toolbartitle = (TextView) findViewById(R.id.titletool);

			setSupportActionBar(toolbar);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			
			toolbar.setLogo(R.drawable.vbuy);
		    toolbar.setNavigationIcon(R.drawable.arrow5);
		    toolbar.setNavigationOnClickListener(new OnClickListener() {
		    	
		    	@Override
		    	public void onClick(View v) {
		    		// TODO Auto-generated method stub
		    		onBackPressed();
		    	}
		    });
			  
			
			
			image = (ImageView) findViewById(R.id.contact_image);
			storenametext = (TextView) findViewById(R.id.branchname);
			productname = (TextView) findViewById(R.id.contact_product_name);
			 username=(EditText)findViewById(R.id.contact_name);
			 mobile=(EditText)findViewById(R.id.mobile_no);
			 email=(EditText)findViewById(R.id.email_id);
			 query=(EditText)findViewById(R.id.message);
			 cancel=(Button)findViewById(R.id.contact_cancel);
			 send=(Button)findViewById(R.id.contact_send);
			 cancel.setOnClickListener(this);
			 send.setOnClickListener(this);
			 gps = new GPSTracker(ContactSeller.this);
			 Intent in = getIntent();
			storename = in.getStringExtra("storename");
			imageurls = in.getStringExtra("imageurls");
			product = in.getStringExtra("productname");
			productid = in.getStringExtra("productid");
			branchid = in.getStringExtra("branchid");
			actiontitle = in.getStringExtra("area");
			storenametext.setText(storename);
			productname.setText(product);
			Log.d("Store Names", storename);
			 imageLoader.displayImage(this.imageurls, image, options, new SimpleImageLoadingListener());
				
				Drawable drawable = LoadImageFromWebOperations(imageurls);
				image.setImageDrawable(drawable);
				
				LoadImageFromWebOperations(imageurls);
				registerViews();
		}
		@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			this.finish();
			overridePendingTransition(0, 0);
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
											ContactSeller.this.getApplicationContext(),
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
				Toast.makeText(ContactSeller.this, msg, Toast.LENGTH_LONG).show();
				finish();
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
					@Override
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

					@Override
					public void onLoadingComplete(Bitmap loadedImage) {
						progressbar.setVisibility(View.GONE);
					}
				});
			}

	 @Override
	 public void onClick(View v) {
	 	// TODO Auto-generated method stub
	 	
	 	
	 	 if(v.getId()==R.id.contact_cancel)
	 	{
	 		finish();
	 		
	 	}
	 	

	 	
	 }


	 /**
	  * Background Async Task to Create user new product
	  * */
	 class CreateNewProduct extends AsyncTask<String, String, String> {

	 	/**
	 	 * Before starting background thread Show Progress Dialog
	 	 * */
		 private final ProgressDialog pDialog = new ProgressDialog(ContactSeller.this);
	 	@Override
	 	protected void onPreExecute() {
	 		super.onPreExecute();
	 		pDialog.setMessage("Sending Details");
	 		pDialog.setIndeterminate(false);
	 		pDialog.setCancelable(true);
	 		pDialog.show();
	 	}

	 	/**
	 	 * Creating product
	 	 * */
	 	protected String doInBackground(String... args) {
	 		String temp = null;
	 		//JSONObject ProductContactResultSet=new JSONObject();
	 		int branchidint= Integer.valueOf(branchid);
	 		int productidint= Integer.valueOf(productid);
	 		usernamevalue=username.getText().toString();
	 		mobilevalue=mobile.getText().toString();
	 		emailvalue=email.getText().toString();
	 		queryvalue=query.getText().toString();
	 		
	 		
	 		String url="http://www.vbuy.in/api/Products/ContactSeller";
	 		 HttpParams myParams = new BasicHttpParams();
	 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
	 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
	 	    HttpClient httpclient = new DefaultHttpClient(myParams );
	 	    

	 	    try {
	 	    	JSONObject ProductContactResultSet=new JSONObject();
			ProductContactResultSet.put("Name", usernamevalue);
			ProductContactResultSet.put("Email", emailvalue);
	 		ProductContactResultSet.put("Mobile", mobilevalue);
	 		ProductContactResultSet.put("Subject", queryvalue);
	 		ProductContactResultSet.put("Branchid", branchidint);
	 		ProductContactResultSet.put("ProductId", productidint);
	 	    	String json=ProductContactResultSet.toString();
	 	        HttpPost httppost = new HttpPost(url.toString());
	 	        httppost.setHeader("Content-type", "application/json");

	 	        StringEntity se = new StringEntity(ProductContactResultSet.toString()); 
	 	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	 	        httppost.setEntity(se); 

	 	        HttpResponse response = httpclient.execute(httppost);
	 	        temp = EntityUtils.toString(response.getEntity());
	 	        Log.i("tag", temp);


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
	 	    temp=temp.replace("\"", "");
	 		return temp;
		}

	 	
		/**
	 	 * After completing background task Dismiss the progress dialog
	 	 * **/
	 	protected void onPostExecute(String result) {
	 		// dismiss the dialog once done
	 		pDialog.dismiss();
	 		if(result.equalsIgnoreCase("Failed")){
	 			Toast.makeText(getApplicationContext(),
	 					"Check your network connection", Toast.LENGTH_LONG)
	 					.show();
	 		}
	 		else{
	 		if(result.equalsIgnoreCase("success")){
	 			Toast.makeText(ContactSeller.this, "Sent Successfully", Toast.LENGTH_LONG).show();
	 			finish();
	 		}
	 		else{
	 			Toast.makeText(ContactSeller.this, "You can only sent one message to the store for same product in 2 hours", Toast.LENGTH_LONG).show();
	 		}
	 	}
	 	}
	 }

	 private void registerViews() {
	        // TextWatcher would let us check validation error on the fly
		 		username.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.hasText(username);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
		 		email.addTextChangedListener(new TextWatcher() {
	            // after every change has been made to this editText, we would like to check validity
	            public void afterTextChanged(Editable s) {
	                Validation.isEmailAddress(email, true);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
		 		mobile.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.isPhoneNumber(mobile);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
		 		send.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                /*
	                Validation class will check the error and display the error on respective fields
	                but it won't resist the form submission, so we need to check again before submit
	                 */
	                if ( checkValidation () )
	                    submitForm();
	                else
	                    Toast.makeText(ContactSeller.this, "Form contains error", Toast.LENGTH_LONG).show();
	            }
	        });
	 }
	 private void submitForm() {
	       
		 usernamevalue=username.getText().toString();
	 		mobilevalue=mobile.getText().toString();
	 		emailvalue=email.getText().toString();
	 		queryvalue=query.getText().toString();
	 		
	 		
	 		
	 		if(usernamevalue.length()>0&&mobilevalue.length()>0&&emailvalue.length()>0&&queryvalue.length()>0)
	 		{
	 			
		 		new CreateNewProduct().execute();
	 			}else
	 		{
	 			Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
	 		}
	 		
		 
		 
		 // Submit your form here. your form is valid
	        //Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
	    }
	 
	    private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasText(username)) ret = false;
	        if (!Validation.isEmailAddress(email, true)) ret = false;
	        if (!Validation.isPhoneNumber(mobile)) ret = false;
	 
	        return ret;
	    }
}
