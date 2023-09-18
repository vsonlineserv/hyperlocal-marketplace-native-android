package in.vbuy.client;


import in.vbuy.client.util.ListHeight;
import in.vbuy.client.util.Product;
import in.vbuy.client.util.ShopCart;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class PaymentOptionActivity extends BaseActivity {
	public static ListView menulist = null;
	ShopCart sharedPreference;
	List<Product> favorites;
	public static TextView total;
	public static MyOrderAdapter1 adapter;
	DBLogin db = new DBLogin(this);
	public static Button checkout;
	Activity activity;
	static String authen;
	static String username="";
	static String itemname;
	static String productid;
	static String branchid;
	static Double unitprice;
	static Double ship_amount;
	Double price;
	RadioGroup delivery;
	RadioGroup payment;
	AlertDialog alertDialog ;
	String deliverymethod,paymentmethod;
	Button confirm;
	static String update;
	static int quan;
	static EditText coupon;
	Button apply;
	public static TextView shipping_amounts,discount_amount,net_amount;
	TextView address1_text,address2_text,city_text,phno_text,postal_text,state_text;
	 String address1,address2,city,phno,postal,state;
	 
	 private Toolbar toolbar;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_layout);
		
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
		
		activity=this;
		delivery=(RadioGroup)findViewById(R.id.rg);
		payment=(RadioGroup)findViewById(R.id.rg1);
		menulist = (ListView) findViewById(R.id.List);
		total = (TextView) findViewById(R.id.total);
		address1_text = (TextView) findViewById(R.id.address);
		address2_text = (TextView) findViewById(R.id.near);
		city_text = (TextView) findViewById(R.id.city);
		postal_text = (TextView) findViewById(R.id.postal);
		phno_text = (TextView) findViewById(R.id.mobile);
		state_text = (TextView) findViewById(R.id.state);
		shipping_amounts = (TextView) findViewById(R.id.shipping_amounts);
		discount_amount = (TextView) findViewById(R.id.Discount_amount);
		net_amount = (TextView) findViewById(R.id.net_amount);
		coupon = (EditText) findViewById(R.id.coupon);
		confirm = (Button) findViewById(R.id.confirmorderbtn);
		apply = (Button) findViewById(R.id.apply);
		sharedPreference = new ShopCart();
		favorites = sharedPreference.getFavorites(this);
		total.setText(""+sharedPreference.totalcart(this));
		shipping_amounts.setText(""+sharedPreference.totalshipping(this));
		discount_amount.setText("0.00");
		net_amount.setText(""+sharedPreference.totalValue(this));
		adapter=(new MyOrderAdapter1(this, favorites));
		menulist.setAdapter(adapter);
		getListViewSize(menulist);
		Intent in=getIntent();
		address1=in.getStringExtra("address1" );
		address2=in.getStringExtra("address2");
		city=in.getStringExtra("city");
		state=in.getStringExtra("state");
		postal=in.getStringExtra("postal");
		phno=in.getStringExtra("phno");
		address1_text.setText(address1);
		address2_text.setText(address2);
		city_text.setText(city);
		phno_text.setText(phno);
		postal_text.setText(postal);
		state_text.setText(state);
		 db.open();
		 Cursor c = db.getAllContacts();
		if (db.getAllContactscount(1)){
	    	 c = db.getContact(1);
	    	
	    	username=(c.getString(2));
	    	 if (cachetoken.token.size() > 0){
		 authen = cachetoken.token.get("token");
		}
	    	 else{
	    		 Intent intent = new Intent(
							PaymentOptionActivity.this.getApplicationContext(),
							RetailerMain.class);
					intent.putExtra("dir", "session");
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(intent);
	    	 }
		}
		db.close();
		apply.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				new MyOrderAdapter1.Getdiscount().execute();
			}
		});
		confirm.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				
				// TODO Auto-generated method stub
				//if((delivery.getCheckedRadioButtonId()==R.id.pickup || delivery.getCheckedRadioButtonId()==R.id.doordeliver ))sri edit
				 if((delivery.getCheckedRadioButtonId()==R.id.doordeliver ))
                 {
					 if(delivery.getCheckedRadioButtonId()==R.id.doordeliver){
						 deliverymethod="HomeDelivery";
					 }
					 /*else{//sri edit
						 deliverymethod="HomeDelivery";
					 }*/
					//if((payment.getCheckedRadioButtonId()==R.id.cashon || payment.getCheckedRadioButtonId()==R.id.Cardwallet  || payment.getCheckedRadioButtonId()==R.id.cardon ))
                  if((payment.getCheckedRadioButtonId()==R.id.cashon ))
                  {
                	  if(payment.getCheckedRadioButtonId()==R.id.cashon){
                		  paymentmethod="CashOnDelivery";
                		  new AddProductPay().execute();
 					 }
 					 /*else if(payment.getCheckedRadioButtonId()==R.id.Cardwallet){
 						 paymentmethod="PaymentGateway1";
 						 new AddProductPay().execute();
 					 }
 					 else if(payment.getCheckedRadioButtonId()==R.id.cardon){
 						 paymentmethod="CardOnDelivery";
 						 new AddProductPay().execute();
 					 }*/
                  }
                  else{
                	  AlertDialog alert= new AlertDialog.Builder(PaymentOptionActivity.this).create();
                      alert.setTitle("VBuy Shopping");
                      alert.setMessage("Select Payment Option");
                      alert.show();
                  }
                 }
                 else    
                 {
                       
                	 AlertDialog alert= new AlertDialog.Builder(PaymentOptionActivity.this).create();
                     alert.setTitle("VBuy Shopping");
                     alert.setMessage("Select Delivery Option");
                     alert.show();
                 }
			}

            

 });
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
	                listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		overridePendingTransition(0, 0);
	}
	 public static class MyOrderAdapter1 extends ArrayAdapter<Product> {
		 public static TextView quantity = null;
		 public static  TextView productPriceTxt = null;
		 public static  TextView quant = null;
		 public static  TextView unit = null;
		 public static  TextView shipping_quantity = null;
		 public static  TextView shipping_amount = null;
		 public static  TextView shipping_total = null;
		LayoutInflater inflater;
			private static Context context;
			List<Product> products;
			ShopCart sharedPreference;
			protected ImageLoader imageLoader = ImageLoader.getInstance();
			DisplayImageOptions options;
			public MyOrderAdapter1(Context context, List<Product> products) {
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
				final View arg=	 convertView;
				final	TextView productNameTxt = (TextView) convertView
							.findViewById(R.id.proname);
				shipping_quantity = (TextView) convertView
							.findViewById(R.id.shipping_qua);
				 shipping_amount = (TextView) convertView
							.findViewById(R.id.shipping_amount);
				 shipping_total = (TextView) convertView
							.findViewById(R.id.shipping_total);
				 quantity = (TextView) convertView
							.findViewById(R.id.quantity);
				 productPriceTxt = (TextView) convertView
							.findViewById(R.id.price);
				 quant = (TextView) convertView
							.findViewById(R.id.qty);
				 unit = (TextView) convertView
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
				shipping_quantity.setText(""+product.getQty());
				shipping_amount.setText(""+product.getItemDesc());
				shipping_total.setText(""+(product.getQty()*(product.getItemDesc())));
				quantity.setText(""+product.getQty());
				quant.setText(""+product.getQty());
				Double a=(product.getQty()*(product.getPrice()));
				unit.setText(""+product.getPrice());
				productPriceTxt.setText(""+a);
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
										new DeleteProductPay().execute();
										
										}
										db.close();
									}
								});
					
				
				plus.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramView) {
						quantity = (TextView) arg
								.findViewById(R.id.quantity);
					 productPriceTxt = (TextView) arg
								.findViewById(R.id.price);
					 quant = (TextView) arg
								.findViewById(R.id.qty);
					 unit = (TextView) arg
								.findViewById(R.id.unitprice);
					 shipping_quantity = (TextView) arg
								.findViewById(R.id.shipping_qua);
					 shipping_amount = (TextView) arg
								.findViewById(R.id.shipping_amount);
					 shipping_total = (TextView) arg
								.findViewById(R.id.shipping_total);
						int old=Integer.parseInt(quantity.getText().toString());
						if(old<=99){
						
						branchid=(products.get(position).getbranchid());
						unitprice=(products.get(position).getunitprice());
						itemname=(products.get(position).getItemName());
						productid=(products.get(position).getproductId());
						ship_amount=(products.get(position).getItemDesc());
						quan=(old)+1;
						update="plus";
						DBLogin	db = new DBLogin(context);
						db.open();
						if (db.getAllContactscount(1)){
						new UpdateProductPay().execute();
						}
						db.close();
						
						}
					}
					
					});
				minus.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramView) {
						quantity = (TextView) arg
								.findViewById(R.id.quantity);
					 productPriceTxt = (TextView) arg
								.findViewById(R.id.price);
					 quant = (TextView) arg
								.findViewById(R.id.qty);
					 unit = (TextView) arg
								.findViewById(R.id.unitprice);
					 shipping_quantity = (TextView) arg
								.findViewById(R.id.shipping_qua);
					 shipping_amount = (TextView) arg
								.findViewById(R.id.shipping_amount);
					 shipping_total = (TextView) arg
								.findViewById(R.id.shipping_total);
						int old=Integer.parseInt(quantity.getText().toString());
						if(old>1){
						branchid=(products.get(position).getbranchid());
						unitprice=(products.get(position).getunitprice());
						itemname=(products.get(position).getItemName());
						productid=(products.get(position).getproductId());
						ship_amount=(products.get(position).getItemDesc());
						quan=(old)-1;
						update="minus";
						DBLogin	db = new DBLogin(context);
						db.open();
						if (db.getAllContactscount(1)){
						new UpdateProductPay().execute();
						}
						db.close();
						
						}
					}
					});
				return convertView;
			}
	
			// Discount Coupon
			
			static class Getdiscount extends AsyncTask<String, String, String> {

				 	/**
				 	 * Before starting background thread Show Progress Dialog
				 	 * */
					 
					private final ProgressDialog progressDialog = new ProgressDialog(context);

					protected void onPreExecute() {
						progressDialog.setCancelable(false);
						progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog.setIndeterminate(true);
						progressDialog.setMessage("Loading...");
						progressDialog.show();
					}

				 	
				 	protected String doInBackground(String... args) {
				 		String result = null;
				 		
				 		
				 		 HttpParams myParams = new BasicHttpParams();
					 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
					 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
					 	    //HttpClient httpclient = new DefaultHttpClient(myParams );
				 	    String url=context.getString(R.string.coupon)+coupon.getText().toString()+"&userName="+username;
				 	    try {
				 	    	HttpClient client = new DefaultHttpClient(new BasicHttpParams());
				 	    	HttpGet httpGet = new HttpGet(url);
				 	    	httpGet.setHeader("Content-type", "application/json");
				 	    	httpGet.addHeader("Authorization",  "Bearer"+" "+authen );

				 	    	    HttpResponse response = client.execute(httpGet);
				 	    	  result = EntityUtils.toString(response.getEntity());
			  
					
				 	   } catch (ClientProtocolException e) {
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
				progressDialog.dismiss();
				if(result.equalsIgnoreCase("Failed")){
						Toast.makeText(context,
								"Check your network connection", Toast.LENGTH_LONG)
								.show();
					}
				else{
					PaymentOptionActivity.discount_amount.setText(""+(Double.parseDouble(result)));
				PaymentOptionActivity.total.setText(""+ShopCart.totalcart(context));
				PaymentOptionActivity.shipping_amounts.setText(""+ShopCart.totalshipping(context));
				Double ans=(ShopCart.totalValue(context))-(Double.parseDouble(result));
				PaymentOptionActivity.net_amount.setText(""+ans);
			}
			}
			}
			
			// Update Cart Items
			 
			 class UpdateProductPay extends AsyncTask<String, String, String> {

					
					
				 	/**
				 	 * Before starting background thread Show Progress Dialog
				 	 * */
					
				 private final ProgressDialog progressDialog1 = new ProgressDialog(getContext());

					protected void onPreExecute() {
						progressDialog1.setCancelable(false);
						progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog1.setIndeterminate(true);
						progressDialog1.setMessage("Updating To Cart...");
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
				 		progressDialog1.dismiss();
				 		if(result.equalsIgnoreCase("Failed")){
							Toast.makeText(context,
									"Check your network connection", Toast.LENGTH_LONG)
									.show();
						}
				 		else{
				 		int old=Integer.parseInt(quantity.getText().toString());
				 		if(update.equalsIgnoreCase("plus")){
				 			
				 			
								quantity.setText(""+(old+1));
								Double as=((old+1)*(unitprice));
								sharedPreference.updateProduct(context,itemname,(old+1));
								productPriceTxt.setText(""+(as));
								unit.setText(""+unitprice);
								quant.setText(""+(old+1));
								shipping_quantity.setText(""+(old+1));
								shipping_amount.setText(""+ship_amount);
								shipping_total.setText(""+((old+1)*ship_amount));
								ShopCartActivity.total.setText(""+sharedPreference.totalValue(context));
								total.setText(""+sharedPreference.totalValue(context));
								ShopCartActivity.adapter.notifyDataSetChanged();
								
				 		}
				 		if(update.equalsIgnoreCase("minus")){
				 			
							
				 			quantity.setText(""+(old-1));
							Double as=((old-1)*(unitprice));
							sharedPreference.updateProduct(context,itemname,(old-1));
							productPriceTxt.setText(""+(as));
							unit.setText(""+unitprice);
							quant.setText(""+(old-1));
							shipping_quantity.setText(""+(old-1));
							shipping_amount.setText(""+ship_amount);
							shipping_total.setText(""+((old-1)*ship_amount));
							ShopCartActivity.total.setText(""+sharedPreference.totalValue(context));
							total.setText(""+sharedPreference.totalValue(context));
							
				 		}
				 		List<Product> products1;
						products1 = ShopCart.getFavorites(getContext());
						 
						ShopCartActivity.menulist.setAdapter(new ShopCartActivity.MyOrderAdapter(getContext(), products1));
						new Getdiscount().execute();
				 	}
				 	}
				}
			
		
	 // Delete Progress
	 class DeleteProductPay extends AsyncTask<String, String, String> {

			
			
		 	/**
		 	 * Before starting background thread Show Progress Dialog
		 	 * */
			
		 private final ProgressDialog progressDialog1 = new ProgressDialog(context);

			protected void onPreExecute() {
				progressDialog1.setCancelable(false);
				progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog1.setIndeterminate(true);
				progressDialog1.setMessage("Deleting From Cart...");
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
		 		progressDialog1.dismiss();
		 		
		 		if(result.equalsIgnoreCase("Failed")){
					Toast.makeText(context,
							"Check your network connection", Toast.LENGTH_LONG)
							.show();
				}
		 		else{
				sharedPreference.deleteProduct(getContext(),itemname,branchid);
				
				try {
					List<Product> products1;
					products1 = ShopCart.getFavorites(getContext());
					ShopCartActivity.menulist.setAdapter(new MyOrderAdapter1(getContext(), products1));
					menulist.setAdapter(new MyOrderAdapter1(getContext(), products1));
					getListViewSize(menulist);
					ShopCartActivity.total.setText(""+sharedPreference.totalValue(getContext()));
					total.setText(""+sharedPreference.totalValue(getContext()));
				
					
				} catch (Exception e) {
					Log.i("Delete", "No Data");
				}
		 		
				new Getdiscount().execute();
		 		
		 	}

		}
	 }
}
	
	// Cart add to Server
		
		
			class AddProductPay extends AsyncTask<String, String, String> {

				
				
			 	/**
			 	 * Before starting background thread Show Progress Dialog
			 	 * */
				 private final ProgressDialog progressDialog1 = new ProgressDialog(activity);

					protected void onPreExecute() {
						progressDialog1.setCancelable(false);
						progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog1.setIndeterminate(true);
						progressDialog1.setMessage("Confirm Order...");
						progressDialog1.show();
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
						jsonob.put("ProductId", favorites.get(i).getproductId()) ;
						jsonob.put("Quantity", favorites.get(i).getQty()) ;
						jsonob.put("SpecialPrice", (int)favorites.get(i).getPrice()) ;
						jsonob.put("Name", favorites.get(i).getItemName()) ;
						jsonob.put("PictureName", favorites.get(i).getImageId()) ;
						jsonob.put("BranchId", favorites.get(i).getbranchid()) ;
						jsonob.put("Branch", favorites.get(i).getbranch()) ;
						int tot=(favorites.get(i).getQty()*((int)favorites.get(i).getPrice()));
						jsonob.put("SubTotal", tot) ;
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
			 	    	cart.put("userName", username);
			 	    	cart.put("couponCode", coupon.getText().toString());
			 	    	cart.put("deliveryMethod", deliverymethod);
			 	    	cart.put("paymentMethod", paymentmethod);
			 	    	String json=cart.toString();
			 	   	System.out.println("Confirm Order"+json);
			 	        HttpPost httppost = new HttpPost(getString(R.string.confirmorder));
			 	        httppost.setHeader("Content-type", "application/json");
			 	        httppost.addHeader("Authorization",  "Bearer"+" "+authen );
			 	        StringEntity se = new StringEntity(json); 
			 	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			 	        httppost.setEntity(se); 

			 	        HttpResponse response = httpclient.execute(httppost);
			 	        temp = EntityUtils.toString(response.getEntity());
			 	       temp=temp.replace("\"", "");
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
			 		progressDialog1.dismiss();
			 		if(result.equalsIgnoreCase("Failed")){
						Toast.makeText(getApplicationContext(),
								"Check your network connection", Toast.LENGTH_LONG)
								.show();
					}
			 	
			 		else{ 
			 			if(result.equalsIgnoreCase("0")){
			 			Toast.makeText(getApplicationContext(),"Error Confirmation Order", 
				                Toast.LENGTH_SHORT).show();
			 		}
			 		else if(result.length()<25){
			 			ShopCart.clearall(getApplicationContext());
			 			Intent in=new Intent(getApplicationContext(),confirmPageActivity.class);
						in.putExtra("id", result);
						startActivity(in);
			 		}
			 		else
			 		{
			 			ShopCart.clearall(getApplicationContext());
			 			Intent in=new Intent(getApplicationContext(),PayuActivity.class);
						in.putExtra("address", result);
						startActivity(in);
			 		}
			 		
			 		}
			 	}

			}
			
			
	 
}
