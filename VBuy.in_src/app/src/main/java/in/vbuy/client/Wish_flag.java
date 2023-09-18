package in.vbuy.client;

import java.util.HashMap;

import android.util.Log;

public class Wish_flag {
	public static	HashMap <String ,String>feature =new HashMap<String ,String>();
	public static	HashMap <String ,String>product =new HashMap<String ,String>();
	
	
	public static boolean flag_features(String flag, String id) {
		boolean status = true;
		try {
		
			feature.put(flag, id);
		System.out.println(id);
			 

		} catch (Exception e) {
			Log.i(" Exception While adding the token", e.getMessage());
			status = false;
		}
		return status;
	}
	public static boolean flag_products(String flag, String id) {
		boolean status = true;
		try {
		
			product.put(flag, id);
		System.out.println(id);
			 

		} catch (Exception e) {
			Log.i(" Exception While adding the token", e.getMessage());
			status = false;
		}
		return status;
	}
	
}
