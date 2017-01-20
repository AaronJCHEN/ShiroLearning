package com.sjw.ShiroTest.Pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Watson on 01/19/2017.
 */
@Alias(value = "Browse")
public class BrowsePojo implements Serializable{
    private int productId;
    private String username;
    private Timestamp browseDate;
    private int browseTimes;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Timestamp getBrowseDate() {
        return browseDate;
    }

    public void setBrowseDate(Timestamp browseDate) {
        this.browseDate = browseDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(int browseTimes) {
        this.browseTimes = browseTimes;
    }
}
