package com.sjw.ShiroTest.Pojo;

import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.Map;

@Alias(value = "Order")
public class OrderPojo {
    private int userId;
    private String username;
    private List<ProductPojo> orderList;
    private double price;
    private String productIdList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ProductPojo> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ProductPojo> orderList) {
        this.orderList = orderList;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductIdList(){
        String list = "";
        for (ProductPojo p : this.orderList) {
            if (list.equals(""))
                list = String.valueOf(p.getId());
            else
                list = list+","+String.valueOf(p.getId());
        }

        return list;
    }
}
