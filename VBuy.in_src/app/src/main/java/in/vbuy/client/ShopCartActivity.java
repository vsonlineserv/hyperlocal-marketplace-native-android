package in.vbuy.client;



import in.vbuy.client.util.Product;
import in.vbuy.client.util.ShopCart;

import java.io.IOException;
import java.util.List;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ShopCartActivity extends ActionBarActivity {
	public static ListView menulist = null;
	protected Typeface fontStyle = null;
	ShopCart sharedPreference;
	List<Product> favorites;
	public static TextView total;
	public static MyOrderAdapter adapter;
	DBLogin db = new DBLogin(this);
	static Button checkout_button;
	static Activity activity;
	static String authen;
	static String username;
	static String itemname;
	static String productid;
	static String branchid;
	static Double ship_amount;
	String area=" ";
	
	static Double unitprice;
	static TextView no_product;
	String actiontitle;
	Double price;
	AlertDialog alertDialog ;
	static String update;
	static int quan;
	public static TextView quantity1 = null;
	 public static  TextView productPriceTxt1 = null;
	 public static  TextView quant1 = null;
	 public static  TextView unit1 = null;
	 public static  TextView shipping_quantity = null;
	 public static  TextView shipping_amount = null;
	 public static  TextView shipping_total = null;
	 private Toolbar toolbar;
	 
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		setContentView(R.layout.buy);
		
		toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		//toolbartitle = (TextView) findViewById(R.id.titletool);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		
		
		activity=this;
		menulist = (ListView) findViewById(R.id.order);
		total = (TextView) findViewById(R.id.price);
		no_product=(TextView)findViewById(R.id.no_product);
		checkout_button = (Button) findViewById(R.id.checkout);
		sharedPreference = new ShopCart();
		favorites = sharedPreference.getFavorites(this);
		total.setText(""+sharedPreference.totalValue(this));
		
		if(favorites!=null){
		adapter=(new MyOrderAdapter(this, favorites));
		menulist.setAdapter(adapter);
		}
		Log.i("Excecute", "Success");
		if(favorites==null){
			checkout_button.setVisibility(View.INVISIBLE);
			no_product.setVisibility(View.VISIBLE);
			menulist.setVisibility(View.INVISIBLE);
		}
		else{
			if(favorites.size()==0){
				checkout_button.setVisibility(View.INVISIBLE);
				no_product.setVisibility(View.VISIBLE);
				menulist.setVisibility(View.INVISIBLE);
			}
			else{
			checkout_button.setVisibility(View.VISIBLE);
			no_product.setVisibility(View.INVISIBLE);
			menulist.setVisibility(View.VISIBLE);
		}
		}
		
		DBAdapter db1=new DBAdapter(getApplicationContext());
		 db1.open();
		 Cursor c = db1.getAllContacts();
		if (db1.getAllContactscount(1)){
	    	 c = db1.getContact(1);
	    	 area=c.getString(1);
		}
		else{
			area="Chennai";
		}
		db1.close();
		
		
		
		DBLogin db2=new DBLogin(getApplicationContext());
		 db2.open();
		 Cursor c1 = db2.getAllContacts();
		if (db2.getAllContactscount(1)){
	    	 c1 = db2.getContact(1);
	    	
	    	username=(c1.getString(2));
	    	 if (cachetoken.token.size() > 0){
		 authen = cachetoken.token.get("token");
		}
	    	 else{
	    		 Intent intent = new Intent(
							ShopCartActivity.this.getApplicationContext(),
							RetailerMain.class);
					intent.putExtra("dir", "session");
					startActivity(intent);
	    	 }
		}
		
		db2.close();
		
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
		
		checkout_button.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				
				
									 
				 db.open();
				 Cursor c = db.getAllContacts();
				 try {
					 
					 if (isNetworkAvailable()){
						 if (db.getAllContactscount(1)){
						if (cachetoken.token.size() > 0)

						{
							

							 Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
							 intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							 startActivity(intent);
							
						}} else {
							 
							if (isNetworkAvailable())
							{
							
									Intent intent = new Intent(getApplicationContext(), RetailerMain.class);
									intent.putExtra("dir", "cart");
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
				 
				 
				   
				    db.close();
				
				   
			}
			
		});
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		overridePendingTransition(0, 0);
	}
	
	 @Override
		public void onResume() {
			super.onResume();
		    invalidateOptionsMenu();
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
 		 /* subMenu.add(0, 6, 3, "Wishlist").setIcon(R.drawable.wish_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
 		   /*  subMenu.add(0, 3, 3, "Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
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
	    								ShopCartActivity.this.getApplicationContext(),
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
				in.putExtra("dir", "cart");
				in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(in);
	        return true;
	      }
	      else if (itemId_ == 1) {
	    	  Intent in=new Intent(getApplicationContext(),TrackOrderActivity.class);
				startActivity(in);
				in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

			Toast.makeText(ShopCartActivity.this, msg, Toast.LENGTH_LONG).show();
		}
	 
	 public static class MyOrderAdapter extends ArrayAdapter<Product> {
		 
		LayoutInflater inflater;
			private Context context;
			List<Product> products;
			ShopCart sharedPreference;
			protected ImageLoader imageLoader = ImageLoader.getInstance();
			DisplayImageOptions options;
			public MyOrderAdapter(Context context, List<Product> products) {
				super(context, R.layout.payment_row, products);
				this.context = context;
				this.products = products;
				sharedPreference = new ShopCart();
				options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.hidescreen)
				.cacheInMemory()
				.cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
			}

			

			@Override
			public int getCount() {
				return products.size();
			}

			@Override
			public Product getItem(int position) {
				return products.get(position);
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
			 	
					 inflater = (LayoutInflater) context
							.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
					 convertView = inflater.inflate(R.layout.payment_row, null);
					 
					 convertView.setOnClickListener(null);
					 convertView.setOnLongClickListener(null);
					 convertView.setLongClickable(false);
					 shipping_quantity = (TextView) convertView
								.findViewById(R.id.shipping_qua);
					 shipping_amount = (TextView) convertView
								.findViewById(R.id.shipping_amount);
					 shipping_total = (TextView) convertView
								.findViewById(R.id.shipping_total);
				final View arg=	 convertView;
				final	TextView productNameTxt = (TextView) convertView
							.findViewById(R.id.proname);
				final	TextView quantity = (TextView) convertView
							.findViewById(R.id.quantity);
				 final	TextView productPriceTxt = (TextView) convertView
							.findViewById(R.id.price);
				 final	TextView quant = (TextView) convertView
							.findViewById(R.id.qty);
				 final	TextView unit = (TextView) convertView
							.findViewById(R.id.unitprice);
				final	TextView store = (TextView) convertView
						.findViewById(R.id.store);
				final	ImageView favoriteImg = (ImageView) convertView
							.findViewById(R.id.pro_img);
				final	ImageView delete= (ImageView) convertView
						.findViewById(R.id.delete);
				final	ImageView hide= (ImageView) convertView
						.findViewById(R.id.hide);
				final	ImageView plus= (ImageView) convertView
						.findViewById(R.id.plus);
				final	ImageView minus= (ImageView) convertView
						.findViewById(R.id.minus);
					
				final Product product = (Product) getItem(position);
				productNameTxt.setText(product.getItemName());
				store.setText(product.getbranch());
				quantity.setText(""+product.getQty());
				quant.setText(""+product.getQty());
				Double a=(product.getQty()*(product.getPrice()));
				unit.setText(""+product.getPrice());
				productPriceTxt.setText(""+a);
				shipping_quantity.setText(""+product.getQty());
				shipping_amount.setText(""+product.getItemDesc());
				shipping_total.setText(""+(product.getQty()*(product.getItemDesc())));
				String  temp="http://images.vbuy.in/VBuyImages/large/";
				String image = product.getImageId();
				String pic=temp+image;
				String img=pic.replaceAll("\\s", "%20");
				imageLoader.displayImage(img,favoriteImg, options, new SimpleImageLoadingListener() {
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
						

						favoriteImg.setImageResource(R.drawable.hidescreen);
						
					}
					public void onLoadingComplete(Bitmap loadedImage) {
						hide.setVisibility(View.GONE);
						
					}
					
				});
				delete.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramView) {
						Log.i("Delete", "Item");
						
						DBLogin	db = new DBLogin(context);
						db.open();
						if (db.getAllContactscount(1)){
										branchid=(products.get(position).getbranchid());
										unitprice=(products.get(position).getunitprice());
										itemname=(products.get(position).getItemName());
										productid=(products.get(position).getproductId());
										quan=(products.get(position).getQty());
										new DeleteProduct().execute();
										
										}
										else{
											Log.i("Selected Position",products.get(position).getItemName());
											sharedPreference.deleteProduct(context,products.get(position).getItemName(),products.get(position).getbranchid());
											
											try {
												List<Product> products1;
												products1 = ShopCart.getFavorites(context);
												ShopCartActivity.menulist.setAdapter(new MyOrderAdapter(context, products1));
												ShopCartActivity.total.setText(""+sharedPreference.totalValue(context));
												if(products1.size()==0){
													checkout_button.setVisibility(View.INVISIBLE);
													no_product.setVisibility(View.VISIBLE);
													menulist.setVisibility(View.INVISIBLE);
												}
												else{
													checkout_button.setVisibility(View.VISIBLE);
													no_product.setVisibility(View.INVISIBLE);
													menulist.setVisibility(View.VISIBLE);
												}
											} catch (Exception e) {
												Log.i("Delete", "No Data");
											}
										}
						db.close();
									}
								});
					
				
				plus.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramView) {
						quantity1 = (TextView) arg
								.findViewById(R.id.quantity);
					 productPriceTxt1 = (TextView) arg
								.findViewById(R.id.price);
					 quant1 = (TextView) arg
								.findViewById(R.id.qty);
					 unit1 = (TextView) arg
								.findViewById(R.id.unitprice);
					 shipping_quantity = (TextView) arg
								.findViewById(R.id.shipping_qua);
					 shipping_amount = (TextView) arg
								.findViewById(R.id.shipping_amount);
					 shipping_total = (TextView) arg
								.findViewById(R.id.shipping_total);
						int old=Integer.parseInt(quantity.getText().toString());
						if(old<=99){
							DBLogin	db = new DBLogin(context);
							db.open();
							if (db.getAllContactscount(1)){
						branchid=(products.get(position).getbranchid());
						unitprice=(products.get(position).getunitprice());
						itemname=(products.get(position).getItemName());
						productid=(products.get(position).getproductId());
						ship_amount=(products.get(position).getItemDesc());
						
						quan=(old)+1;
						update="plus";
						new UpdateProducts().execute();
						}
						else{
							
								quantity.setText(""+(old+1));
								Double as=(((old+1))*(product.getPrice()));;
								sharedPreference.updateProduct(context,products.get(position).getItemName(),(old+1));
								productPriceTxt.setText(""+(as));
								unit.setText(""+products.get(position).getunitprice());
								quant.setText(""+(old+1));
								shipping_quantity.setText(""+(old+1));
								shipping_amount.setText(""+products.get(position).getItemDesc());
								shipping_total.setText(""+((old+1)*products.get(position).getItemDesc()));
								ShopCartActivity.total.setText(""+sharedPreference.totalValue(context));
							}
							db.close();
						
						}
					}
					
					});
				minus.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramView) {
						quantity1 = (TextView) arg
								.findViewById(R.id.quantity);
					 productPriceTxt1 = (TextView) arg
								.findViewById(R.id.price);
					 quant1 = (TextView) arg
								.findViewById(R.id.qty);
					 unit1 = (TextView) arg
								.findViewById(R.id.unitprice);
					 shipping_quantity = (TextView) arg
								.findViewById(R.id.shipping_qua);
					 shipping_amount = (TextView) arg
								.findViewById(R.id.shipping_amount);
					 shipping_total = (TextView) arg
								.findViewById(R.id.shipping_total);
						int old=Integer.parseInt(quantity.getText().toString());
						if(old>1){
							DBLogin	db = new DBLogin(context);
							db.open();
							if (db.getAllContactscount(1)){
						branchid=(products.get(position).getbranchid());
						unitprice=(products.get(position).getunitprice());
						itemname=(products.get(position).getItemName());
						productid=(products.get(position).getproductId());
						ship_amount=(products.get(position).getItemDesc());
						quan=(old)-1;
						update="minus";
						new UpdateProducts().execute();
						}
						else{
						
						
							quantity.setText(""+(old-1));
							Double as=(((old-1))*(product.getPrice()));;
							sharedPreference.updateProduct(context,products.get(position).getItemName(),(old-1));
							productPriceTxt.setText(""+(as));
							unit.setText(""+products.get(position).getunitprice());
							quant.setText(""+(old-1));
							shipping_quantity.setText(""+(old-1));
							shipping_amount.setText(""+products.get(position).getItemDesc());
							shipping_total.setText(""+((old-1)*products.get(position).getItemDesc()));
							ShopCartActivity.total.setText(""+sharedPreference.totalValue(context));
						}
							db.close();
						}
					}
					});
				return convertView;
			}
			// Update Cart Items
			 
			 class UpdateProducts extends AsyncTask<String, String, String> {

					
					
				 	/**
				 	 * Before starting background thread Show Progress Dialog
				 	 * */
					
				 private final ProgressDialog progressDialog11 = new ProgressDialog(activity);

					protected void onPreExecute() {
						progressDialog11.setCancelable(false);
						progressDialog11.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog11.setIndeterminate(true);
						progressDialog11.setMessage("Updating To Cart...");
						progressDialog11.show();
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
				 	    	cart.put("AdditionalShippingCharge",ship_amount);
				 	    	cart.put("BranchId",branchid);
				 	    	cart.put("ProductId", productid);
				 	    	cart.put("Quantity", quan);
				 	    	cart.put("UnitPrice", unitprice);
				 	    	cart.put("UserName", username);
				 	    	String json=cart.toString();
				 	    	Log.d("Delete Product :", json);
				 	        HttpPost httppost = new HttpPost(getContext().getString(R.string.updatecart));
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
				 			progressDialog11.dismiss();
				 			Toast.makeText(getContext(),
				 					"Check your network connection", Toast.LENGTH_LONG)
				 					.show();
				 		}
				 		else{
				 		progressDialog11.dismiss();
				 		int old=Integer.parseInt(quantity1.getText().toString());
				 		if(update.equalsIgnoreCase("plus")){
				 			
								
								quantity1.setText(""+(old+1));
								Double as=((old+1)*(unitprice));
								sharedPreference.updateProduct(context,itemname,(old+1));
								productPriceTxt1.setText(""+(as));
								unit1.setText(""+unitprice);
								quant1.setText(""+(old+1));
								shipping_quantity.setText(""+(old+1));
								shipping_amount.setText(""+ship_amount);
								shipping_total.setText(""+((old+1)*ship_amount));
								ShopCartActivity.total.setText(""+sharedPreference.totalValue(context));
				 		}
				 		if(update.equalsIgnoreCase("minus")){
				 			
							
				 			quantity1.setText(""+(old-1));
							Double as=((old-1)*(unitprice));
							sharedPreference.updateProduct(context,itemname,(old-1));
							productPriceTxt1.setText(""+(as));
							unit1.setText(""+unitprice);
							quant1.setText(""+(old-1));
							shipping_quantity.setText(""+(old-1));
							shipping_amount.setText(""+ship_amount);
							shipping_total.setText(""+((old-1)*ship_amount));
							ShopCartActivity.total.setText(""+sharedPreference.totalValue(context));
							
				 		}
				 	}
				 	}
				}
			
		
	 // Delete Progress
	 class DeleteProduct extends AsyncTask<String, String, String> {

			
			
		 	/**
		 	 * Before starting background thread Show Progress Dialog
		 	 * */
			
		 private final ProgressDialog progressDialog1 = new ProgressDialog(activity);

			protected void onPreExecute() {
				progressDialog1.setCancelable(false);
				progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog1.setIndeterminate(true);
				progressDialog1.setMessage("Deleting From Cart...");
				progressDialog1.show();
			}

		 	
		 	protected String doInBackground(String... args) {
		 		String temp = null;
		 		
		 		
		 		HttpParams myParams = new BasicHttpParams();
		 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
		 	    HttpClient httpclient = new DefaultHttpClient(myParams );
		 	    

		 	    try {
		 	    	JSONObject cart=new JSONObject();
		 	    	cart.put("BranchId",branchid);
		 	    	cart.put("ProductId", productid);
		 	    	cart.put("Quantity", quan);
		 	    	cart.put("UnitPrice", unitprice);
		 	    	cart.put("UserName", username);
		 	    	String json=cart.toString();
		 	    	Log.d("Delete Product :", json);
		 	        HttpPost httppost = new HttpPost(getContext().getString(R.string.deletecart));
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
		 			progressDialog1.dismiss();
		 			Toast.makeText(getContext(),
		 					"Check your network connection", Toast.LENGTH_LONG)
		 					.show();
		 		}
		 		else{
		 		progressDialog1.dismiss();
		 		Log.i("Selected Position",itemname);
				sharedPreference.deleteProduct(getContext(),itemname,branchid);
				
				try {
					List<Product> products1;
					products1 = ShopCart.getFavorites(getContext());
					ShopCartActivity.menulist.setAdapter(new MyOrderAdapter(getContext(), products1));
					ShopCartActivity.total.setText(""+sharedPreference.totalValue(getContext()));
					if(products1.size()==0){
						checkout_button.setVisibility(View.INVISIBLE);
						no_product.setVisibility(View.VISIBLE);
						menulist.setVisibility(View.INVISIBLE);
					}
					else{
						checkout_button.setVisibility(View.VISIBLE);
						no_product.setVisibility(View.INVISIBLE);
						menulist.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					Log.i("Delete", "No Data");
				}
		 		
		 		}
		 		
		 	}

		}
	 
}
}