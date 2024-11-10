package edu.ou.flowerstore.utils.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ou.flowerstore.databinding.FragmentBottomNavigationBinding;

public class BottomNavigation extends Fragment {
    private FragmentBottomNavigationBinding binding;

    public BottomNavigation() {
    }

    public static BottomNavigation newInstance() {
        BottomNavigation fragment = new BottomNavigation();
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
        binding = FragmentBottomNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}