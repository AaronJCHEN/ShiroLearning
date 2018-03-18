package com.sjw.ShiroTest.Pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias(value = "Product")
public class ProductPojo implements Serializable {
    private static final long serialVersionUID = -7898194272883238970L;

    public static final String OBJECT_KEY = "PRODUCT";

    private int id;
    private String name;
    private float price;
    private float productAmount;
    private String category;
    private String tags;
    private String description;
    private double remains;
    private String img;
    private boolean recommended;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRemains() {
        return remains;
    }

    public void setRemains(double remains) {
        this.remains = remains;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isRecommanded() {
        return recommended;
    }

    public void setRecommanded(boolean recommended) {
        this.recommended = recommended;
    }

    public float getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(float productAmount) {
        this.productAmount = productAmount;
    }
}
