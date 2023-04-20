package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CashInAdapter extends BaseAdapter {
    Context context;
    ArrayList<BankCard> bankCards;
    //String[] countryNames;
    LayoutInflater inflter;

    //    public CashInAdapter(Context applicationContext, int[] flags, String[] countryNames) {
    public CashInAdapter(Context applicationContext, ArrayList<BankCard> bankCards) {
        this.context = applicationContext;
        this.bankCards = bankCards;
        //this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return bankCards.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.cash_in_adapter, null);
        // icon = (ImageView) view.findViewById(R.id.imageView);
        TextView balance = (TextView) view.findViewById(R.id.textView10);
        TextView cardNumber = (TextView) view.findViewById(R.id.textView11);
        ImageView cardImage=view.findViewById(R.id.cardImage3);
        balance.setText(bankCards.get(i).balance);
        cardNumber.setText(bankCards.get(i).cardNumber);
        cardImage.setImageResource(bankCards.get(i).smallImageResourceID);
        //icon.setImageResource(flags[i]);
        //names.setText(countryNames[i]);
        return view;
    }

}