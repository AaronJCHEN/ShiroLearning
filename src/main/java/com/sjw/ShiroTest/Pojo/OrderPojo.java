package com.sjw.ShiroTest.Pojo;

import org.apache.ibatis.type.Alias;
import org.springframework.format.datetime.DateFormatter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Alias(value = "Order")
public class OrderPojo implements Serializable {
    private static final long serialVersionUID = -333488318903459738L;
    private int id;
    private int userId = 0;
    private String orderNum;
    private String username;
    private List<ProductPojo> productList;
    private double price;
    private String productIdList;
    private boolean isSale;

    public OrderPojo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat d = new SimpleDateFormat("yyyyMMddHHmmss");
        if(userId != 0)
            this.orderNum = "O"+d.format(date)+ String.format("%05d",this.userId);
        else
            this.orderNum = "O"+d.format(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        this.orderNum = this.orderNum + String.format("%05d",this.userId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ProductPojo> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductPojo> productList) {
        this.productList = productList;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public String getProductIdList(){
        String list = "";
        for (ProductPojo p : this.productList) {
            if (list.equals(""))
                list = String.valueOf(p.getId());
            else
                list = list+","+String.valueOf(p.getId());
        }

        return list;
    }

    //Method
    public void removeSoldOutProduct(List<ProductPojo> soldOutList){
       for (ProductPojo p : soldOutList){
           this.productList.remove(p);
       }
    }
}
