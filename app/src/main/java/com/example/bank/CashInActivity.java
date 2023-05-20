package com.example.bank;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CashInActivity extends AppCompatActivity {
    private TextView title1;
    private TextView title2;
    private TextView title3;
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText editTextChange;
    private final String login="valerik228";

    private Button transfer;
    private Button swap;

    private Integer cardNumberFrom,cardNumberTo;
    private ArrayList<BankCard> bankCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_in);
        title1=findViewById(R.id.textViewTitle1);
        title2=findViewById(R.id.textViewTitle2);
        title3=findViewById(R.id.textViewTitle3);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);

        transfer=findViewById(R.id.buttonTransfer);
        swap=findViewById(R.id.buttonSwap);

        editTextChange=findViewById(R.id.editTextChange);
        DataBase dataBase=new DataBase();

        title1.setText("Откуда");
        title2.setText("Куда");
        title3.setText("Сколько");

        bankCards=dataBase.getBankCards(login);

        CashInAdapter cashInAdapter1=new CashInAdapter(getApplicationContext(), bankCards);
        spinner1.setAdapter(cashInAdapter1);

        CashInAdapter cashInAdapter2=new CashInAdapter(getApplicationContext(), bankCards);
        spinner2.setAdapter(cashInAdapter2);
        spinner1.setOnItemSelectedListener(itemSelectedListener1);
        spinner2.setOnItemSelectedListener(itemSelectedListener2);
        transfer.setOnClickListener(buttonsOnClickListener);
        swap.setOnClickListener(buttonsOnClickListener);

    }
    View.OnClickListener buttonsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {//проверить на то что edit text не пустой
            if(view.getId()==R.id.buttonTransfer){
                //Продолжать тут
                //Выбраны не одинаковые карты
                if(cardNumberFrom!=cardNumberTo){
                    String text=editTextChange.getText().toString();
                    if(text.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Не введено сколько перевести!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Integer change=Integer.parseInt(text);
                        Integer balanceCardFrom=Integer.parseInt(bankCards.get(cardNumberFrom).balance);
                        //Если баланс на карте меньше чем хочет перевести
                        if(balanceCardFrom<change){
                            Toast.makeText(getApplicationContext(), "Недостаточно средств!", Toast.LENGTH_LONG).show();
                        }
                        else{//Если баланс на карте больше или равен change
                            DataBase dataBase=new DataBase();
                            dataBase.transfer(bankCards.get(cardNumberFrom).cardNumber,bankCards.get(cardNumberTo).cardNumber,change.toString());
                            startAllCardsActivity(view);
                        }
                    }

                }
                else{//Выбраны одинаковые карты
                    Toast.makeText(getApplicationContext(), "Выбраны одинаковые карты!", Toast.LENGTH_LONG).show();
                }
            }
            if(view.getId()==R.id.buttonSwap){
                spinner1.setSelection(cardNumberTo);
                spinner2.setSelection(cardNumberFrom);
            }

        }
    };
    AdapterView.OnItemSelectedListener itemSelectedListener1=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            cardNumberFrom=i;

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    AdapterView.OnItemSelectedListener itemSelectedListener2=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            cardNumberTo=i;

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public void startAllCardsActivity(View v) {
        Intent intent = new Intent(this, AllCardsActivity.class);
        startActivity(intent);
        finish();
    }



}