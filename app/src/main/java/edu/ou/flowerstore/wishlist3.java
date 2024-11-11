package edu.ou.flowerstore;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class wishlist3 extends AppCompatActivity {

    int[] images = {R.drawable.camtucau, R.drawable.camtucau, R.drawable.camtucau, R.drawable.camtucau,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};
    String[] names = {"A", "B", "C", "D", "E", "F", "G", "H"};
    int[] prices = {30000, 10000, 11111, 22222, 333333, 444444, 555555, 6666666};
    int[] ratings = {1, 2, 3, 4, 5, 4, 1, 3};

    RecyclerView recyclerView;
    ArrayList<Item> itemList;
    MyArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist3);

        // Khởi tạo dữ liệu
        itemList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            itemList.add(new Item(images[i], names[i], prices[i], ratings[i]));
        }

        // Thiết lập RecyclerView với GridLayoutManager (2 cột)
        recyclerView = findViewById(R.id.flower_listView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        myAdapter = new MyArrayAdapter(this, R.layout.layout_list_item, itemList);
        recyclerView.setAdapter(myAdapter);
    }
}