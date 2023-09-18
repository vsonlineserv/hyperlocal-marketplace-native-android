package in.vbuy.client.util;


import in.vbuy.client.cachetoken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HelperHttp {

	public static HttpClient httpclient;

	private static List<NameValuePair> buildNameValuePair(
			Hashtable<String, String> httpPost) {
		System.out.println("POST==>" + httpPost);
		List<NameValuePair> nvps;
		if (httpPost == null)
		{
			return null;
		}
		else{
		 nvps = new ArrayList<NameValuePair>();
		Enumeration<String> keys = httpPost.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = (String) httpPost.get(key);
			BasicNameValuePair nv = new BasicNameValuePair(key, value);
			nvps.add(nv);
		}
		}
		return nvps;
	}

	private static String buildGetUrl(List<NameValuePair> params, String url) {
		String paramString = URLEncodedUtils.format(params, "utf-8");
		if (!url.endsWith(""))
			url += "";

		url += paramString;
		return url;
	}

	public static DefaultHttpClient getThreadSafeClient() {
		if (httpclient != null)
			return (DefaultHttpClient) httpclient;
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 100);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		// Create and initialize scheme registry
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));

		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		httpclient = new DefaultHttpClient(cm, params);

		return (DefaultHttpClient) httpclient;
	}

	public static String getJSONResponseFromURL(String url,
			Hashtable<String, String> httpGetParams) {
		String json_string = "";
		String token = cachetoken.token.get("token");
		List<NameValuePair> nvps = buildNameValuePair(httpGetParams);
		url = buildGetUrl(nvps, url);
		System.out.println("URL==>" + url);
		InputStream is = null;
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Authorization",  "Bearer"+" "+token );
			HttpResponse response = getThreadSafeClient().execute(httpget);

			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is), 8192);
			String line = null;
			while ((line = reader.readLine()) != null) {
				json_string = json_string + line;
			}
			response.getEntity().consumeContent();
			System.out.println("Json Response==>" + json_string);
		}
		catch (ClientProtocolException e) {
			return null;
 	    } catch (HttpHostConnectException e){
	 		  return null;
 	    }catch (IOException e) {
 	    	return null;
 	    }
		catch (Exception e) {
			return null;
		}
		return json_string;
	}
	
	public static String getJSONFromUrl(String url,String method, JSONObject params) {
		InputStream is = null;
		System.out.println("URL==>" + url+params);
		String json;
		String token = cachetoken.token.get("token");
		JSONObject jObj=params;
        // Making HTTP request
        try {
            if (method == "POST") {
                // request method is POST
                /*// defaultHttpClient
            	HttpParams myParams = new BasicHttpParams();
		 	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		 	    HttpConnectionParams.setSoTimeout(myParams, 10000);*/
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                StringEntity se = new StringEntity( jObj.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(se);
                httpPost.addHeader("Authorization",  "Bearer"+" "+token );
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } else if (method == "GET") {
                
            }

        } catch (UnsupportedEncodingException e) {
        	return null;
        } catch (ClientProtocolException e) {
        	return null;
        }catch (HttpHostConnectException e){
	 		  return null;
 	    } catch (IOException e) {
        	return null;
        }
       
		catch (Exception e) {
			Log.e("log_tag", "Error in http connection" + e.toString());
			return null;
		}
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.d("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return null;
        }


        // return JSON String
        return json;

    }

}
