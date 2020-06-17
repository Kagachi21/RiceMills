package com.example.ricemills.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ricemills.FragmentFitur.GrafikFragment;
import com.example.ricemills.FragmentFitur.PemasokanFragment;
import com.example.ricemills.FragmentFitur.PenggilinganFragment;
import com.example.ricemills.FragmentFitur.PenjualanFragment;
import com.example.ricemills.FragmentFitur.StockFragment;
import com.example.ricemills.FragmentFitur.ViewFragment;
import com.example.ricemills.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    Button btn_pemasokan, btn_penggilingan, btnpenjualan, btnview, btnstok, btngrafik;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_pemasokan =view.findViewById(R.id.btn_Pemasokan);
        btn_penggilingan =view.findViewById(R.id.btn_penggilingan);
        btnpenjualan =view.findViewById(R.id.btn_Penjualan);
        btnview =view.findViewById(R.id.btn_view);
        btnstok =view.findViewById(R.id.btn_stock);
        btngrafik =view.findViewById(R.id.btn_grafik);

        btn_pemasokan.setOnClickListener(this);
        btngrafik.setOnClickListener(this);
        btnstok.setOnClickListener(this);
        btnview.setOnClickListener(this);
        btnpenjualan.setOnClickListener(this);
        btn_penggilingan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_Pemasokan:
                fragment = new PemasokanFragment();
                replaceFragment(fragment);
                break;

            case R.id.btn_penggilingan:
                fragment = new PenggilinganFragment();
                replaceFragment(fragment);
                break;

            case R.id.btn_Penjualan:
                fragment = new PenjualanFragment();
                replaceFragment(fragment);
                break;

            case R.id.btn_view:
                fragment = new ViewFragment();
                replaceFragment(fragment);
                break;

            case R.id.btn_stock:
                fragment = new StockFragment();
                replaceFragment(fragment);
                break;

            case R.id.btn_grafik:
                fragment = new GrafikFragment();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
