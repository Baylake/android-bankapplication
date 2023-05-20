package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CurrencyRatesGraphActivity extends AppCompatActivity {

    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_rates_graph);
        Intent intent = getIntent();
        View view = findViewById(R.id.ConstraintLayoutGraph);
        view.setBackground(AnimatedBackground.createGradient());
        // Получение номера объекта с помощью getStringExtra()
        String charCode = intent.getStringExtra("charCode");
        CurrencyRate currencyRate=new CurrencyRate();
        currencyRate.addCurrencyRate(charCode,30);
        float[] data=new float[currencyRate.currencies.size()];
        for(int i=0;i<data.length;i++){//реверс массива
            data[i]=currencyRate.currencies.get(data.length-i-1).value/currencyRate.currencies.get(data.length-i-1).nominal;//учитывается возможность изменения номинала
        }
        //data[3]=currencyRate1.currencies.get(3).value;
        graph = findViewById(R.id.graph_view);
        //float[] testdata=new float[]{1,2};
        graph.setData(data);
    }
}