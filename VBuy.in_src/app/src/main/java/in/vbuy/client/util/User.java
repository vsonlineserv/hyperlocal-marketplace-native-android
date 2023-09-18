package in.vbuy.client.util;

public class User {

    public String storename;

    public String branchadd1;
    
    public String branchadd2;
    
    public String price;
    
    public String specialprice;
    
    public String lat;
    
    public String lon;
    
    public String additionalshippingcharge;
    
    public String deliverytime;



    public User(String storename, String branchadd1,String branchadd2,String price,String specialprice,String lat,String lon,String additionalshippingcharge,String deliverytime) {

       this.storename = storename;

       this.branchadd1 = branchadd1;
       
       this.branchadd2=branchadd2;
       
       this.price=price;
       
       this.specialprice=specialprice;
       
       this.lat=lat;
       
       this.lon=lon;
       
       this.additionalshippingcharge=additionalshippingcharge;
       
       this.deliverytime=deliverytime;

    }

}