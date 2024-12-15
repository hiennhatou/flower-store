package edu.ou.flowerstore.utils.zalopay;

public class ResponseCreateZalopayOrderBody {
    private String zpTransToken;
    private int returnCode;
    private String subReturnMessage;
    private int subReturnCode;

    public int getSubReturnCode() {
        return subReturnCode;
    }

    public void setSubReturnCode(int subReturnCode) {
        this.subReturnCode = subReturnCode;
    }

    public String getSubReturnMessage() {
        return subReturnMessage;
    }

    public void setSubReturnMessage(String subReturnMessage) {
        this.subReturnMessage = subReturnMessage;
    }

    public ResponseCreateZalopayOrderBody(int returnCode, String zpTransToken, String returnMessage, int subReturnCode) {
        this.zpTransToken = zpTransToken;
        this.returnCode = returnCode;
        this.subReturnMessage = returnMessage;
        this.subReturnCode = subReturnCode;
    }

    public String getZpTransToken() {
        return zpTransToken;
    }

    public void setZpTransToken(String zpTransToken) {
        this.zpTransToken = zpTransToken;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
}
