package edu.ou.flowerstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Active_Fragment extends Fragment {

    private LinearLayout active;

    public Active_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_, container,false);
        active = view.findViewById(R.id.active_frag);

        for(int i = 0; i<7; i++)
        {
            View ac = inflater.inflate(R.layout.item_order, active, false);
            active.addView(ac);
        }
        return view;
    }
}