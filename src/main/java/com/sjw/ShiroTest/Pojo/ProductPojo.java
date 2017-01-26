package com.sjw.ShiroTest.Pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias(value = "product")
public class ProductPojo implements Serializable {
    private static final long serialVersionUID = -7898194272883238970L;

    public static final String OBJECT_KEY = "PRODUCT";

    private int id;
    private String name;
    private float price;
    private String category;
    private String[] tags;
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String tags) {
        String[] tmp_tags = null;
        if (tags.contains(","))
            tmp_tags = tags.split(",");

        this.tags = tmp_tags;
    }

    public void appendTags(String tag){
        if (this.tags != null && this.tags.length>0){
            String[] newTags = new String[tags.length+1];
            newTags[tags.length] = tag;
            System.arraycopy(tags,0,newTags,0,tags.length);
            this.tags = newTags;
        }
        else{
            String[] newTags = new String[1];
            newTags[0] = tag;
            this.tags = newTags;
        }
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
}
