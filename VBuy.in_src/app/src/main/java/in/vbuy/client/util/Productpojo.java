package in.vbuy.client.util;



public class Productpojo {

	
	String id;
	String pid;
	String category;
	String description;
	String storeslist;
	String proname,imgurl,image,wish,wishlist;
	public Productpojo() {
		// TODO Auto-generated constructor stub
	}
	

	public Productpojo(String id, String pid, String proname, String imgurl,String price, String storeslist,String imagename,String wish) 
	{
		super();
		
		this.id=id;
		this.pid=pid;
		this.proname=proname;
		this.imgurl=imgurl;
		this.storeslist=storeslist;
		this.description=price;
		this.image=imagename;
		this.wish=wish;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getStoreslist() {
		return storeslist;
	}
	public void setStoreslist(String storeslist) {
		this.storeslist = storeslist;
	}
	public String getimage() {
		return image;
	}
	public void setimage(String image) {
		this.image = image;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	
	public String getwishlist() {
		return wishlist;
	}
	public void setwishlist(String wishlist) {
		this.wishlist = wishlist;
	}
	
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getwish() {
		return wish;
	}
	public void setwish(String wish) {
		this.wish = wish;
	}
	
	// Seller Get and Set Method
	
	public String storename;

    public String branchadd1;
    
    public String branchadd2;
    
    public String pricelist;
    
    public String specialprice;
    
    public String lat;
    
    public String lon;
    
    public String additional;
    
    public String time;

    public String branchid;
    
    public String parameter;
    
    public String keyfeature;
	
	public String getstorename() {
		return storename;
	}
	public void setstorename(String storename) {
		this.storename = storename;
	}
	public String getbranchadd1() {
		return branchadd1;
	}
	public void setbranchadd1(String branchadd1) {
		this.branchadd1 = branchadd1;
	}
	public String getbranchadd2() {
		return branchadd2;
	}
	public void setbranchadd2(String branchadd2) {
		this.branchadd2 = branchadd2;
	}
	public String getpricelist() {
		return pricelist;
	}
	public void setpricelist(String pricelist) {
		this.pricelist = pricelist;
	}
	public String getspecialprice() {
		return specialprice;
	}
	public void setspecialprice(String specialprice) {
		this.specialprice = specialprice;
	}
	public String getlat() {
		return lat;
	}
	public void setlat(String lat) {
		this.lat = lat;
	}
	public String getlon() {
		return lon;
	}
	public void setlon(String lon) {
		this.lon = lon;
	}
	public String getadditional() {
		return additional;
	}
	public void setaddition(String additional) {
		this.additional = additional;
	}
	public String gettime() {
		return time;
	}
	public void settime(String time) {
		this.time = time;
	}
	public String getbranchid() {
		return branchid;
	}
	public void setbranchid(String branchid) {
		this.branchid = branchid;
	}
	
	public String getparameter() {
		return parameter;
	}
	public void setparameter(String parameter) {
		this.parameter = parameter;
	}
	
	public String getkeyfeature() {
		return keyfeature;
	}
	public void setkeyfeature(String keyfeature) {
		this.keyfeature = keyfeature;
	}
	
	// Min and Max Price
	String minimum,maximum;
	public String getminimum() {
		return minimum;
	}
	public void setminimum(String minimum) {
		this.minimum = minimum;
	}
	public String getmaximum() {
		return maximum;
	}
	public void setmaximum(String maximum) {
		this.maximum = maximum;
	}
	
}
