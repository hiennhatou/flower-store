package edu.ou.flowerstore.ui.main;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.MainFragmentHomeBinding;
import edu.ou.flowerstore.ui.cart.CartActivity;
import edu.ou.flowerstore.utils.adapters.OverviewProductAdapter;

public class HomeFragment extends Fragment {
    MainFragmentHomeBinding binding;
    List<OverviewProductAdapter.OverviewProduct> products;
    OverviewProductAdapter productsAdapter;
    HomeViewModel viewModel;
    boolean isLoading = false;

    public HomeFragment() {
        products = new ArrayList<>();
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
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.loadProducts();
        products = viewModel.getProducts();
        productsAdapter = new OverviewProductAdapter(products);
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
    }

    private void initFragment() {
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recycle_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        recyclerView.setAdapter(productsAdapter);
        recyclerView.addOnScrollListener(new OnScrollRecyclerView());
        viewModel.getLiveDataProducts().observeForever(a -> {
            productsAdapter.notifyDataSetChanged();
        });
    }

    class OnScrollRecyclerView extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

            if (!isLoading && gridLayoutManager != null && gridLayoutManager.findLastVisibleItemPosition() == products.size() - 1) {
                isLoading = true;
                Task<QuerySnapshot> loading = viewModel.loadProducts();
                if (loading != null) loading.addOnSuccessListener(v -> {
                    isLoading = false;
                });
                else
                    isLoading = false;
            }
        }
    }
}