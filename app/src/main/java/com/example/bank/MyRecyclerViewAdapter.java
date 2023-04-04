package com.example.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<BankCard> bankCards;

    public MyRecyclerViewAdapter(Context context, List<BankCard> bankCards) {
        this.bankCards = bankCards;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.second_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BankCard card = bankCards.get(position);
        holder.imageButtonPlus.setImageResource(R.drawable.main_activity_plus);
        holder.header.setText(R.string.header);
        DataBase data = new DataBase();
        data.selectCardBalance("valerik228");
        //data.mapAnswer.get(0).get("balance");
        holder.cardBalance1.setText(data.mapAnswer.get(0).get("balance"));
        holder.cardBalance2.setText(data.mapAnswer.get(1).get("balance"));
        holder.cardNumber1.setText(data.mapAnswer.get(0).get("cards_card_id"));
        holder.cardNumber2.setText(data.mapAnswer.get(1).get("cards_card_id"));
    }

    @Override
    public int getItemCount() {
        return bankCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageButton imageButtonPlus;
        final TextView header;
        final Button allCards;
        final TextView cardBalance1, cardBalance2, cardNumber1, cardNumber2;
        ViewHolder(View view){
            super(view);
            imageButtonPlus = view.findViewById(R.id.imageButton3);
            header = view.findViewById(R.id.textView5);
            allCards = view.findViewById(R.id.button);
            cardBalance1 = view.findViewById(R.id.textView6);
            cardBalance2 = view.findViewById(R.id.textView8);
            cardNumber1 = view.findViewById(R.id.textView7);
            cardNumber2 = view.findViewById(R.id.textView9);
        }
    }
}