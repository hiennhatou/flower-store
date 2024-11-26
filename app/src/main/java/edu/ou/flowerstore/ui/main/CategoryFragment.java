package edu.ou.flowerstore.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.MainFragmentCategoryBinding;
import edu.ou.flowerstore.utils.adapters.CategoryAdapter;

public class CategoryFragment extends Fragment {
    MainFragmentCategoryBinding binding;
    List<CategoryAdapter.OverviewCategory> categories;
    public CategoryFragment() {
        CategoryAdapter.OverviewCategory category1 = new CategoryAdapter.OverviewCategory("1", "Hoa cưới", "https://images.pexels.com/photos/1488310/pexels-photo-1488310.jpeg?w=640&h=427");
        CategoryAdapter.OverviewCategory category2 = new CategoryAdapter.OverviewCategory("1", "Hoa chúc mừng", "https://images.pexels.com/photos/264787/pexels-photo-264787.jpeg?w=640&h=480");
        CategoryAdapter.OverviewCategory category3 = new CategoryAdapter.OverviewCategory("1", "Hoa văn phòng", "https://rosaholics.com/cdn/shop/articles/1_Top_Flower_Arrangements_for_Offices_900x.jpg?v=1715759991");
        CategoryAdapter.OverviewCategory category4 = new CategoryAdapter.OverviewCategory("1", "Hoa kỷ niệm", "https://as2.ftcdn.net/v2/jpg/04/26/03/73/1000_F_426037381_ffO5v64mavE4elLieONsoI3HPm0Bs31N.jpg");
        CategoryAdapter.OverviewCategory category5 = new CategoryAdapter.OverviewCategory("1", "Hoa giáng sinh", "https://images.pexels.com/photos/19309748/pexels-photo-19309748.jpeg?w=640&h=482");
        CategoryAdapter.OverviewCategory category6 = new CategoryAdapter.OverviewCategory("1", "Cây cảnh", "https://images.pexels.com/photos/6890414/pexels-photo-6890414.jpeg?w=640&h=411");
        categories = List.of(category1, category2, category3, category4, category5, category6);
    }
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        binding = MainFragmentCategoryBinding.inflate(inflater, container, false);
        CategoryAdapter adapter = new CategoryAdapter(categories);
        binding.categoryList.setAdapter(adapter);
        return binding.getRoot();
    }
}