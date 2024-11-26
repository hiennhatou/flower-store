package edu.ou.flowerstore.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FlowerStoreApplication application;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = FlowerStoreApplication.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MainTabAdapter pagerAdapter = new MainTabAdapter(this);
        binding.pager.registerOnPageChangeCallback(new MainViewModel.PageChange(this::onPageChange));
        binding.pager.setAdapter(pagerAdapter);

        binding.bottomNavigation.setOnItemSelectedListener(this::onSelectHeader);
    }

    private void onPageChange(int position) {
        switch (position) {
            case 0:
                binding.bottomNavigation.setSelectedItemId(R.id.nav_home);
                break;
            case 1:
                binding.bottomNavigation.setSelectedItemId(R.id.nav_wishlist);
                break;
            case 2:
                binding.bottomNavigation.setSelectedItemId(R.id.nav_categories);
                break;
            case 3:
                binding.bottomNavigation.setSelectedItemId(R.id.nav_profile);
                break;
        }
    }

    private boolean onSelectHeader(MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            binding.pager.setCurrentItem(0, false);
        } else if (item.getItemId() == R.id.nav_wishlist) {
            binding.pager.setCurrentItem(1, false);
        } else if (item.getItemId() == R.id.nav_categories) {
            binding.pager.setCurrentItem(2, false);
        } else if (item.getItemId() == R.id.nav_profile) {
            binding.pager.setCurrentItem(3, false);
        }
        return true;
    }
}