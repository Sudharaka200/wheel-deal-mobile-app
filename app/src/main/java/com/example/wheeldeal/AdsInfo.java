package com.example.wheeldeal;

public class AdsInfo {
    private String aCategory;
    private String aBrand;
    private String aModel;
    private int aMilage;
    private int aCapacity;
    private String aDescription;
    private int aPrice;
    private String aLocation;

    private String imageUrl;

    public AdsInfo() {
    }

    public AdsInfo(String aCategory, String aBrand,String aModel, int amilage, int aCapacity, String aDescription, int aPrice, String aLocation, String imageUrl) {
        this.aCategory = aCategory;
        this.aBrand = aBrand;
        this.aModel= aModel;
        this.aMilage = amilage;
        this.aCapacity = aCapacity;
        this.aDescription = aDescription;
        this.aPrice = aPrice;
        this.aLocation = aLocation;
        this.imageUrl = imageUrl;
    }

    public String getaCategory() {
        return aCategory;
    }

    public void setaCategory(String aCategory) {
        this.aCategory = aCategory;
    }

    public String getaBrand() {
        return aBrand;
    }

    public void setaBrand(String aBrand) {
        this.aBrand = aBrand;
    }

    public int getaMilage() {
        return aMilage;
    }

    public void setaMilage(int aMilage) {
        this.aMilage = aMilage;
    }

    public String getaModel() {
        return aModel;
    }

    public void setaModel(String aModel){this.aModel = aModel;}

    public int getaCapacity() {
        return aCapacity;
    }

    public void setaCapacity(int aCapacity) {
        this.aCapacity = aCapacity;
    }

    public String getaDescription() {
        return aDescription;
    }

    public void setaDescription(String aDescription) {
        this.aDescription = aDescription;
    }

    public int getaPrice() {
        return aPrice;
    }

    public void setaPrice(int aPrice) {
        this.aPrice = aPrice;
    }

    public String getaLocation() {
        return aLocation;
    }

    public void setaLocation(String aLocation) {
        this.aLocation = aLocation;
    }

    public String getImageUrl(){
        return  imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
