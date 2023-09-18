package in.vbuy.client;

public class GetSetArea {
	String AreaName,City,Latitude,Longitude;
	public GetSetArea(String AreaName,String City,String Latitude,String Longitude)
	{
		this.AreaName=AreaName;
		this.City=City;
		this.Latitude=Latitude;
		this.Longitude=Longitude;
	}
public GetSetArea() {
	// TODO Auto-generated constructor stub
}
public String getAreaName()
{
	return AreaName;
}
public void setAreaName(String AreaName)
{
	this. AreaName=AreaName;
}
public String getCity()
{
	return City;
}
public void setCity(String City)
{
	this. City=City;
}
public String getLatitude(){
	return Latitude;
}
public void setLatitude(String Latitude)
{
	this.Latitude=Latitude;
}
public String getLongitude()
{
	return Longitude;
}
public void setLongitude(String Longitude)
{
	this.Longitude=Longitude;
}
}

