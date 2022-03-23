package com.example.testproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testproject.service.MyService;
import com.example.testproject.unite.OnDataPass;
import com.example.testproject.R;
import com.example.testproject.adapter.FragmentAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDataPass, View.OnClickListener {

    private static final String APP_PREFERENCES = "123";

    private TextView textView;
    private Button add;
    private Button remove;
    public ViewPager2 viewPager2;
    private FragmentAdapter fragmentAdapter;
    private ArrayList<Integer> arrayList;
    private Integer numCount;
    private int count = 1;
    private int idNotif;
    private Intent resultIntent;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager2 = findViewById(R.id.pager);
        textView = findViewById(R.id.textView);
        add = findViewById(R.id.btn_add);
        remove = findViewById(R.id.btn_remove);
        arrayList = new ArrayList<>();
        resultIntent = getIntent();
        add.setOnClickListener(this);
        remove.setOnClickListener(this);


        checkSharedPreferences();


        if (resultIntent.getIntExtra("id", 0) > 0) {
            idNotif = resultIntent.getIntExtra("id", 0);
            Log.d("TAG", " resultIntent = " + resultIntent.getIntExtra("id", 0));
            viewPager2.setCurrentItem(idNotif - 1);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    public void onDataPass(int data) {
        if (count == 1) {
            remove.setVisibility(View.GONE);
            remove.setClickable(false);
        } else {
            remove.setVisibility(View.VISIBLE);
            remove.setClickable(true);
        }

        textView.setText(data + "");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_add:
                addButton();
                break;

            case R.id.btn_remove:
                removeButton();
                break;
        }
    }

    private void addButton() {
        count++;
        arrayList.add(count);
        numCount = arrayList.size();
        fragmentAdapter = new FragmentAdapter(this, arrayList, numCount);
        viewPager2.setAdapter(fragmentAdapter);
        viewPager2.setCurrentItem(count);

    }

    private void removeButton() {
        arrayList.remove(count - 1);
        numCount = arrayList.size();
        Log.d("tag", "numCount = " + numCount);

        fragmentAdapter = new FragmentAdapter(this, arrayList, numCount);
        viewPager2.setAdapter(fragmentAdapter);
        viewPager2.setCurrentItem(count);

        Intent intent = new Intent(getApplicationContext(), MyService.class);

        intent.putExtra("status", false);
        intent.putExtra("count", count);
        startService(intent);
        count--;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "count = " + count);
        saveCount(count);

    }

    private void checkSharedPreferences() {
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("count", 0) > 0) {
            count = sharedPreferences.getInt("count", 0);

            for (int i = 1; i <= count; i++) {
                arrayList.add(i);
                numCount = arrayList.size();
                fragmentAdapter = new FragmentAdapter(this, arrayList, numCount);
                viewPager2.setAdapter(fragmentAdapter);

            }
        } else {
            arrayList.add(1);
            numCount = arrayList.size();
            fragmentAdapter = new FragmentAdapter(this, arrayList, numCount);
            viewPager2.setAdapter(fragmentAdapter);
        }

    }

    private void saveCount(int count) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count", count);
        editor.apply();

    }
}