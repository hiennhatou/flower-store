package edu.ou.flowerstore;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FlowerStoreApplication extends Application {
    static private FlowerStoreApplication instance;
    private final MutableLiveData<Integer> cartAmount = new MutableLiveData<>(0);

    @Override
    public void onCreate() {
        super.onCreate();
        FlowerStoreApplication.instance = this;
    }

    public static FlowerStoreApplication getInstance() {
        return instance;
    }

    public LiveData<Integer> getCartAmount () {
        return cartAmount;
    }

    public void setCartAmount(Integer text) {
        cartAmount.setValue(text);
    }
}
