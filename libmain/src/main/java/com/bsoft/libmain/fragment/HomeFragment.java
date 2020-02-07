package com.bsoft.libmain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.bsoft.libmain.databinding.MainFragmentHomeBinding;
import com.bsoft.libnavannotation.FragmentDestination;


@FragmentDestination(pageUrl = "main/tabs/home", asStart = true)
public class HomeFragment extends Fragment {


    public static HomeFragment newInstance(String feedType) {
        Bundle args = new Bundle();
        args.putString("feedType", feedType);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainFragmentHomeBinding binding = MainFragmentHomeBinding.inflate(inflater, container, false);
        binding.getRoot().setFitsSystemWindows(true);
        return binding.getRoot();
    }
}
