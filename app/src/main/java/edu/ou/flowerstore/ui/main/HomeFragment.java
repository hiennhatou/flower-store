package edu.ou.flowerstore.ui.main;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.MainFragmentHomeBinding;
import edu.ou.flowerstore.ui.cart.CartActivity;
import edu.ou.flowerstore.utils.adapters.OverviewProductAdapter;

public class HomeFragment extends Fragment {
    MainFragmentHomeBinding binding;
    List<OverviewProductAdapter.OverviewProduct> products;

    public HomeFragment() {
        OverviewProductAdapter.OverviewProduct product1 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product2 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, true);
        OverviewProductAdapter.OverviewProduct product3 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product4 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product5 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product6 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product7 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product8 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product9 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);
        OverviewProductAdapter.OverviewProduct product10 = new OverviewProductAdapter.OverviewProduct("1", "Hoa cẩm tú cầu", 120000, "https://hoathangtu.com/wp-content/uploads/2023/03/IMG_1896-scaled.jpg", 4.5, false);

        products = List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10);
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        {
            binding = MainFragmentHomeBinding.inflate(inflater, container, false);
            View view = binding.getRoot();
            initFragment();
            initEvent();
            return view;
        }
    }


    static class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position % 2 == 0) {
                outRect.right = 30;
            } else {
                outRect.left = 30;
            }

            if (position <= state.getItemCount() - 2)
                outRect.bottom = 40;
        }
    }

    private void initEvent() {
        binding.seeMoreCategories.setOnClickListener(v -> {
            BottomNavigationView bottomNav = binding.getRoot().getRootView().findViewById(R.id.bottom_navigation);
            bottomNav.setSelectedItemId(R.id.nav_categories);
        });

        binding.cartBtn.setOnClickListener(this::onClickCartBtn);
    }

    private void initFragment() {
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recycle_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        recyclerView.setAdapter(new OverviewProductAdapter(products));
    }

    private void onClickCartBtn(View v) {
        Intent intent = new Intent(getContext(), CartActivity.class);
        startActivity(intent);
    }
}