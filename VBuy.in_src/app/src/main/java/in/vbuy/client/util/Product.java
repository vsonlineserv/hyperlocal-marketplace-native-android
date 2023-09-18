package in.vbuy.client.util;

public class Product {

	private String imageId;
	private String itemName;
	private double itemDesc;
	private String Id;
	private String storeId;
	private String customerId;
	private String productId;
	private String branchid;
	private String branch;
	private double unitprice;
	private double price;
	private int qty;

	public String getId() {
		return Id;
	}

	public void setId(String Id) {
		this.Id = Id;
	}
	public String getstoreId() {
		return storeId;
	}

	public void setstoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getcustomerId() {
		return customerId;
	}

	public void setcustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getproductId() {
		return productId;
	}

	public void setproductId(String productId) {
		this.productId = productId;
	}
	public String getbranchid() {
		return branchid;
	}

	public void setbranchid(String branchid) {
		this.branchid = branchid;
	}
	public String getbranch() {
		return branch;
	}

	public void setbranch(String branch) {
		this.branch = branch;
	}
	

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(double itemDesc) {
		this.itemDesc = itemDesc;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getunitprice() {
		return unitprice;
	}

	public void setunitprice(double unitprice) {
		this.unitprice = unitprice;
	}
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	
}
