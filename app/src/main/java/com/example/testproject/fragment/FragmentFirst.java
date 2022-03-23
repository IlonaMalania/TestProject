package com.example.testproject.fragment;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.testproject.service.MyService;
import com.example.testproject.unite.OnDataPass;
import com.example.testproject.R;


public class FragmentFirst extends Fragment  {

    Button button;
    private OnDataPass mDataPasser;
    Integer data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first, container, false);

        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyService.class);
                intent.putExtra("data", data);
                intent.putExtra("status", true);
                getActivity().startService(intent);














            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        data = getArguments().getInt("key");
        Integer position = getArguments().getInt("position");
        Log.d("TAG", "data = " + data);

        //  button.setText(data);
        mDataPasser.onDataPass(data);
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        mDataPasser = (OnDataPass) a;
    }


}