package in.vbuy.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONfunctions {
	
	public static JSONObject getJSONfromURL(String url)
	{
		InputStream is = null;
	    JSONObject jObj = null;
	    String json = "";
		
	    try {

	    	// defaultHttpClient
	    	   DefaultHttpClient httpClient = new DefaultHttpClient();
	    	   HttpGet httpget= new HttpGet(url);
	    	   HttpResponse httpResponse = httpClient.execute(httpget);
	    	   HttpEntity httpEntity = httpResponse.getEntity();
	    	        is = httpEntity.getContent();

	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }

	    	    try {
	    	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
	    	        StringBuilder sb = new StringBuilder();
	    	        String line = null;
	    	        while ((line = reader.readLine()) != null) {
	    	            sb.append(line + "\n");
	    	        }
	    	        is.close();
	    	        json = sb.toString();
	    	    } catch (Exception e) {
	    	        }

	    	    try {
	    	        jObj = new JSONObject(json);
	    	    } catch (JSONException e) {
	    	        Log.e("JSON Parser", "Error parsing data " + e.toString());
	    	    }

		return jObj;
		
	}
	

}
