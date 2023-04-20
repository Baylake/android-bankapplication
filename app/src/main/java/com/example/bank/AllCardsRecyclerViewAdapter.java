package com.example.bank;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AllCardsRecyclerViewAdapter extends RecyclerView.Adapter<AllCardsRecyclerViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<BankCard> bankCards;
    private Context context;
    private Integer clickCounter;
    public AllCardsRecyclerViewAdapter(Context context, List<BankCard> bankCards) {
        this.bankCards = bankCards;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
        this.clickCounter=1;
    }

    @NonNull
    @Override
    public AllCardsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_all_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.i("fragment",bankCards.toString());
        Log.i("fragment","position"+holder.getAdapterPosition());
        int pos=holder.getAdapterPosition();

        holder.textView13.setText(bankCards.get(pos).cardNumber);
        holder.textView14.setText(bankCards.get(pos).cvvCode);
        holder.textView15.setText(bankCards.get(pos).date);
        holder.textView16.setText(bankCards.get(pos).balance+" ₽");

        holder.linearLayout.removeAllViews();
        holder.linearLayout.addView(holder.imageView4);
        holder.imageView4.setImageResource(bankCards.get(pos).bigImageResourceID);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((clickCounter%2)==0){
                    holder.linearLayout.removeAllViews();
                    holder.linearLayout.addView( holder.imageView4);
                    clickCounter++;
                }else{
                    holder.linearLayout.removeAllViews();
                    holder.linearLayout.addView(holder.textView13);
                    holder.linearLayout.addView(holder.textView14);
                    holder.linearLayout.addView(holder.textView15);
                    clickCounter++;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView13;
        TextView textView14;
        TextView textView15;
        TextView textView16;
        ImageView imageView4;
        ViewHolder(View view){
            super(view);
            linearLayout=view.findViewById(R.id.LinearLayout3);
            textView13=view.findViewById(R.id.textView13);
            textView14=view.findViewById(R.id.textView14);
            textView15=view.findViewById(R.id.textView15);
            textView16=view.findViewById(R.id.textView16);
            imageView4=view.findViewById(R.id.imageView4);
        }
    }

}