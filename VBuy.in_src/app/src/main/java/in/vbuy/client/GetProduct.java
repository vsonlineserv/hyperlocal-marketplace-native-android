package in.vbuy.client;

public class GetProduct {
	String ProductId,Name,PictureName;
	public GetProduct(String ProductId,String Name,String PictureName)
	{
		this.ProductId=ProductId;
		this.Name=Name;
		this.PictureName=PictureName;
	}
public GetProduct() {
	// TODO Auto-generated constructor stub
}
public String getProductId()
{
	return ProductId;
}
public void setProductId(String ProductId)
{
	this.ProductId=ProductId;
}
public String getName(){
	return Name;
}
public void setName(String Name)
{
	this.Name=Name;
}
public String getPictureName(){
	return PictureName;
}
public void setPictureName(String PictureName)
{
	this.PictureName=PictureName;
}
}

