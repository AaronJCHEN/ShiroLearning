package com.sjw.ShiroTest.Pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Watson on 01/24/2017.
 */
@Alias(value = "Comment")
public class CommentPojo implements Serializable{
    private static final long serialVersionUID = 3515961391943914219L;
    private int productId;
    private int userId;
    private String username;
    private Timestamp commentDate;
    private String comments;
    private String pics[];

    CommentPojo(){
        this.commentDate = new Timestamp(System.currentTimeMillis());
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
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
