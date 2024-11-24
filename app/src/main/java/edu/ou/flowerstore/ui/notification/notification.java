package edu.ou.flowerstore.ui.notification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ou.flowerstore.R;

public class notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout
        setContentView(R.layout.activity_notification);

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add data
        List<NotificationItem> notificationList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            notificationList.add(new NotificationItem(
                    "Order Shipped",
                    "Your order has been shipped successfully. Check your email for details.",
                    "1h ago"
            ));
        }

        // Set adapter
        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);


    }
}
