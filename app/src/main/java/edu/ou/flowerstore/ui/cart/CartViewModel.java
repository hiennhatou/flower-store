package edu.ou.flowerstore.ui.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.ou.flowerstore.FlowerStoreApplication;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<List<CartItem>> cartItemsLiveData = FlowerStoreApplication.getInstance().getCartItemsLiveData();

    public MutableLiveData<List<CartItem>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }
}
