package edu.ou.flowerstore;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.ou.flowerstore.db.RoomDB;
import edu.ou.flowerstore.db.entities.CartEntity;
import edu.ou.flowerstore.ui.cart.CartItem;
import edu.ou.flowerstore.utils.firebase.AppFirebase;
import edu.ou.flowerstore.utils.zalopay.ZaloPayApi;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPaySDK;

public class FlowerStoreApplication extends Application {
    static private FlowerStoreApplication instance;

    private AppFirebase appFirebase;
    private RoomDB roomDB;
    private LiveData<List<CartEntity>> cartEntitiesLiveData;

    private MutableLiveData<FirebaseUser> currentUserLiveData = new MutableLiveData<>(null);

    private final MutableLiveData<List<CartItem>> cartItemsLiveData = new MutableLiveData<>(new ArrayList<>());

    @Override
    public void onCreate() {
        super.onCreate();
        FlowerStoreApplication.instance = this;
        appFirebase = new AppFirebase();
        currentUserLiveData.setValue(appFirebase.getFirebaseAuth().getCurrentUser());

        roomDB = Room.databaseBuilder(this.getApplicationContext(), RoomDB.class, "littleflower").allowMainThreadQueries().build();
        cartEntitiesLiveData = roomDB.cartDAO().getAllInCart();

        ZaloPaySDK.init(2554, Environment.SANDBOX);
        cartEntitiesLiveData.observeForever(data -> {
            List<CartItem> cartItems = new ArrayList<>();
            List<String> productsId = data.stream().map(CartEntity::getProduct_id).collect(Collectors.toList());
            if (productsId.isEmpty()) {
                cartItemsLiveData.setValue(cartItems);
                return;
            }
            appFirebase.getProductsCollection().whereIn(FieldPath.documentId(), productsId).get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) return;
                QuerySnapshot result = task.getResult();
                List<CartEntity> productsInCart = cartEntitiesLiveData.getValue();
                if (productsInCart != null)
                    result.getDocuments().forEach(product -> {
                        Optional<CartEntity> cartEntity = productsInCart.stream().filter(p -> p.getProduct_id().equals(product.getId())).findFirst();
                        if (!cartEntity.isPresent()) {
                            cartItemsLiveData.setValue(cartItems);
                            return;
                        }
                        cartItems.add(new CartItem(product.getId(), ((List<String>) product.get("image")).get(0), product.getString("name"), product.getLong("price"), cartEntity.get().getQuantity(), cartEntity.get().getId()));
                    });
                cartItemsLiveData.setValue(cartItems);
            });
        });
    }

    public MutableLiveData<FirebaseUser> getCurrentUserLiveData() {
        return currentUserLiveData;
    }

    public MutableLiveData<List<CartItem>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public RoomDB getRoomDB() {
        return roomDB;
    }

    public static FlowerStoreApplication getInstance() {
        return instance;
    }

    public AppFirebase getAppFirebase() {
        return appFirebase;
    }
}
