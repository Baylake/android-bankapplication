package com.example.bank;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
//TODO Сделать фрагмент с полной информацией о картах и возможных действиях(grid)
public class MainActivity extends AppCompatActivity {
    //public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<BankCard> cards = new ArrayList<>();
        BankCard card1 = new BankCard("1234 5678 9012 3456", "Mir","Dmitro Eblan","1.12.2022","000","0000","MIR","1000");
        BankCard card2 = new BankCard("1234 5678 9012 3456", "Mir","Dmitro Eblan","1.12.2022","000","0000","MIR","1000");
        cards.add(card1);
        cards.add(card2);



//        ArrayList<String> pages = new ArrayList<>();
//        pages.add("Кошелек");
//        pages.add("Финансы");
//        pages.add("Camel");
//        pages.add("Sheep");
//        pages.add("Goat");
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(this, cards);
        //adapter = new MyRecyclerViewAdapter(this, viewColors, pages);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "Ну типа соси ",Toast.LENGTH_SHORT ).show();
//        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
//    }
}