package com.example.bank;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//TODO Кнопку Out переместить из recycler_view_all_cards в activity_all_cards
//TODO парсить с фанпея цену дивайнов в рубли!
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<BankCard> bankCards;
    private Context context;

    private final CurrencyRate currencyRate;
    private static final int TYPE_CARDS = 0;
    private static final int TYPE_CURRENCY = 1;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_CARDS;
        }
        if (position == 1) {
            return TYPE_CURRENCY;
        }
        return TYPE_CARDS;

    }

    public MainRecyclerViewAdapter(Context context, ArrayList<BankCard> bankCards, CurrencyRate currencyRate) {
        this.bankCards = bankCards;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.currencyRate = currencyRate;
    }

    @NonNull
    @Override
    public MainRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_CARDS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_main_1, parent, false);
            return new ViewHolder(view, TYPE_CARDS);
        } else if (viewType == TYPE_CURRENCY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_main_2, parent, false);
            return new ViewHolder(view, TYPE_CURRENCY);
        }
        throw new RuntimeException("Invalid view type");
//        View view = inflater.inflate(R.layout.recycle_view_main_1, parent, false);
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        if (holder instanceof HeaderViewHolder) {
//            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
//            // привязываем данные к headerHolder
//        } else if (holder instanceof ItemViewHolder) {
//            ItemViewHolder itemHolder = (ItemViewHolder) holder;
//            // привязываем данные к itemHolder
//        }

        if (position == 1) {
            holder.allRates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, AllCurrencyRatesActivity.class));
                }
            });
            if (currencyRate.currencies.size() == 4) {//Все по плану
                holder.currencyName1.setText(currencyRate.currencies.get(0).name);
                holder.currencyName2.setText(currencyRate.currencies.get(2).name);

                holder.currencyCode1.setText(currencyRate.currencies.get(0).charCode);
                holder.currencyCode2.setText(currencyRate.currencies.get(2).charCode);

                holder.currencyValue1.setText(currencyRate.currencies.get(0).value.toString());
                holder.currencyValue2.setText(currencyRate.currencies.get(2).value.toString());

                holder.imageCurrency1.setImageResource(currencyRate.currencies.get(0).image);
                holder.imageCurrency2.setImageResource(currencyRate.currencies.get(2).image);

                if (currencyRate.currencies.get(0).value > currencyRate.currencies.get(1).value) {
                    holder.imageArrow1.setImageResource(R.drawable.main_activity_arrow_up);
                } else {
                    holder.imageArrow1.setImageResource(R.drawable.main_activity_arrow_down);
                }

                if (currencyRate.currencies.get(2).value > currencyRate.currencies.get(3).value) {
                    holder.imageArrow2.setImageResource(R.drawable.main_activity_arrow_up);
                } else {
                    holder.imageArrow2.setImageResource(R.drawable.main_activity_arrow_down);
                }
            } else {//На случай непредвиденной багулины(если день прошел на курсов за предыдущий день еще нет
                holder.currencyName1.setText(currencyRate.currencies.get(0).name);
                holder.currencyName2.setText(currencyRate.currencies.get(1).name);

                holder.currencyCode1.setText(currencyRate.currencies.get(0).charCode);
                holder.currencyCode2.setText(currencyRate.currencies.get(1).charCode);

                holder.currencyValue1.setText(currencyRate.currencies.get(0).value.toString());
                holder.currencyValue2.setText(currencyRate.currencies.get(1).value.toString());

                holder.imageCurrency1.setImageResource(currencyRate.currencies.get(0).image);
                holder.imageCurrency2.setImageResource(currencyRate.currencies.get(1).image);

                holder.imageArrow1.setImageResource(R.drawable.main_activity_arrow_up);


                holder.imageArrow1.setImageResource(R.drawable.main_activity_arrow_up);
                holder.imageArrow2.setImageResource(R.drawable.main_activity_arrow_up);
            }
        } else {

            holder.header.setText(R.string.header);

            Log.i("1", bankCards.toString());
            holder.cardBalance1.setText(bankCards.get(0).balance + " ₽");
            holder.cardBalance2.setText(bankCards.get(1).balance + " ₽");

            holder.cardNumber1.setText(bankCards.get(0).paySystemName + " ** " + bankCards.get(0).cardNumber.substring(12));
            holder.cardNumber2.setText(bankCards.get(1).paySystemName + " ** " + bankCards.get(1).cardNumber.substring(12));

            holder.imageViewCard1.setImageResource(bankCards.get(0).smallImageResourceID);
            holder.imageViewCard2.setImageResource(bankCards.get(1).smallImageResourceID);
            holder.allCards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, AllCardsActivity.class));
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return bankCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Переменные для TYPE_CARDS
        Button imageButtonPlus;
        TextView header;
        Button allCards;
        TextView cardBalance1, cardBalance2, cardNumber1, cardNumber2;
        ImageView imageViewCard1, imageViewCard2;

        //Переменный для TYPE_CURRENCY
        TextView currencyName1, currencyName2, currencyCode1, currencyCode2, currencyValue1, currencyValue2;
        ImageView imageCurrency1, imageCurrency2, imageArrow1, imageArrow2;

        Button allRates;

        ViewHolder(View view, int viewType) {
            super(view);

            if (viewType == TYPE_CARDS) {
                imageButtonPlus = view.findViewById(R.id.button4);
                header = view.findViewById(R.id.textView5);
                allCards = view.findViewById(R.id.button3);
                cardBalance1 = view.findViewById(R.id.textView6);
                cardBalance2 = view.findViewById(R.id.textView8);
                cardNumber1 = view.findViewById(R.id.textView7);
                cardNumber2 = view.findViewById(R.id.textView9);
                imageViewCard1 = view.findViewById(R.id.imageViewCard1);
                imageViewCard2 = view.findViewById(R.id.imageViewCard2);
            }
            if (viewType == TYPE_CURRENCY) {
                currencyName1 = view.findViewById(R.id.textCurrencyName1);
                currencyName2 = view.findViewById(R.id.textCurrencyName2);
                currencyCode1 = view.findViewById(R.id.textCurrencyCode1);
                currencyCode2 = view.findViewById(R.id.textCurrencyCode2);
                currencyValue1 = view.findViewById(R.id.textCurrencyValue1);
                currencyValue2 = view.findViewById(R.id.textCurrencyValue2);
                imageCurrency1 = view.findViewById(R.id.imageViewCurrency1);
                imageCurrency2 = view.findViewById(R.id.imageViewCurrency2);
                imageArrow1 = view.findViewById(R.id.imageArrow1);
                imageArrow2 = view.findViewById(R.id.imageArrow2);
                allRates = view.findViewById(R.id.buttonAllRates);
            }


        }
    }

}