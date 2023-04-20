package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CashInActivity extends AppCompatActivity {
    private TextView title1;
    private TextView title2;
    private TextView title3;
    private Spinner spinner1;
    private Spinner spinner2;
    private String login="valerik228";

    private Button transfer;
    private Integer cardNumberFrom,cardNumberTo;
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
        DataBase dataBase=new DataBase();

        title1.setText("Откуда");
        title2.setText("Куда");
        title3.setText("Сколько");
        CashInAdapter cashInAdapter1=new CashInAdapter(getApplicationContext(),dataBase.getBankCards(login));
        spinner1.setAdapter(cashInAdapter1);

        CashInAdapter cashInAdapter2=new CashInAdapter(getApplicationContext(),dataBase.getBankCards(login));
        spinner2.setAdapter(cashInAdapter2);
        spinner1.setOnItemSelectedListener(itemSelectedListener1);
        spinner2.setOnItemSelectedListener(itemSelectedListener2);
        transfer.setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Продолжать тут
            Log.i("12345",cardNumberFrom.toString()+" "+cardNumberTo.toString());
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
}