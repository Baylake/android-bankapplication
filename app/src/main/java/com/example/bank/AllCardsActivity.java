package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AllCardsActivity extends AppCompatActivity {
    private AllCardsRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);

        ArrayList<BankCard> cards = new ArrayList<>();
        BankCard card1 = new BankCard("1234 5678 9012 3456", "Mir","Dmitro Eblan","1.12.2022","000","0000");
        BankCard card2 = new BankCard("1234 5678 9012 3456", "Mir","Dmitro Eblan","1.12.2022","000","0000");
        cards.add(card1);
        cards.add(card2);



        LinearLayoutManager layoutManager = new LinearLayoutManager(AllCardsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AllCardsRecyclerViewAdapter(this, cards);
        //adapter = new MyRecyclerViewAdapter(this, viewColors, pages);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }
}