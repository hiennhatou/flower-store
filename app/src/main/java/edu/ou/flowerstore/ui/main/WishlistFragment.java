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
import edu.ou.flowerstore.utils.adapters.OverviewProductAdapter;

public class WishlistFragment extends Fragment {
    MainFragmentWishlistBinding binding;
    List<OverviewProductAdapter.OverviewProduct> products;
    public WishlistFragment() {
        OverviewProductAdapter.OverviewProduct product1 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product2 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product3 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product4 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product5 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product6 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product7 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product8 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product9 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product10 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầm", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        products = List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10);
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
        OverviewProductAdapter adapter = new OverviewProductAdapter(products);
        binding.flowerListView.setAdapter(adapter);
        binding.flowerListView.addItemDecoration(new HomeFragment.SpaceItemDecoration());
        return binding.getRoot();
    }
}