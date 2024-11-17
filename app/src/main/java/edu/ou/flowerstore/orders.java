package edu.ou.flowerstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class orders extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        ImageView backMainMenu = findViewById(R.id.backMainMenu);

        backMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(orders.this, wishlist.class);
                startActivity(mainMenu);
            }
        });


        Adapter_View_Pager viewPagerAdapter = new Adapter_View_Pager(this);
        mViewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Active");
                    tab.setContentDescription("Tab for Active Orders");
                    break;
                case 1:
                    tab.setText("Completed");
                    tab.setContentDescription("Tab for Completed Orders");
                    break;
                case 2:
                    tab.setText("Cancelled");
                    tab.setContentDescription("Tab for Cancelled Orders");
                    break;
            }
        }).attach();
    }
}
