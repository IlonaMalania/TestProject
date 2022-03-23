package com.example.testproject.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.example.testproject.activity.MainActivity;
import com.example.testproject.fragment.FragmentFirst;

import java.util.ArrayList;
import java.util.List;


public class FragmentAdapter extends FragmentStateAdapter {

    private ArrayList<Integer> arrayList;
    private int numCount;
    int positionPage;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Integer> arrayList, int numCount) {
        super(fragmentActivity);
        this.arrayList = arrayList;
        this.numCount = numCount;
    }

    @NonNull

    @Override
    public Fragment createFragment(int position) {
        Log.d("TAG", "position = " + position);
        Bundle bundle = new Bundle();
        Log.d("TAG", "arrayList.get(position)" + arrayList.get(position));
        bundle.putInt("key", arrayList.get(position));
        FragmentFirst fragmentFirst = new FragmentFirst();

        fragmentFirst.setArguments(bundle);
        Log.d("TAG", "bundle = " + bundle);
        return fragmentFirst;

    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        positionPage = position;

        Bundle bundle = new Bundle();
        bundle.putInt("position", arrayList.get(position));

        FragmentFirst fragmentFirst = new FragmentFirst();

        fragmentFirst.setArguments(bundle);
    }

    @Override
    public long getItemId(int position) {
        Log.d("tag", "position adapter = " + position);
        return position;
    }


    @Override
    public int getItemCount() {
        return numCount;
    }


}