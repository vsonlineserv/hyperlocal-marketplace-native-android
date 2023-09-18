package in.vbuy.client;

import in.vbuy.client.util.DirectionsJSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapActivity extends FragmentActivity {

	GoogleMap map;
	RadioButton rbDriving;
	RadioButton rbWalking;
	RadioGroup rgModes;
	TextView storenametext,productnametext,pricetext,address1,address2;
	ArrayList<LatLng> markerPoints;
	PolylineOptions lineOptions = null;
	int mMode=0;
	final int MODE_DRIVING=0;
	final int MODE_BICYCLING=1;
	final int MODE_WALKING=2;
	Double	latitude1;
	Double	longitude1;
	Double	latitude2;
	Double	longitude2;
	 TextView tvDistanceDuration;
	String storename,mylat,mylon,storelat,storelon,storeaddress1,storeaddress2,productname,price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.map);
		storenametext=(TextView)findViewById(R.id.shopname);
		productnametext=(TextView)findViewById(R.id.productname);
		pricetext=(TextView)findViewById(R.id.shopspecialprice);
		address1=(TextView)findViewById(R.id.branchaddress1);
		address2=(TextView)findViewById(R.id.branchaddress2);
		tvDistanceDuration = (TextView) findViewById(R.id.tv_distance_time);
		Intent in = getIntent();
		storename = in.getStringExtra("storename");
		mylat = in.getStringExtra("mylatitude");
		mylon = in.getStringExtra("mylongitude");
		storelat = in.getStringExtra("storelatitude");
		storelon = in.getStringExtra("storelongitude");
		productname = in.getStringExtra("productname");
		storeaddress1 = in.getStringExtra("storeaddress1");
		storeaddress2 = in.getStringExtra("storeaddress2");
		price = in.getStringExtra("price");
		storenametext.setText(storename);
		productnametext.setText(productname);
		pricetext.setText(pricetext.getText()+price);
		address1.setText(storeaddress1);
		address2.setText(storeaddress2);
		// Getting reference to rb_driving
		rbDriving = (RadioButton) findViewById(R.id.rb_driving);
		
		
		// Getting reference to rb_walking
		rbWalking = (RadioButton) findViewById(R.id.rb_walking);
		
		// Getting Reference to rg_modes
		rgModes = (RadioGroup) findViewById(R.id.rg_modes);
			latitude1 = Double.parseDouble(mylat);
			longitude1 = Double.parseDouble(mylon);
			latitude2 = Double.parseDouble(storelat);
			longitude2 = Double.parseDouble(storelon);
		rgModes.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
		
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {				
				
				// Checks, whether start and end locations are captured
				lineOptions = null;
					
					// Getting URL to the Google Directions API
					String url = getDirectionsUrl(latitude1, latitude2,longitude1,longitude2);			
					
					DownloadTask downloadTask = new DownloadTask();
					
					// Start downloading json data from Google Directions API
					downloadTask.execute(url);
			}
		});
		
	
		
		// Initializing 
		markerPoints = new ArrayList<LatLng>();
		
		// Getting reference to SupportMapFragment of the activity_main
		SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
		
		// Getting Map for the SupportMapFragment
	//	map = fm.getMap();
		
		// Enable MyLocation Button in the Map
		map.setMyLocationEnabled(true);		
		
		/*map.addMarker(new MarkerOptions()
        .flat(true)
        .icon(BitmapDescriptorFactory
                .fromResource(R.drawable.ic_launcher))
        .anchor(0.5f, 0.5f)
        .position(new LatLng(latitude1, longitude1)).title("My Postion"));*/
			MarkerOptions marker1 = new MarkerOptions().position(new LatLng(latitude1, longitude1)).icon(BitmapDescriptorFactory
	                .fromResource(R.drawable.vbuy_map)).title("My Position");
			MarkerOptions marker2 = new MarkerOptions().position(new LatLng(latitude2, longitude2)).title(storename);
			// adding marker
			map.addMarker(marker1);
			map.addMarker(marker2);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude1, longitude1),13));
			//map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude1, longitude1), 15.0f));
			map.getUiSettings().setZoomControlsEnabled(true);
			// Getting URL to the Google Directions API
			String url = getDirectionsUrl(latitude1, latitude2,longitude1,longitude2);				
			
			DownloadTask downloadTask = new DownloadTask();
			
			// Start downloading json data from Google Directions API
			downloadTask.execute(url);
		
	}	
	
	
	private String getDirectionsUrl(Double latitute1,Double latitude2,Double longitude1,Double longitude2){
					
		// Origin of route
		String str_origin = "origin="+latitute1+","+longitude1;
		
		// Destination of route
		String str_dest = "destination="+latitude2+","+longitude2;	
					
		// Sensor enabled
		String sensor = "sensor=false";			
		
		// Travelling Mode
		String mode = "mode=driving";	
		
		if(rbDriving.isChecked()){
			mode = "mode=driving";
			mMode = 0 ;
		
		}else if(rbWalking.isChecked()){
			mode = "mode=walking";
			mMode = 2;
		}
		
					
		// Building the parameters to the web service
		String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;
					
		// Output format
		String output = "json";
		
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
		
		
		return url;
	}
	
	/** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url 
                urlConnection.connect();

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }
                
                data = sb.toString();

                br.close();

        }catch(Exception e){
               // Log.d("Exception while downloading url", e.toString());
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }
        return data;
     }

	
	
	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String>{			
				
		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {
				
			// For storing data from web service
			String data = "";
					
			try{
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			}catch(Exception e){
				Log.d("Background Task",e.toString());
			}
			return data;		
		}
		
		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);			
			
			ParserTask parserTask = new ParserTask();
			
			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
				
		}		
	}
	
	/** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
    	
    	// Parsing the data in non-ui thread    	
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
			
			JSONObject jObject;	
			List<List<HashMap<String, String>>> routes = null;			           
            
            try{
            	jObject = new JSONObject(jsonData[0]);
            	DirectionsJSONParser parser = new DirectionsJSONParser();
            	
            	// Starts parsing data
            	routes = parser.parse(jObject);    
            }catch(Exception e){
            	e.printStackTrace();
            }
            return routes;
		}
		
		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			MarkerOptions markerOptions = new MarkerOptions();
			 String distance = "";
	            String duration = "";
			// Traversing through all the routes
			 for(int i=0;i<result.size();i++){
	                points = new ArrayList<LatLng>();
	                lineOptions = new PolylineOptions();
	 
	                // Fetching i-th route
	                List<HashMap<String, String>> path = result.get(i);
	 
	                // Fetching all the points in i-th route
				for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
 
                    if(j==0){    // Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }
 
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
 
                    points.add(position);
                }
 
				
				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(3);
				
				// Changing the color polyline according to the mode
				if(mMode==MODE_DRIVING)
					lineOptions.color(Color.RED);
				else if(mMode==MODE_BICYCLING)
					lineOptions.color(Color.GREEN);
				else if(mMode==MODE_WALKING)
					lineOptions.color(Color.BLUE);				
			}
			
			if(result.size()<1){
				Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
				return;
			}
			 tvDistanceDuration.setText("Distance:"+distance + ", Duration:"+duration);
			 
			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);
			
		}			
    }   
    
		
}