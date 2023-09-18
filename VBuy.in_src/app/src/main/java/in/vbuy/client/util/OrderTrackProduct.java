package in.vbuy.client.util;

public class OrderTrackProduct {
	
    
    private String ordernumber;
    
    private String orderdate;
    
    private String totalorder;
    
    private String quantity;

    private String orderstatus;
    
    private String payment;
    
   
		
	public OrderTrackProduct() {
		// TODO Auto-generated constructor stub
	}

	public OrderTrackProduct(String ordernumber,String orderdate,String totalorder,String quantity, String orderstatus,String payment) 
	{
		super();
		
		
		this.ordernumber=ordernumber;
		this.orderdate=orderdate;
		this.totalorder=totalorder;
		this.quantity=quantity;
		this.orderstatus=orderstatus;
		this.payment=payment;
		
	}
	
	
	public String getordernumber() {
		return ordernumber;
	}
	public void setordernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getorderdate() {
		return orderdate;
	}
	public void setorderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String gettotalorder() {
		return totalorder;
	}
	public void setaddition(String totalorder) {
		this.totalorder = totalorder;
	}
	public String getquantity() {
		return quantity;
	}
	public void setquantity(String quantity) {
		this.quantity = quantity;
	}
	public String getorderstatus() {
		return orderstatus;
	}
	public void setorderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	
	public String getpayment() {
		return payment;
	}
	public void setpayment(String payment) {
		this.payment = payment;
	}
	
	}

