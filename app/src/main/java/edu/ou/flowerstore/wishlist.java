package edu.ou.flowerstore;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class wishlist extends AppCompatActivity {
    int image[]= {R.drawable.ic_home, R.drawable.back, R.drawable.camtucau, R.drawable.ic_cart,
            R.drawable.star,R.drawable.ic_categories, R.drawable.ic_profile, R.drawable.search};
    String name[] = {"A", "B", "C", "D", "E", "F", "G", "H"};
    int price[] = {30000, 10000, 11111, 22222,333333,444444,555555,6666666};

    GridView gv;
    ArrayList<Item> mylist;
    MyArrayAdapter myadapter;

    int start[] = {1,2,3,4,5,4,1,3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist);

        mylist = new ArrayList<>();
        for(int i=0;i<name.length;i++){
            mylist.add(new Item(image[i], name[i], price[i], start[i]));
        }
        myadapter = new MyArrayAdapter(wishlist.this, R.layout.viewholder_list_food, mylist);
        gv = findViewById(R.id.gv);
        gv.setAdapter(myadapter);
    }
}