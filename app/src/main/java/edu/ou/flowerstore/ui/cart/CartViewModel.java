package edu.ou.flowerstore.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.db.RoomDB;
import edu.ou.flowerstore.db.entities.CartEntity;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class CartViewModel extends ViewModel {
    private final RoomDB roomDB = FlowerStoreApplication.getInstance().getRoomDB();

    private final LiveData<List<CartEntity>> cartEntityLiveData;
    private final List<CartItem> cartItems = new ArrayList<>();
    private final AppFirebase appFirebase = new AppFirebase();
    private final MutableLiveData<List<CartItem>> cartItemsLiveData = new MutableLiveData<>(cartItems);

    public CartViewModel() {
        cartEntityLiveData = roomDB.cartDAO().getAllInCart();
        cartEntityLiveData.observeForever(data -> {
            List<String> productsId = data.stream().map(CartEntity::getProduct_id).collect(Collectors.toList());
            cartItems.clear();
            if (productsId.isEmpty()) {
                cartItemsLiveData.setValue(cartItems);
                return;
            }
            appFirebase.getProductsCollection().whereIn(FieldPath.documentId(), productsId).get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) return;
                QuerySnapshot result = task.getResult();
                List<CartEntity> productsInCart = cartEntityLiveData.getValue();
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

    public MutableLiveData<List<CartItem>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
