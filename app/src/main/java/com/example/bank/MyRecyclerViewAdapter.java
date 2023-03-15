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
        //holder.allCards.setBackgroundColor(R.color.purple_200);
    }

    @Override
    public int getItemCount() {
        return bankCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageButton imageButtonPlus;
        final TextView header;
        final Button allCards;
        ViewHolder(View view){
            super(view);
            imageButtonPlus = view.findViewById(R.id.imageButton3);
            header = view.findViewById(R.id.textView5);
            allCards = view.findViewById(R.id.button);
        }
    }
}