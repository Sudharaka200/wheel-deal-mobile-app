package com.example.wheeldeal;

public class favInfo {

    private String emailF;
    private String modelF;
    private String locationF;
    private String descriptionF;
    private int priceF;

    public favInfo(String emailF, String modelF, String locationF, String descriptionF, int priceF) {
        this.emailF = emailF;
        this.modelF = modelF;
        this.locationF = locationF;
        this.descriptionF = descriptionF;
        this.priceF = priceF;
    }

    public String getEmailF() {
        return emailF;
    }

    public void setEmailF(String emailF) {
        this.emailF = emailF;
    }

    public String getModelF() {
        return modelF;
    }

    public void setModelF(String modelF) {
        this.modelF = modelF;
    }

    public String getLocationF() {
        return locationF;
    }

    public void setLocationF(String locationF) {
        this.locationF = locationF;
    }

    public String getDescriptionF() {
        return descriptionF;
    }

    public void setDescriptionF(String descriptionF) {
        this.descriptionF = descriptionF;
    }

    public int getPriceF() {
        return priceF;
    }

    public void setPriceF(int priceF) {
        this.priceF = priceF;
    }
}
