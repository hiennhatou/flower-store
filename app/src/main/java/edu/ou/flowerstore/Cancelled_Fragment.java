package edu.ou.flowerstore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Cancelled_Fragment extends Fragment {
    private LinearLayout active;

    public Cancelled_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancelled_, container,false);
        active = view.findViewById(R.id.cancel_frag);

        for(int i = 0; i<7; i++)
        {
            View ac = inflater.inflate(R.layout.cancalled_item, active, false);
            active.addView(ac);
        }
        return view;
    }
}