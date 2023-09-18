package in.vbuy.client;

import in.vbuy.client.util.Validation;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup_Login extends Activity {
	
	TextView login;
	Button signupbtn;
	EditText fname,email,pass,cnfrmpass,phoneno;
	String firstname,emailid,password,confirmpass,phonenumber;
	String direction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_login);
		login=(TextView)findViewById(R.id.login);
		signupbtn=(Button)findViewById(R.id.Signupbtn);
		fname=(EditText)findViewById(R.id.name);
		email=(EditText)findViewById(R.id.email);
		pass=(EditText)findViewById(R.id.password);
		cnfrmpass=(EditText)findViewById(R.id.cfrmpas);
		phoneno=(EditText)findViewById(R.id.phone);
		Intent getin = getIntent();
		direction = getin.getStringExtra("dir");
		 registerViews();
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
        signupbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				firstname=fname.getText().toString();
		 		emailid=email.getText().toString();
		 		password=pass.getText().toString();
		 		confirmpass=cnfrmpass.getText().toString();
		 		phonenumber=phoneno.getText().toString();
		 		
		 		
		 		
		 		
		 		
		 		
		 		/*	if ( checkValidation () )
		 				new CreateNewProduct().execute();	
	                else
	                    Toast.makeText(Signup_Login.this, "Form contains error", Toast.LENGTH_LONG).show();*/
		 		
		 		
		 		if(fname.getText().toString().equals(""))
		        {
		               //Toast.makeText(AddActivity.this, "Enter Your Full Name", Toast.LENGTH_SHORT).show();
		 			fname.setError("Enter Your Full Name");
		        }
		 		else if(!isValidEmail(emailid))
				{
		 			email.setError("Enter Valid Mail");
				
				}

				else if(password.length() <6 )
		        {
		               
					pass.setError("Enter Minimum 6 Digits");
		        }
				
				
				
				else if(!isPhoneNumber(phonenumber))
				{
					phoneno.setError("Enter Valid Phone Number");
				}
				else if(checkValidation())
				{
					new CreateNewProduct().execute();
					
					
				
				}
				else{
					Toast.makeText(Signup_Login.this, "Form contains error", Toast.LENGTH_LONG).show();
				}
		 		
		 		
		 		
			}
		});
        
        
	}
	
	private boolean isValidEmail(String emailid) {
		   String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

		   Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		   Matcher matcher = pattern.matcher(emailid);
		   return matcher.matches();
		  }
	private boolean isPhoneNumber(String phonenumber)
	 {
		 String NUMBER = "^[7-9][0-9]{9}$";

			Pattern pattern = Pattern.compile(NUMBER);
			Matcher matcher = pattern.matcher(phonenumber);
			return matcher.matches();
	 }

	
	private void registerViews() {
        
 
			pass.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.hasText(pass);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
			cnfrmpass.addTextChangedListener(new TextWatcher() {
				   public void afterTextChanged(Editable s) {
				      Validation.cnfpassText(pass,cnfrmpass);
				     
				   }
				 
				   public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				   public void onTextChanged(CharSequence s, int start, int before, int count) {}
				 });
			
			
			
	 		
 
	 		
 }
	 private boolean checkValidation() {
	        boolean ret = true;
	        if (!Validation.passwordText(pass)) ret = false;
	        if (!Validation.cnfpassText(pass,cnfrmpass)) ret = false;
	 
	        return ret;
	    }
	 
	
	class CreateNewProduct extends AsyncTask<String, String, String> {

		
		
	 	/**
	 	 * Before starting background thread Show Progress Dialog
	 	 * */
		 private final ProgressDialog pDialog = new ProgressDialog(Signup_Login.this);
	 	@Override
	 	protected void onPreExecute() {
	 		super.onPreExecute();
	 		pDialog.setMessage("Your Account is Creating");
	 		pDialog.setIndeterminate(false);
	 		pDialog.setCancelable(false);
	 		pDialog.show();
	 	}

	 	/**
	 	 * Creating product
	 	 * */
	 	protected String doInBackground(String... args) {
	 		String temp = null;
	 		
	 		
	 		
	 		//String url="http://staging.vbuy.in/api/Login/RegisterUser";
	 		HttpParams myParams = new BasicHttpParams();
	 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
	 	    HttpConnectionParams.setSoTimeout(myParams, 10000);
	 	    HttpClient httpclient = new DefaultHttpClient(myParams );
	 	    

	 	    try {
	 	    	JSONObject signup=new JSONObject();
	 	    	signup.put("FirstName", firstname);
	 	    	signup.put("Email", emailid);
	 	    	signup.put("Password", password);
	 	    	signup.put("ConfirmPassword", confirmpass);
	 	    	signup.put("PhoneNumber", phonenumber);
	 	    	
	 	    	String json=signup.toString();
	 	    	
	 	        HttpPost httppost = new HttpPost(getString(R.string.registeruser));
	 	        httppost.setHeader("Content-type", "application/json");

	 	        StringEntity se = new StringEntity(signup.toString()); 
	 	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	 	        httppost.setEntity(se); 

	 	        HttpResponse response = httpclient.execute(httppost);
	 	        temp = EntityUtils.toString(response.getEntity());
	 	        Log.i("tag", temp);


	 	    } 
	 	   catch (ClientProtocolException e) {
	 	    	return "Failed";
	 	    } 
	 	   catch (HttpHostConnectException e){
	 		  return "Failed";
	 	    }
	 	   catch (JSONException e) {
				
	 		  return "Failed";
			}
	 	   catch (IOException e) {
	 		  return "Failed";
	 	    } 
	 	   catch (Exception e) {
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
	 			Toast.makeText(Signup_Login.this,
	 					"Check your network connection", Toast.LENGTH_LONG)
	 					.show();	
	 		}
	 		else{
	 		if(result.equalsIgnoreCase("")){
	 			Toast.makeText(Signup_Login.this, "Successfully Created", Toast.LENGTH_LONG).show();
	 			finish();
	 			
	 			
	 		}
	 		else{
	 			Toast.makeText(Signup_Login.this, result, Toast.LENGTH_LONG).show();
	 		}
	 	}
	 	}
	}
}
