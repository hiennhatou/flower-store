package edu.ou.flowerstore.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.MainFragmentWishlistBinding;
import edu.ou.flowerstore.db.entities.ProductEntity;

public class WishlistFragment extends Fragment {
    MainFragmentWishlistBinding binding;
    MutableLiveData<List<ProductEntity>> products;
    public WishlistFragment() {
        ProductEntity product1 = new ProductEntity("Hoa hồng", 120000);
        product1.thumbnail = R.drawable.boutique;

        ProductEntity product2 = new ProductEntity("Hoa cẩm tú cầu", 120000);
        product2.thumbnail = R.drawable.camtucau;

        ProductEntity product3 = new ProductEntity("Hoa hồng", 120000);
        product3.thumbnail = R.drawable.boutique;

        ProductEntity product4 = new ProductEntity("Hoa hồng", 120000);
        product4.thumbnail = R.drawable.boutique;

        ProductEntity product5 = new ProductEntity("Hoa hồng", 120000);
        product5.thumbnail = R.drawable.boutique;

        ProductEntity product6 = new ProductEntity("Hoa hồng", 120000);
        product6.thumbnail = R.drawable.boutique;

        ProductEntity product7 = new ProductEntity("Hoa hồng", 120000);
        product7.thumbnail = R.drawable.boutique;

        ProductEntity product8 = new ProductEntity("Hoa hồng", 120000);
        product8.thumbnail = R.drawable.boutique;

        ProductEntity product9 = new ProductEntity("Hoa hồng", 120000);
        product9.thumbnail = R.drawable.boutique;

        ProductEntity product10 = new ProductEntity("Hoa hồng", 120000);
        product10.thumbnail = R.drawable.boutique;

        products = new MutableLiveData<>(List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10));
    }

    public static WishlistFragment newInstance(String param1, String param2) {
        WishlistFragment fragment = new WishlistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = MainFragmentWishlistBinding.inflate(inflater, container, false);
        ProductsAdapter adapter = new ProductsAdapter(products);
        binding.flowerListView.setAdapter(adapter);
        binding.flowerListView.addItemDecoration(new HomeFragment.SpaceItemDecoration());
        return binding.getRoot();
    }
}