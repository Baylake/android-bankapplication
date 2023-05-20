package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.math.*;
/**
 * \brief активити, на которой рисуется график
 *
 */
public class CurrencyRatesGraphActivity extends AppCompatActivity {

    GraphView graph;
    TextView textMinimum,textMaximum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_rates_graph);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        View view = findViewById(R.id.ConstraintLayoutGraph);
        textMinimum=findViewById(R.id.textMinimum);
        textMaximum=findViewById(R.id.textMaximum);
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
        int indexMin=0,indexMax=0;
        Float min=currencyRate.currencies.get(0).value;
        Float max=currencyRate.currencies.get(0).value;
        for(int i=0;i<currencyRate.currencies.size();i++){
            if(min>currencyRate.currencies.get(i).value){
                indexMin=i;
                min=currencyRate.currencies.get(i).value;
            }
            if(max<currencyRate.currencies.get(i).value){
                indexMax=i;
                max=currencyRate.currencies.get(i).value;
            }
        }


        textMaximum.setText("Максимум: "+String.format("%.2f", max)+"₽ ( "+currencyRate.currencies.get(indexMax).date+" )");
        textMinimum.setText("Минимум: "+String.format("%.2f", min)+"₽ ( "+currencyRate.currencies.get(indexMin).date+" )");
    }
}