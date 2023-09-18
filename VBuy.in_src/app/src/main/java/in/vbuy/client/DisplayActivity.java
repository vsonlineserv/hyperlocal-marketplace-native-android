package in.vbuy.client;

import java.io.IOException;
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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends ActionBarActivity {

	private DbHelpers mHelper;
	private SQLiteDatabase dataBase;

	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> user_fName = new ArrayList<String>();
	private ArrayList<String> user_lName = new ArrayList<String>();
	private ArrayList<String> user_con = new ArrayList<String>();
	private ArrayList<String> user_addr = new ArrayList<String>();
	private ArrayList<String> user_pin = new ArrayList<String>();
	private ArrayList<String> user_mobile = new ArrayList<String>();
	private String username,authen;;
	private ListView userList;
	private AlertDialog.Builder build;
	private String address1,address2,city,phno,postal,state;
	String area=" ";
	
	private Toolbar toolbar;
	ProgressBar pb1,pb2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_activity);

		
		toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		//toolbartitle = (TextView) findViewById(R.id.titletool);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		
		 toolbar.setLogo(R.drawable.vbuy);
		   toolbar.setNavigationIcon(R.drawable.arrow5);
		
		userList = (ListView) findViewById(R.id.List);

		mHelper = new DbHelpers(this);
		DBAdapter db1=new DBAdapter(getApplicationContext());
		 db1.open();
		 Cursor c1 = db1.getAllContacts();
		  
		   
			
			if (db1.getAllContactscount(1)){
		    	 c1 = db1.getContact(1);
		    	 area=c1.getString(1);
		    	
			}
			else
			{
				area="Chennai";
			}
			db1.close();
		
		
		
		DBLogin db = new DBLogin(getApplicationContext());
		db = new DBLogin(getApplicationContext());
		 db.open();
		 Cursor c = db.getAllContacts();
		  
		   
			
			if (db.getAllContactscount(1)){
		    	 c = db.getContact(1);
		    	 
		    	 username=c.getString(2);
		    	 if (cachetoken.token.size() > 0){
			 authen = cachetoken.token.get("token");
			}
		    	 else{
		    		 Intent intent = new Intent(
								DisplayActivity.this.getApplicationContext(),
								RetailerMain.class);
						intent.putExtra("dir", "session");
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(intent);
		    	 }
			}
			
			
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
			
		//add new record
		findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						AddActivity.class);
				i.putExtra("update", false);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);

			}
		});
		
		
		userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				
				address1= user_fName.get(arg2);
				address2= user_lName.get(arg2);
				city=user_con.get(arg2);
				state= user_addr.get(arg2);
				postal= user_pin.get(arg2);
				phno=user_mobile.get(arg2);
				new AddAddress().execute();

			}
		});
		
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(), ShopCartActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(intent);
		overridePendingTransition(0, 0);
	}
	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		 
		 SubMenu search = menu.addSubMenu(0, 11, 0, "Search");
		 search.getItem().setIcon(R.drawable.searchwhite);
		    search.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		 SubMenu subMenu = menu.addSubMenu(0, 22, 1, "all");
		    subMenu.getItem().setIcon(R.drawable.options);
		    subMenu.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		    subMenu.add(0, 1, 1, "My Orders").setIcon(R.drawable.order).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		    
			   
			subMenu.add(0, 5, 2, "Cart").setIcon(R.drawable.cart).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			/*subMenu.add(0, 6, 3, "Wishlist").setIcon(R.drawable.wish_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		    subMenu.add(0, 3, 3, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
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
										DisplayActivity.this.getApplicationContext(),
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
		 
		  else if (itemId_ == 1) {
			  Intent in=new Intent(getApplicationContext(),TrackOrderActivity.class);
				startActivity(in);
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
			Toast.makeText(DisplayActivity.this, msg, Toast.LENGTH_LONG).show();
			finish();
		}

	/**
	 * displays data from SQLite
	 */
	private void displayData() {
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelpers.TABLE_NAME, null);

		userId.clear();
		user_fName.clear();
		user_lName.clear();
		user_con.clear();
		user_addr.clear();
		user_pin.clear();
		user_mobile.clear();
		
		if (mCursor.moveToFirst()) {
			do {
				userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelpers.KEY_ID)));
				user_fName.add(mCursor.getString(mCursor.getColumnIndex(DbHelpers.KEY_FNAME)));
				user_lName.add(mCursor.getString(mCursor.getColumnIndex(DbHelpers.KEY_LNAME)));
				user_con.add(mCursor.getString(mCursor.getColumnIndex(DbHelpers.KEY_CONTACT)));
				user_addr.add(mCursor.getString(mCursor.getColumnIndex(DbHelpers.KEY_ADDRESS)));
				user_pin.add(mCursor.getString(mCursor.getColumnIndex(DbHelpers.KEY_PIN)));
				user_mobile.add(mCursor.getString(mCursor.getColumnIndex(DbHelpers.KEY_MOBILE)));
				

			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(DisplayActivity.this,userId, user_fName, user_lName, user_con, user_addr, user_pin,user_mobile);
		userList.setAdapter(disadpt);
		mCursor.close();
	}

	public class DisplayAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList<String> id;
		private ArrayList<String> firstName;
		private ArrayList<String> lastName;
		private ArrayList<String> shipCon;
		private ArrayList<String> shipAddr;
		private ArrayList<String> shipPin;
		private ArrayList<String> shipmobile;
		
		

		public DisplayAdapter(Context c, ArrayList<String> id,ArrayList<String> fname, ArrayList<String> lname,ArrayList<String> scon, ArrayList<String> saddr,ArrayList<String> spin,ArrayList<String> mobile) {
			this.mContext = c;

			this.id = id;
			this.firstName = fname;
			this.lastName = lname;
			this.shipCon = scon;
			this.shipAddr = saddr;
			this.shipPin = spin;
			this.shipmobile = mobile;
			
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return id.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(final int pos, View child, ViewGroup parent) {
			Holder mHolder;
			LayoutInflater layoutInflater;
			if (child == null) {
				layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				child = layoutInflater.inflate(R.layout.listcell, null);
				mHolder = new Holder();
				mHolder.txt_fName = (TextView) child.findViewById(R.id.txt_fName);
				mHolder.txt_lName = (TextView) child.findViewById(R.id.txt_lName);
				mHolder.txt_contact = (TextView) child.findViewById(R.id.txt_contact);
				mHolder.txt_address = (TextView) child.findViewById(R.id.txt_address);
				mHolder.txt_mobile = (TextView) child.findViewById(R.id.mobile);
				mHolder.txt_pin = (TextView) child.findViewById(R.id.txt_pin);
				mHolder.delete = (ImageView) child.findViewById(R.id.delete);
				mHolder.edit = (ImageView) child.findViewById(R.id.edit);
				
				child.setTag(mHolder);
			} else {
				mHolder = (Holder) child.getTag();
			}
			mHolder.txt_fName.setText(firstName.get(pos));
			mHolder.txt_lName.setText(lastName.get(pos));
			mHolder.txt_contact.setText(shipCon.get(pos));
			mHolder.txt_address.setText(shipAddr.get(pos));
			mHolder.txt_pin.setText(shipPin.get(pos));
			mHolder.txt_mobile.setText(shipmobile.get(pos));
			mHolder.delete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View paramView) {
					
					build = new AlertDialog.Builder(DisplayActivity.this);
					build.setTitle("Delete");
					build.setMessage("Do you want to delete ?");
					build.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {

									

									dataBase.delete(
											DbHelpers.TABLE_NAME,
											DbHelpers.KEY_ID + "="
													+ id.get(pos), null);
									displayData();
									dialog.cancel();
								}
							});

					build.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					AlertDialog alert = build.create();
					alert.show();

					
				}
			});

			mHolder.edit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View paramView) {
					
			Intent i = new Intent(getApplicationContext(),
					AddActivity.class);
			i.putExtra("Fname", firstName.get(pos));
			i.putExtra("Lname", lastName.get(pos));
			i.putExtra("Con", shipCon.get(pos));
			i.putExtra("Addr", shipAddr.get(pos));
			i.putExtra("Pin", shipPin.get(pos));
			i.putExtra("mobile", shipmobile.get(pos));
			i.putExtra("ID", id.get(pos));
			i.putExtra("update", true);
			i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
				}
			});
			
			return child;
		}

		public class Holder {
			TextView txt_fName;
			TextView txt_lName;
			TextView txt_contact;
			TextView txt_address;
			TextView txt_pin;
			TextView txt_mobile;
			ImageView delete,edit;
			
		}

	}
	class AddAddress extends AsyncTask<String, String, String> {

		
		
	 	/**
	 	 * Before starting background thread Show Progress Dialog
	 	 * */
		
		 private final ProgressDialog pDialog1 = new ProgressDialog(DisplayActivity.this);
		 	@Override
		 	protected void onPreExecute() {
		 		super.onPreExecute();
		 		pDialog1.setMessage("Taking You To Payment...");
		 		pDialog1.setIndeterminate(false);
		 		pDialog1.setCancelable(false);
		 		pDialog1.show();
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
	 	    	cart.put("Address1", address1);
	 	    	cart.put("Address2", address2);
	 	    	cart.put("City", city);
	 	    	cart.put("PhoneNumber", phno);
	 	    	cart.put("PostalCode", postal);
	 	    	cart.put("State", state);
	 	    	cart.put("UserName", username);
	 	    	String json=cart.toString();
	 	    	
	 	        HttpPost httppost = new HttpPost(getString(R.string.addaddress));
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
	 			pDialog1.dismiss();
	 			Toast.makeText(getApplicationContext(),
	 					"Check your network connection", Toast.LENGTH_LONG)
	 					.show();
	 		}
	 		else{
	 		pDialog1.dismiss();
	 		/*Toast.makeText(getApplicationContext(),"Address Stored To DataBase", 
	                Toast.LENGTH_SHORT).show();*/
	 		Intent in = new Intent(getApplicationContext(),
					PaymentOptionActivity.class);
	 		
	 		in.putExtra("address1", address1);
	 		in.putExtra("address2", address2);
	 		in.putExtra("city", city);
	 		in.putExtra("state", state);
	 		in.putExtra("postal", postal);
	 		in.putExtra("phno", phno);
	 		in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
 			startActivity(in);	
	 	}

	}
	}
}
