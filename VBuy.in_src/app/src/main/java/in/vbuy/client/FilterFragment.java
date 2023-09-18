package in.vbuy.client;
import in.vbuy.client.util.Categorypojo;
import in.vbuy.client.util.FilterMainAdapter;
import in.vbuy.client.util.HelperHttp;
import in.vbuy.client.util.SubCategorySupportfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FilterFragment extends ListFragment {
ArrayList<String> filtersname = new ArrayList<String>();
ArrayList<String> itemnameadapter = new ArrayList<String>();
ArrayList<String> splited = new ArrayList<String>();
ArrayList<String> groupvalue = new ArrayList<String>();
ArrayList<String> defaultValue = new ArrayList<String>();
ArrayList<String> finalArraylist = new ArrayList<String>();
ArrayList<String> childarray = new ArrayList<String>();
ArrayList<String> brandnameArray = new ArrayList<String>();
ArrayList<String> brandidArray = new ArrayList<String>();
private ArrayList<String> parentItems = new ArrayList<String>();
private ArrayList<String> childstringarray = new ArrayList<String>();
private ArrayList<Object> childItems = new ArrayList<Object>();
private static final String View =null;
View previous;
FilterMainAdapter fmp;
public String[] filtername;
public String[] splitarray;
public String[] brandnameString;
public String[] brandidstring;
String minijson,maxjson;
int minint,maxint;
String productid,productname,search;
Context context = null;
	public FilterFragment(){}
	
    @Override
    
    public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle savedInstanceState) {
    	productname=SubCategorySupportfile.filterid.get("productname");
    	productid=SubCategorySupportfile.filterid.get("productid");
    	search=SubCategorySupportfile.filterid.get("search");
    	previous = new View(getActivity());
        
    	try{
    		
    				if (isNetworkAvailable())
    				{
    					executeAsyncTask();
    				}
    				else {
    					
    					// if false show msg
    					showToast("No Network Connection!!!");
    				}
    		
    		
    		}
    		catch(Exception e)
    		{
    			Log.d("error",""+e.toString());
    		}
    		

    	return  inflater.inflate(R.layout.filter_fragment, viewGroup, false);
        
    }
    
    private void executeAsyncTask() {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		GetDeptAyncTask async = new GetDeptAyncTask();
		// ht.put("company_id", "1");
		Hashtable[] ht_array = { ht };
		async.execute(ht_array);
	}
	private   class GetDeptAyncTask extends
	AsyncTask<Hashtable<String, String>, Void, String> {

public   final ProgressDialog progressDialog = new ProgressDialog(getActivity());

protected void onPreExecute() {
	progressDialog.setCancelable(false);
	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	progressDialog.setIndeterminate(true);
	progressDialog.setMessage("Loading, please wait few seconds");
	progressDialog.setTitle("Connecting with VBuy.in");
	progressDialog.show();
}

protected String doInBackground(Hashtable<String, String>... params)

{
@SuppressWarnings("rawtypes")
Hashtable ht = params[0];
String urladdress;
String filterall;
String json;
String price_brand;
if(SubCategorySupportfile.filterhash.size()>0)
{
	json =SubCategorySupportfile.filterhash.get("filter");
	price_brand=SubCategorySupportfile.filterhash.get("brand");
	parseJsonString(json , price_brand);
	}
else{
if(productid !=null){
	urladdress=getString(R.string.pricerate)+productid;	
	filterall=getString(R.string.productidfilter)+productid;
	 }
	 else{
		 urladdress=getString(R.string.search_pricerate)+search;
	    filterall=getString(R.string.search_pricerate)+search;
		  
	 }
	 price_brand = HelperHttp.getJSONResponseFromURL(urladdress
			, ht);
	
  json =HelperHttp.getJSONResponseFromURL(filterall, ht); 
 
	System.out.println(json);

	if (json != null && price_brand !=null ){
		 SubCategorySupportfile.addfilterhash("brand", price_brand);
		 SubCategorySupportfile.addfilterhash("filter", json);
		parseJsonString(json , price_brand);
	}
	else {
		return"Failed";
	}
	
}
	return "SUCCESS";
}


protected void parseJsonString(String json , String price_brand) {
	try {
		
		JSONObject jsonobj=new JSONObject(price_brand);
		minijson=jsonobj.optString("Min", "");
		maxjson=jsonobj.optString("Max", "");
		Log.d("Maximum Price", jsonobj.optString("Max", ""));
		
		JSONArray subarray=jsonobj.getJSONArray("Brand");
		for (int k = 0; k <= subarray.length() - 1; k++) {
			JSONObject s = subarray.getJSONObject(k);
			String brandname,id;
			brandname=s.optString("BrandName", "");
			id=s.optString("Id", "");
			brandnameArray.add(brandname);
			brandidArray.add(id);
		}
		double maxans = Double.parseDouble(maxjson);
		double minians = Double.parseDouble(minijson);
		minint=(int)minians;
		maxint=(int)maxans;
		if(SubCategorySupportfile.filterprice.size()>0)
		{}
		else
		{
			 SubCategorySupportfile.addprice(minint,maxint);
		}
		
		JSONArray array = new JSONArray(json);
		Categorypojo cpojo= new Categorypojo();
		for (int i = 0; i <= array.length() - 1; i++) {
			JSONObject j = array.getJSONObject(i);
			
			String FilterParameter,FilterValueText;
			String  temp;
			
			FilterParameter=j.optString("FilterParameter", "");
			FilterValueText=j.optString("FilterValueText", "");
			
			
			
			temp=FilterParameter+"~"+FilterValueText;
			filtersname.add(temp);
			
		}

		
		
		
		boolean status = SubCategorySupportfile.addcategorydata(
				"filter", filtersname);

		if (status) {

			Log.d("Added to hash map ", "added Ok");
		} else {
			Log.d("Added to hash map ", "not add to hashmap ");
		}
		System.out.println(SubCategorySupportfile.Categorydata);
	} catch (JSONException e) {
		e.printStackTrace();
	}

}

protected void onPostExecute(String result) {

	progressDialog.dismiss();
	if(result.equalsIgnoreCase("SUCCESS")){
getfilterdetials();
ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
       R.layout.filter_main_row, R.id.filter_main, filtername);
setListAdapter(adapter);
ListView lv = getListView();

position(0);


lv.setOnItemClickListener(new OnItemClickListener() {          

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		SubCategorySupportfile.addposition("position", position);
		 previous.setBackgroundResource(0);
		arg1.setBackgroundResource(R.color.pressed_color);
        previous=arg1;
		if(position==0){
			
			FilterOutputFragment txt = (FilterOutputFragment)getFragmentManager().findFragmentById(R.id.output);
	        txt.price(minint,maxint);
		}
		else if(position==1){
			FilterOutputFragment txt = (FilterOutputFragment)getFragmentManager().findFragmentById(R.id.output);
	        txt.brandFilter(brandnameString,brandidstring, filtername[position]);
		}
		else if(position>1){
		 childstringarray = (ArrayList<String>) childItems.get(position-2);
		String[] childstring = childstringarray.toArray(new String[0]);
		// TODO Auto-generated method stub
		FilterOutputFragment txt = (FilterOutputFragment)getFragmentManager().findFragmentById(R.id.output);
        txt.display(childstring,filtername[position]);
        
		}
		
	}
	
});
	}
	else{
		Toast.makeText(getActivity(),
					"Check your network connection", Toast.LENGTH_LONG)
					.show();
	}
}
	}
	
	
	public void position(int position){
		SubCategorySupportfile.addposition("position", position);
		if(position==0){
			
			FilterOutputFragment txt = (FilterOutputFragment)getFragmentManager().findFragmentById(R.id.output);
		    txt.price(minint,maxint);
		}
		else if(position==1){
			FilterOutputFragment txt = (FilterOutputFragment)getFragmentManager().findFragmentById(R.id.output);
		    txt.brandFilter(brandnameString,brandidstring, filtername[position]);
		}
		else if(position>1){
		 childstringarray = (ArrayList<String>) childItems.get(position-2);
		String[] childstring = childstringarray.toArray(new String[0]);
		// TODO Auto-generated method stub
		FilterOutputFragment txt = (FilterOutputFragment)getFragmentManager().findFragmentById(R.id.output);
		txt.display(childstring,filtername[position]);

		}	
	}
	
	
	
	public static Set<String> sortList (List<String> myList){
	    Set<String> hashsetList = new HashSet<String>(myList);

	    Set<String> treesetList = new TreeSet<String>(myList);
		return treesetList;
	}
	public void getfilterdetials() {
		filtername = null;
		splitarray = null;
if(filtersname.size()>0){
		itemnameadapter = SubCategorySupportfile.Categorydata.get("filter");
		
	     
		for (int i=0;i<itemnameadapter.size();i++){
		String listvalue=	itemnameadapter.get(i);
		String[]splittemp= listvalue.split("~");
		splited.add(splittemp[0]);
		}
		groupvalue=new ArrayList<String> (sortList(splited));
		//ArrayList<String> groupvalue.get(0) = new ArrayList<String>();
		
		

		for(int j=0;j<groupvalue.size();j++){
			 parentItems.add(groupvalue.get(j));
			 ArrayList<String> child = new ArrayList<String>();

		//items.add(new SectionItem(groupvalue.get(j)));
		for (int i=0;i<itemnameadapter.size();i++){
			String listvalue=	itemnameadapter.get(i);
			String[]splittemp= listvalue.split("~");
			if(groupvalue.get(j).equalsIgnoreCase(splittemp[0])){
				child.add(splittemp[1]);	
				
	   // items.add(new EntryItem(splittemp[0], splittemp[1]));
			}
			
		}
		childItems.add(child);

		}
		defaultValue.add("Price");
		defaultValue.add("Brand");
		finalArraylist.addAll(defaultValue);
		finalArraylist.addAll(groupvalue);
	}
	else{
		defaultValue.add("Price");
		defaultValue.add("Brand");
		finalArraylist.addAll(defaultValue);
	}
		filtername = finalArraylist.toArray(new String[0]);
		brandnameString = brandnameArray.toArray(new String[0]);
		brandidstring = brandidArray.toArray(new String[0]);
			
	}
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
		Toast.makeText(this.getActivity(), msg, Toast.LENGTH_LONG).show();
		this.getActivity().finish();
	}

}