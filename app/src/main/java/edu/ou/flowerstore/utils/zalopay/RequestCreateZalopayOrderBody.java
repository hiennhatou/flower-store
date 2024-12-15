package edu.ou.flowerstore.utils.zalopay;

import com.google.gson.annotations.SerializedName;

public class RequestCreateZalopayOrderBody {
    @SerializedName("app_id")
    private int appId;

    @SerializedName("app_user")
    private String appUser;

    @SerializedName("app_trans_id")
    private String appTransId;

    @SerializedName("app_time")
    private Long appTime;

    @SerializedName("amount")
    private Long amount;

    @SerializedName("item")
    private String item;

    @SerializedName("description")
    private String description;

    @SerializedName("embed_data")
    private String embedData = "{}";

    @SerializedName("mac")
    private String mac;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public String getAppTransId() {
        return appTransId;
    }

    public void setAppTransId(String appTransId) {
        this.appTransId = appTransId;
    }

    public Long getAppTime() {
        return appTime;
    }

    public void setAppTime(Long appTime) {
        this.appTime = appTime;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmbedData() {
        return embedData;
    }

    public void setEmbedData(String embedData) {
        this.embedData = embedData;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
