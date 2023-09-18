package in.vbuy.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AreaHttpConection {

    public AreaHttpConection(){}
  public List<GetSetArea> getParseJsonWCF(String sname){
	List<GetSetArea> list=new ArrayList<GetSetArea>();

	try
	{
		String temp=sname.replace(" ", " ");
		URL url=new URL("http://staging.vbuy.in/api/Landing/GetSearchAreaFilter?City=th"+temp);
		URLConnection urlconection=url.openConnection();
		BufferedReader reader=new BufferedReader(new InputStreamReader(urlconection.getInputStream()));
		String line=reader.readLine();
		List<GetSetArea> productModelArray = new ArrayList<GetSetArea>();
		Gson gson=new Gson();
		productModelArray = gson.fromJson(line,new TypeToken<List<GetSetArea>>(){}.getType());
		
		for(int i=0;i<productModelArray.size();i++)
		{
			GetSetArea gp=new GetSetArea();
			gp.setAreaName(productModelArray.get(i).getAreaName());
			gp.setLatitude(productModelArray.get(i).getLatitude());
			gp.setLongitude(productModelArray.get(i).getLongitude());
			list.add(gp);
		}
	}catch(Exception e)
	{
	e.printStackTrace();
  }
	return list;
}
}