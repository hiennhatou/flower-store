package edu.ou.flowerstore.utils.zalopay;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.ou.flowerstore.utils.MACGenerator;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ZaloPayUtil {
    private static final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://sb-openapi.zalopay.vn/").addConverterFactory(GsonConverterFactory.create(gson)).build();
    private static final ZaloPayApi api = retrofit.create(ZaloPayApi.class);

    public static Call<ResponseCreateZalopayOrderBody> createPayment(String orderId, String uid, long totalPrice, String transId) {
        RequestCreateZalopayOrderBody data = new RequestCreateZalopayOrderBody();
        data.setAppId(2554);
        data.setAppUser(uid);
        data.setAppTransId(transId);
        data.setAppTime((new Date()).getTime());
        data.setAmount(totalPrice);
        data.setDescription(String.format("Thanh toán đơn hàng %s", orderId));
        data.setItem("[]");
        data.setEmbedData("{}");
        data.setMac(MACGenerator.generateMAC(String.format(new Locale("vi", "vn"), "%d|%s|%s|%d|%d|%s|%s", data.getAppId(), data.getAppTransId(), data.getAppUser(), data.getAmount(), data.getAppTime(), data.getEmbedData(), data.getItem()), "sdngKKJmqEMzvh5QQcdD2A9XBSKUNaYn"));
        return api.createOrder(data);
    }

    public static Call<ResponseCreateZalopayOrderBody> queryTransStatus(String appTransId) {
        RequestQueryTransStatus data = new RequestQueryTransStatus();
        data.setAppId(2554);
        data.setAppTransId(appTransId);
        data.setMac(MACGenerator.generateMAC(String.format(new Locale("vi", "vn"),"%d|%s|%s", data.getAppId(), data.getAppTransId(), "sdngKKJmqEMzvh5QQcdD2A9XBSKUNaYn"), "sdngKKJmqEMzvh5QQcdD2A9XBSKUNaYn"));
        return api.queryTransStatus(data);
    }
}
