package edu.ou.flowerstore.utils.zalopay;

import com.google.gson.annotations.SerializedName;

public class RequestQueryTransStatus {
    @SerializedName("app_id")
    private long appId;

    @SerializedName("app_trans_id")
    private String appTransId;

    @SerializedName("mac")
    private String mac;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getAppTransId() {
        return appTransId;
    }

    public void setAppTransId(String appTransId) {
        this.appTransId = appTransId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
