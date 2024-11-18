package edu.ou.flowerstore.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import edu.ou.flowerstore.databinding.MainFragmentHomeBinding;

public class MainViewModel extends AndroidViewModel {
    public MainViewModel(Application application) {
        super(application);
    }

    public static class HomeViewPagerAdapter extends FragmentStateAdapter {
        List<Class<? extends Fragment>> pages = List.of(HomeFragment.class, CartFragment.class, WishlistFragment.class, CategoryFragment.class, ProfileFragment.class);

        public HomeViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            try {
                return pages.get(position).newInstance();
            } catch (IllegalAccessException | InstantiationException ignored) {
            }
            return new HomeFragment();
        }

        @Override
        public int getItemCount() {
            return pages.size();
        }
    }

    static class PageChange extends ViewPager2.OnPageChangeCallback {
        Callback callback;
        public PageChange(Callback callback) {
            super();
            this.callback = callback;
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            callback.doCallback(position);
        }

        interface Callback {
            void doCallback(int position);
        }
    }
}
