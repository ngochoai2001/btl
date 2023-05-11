package com.example.btl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btl.R;

public class FragmentHoaDon  extends Fragment {


    public static FragmentHoaDon newInstance() {

        Bundle args = new Bundle();

        FragmentHoaDon fragment = new FragmentHoaDon();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don,container,false);
        return view;
    }
}
