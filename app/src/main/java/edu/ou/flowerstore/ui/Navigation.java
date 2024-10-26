package edu.ou.flowerstore.ui;

import android.content.Context;
import android.content.Intent;

import edu.ou.flowerstore.ui.subactivity.SubActivity;

public class Navigation {
    public static void toSubActivity(Context context) {
        Intent intent = new Intent(context, SubActivity.class);
        context.startActivity(intent);
    }
}
