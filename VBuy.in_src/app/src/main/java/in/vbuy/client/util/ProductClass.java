package in.vbuy.client.util;

public class ProductClass {
	private String storename;
	
	private Double km;

	private String branchadd1;
    
    private String branchadd2;
    
    private String pricelist;
    
    private String specialprice;
    
    private String lat;
    
    private String lon;
    
    private String additional;
    
    private String time;

    private String branchid;
    
    private String parameter;
    
   
		
	public ProductClass() {
		// TODO Auto-generated constructor stub
	}

	public ProductClass(Double km, String storename, String branchadd1, String branchadd2, String pricelist,
			String specialprice,String lat,String lon,String additional,String time, String branchid) 
	{
		super();
		
		this.storename=storename;
		this.branchadd1=branchadd1;
		this.branchadd2=branchadd2;
		this.pricelist=pricelist;
		this.specialprice=specialprice;
		this.lat=lat;
		this.lon=lon;
		this.additional=additional;
		this.time=time;
		this.branchid=branchid;
		this.parameter=parameter;
		this.km=km;
		
	}
	public Double getkm() {
		return km;
	}
	public void setkm(Double km) {
		this.km = km;
	}
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
	
	}

