package com.example.bank;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Integer> viewColors = new ArrayList<>();
        viewColors.add(Color.BLUE);
        viewColors.add(Color.YELLOW);
        viewColors.add(Color.MAGENTA);
        viewColors.add(Color.RED);
        viewColors.add(Color.BLACK);

        ArrayList<String> pages = new ArrayList<>();
        pages.add("Кошелек");
        pages.add("Финансы");
        pages.add("Camel");
        pages.add("Sheep");
        pages.add("Goat");
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(this, viewColors, pages);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }
}