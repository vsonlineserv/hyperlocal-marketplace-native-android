package in.vbuy.client;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class PayuActivity extends ActionBarActivity{
	WebView webView;
	 Handler mHandler = new Handler();
	 private Toolbar toolbar;
	 private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		   super.onCreate(savedInstanceState);
	       setContentView(R.layout.payu_lay);
	       
	       toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
	     //toolbartitle = (TextView) findViewById(R.id.titletool);

	       setSupportActionBar(toolbar);
	       getSupportActionBar().setDisplayShowTitleEnabled(false);
	       
	       
	       toolbar.setLogo(R.drawable.vbuy);
	       toolbar.setNavigationIcon(R.drawable.arrow5);
	       
	            progressDialog = new ProgressDialog(PayuActivity.this);
	            progressDialog.setMessage("Loading...");
	            progressDialog.show();
	       
	     toolbar.setNavigationOnClickListener(new OnClickListener() {
	  	
	  	@Override
	  	public void onClick(View v) {
	  		// TODO Auto-generated method stub
	  		onBackPressed();
	  	}
	  });
	     
	       webView = (WebView)this.findViewById(R.id.myWebView);
	      // webView = new WebView(this);
	      
	       
	       Intent in=getIntent();
			String html=((in.getStringExtra("address")));
			html=html.replace("\\", "");
			String mime = "text/html";
			String encoding = "utf-8";
	    	webView.setVisibility(0);
	    	webView.getSettings().setBuiltInZoomControls(true);
	    	webView.getSettings().setCacheMode(2);
	    	webView.getSettings().setDomStorageEnabled(true);
	    	webView.clearHistory();
	    	webView.clearCache(true);
	      	 webView.getSettings().setJavaScriptEnabled(true);
	      	 webView.getSettings().setSupportZoom(true);
	      	 webView.getSettings().setUseWideViewPort(false);
	      	 webView.getSettings().setLoadWithOverviewMode(false);
	      	 webView.getSettings().setDomStorageEnabled(true);
	      	 webView.loadDataWithBaseURL(null, html, mime, encoding, null);
	         webView.setWebViewClient(new myWebClient());
	      	 webView.loadUrl("javascript:document.getElementById('payuForm').submit();");
	      

	}
	
	public class myWebClient extends WebViewClient {
		
      //  https://www.payumoney.com/txn/mobile/#/user
	    @Override
	    public void onPageStarted(WebView view, String url, Bitmap favicon) {
	        // TODO Auto-generated method stub
	        
	        Log.d("URL Finding", url);
	        boolean pb = url.contains("https://www.payumoney.com/txn/mobile/#/user");
	        if(pb){
	        	 if (progressDialog.isShowing()) {
	                 progressDialog.dismiss();
	                
	             }
	        }
	        
	        boolean result = url.contains("confirmOrder");
	        if(result){
	        	  String[] namestring=url.split("/");
	              String order_id=namestring[namestring.length-1];
	              Log.d("Order Id",order_id );
	        	Intent intent = new Intent(PayuActivity.this, confirmPageActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            	intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	            intent.putExtra("id", order_id);
	            startActivity(intent);

	            finish();
	        }
	        super.onPageStarted(view, url, favicon);
	    }

	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        // TODO Auto-generated method stub

	        view.loadUrl(url);
	        return true;

	    }
	}
	 public void onBackPressed() {
	        //Display alert message when back button has been pressed
	        backButtonHandler();		
	        return;
	    }
	 
	    public void backButtonHandler() {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
	                PayuActivity.this);
	        // Setting Dialog Title
	        alertDialog.setTitle("Leave PayU Money?");
	        // Setting Dialog Message
	        alertDialog.setMessage("Are you sure you want to leave the PayU Money?");
	        // Setting Icon to Dialog
	        alertDialog.setIcon(R.drawable.icon_dialog);
	        // Setting Positive "Yes" Button
	        alertDialog.setPositiveButton("YES",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                    	Intent intent = new Intent(getApplicationContext(), CategoryProductActivity.class);
	                    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);                	
	                    	startActivity(intent);
	                    	
	                    }
	                });
	        // Setting Negative "NO" Button
	        alertDialog.setNegativeButton("NO",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        // Write your code here to invoke NO event
	                        dialog.cancel();
	                    }
	                });
	        // Showing Alert Message
	        alertDialog.show();
	    }
}
