package in.vbuy.client;

import in.vbuy.client.PaymentOptionActivity.MyOrderAdapter1;
import in.vbuy.client.util.ListHeight;
import in.vbuy.client.util.Product;
import in.vbuy.client.util.ShopCart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RetailerMain extends ActionBarActivity implements OnClickListener  {
EditText username,password,email;
Button log,send,cancel;
String user,pass,emailid;
RelativeLayout rl;
ShopCart sharedPreference;
List<Product> favorites;
ImageView net;
String authen;
String login_name="";
TextView message,forgot,signup;
static String userName,passWord; 
DBLogin db = new DBLogin(this);

private Toolbar toolbar;

 ProgressDialog pDialog; 
 String direction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.retailer_main);
		username=(EditText) findViewById(R.id.username);
		password=(EditText) findViewById(R.id.password);
		forgot=(TextView) findViewById(R.id.forgot);
		signup=(TextView)findViewById(R.id.signup);
		rl=(RelativeLayout) findViewById(R.id.login_form);
		net=(ImageView) findViewById(R.id.netsignal);
		log=(Button) findViewById(R.id.login);
		log.setOnClickListener(this);
		Intent getin = getIntent();
		direction = getin.getStringExtra("dir");
		
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
	       //toolbar.setTitle("  "+area);
		
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),
						Signup_Login.class);
				in.putExtra("dir", direction);
				in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(in);
			}
		});
		forgot.setOnClickListener(this);
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
		 db = new DBLogin(this);
		 db.open();
		 Cursor c = db.getAllContacts();
		 try {
			 
			 if (isNetworkAvailable()){
				if (cachetoken.token.size() > 0)

				{
					
					Intent in = new Intent(getApplicationContext(),
							CategoryProductActivity.class);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 			in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		 			
		 			startActivity(in);
					
				} else {
					 
					if (isNetworkAvailable())
					{
						if (db.getAllContactscount(1)){
					    	 c = db.getContact(1);
					    	userName=(c.getString(2));
					    	passWord=(c.getString(3));
					    	rl.setVisibility(View.INVISIBLE);
					    	new Userlogin().execute();
					    }
						else{
							
						}
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
		 
		 
		   
		    db.close();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		overridePendingTransition(0, 0);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		//** Create an option menu from res/menu/items.xml *//*
		SubMenu search = menu.addSubMenu(0, 11, 0, "Search");
		 search.getItem().setIcon(R.drawable.searchwhite);
		    search.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		 SubMenu subMenu = menu.addSubMenu(0, 22, 1, "all");
		    subMenu.getItem().setIcon(R.drawable.options);
		    subMenu.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		   
			subMenu.add(0, 5, 2, "Cart").setIcon(R.drawable.cart).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		/*    subMenu.add(0, 3, 3, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
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
		  
		     
		  return true;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.login)
		{
			String uname,pword;
			uname=username.getText(). toString();
			pword=password.getText(). toString();
			
			
			if(uname.length()>0&&pword.length()>0)
				
			{
				
				userName=uname;
				passWord=pword;
				
				new Userlogin().execute();
			}
			else 
			{
				Toast.makeText(getApplicationContext(), "Enter the Username/Password",
						   Toast.LENGTH_LONG).show();
			}
		}
		
		else if(v.getId()==R.id.forgot)
		{
			forgotmail();
			
			
		}
	}

	 class Userlogin extends AsyncTask<String, String, String> {

		 	/**
		 	 * Before starting background thread Show Progress Dialog
		 	 * */
		
		 	@Override
		 	protected void onPreExecute() {
		 		super.onPreExecute();
		 		 pDialog = new ProgressDialog(RetailerMain.this);
		 		if(direction.equalsIgnoreCase("session")){
		 			pDialog.setMessage("Session");
		    	}
	 			else{
	 				pDialog.setMessage("Loging");
	 			}
		 		
		 		pDialog.setIndeterminate(false);
		 		pDialog.setCancelable(false);
		 		pDialog.show();
		 	}

		 	/**
		 	 * Checking Username and Password
		 	 * */
		 	protected String doInBackground(String... args) {
		 		String temp = null;
		 		
		 		
		 		 HttpParams myParams = new BasicHttpParams();
			 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
			 	    //HttpClient httpclient = new DefaultHttpClient(myParams );
		 	    

		 	    try {
		 	    	HttpPost httppost = new HttpPost(getString(R.string.login));
		 	    	httppost.setHeader("Content-type", "application/json");
		 	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		 	    	nameValuePairs.add(new BasicNameValuePair("grant_type",    "password"));
		 	    	nameValuePairs.add(new BasicNameValuePair("username",      userName));
		 	    	nameValuePairs.add(new BasicNameValuePair("password",  passWord));
		 	    	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		 	    	HttpClient httpClient = new DefaultHttpClient(myParams);
		 	    	HttpResponse response = httpClient.execute(httppost);
		 	    	 temp = EntityUtils.toString(response.getEntity());
		 	    	
		 	    	Log.i("tag", temp);

		 	    } catch (ClientProtocolException e) {
		 	    	return "Failed";
		 	    } 
		 	   catch (HttpHostConnectException e){
		 		  return "Failed";
		 	    }
		 	  
		 	   catch (IOException e) {
		 		  return "Failed";
		 	    } 
		 	   catch (Exception e) {
					return "Failed";
				}
		 		return temp;
			}

		 	
			/**
		 	 * After completing background task Dismiss the progress dialog
		 	 * **/
		 	protected void onPostExecute(String result) {
		 		
		 		if(result.equalsIgnoreCase("Failed")){
		 			pDialog.dismiss();
		 			Toast.makeText(RetailerMain.this,
		 					"Check your network connection", Toast.LENGTH_LONG)
		 					.show();
		 		}
		 		else{
		 		
		 		try{
		 			JSONObject jsonObject = new JSONObject(result);
		 			if(jsonObject.isNull("access_token")){
		 				String error = jsonObject.getString("error_description");
		 				pDialog.dismiss();
		 				Toast.makeText(RetailerMain.this, error, Toast.LENGTH_LONG).show();
		 			}
		 			else{
		 				String response = jsonObject.getString("access_token");
			 	    	Log.i("JSON", response);
			 	    	boolean status = cachetoken.addcategorydata(
			 					"token", response);

			 			if (status) {

			 				Log.d("Added to hash map ", "added Ok");
			 			} else {
			 				Log.d("Added to hash map ", "not add to hashmap ");
			 			}
			 			if(direction.equalsIgnoreCase("session")){
				    		finish();
				    	}
			 			else{
			 			new Useraddr().execute();
			 			}
		 			}
		 	    	
		 		}
		 		catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 	   
		 		
		 	}
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
	 
	 public void forgotmail() {
			
		 final	Dialog dialog = new Dialog(RetailerMain.this,R.style.mydialogstyle);
		 	
		     dialog.setContentView(R.layout.dialog_forgot);
		     message=(TextView)dialog.getWindow(). findViewById(R.id.msg);
		     email=(EditText)dialog.getWindow(). findViewById(R.id.forgot_email);
		     send=(Button)dialog.getWindow(). findViewById(R.id.mail_send);
		     cancel=(Button)dialog.getWindow(). findViewById(R.id.close);
		     dialog.show();
		     Window window = dialog.getWindow();
		     window.setGravity(Gravity.TOP);
		     window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		     dialog.setTitle(null);
		     
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.y = 0; params.x = 0;
		params.gravity = Gravity.CENTER | Gravity.LEFT;       
		dialog.getWindow().setAttributes(params); 
		
		send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	emailid=email.getText().toString();
            	if(emailid.length()>0){
            	new forgotmail().execute();
            	}
            	else{
            		Toast.makeText(RetailerMain.this, "Please Fill The Mail_Id", Toast.LENGTH_LONG).show();
            	}
            }
        });
		cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	dialog.dismiss();

            }
        });
		
	
	}
	 
	 class forgotmail extends AsyncTask<String, String, String> {

		 	/**
		 	 * Before starting background thread Show Progress Dialog
		 	 * */
			 private final ProgressDialog pDialog1 = new ProgressDialog(RetailerMain.this);
		 	@Override
		 	protected void onPreExecute() {
		 		super.onPreExecute();
		 		pDialog1.setMessage("Request Sending...");
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
		 	    String url=getString(R.string.forgotpassword)+emailid;

		 	    HttpClient client = new DefaultHttpClient(new BasicHttpParams());
				HttpGet httpGet = new HttpGet(url);
				httpGet.setHeader("Content-type", "application/json");
				
				    
				 
					  HttpResponse response = client.execute(httpGet);
					result = EntityUtils.toString(response.getEntity());
					Log.d("Reply Responsr", result);
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
		 			Toast.makeText(RetailerMain.this,
		 					"Check your network connection", Toast.LENGTH_LONG)
		 					.show();
		 		}
		 		else{
		 		pDialog1.dismiss();
		 		if(result.equalsIgnoreCase("true")){
		 			message.setText("A link has been sent to your mail to reset the password");
		 			message.setTextColor(getResources()
		 					.getColorStateList(R.color.green));
		 		}
		 		else{
		 			message.setText("Sorry. Unable to identify user. Please verify provided email id");
		 			message.setTextColor(getResources()
		 					.getColorStateList(R.color.red));
		 		}
		 	}
		 
		 	}
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

			rl.setVisibility(View.INVISIBLE);
			net.setBackgroundResource(R.drawable.nosignal);
			
			Toast.makeText(RetailerMain.this, msg, Toast.LENGTH_LONG).show();
		}
		// Fetching User Details
		
		class Useraddr extends AsyncTask<String, String, String> {

		 	/**
		 	 * Before starting background thread Show Progress Dialog
		 	 * */
			
		 	protected void onPreExecute() {
		 		super.onPreExecute();
		 		
		 	}

		 	/**
		 	 * Checking Username and Password
		 	 * */
		 	protected String doInBackground(String... args) {
		 		String temp = null;
		 		
		 		 authen = cachetoken.token.get("token");
		 		 HttpParams myParams = new BasicHttpParams();
			 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
			 	    //HttpClient httpclient = new DefaultHttpClient(myParams );
			 	  //  String url=getString(R.string.getbranchid);
			 	    String url="http://staging.vbuy.in/api/Login/GetMyDetails?username="+userName;
		 	    try {
		 	    	HttpClient client = new DefaultHttpClient(new BasicHttpParams());
		 	    	HttpGet httpGet = new HttpGet(url);
		 	    	httpGet.setHeader("Content-type", "application/json");
		 	    	httpGet.addHeader("Authorization",  "Bearer" +" " + authen );

		 	    	  HttpResponse response = client.execute(httpGet);
		 	    	  temp = EntityUtils.toString(response.getEntity());
			 	    	
			 	    	Log.i("tag1", temp);
			 	    	try {
		 	    	  		JSONObject jsonObject = new JSONObject(temp);
							login_name = jsonObject.getString("FirstName");
							
							
							
						} catch (JSONException e) {
							return "Failed";
						}
		 	    } catch (ClientProtocolException e) {
		 	    	return "Failed";
		 	    } catch (IOException e) {
		 	    	return "Failed";
		 	    }
		 	   catch(Exception e){
		 		   return "Failed";
		 	   }
		 		return "SUCCESS";
			}

		 	
			/**
		 	 * After completing background task Dismiss the progress dialog
		 	 * **/
		 	protected void onPostExecute(String result) {
		 		if(result.equalsIgnoreCase("Failed")){
		 			pDialog.dismiss();
		 			Toast.makeText(RetailerMain.this,
		 					"Check your network connection", Toast.LENGTH_LONG)
		 					.show();
		 		}
		 		
		 		else if (result == "SUCCESS") {
		 			
		 			if(login_name.equalsIgnoreCase("")){
		 			
		 			}
		 			else{
		 			db.open();
			 		Long id = db.insertContact(login_name,
			    	        userName,passWord);
			 		db.close();
		 		}
		 			sharedPreference = new ShopCart();
					favorites = sharedPreference.getFavorites(getApplicationContext());
					if(sharedPreference.checkproduct(getApplicationContext())){
		 			new AddProduct().execute();
					}
					else{
						new GetCart().execute();
					}
		 			
		 	}
		 		}
		
		
}
		
		// Cart add to Server
		
		
		class AddProduct extends AsyncTask<String, String, String> {

			
			
		 	/**
		 	 * Before starting background thread Show Progress Dialog
		 	 * */
			
		 	protected void onPreExecute() {
		 		super.onPreExecute();
		 	
		 	}

		 	/**
		 	 * add product
		 	 * */
		 	protected String doInBackground(String... args) {
		 		String temp = null;
		 		sharedPreference = new ShopCart();
				favorites = sharedPreference.getFavorites(getApplicationContext());
				JSONArray jsonfilter=new JSONArray();
				 try {
				if(favorites.size()>0){
					
					 
					 for(int i=0;i<favorites.size(); i++)
					 {
						 JSONObject jsonob=new JSONObject();
					jsonob.put("Id", favorites.get(i).getId()) ;
					jsonob.put("StoreId", favorites.get(i).getstoreId()) ;
					jsonob.put("CustomerId", favorites.get(i).getcustomerId()) ;
					jsonob.put("ProductId", favorites.get(i).getproductId()) ;
					jsonob.put("Quantity", favorites.get(i).getQty()) ;
					jsonob.put("UnitPrice", favorites.get(i).getunitprice()) ;
					jsonob.put("SpecialPrice", favorites.get(i).getPrice()) ;
					jsonob.put("Name", favorites.get(i).getItemName()) ;
					jsonob.put("PictureName", favorites.get(i).getImageId()) ;
					jsonob.put("BranchId", favorites.get(i).getbranchid()) ;
					jsonob.put("Branch", favorites.get(i).getbranch()) ;
					jsonob.put("AdditionalShippingCharge", favorites.get(i).getItemDesc()) ;
					jsonfilter.put(jsonob);
					 }
					System.out.println("Array"+jsonfilter);
				}
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
		 	    	cart.put("userName", userName);
		 	    	String json=cart.toString();
		 	    	
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
		 	    temp=temp.replace("\"", "");
		 		return temp;
			}

		 	
			/**
		 	 * After completing background task Dismiss the progress dialog
		 	 * **/
		 	protected void onPostExecute(String result) {
		 		if(result.equalsIgnoreCase("Failed")){
		 			pDialog.dismiss();
		 			Toast.makeText(RetailerMain.this,
		 					"Check your network connection", Toast.LENGTH_LONG)
		 					.show();
		 		}
		 		else{
		 		ShopCart.clearall(getApplicationContext());
		 		new GetCart().execute();
		 	}
		 	}
		}
		
		// Getting Shop Cart items
		
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
		 	    String url=getString(R.string.getcart)+userName;

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
						boolean status = ShopCart.addProduct(getApplicationContext(),p.getbranchid(),p.getproductId(), p);
		 				
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

		 	
			/**
		 	 * After completing background task Dismiss the progress dialog
		 	 * **/
		 	protected void onPostExecute(String result) {
		 		if(result.equalsIgnoreCase("Failed")){
		 			pDialog.dismiss();
		 			Toast.makeText(RetailerMain.this,
		 					"Check your network connection", Toast.LENGTH_LONG)
		 					.show();
		 		}
		 		else{
		 		pDialog.dismiss();
		 		if(direction.equalsIgnoreCase("home"))
		 		{
		 			finish();
		 		}
		 		else if(direction.equalsIgnoreCase("cart"))
		 		{
		 			Intent intent = new Intent(getApplicationContext(), ShopCartActivity.class);
		 	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 	    	
		 	    	startActivity(intent);
		 		}
		 	}
		 		}
		
}
}