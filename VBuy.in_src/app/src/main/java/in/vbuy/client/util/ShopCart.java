package in.vbuy.client.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;

public class ShopCart {
	public static final String PREFS_NAME = "PRODUCT_APP";
	public static final String FAVORITES = "Product_cart";
	static SharedPreferences settings;
	static Editor editor;

	public static void saveFavorites(Context context, List<Product> favorites) {
		SharedPreferences settings;
		Editor editor;

		settings = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		editor = settings.edit();

		Gson gson = new Gson();
		String jsonFavorites = gson.toJson(favorites);

		editor.putString(FAVORITES, jsonFavorites);
	
		editor.commit();
	}
	
	public static boolean addProduct(Context context,String branchid,String productid, Product product) {
		boolean status = true;
		try {
			
			List<Product> favorites = getFavorites(context);
			if (favorites == null)
				favorites = new ArrayList<Product>();
			int len=favorites.size();
			String name="";
			for(int i=0;i<len; i++){
				
				if (favorites.get(i).getbranchid().equalsIgnoreCase(branchid) && favorites.get(i).getproductId().equalsIgnoreCase(productid)) {
					favorites.get(i).setQty(favorites.get(i).getQty()+1);
					name="fill";
				}
			
			
			}
			if(name.equalsIgnoreCase("")){
			favorites.add(product);
			}
			saveFavorites(context, favorites);

		} catch (Exception e) {
			Log.i(" Exception While adding the product", e.getMessage());
			status = false;
		}
		return status;
	}

	public void updateProduct(Context context,String itemName,int qua) {
		
		List<Product> favorites = getFavorites(context);
		if (favorites == null)
			favorites = new ArrayList<Product>();
		int len=favorites.size();
		for(int i=0;i<len; i++){
			
			if (favorites.get(i).getItemName().equalsIgnoreCase(itemName)) {
				favorites.get(i).setQty(qua);
				
			}
		
		
		}
		
		saveFavorites(context, favorites);

		
	}
	public static ArrayList<Product> getFavorites(Context context) {
		SharedPreferences settings;
		List<Product> favorites;

		settings = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		if (settings.contains(FAVORITES)) {
			String jsonFavorites = settings.getString(FAVORITES, null);
			Gson gson = new Gson();
			Product[] favoriteItems = gson.fromJson(jsonFavorites,
					Product[].class);

			favorites = Arrays.asList(favoriteItems);
			favorites = new ArrayList<Product>(favorites);
			
		} else
			return null;

		return (ArrayList<Product>) favorites;
	}
	
	public void removeFavorite(Context context, Product product) {
		ArrayList<Product> favorites = getFavorites(context);
		if (favorites != null) {
			favorites.remove(product);
			
			saveFavorites(context, favorites);
		}
	
	}
	public void deleteProduct(Context context,String itemName,String storeid) {
		ArrayList<Product> favorites = getFavorites(context);
		ArrayList<Product> favorites1 = getFavorites(context);
		if (favorites != null) {
			int len=favorites.size();
			for(int i=0;i<len; i++){
		
			if (favorites.get(i).getItemName().equalsIgnoreCase(itemName)&& favorites.get(i).getbranchid().equalsIgnoreCase(storeid)) {
			favorites1.remove(i);
			Log.d("Deleted", itemName);
			saveFavorites(context, favorites1);
			}
		}
	}
	}
	public static boolean checkproduct (Context context){
		Boolean result=true;
		List<Product> favorites = getFavorites(context);
		if (favorites != null) {
			result=true;
		}
		else{
			result= false;
		}
		
		return result;
	}
	
public static void clearall(Context context) {
		
		List<Product> favorites = getFavorites(context);
		
		
		favorites.clear();
		
		
		saveFavorites(context, favorites);

		
	}
	
	public static double totalcart(Context context) {
		double val = 0;
		ArrayList<Product> favorites = getFavorites(context);
		if (favorites != null) {
		int len=favorites.size();
		for(int i=0;i<len; i++){
			
			val=val+(favorites.get(i).getPrice()*favorites.get(i).getQty());
		}
		}
		else{
			val=0;
		}
		return val;
	}
	
	public static double totalshipping(Context context) {
		double val = 0;
		ArrayList<Product> favorites = getFavorites(context);
		if (favorites != null) {
		int len=favorites.size();
		for(int i=0;i<len; i++){
			
			val=val+(favorites.get(i).getItemDesc()*favorites.get(i).getQty());
		}
		}
		else{
			val=0;
		}
		return val;
	}
	
	public static double totalValue(Context context) {
		double val = 0;
		ArrayList<Product> favorites = getFavorites(context);
		if (favorites != null) {
		int len=favorites.size();
		for(int i=0;i<len; i++){
			
			val=val+(favorites.get(i).getPrice()*favorites.get(i).getQty())+(favorites.get(i).getItemDesc()*favorites.get(i).getQty());
		}
		}
		else{
			val=0;
		}
		return val;
	}
	public static int totalQuantity(Context context) {
		int val = 0;
		ArrayList<Product> favorites = getFavorites(context);
		if (favorites != null) {
			int len=favorites.size();
			val=len;
		}
		else{
			val=0;
		}
		return val;
	}

}
