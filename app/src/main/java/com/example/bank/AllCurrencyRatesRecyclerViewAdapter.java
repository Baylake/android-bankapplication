package com.example.bank;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AllCurrencyRatesRecyclerViewAdapter extends RecyclerView.Adapter<AllCurrencyRatesRecyclerViewAdapter.ViewHolder> {

    CurrencyRate currencyRate;
    private final LayoutInflater inflater;


    public AllCurrencyRatesRecyclerViewAdapter(Context context,CurrencyRate currencyRate) {
    this.currencyRate=currencyRate;
    this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AllCurrencyRatesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_adapter_all_currency_rates, parent, false);

        return new AllCurrencyRatesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCurrencyRatesRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.image.setImageResource(currencyRate.currencies.get(position).image);
        if(currencyRate.currencies.get(position).name.length()>20){
            holder.name.setText(currencyRate.currencies.get(position).name.substring(0,20)+"...");
        }
        else{
            holder.name.setText(currencyRate.currencies.get(position).name);
        }

        if (currencyRate.currencies.get(position).nominal==1) {
        holder.charCode.setText(currencyRate.currencies.get(position).charCode);
        }
        else {
            holder.charCode.setText(currencyRate.currencies.get(position).nominal + " " + currencyRate.currencies.get(position).charCode);
        }

        holder.value.setText(currencyRate.currencies.get(position).value.toString());
        if(currencyRate.currencies.get(position).value>currencyRate.currencies.get(position+43).value){
            holder.arrow.setImageResource(R.drawable.main_activity_arrow_up);
        }
        else if(currencyRate.currencies.get(position).value.equals(currencyRate.currencies.get(position+43).value)){
            holder.arrow.setImageResource(R.drawable.main_activity_arrow_equal);
        }
        else{
            holder.arrow.setImageResource(R.drawable.main_activity_arrow_down);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CurrencyRatesGraphActivity.class);
                // Передача строки с ключом "message" и значением "Hello"
                intent.putExtra("charCode", currencyRate.currencies.get(position).charCode);
                // Запуск SecondActivity
                view.getContext().startActivity(intent);
                //startActivity(intent);
            }
        });
        //holder.name.setText("123");

    }


    @Override
    public int getItemCount() {
        return currencyRate.currencies.size()/2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView arrow;
        TextView charCode;
        TextView name;
        TextView value;

        LinearLayout linearLayout;
        ViewHolder(View view){
            super(view);
            image=view.findViewById(R.id.allCurrencyImage1);
            arrow=view.findViewById(R.id.allCurrencyArrow1);
            charCode=view.findViewById(R.id.allCurrencyCharCode);
            name=view.findViewById(R.id.allCurrencyName);
            value=view.findViewById(R.id.allCurrencyValue);
            linearLayout=view.findViewById(R.id.allCurrencyLinearLayout);
        }
    }

}



