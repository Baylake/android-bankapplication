package com.example.bank;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    private Context context;
    public MyRecyclerViewAdapter(Context context, List<BankCard> bankCards) {
        this.bankCards = bankCards;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
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
        holder.header.setText(R.string.header);
        DataBase data = new DataBase();
        data.selectCardBalance("valerik228");
        holder.cardBalance1.setText(data.mapAnswer.get(0).get("balance"));
        holder.cardBalance2.setText(data.mapAnswer.get(1).get("balance"));
        holder.cardNumber1.setText(data.mapAnswer.get(0).get("cards_card_id"));
        holder.cardNumber2.setText(data.mapAnswer.get(1).get("cards_card_id"));
        holder.allCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Recycle",context.toString()+" "+view.toString());
                context.startActivity(new Intent(context,AllCardsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button imageButtonPlus;
        final TextView header;
        final Button allCards;
        final TextView cardBalance1, cardBalance2, cardNumber1, cardNumber2;
        ViewHolder(View view){
            super(view);
            imageButtonPlus = view.findViewById(R.id.button4);
            header = view.findViewById(R.id.textView5);
            allCards = view.findViewById(R.id.button3);
            cardBalance1 = view.findViewById(R.id.textView6);
            cardBalance2 = view.findViewById(R.id.textView8);
            cardNumber1 = view.findViewById(R.id.textView7);
            cardNumber2 = view.findViewById(R.id.textView9);

        }
    }

}