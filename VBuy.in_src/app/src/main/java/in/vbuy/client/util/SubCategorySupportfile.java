package in.vbuy.client.util;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

public class SubCategorySupportfile {
	
	public static	HashMap <String ,Integer>position =new HashMap<String ,Integer>();
	
	public static	HashMap <String ,Integer>sort =new HashMap<String ,Integer>();
	
	public static ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	
	public static ArrayList<HashMap<String, String>> mybrand = new ArrayList<HashMap<String, String>>();
	
	public static	HashMap <String ,Integer>filterprice =new HashMap<String ,Integer>();
	
	public static	HashMap <String ,String>filterid =new HashMap<String ,String>();
	
	public static	HashMap <String ,String>filterhash =new HashMap<String ,String>();
	
	public static	HashMap <String ,ArrayList<String>>Categorydata =new HashMap<String ,ArrayList<String>>();
	
	public static	HashMap <String ,ArrayList<String>>filter =new HashMap<String ,ArrayList<String>>();
	
	public static	HashMap <String ,ArrayList<Productpojo>>productdetials =new HashMap<String ,ArrayList<Productpojo>>();
	
	
	public static boolean addprice(int min,  int max) {
		boolean status = true;
		try {
		
			

				filterprice.put("min", min);
				filterprice.put("max", max);
				
		System.out.println(filterprice);
			

		} catch (Exception e) {
			Log.i(" Exception While adding the mylist", e.getMessage());
			status = false;
		}
		return status;
	}
	
	public static boolean addmylist(HashMap<String, String> map) {
		boolean status = true;
		try {
		
			if (map.size()>0) {

				mylist.add(map);
				
		System.out.println(mylist);
			} else { // update the quantity with the existing quantity
				
				Log.i("New value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the mylist", e.getMessage());
			status = false;
		}
		return status;
	}
	
	public static boolean addmybrand(HashMap<String, String> brand) {
		boolean status = true;
		try {
		
			if (brand.size()>0) {

				mybrand.add(brand);
				
		System.out.println(mybrand);
			} else { // update the quantity with the existing quantity
				
				Log.i("New value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the mylist", e.getMessage());
			status = false;
		}
		return status;
	}
	

	public static boolean addfilterid(String productname, String productid, String search) {
		boolean status = true;
		try {
		
			if (productname.length()>0) {
				filterid.put("productname",  productname);
				filterid.put("productid",  productid);
				filterid.put("search",  search);
		System.out.println(filterid);
			} else { // update the quantity with the existing quantity
				
				Log.i("New value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the product", e.getMessage());
			status = false;
		}
		return status;
	}
	
	public static boolean addfilterhash(String name, String filter) {
		boolean status = true;
		try {
		
			if (name.length()>0) {
				filterhash.put(name,  filter);
		System.out.println(filterhash);
			} else { // update the quantity with the existing quantity
				
				Log.i("New value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the product", e.getMessage());
			status = false;
		}
		return status;
	}
	
	public static boolean addsort(String sortname, int sortid) {
		boolean status = true;
		try {
		
			if (sortname.length()>0) {
				sort.put("sort",  sortid);
		System.out.println(sort);
			} else { // update the quantity with the existing quantity
				
				Log.i("New value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the product", e.getMessage());
			status = false;
		}
		return status;
	}
	
	
	public static boolean addposition(String sortname, int sortid) {
		boolean status = true;
		try {
		
			if (sortname.length()>0) {
				position.put("position",  sortid);
		System.out.println(position);
			} else { // update the quantity with the existing quantity
				
				Log.i("New value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the product", e.getMessage());
			status = false;
		}
		return status;
	}
	
	
	
	
	public static boolean addcategorydata(String itemName, List<String> product1) {
		boolean status = true;
		try {
		
			if (product1.size()>0) {
				Categorydata.put(itemName,  new ArrayList<String>(product1));
		System.out.println(Categorydata);
			} else { // update the quantity with the existing quantity
				
				Log.i("New value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the product", e.getMessage());
			status = false;
		}
		return status;
	}
	
	public static boolean addfilter(String itemName, List<String> filters) {
		boolean status = true;
		try {
		
			if (filters.size()>0) {
				filter.put(itemName,  new ArrayList<String>(filters));
		System.out.println(filter);
			} else { // update the quantity with the existing quantity
				
				Log.i("Filter value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the Filter product", e.getMessage());
			status = false;
		}
		return status;
	}
	
	public static boolean addProducts(String itemName, ArrayList<Productpojo> product2) {
		boolean status = true;
		try {
		
			if (product2.size()>0) {
				productdetials.put(itemName, product2);
		
			} else { // update the quantity with the existing quantity
				
				Log.i("New value for","error" );
			}

		} catch (Exception e) {
			Log.i(" Exception While adding the product", e.getMessage());
			status = false;
		}
		return status;
	}
	
	
	
}
