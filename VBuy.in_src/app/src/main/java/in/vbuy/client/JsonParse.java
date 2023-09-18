package in.vbuy.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonParse {
    public JsonParse(){}
  public List<GetProduct> getParseJsonWCF(String sname){
	List<GetProduct> list=new ArrayList<GetProduct>();
	try
	{
		String temp=sname.replace(" ", " ");
		URL url=new URL("http://staging.vbuy.in/api/Landing/GetSearchProductsFilter?searchString="+temp);
		URLConnection urlconection=url.openConnection();
		BufferedReader reader=new BufferedReader(new InputStreamReader(urlconection.getInputStream()));
		String line=reader.readLine();
		List<GetProduct> productModelArray = new ArrayList<GetProduct>();
		Gson gson=new Gson();
		productModelArray = gson.fromJson(line,new TypeToken<List<GetProduct>>(){}.getType());
		
		for(int i=0;i<productModelArray.size();i++)
		{
			GetProduct gp=new GetProduct();
			gp.setName(productModelArray.get(i).getName());
			gp.setProductId(productModelArray.get(i).getProductId());
			gp.setPictureName(productModelArray.get(i).getPictureName());
			list.add(gp);			
		}
	}catch(Exception e)
	{
	e.printStackTrace();
  }
	return list;
}
}