package com.sjw.ShiroTest.Pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by Watson on 01/24/2017.
 */
@Alias(value = "Comment")
public class CommentPojo implements Serializable{
    private static final long serialVersionUID = 3515961391943914219L;
    private int productId;
    private int userId;
    private String comments;
    private String pics[];

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String[] getPics() {
        return pics;
    }

    public void setPics(String pics) {
        String[] tmp_pics = null;
        if (pics.contains(","))
            tmp_pics = pics.split(",");

        this.pics = tmp_pics;
    }
}
