package in.vbuy.client;

import java.util.HashMap;

import android.util.Log;

public class cachetoken {
	public static	HashMap <String ,String>token =new HashMap<String ,String>();
	
	
	
	public static boolean addcategorydata(String itemName, String id) {
		boolean status = true;
		try {
		
				token.put(itemName, id);
		System.out.println(id);
			 

		} catch (Exception e) {
			Log.i(" Exception While adding the token", e.getMessage());
			status = false;
		}
		return status;
	}
	
}
