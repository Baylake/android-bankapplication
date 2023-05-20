package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//TODO Сделать фрагмент с полной информацией о картах и возможных действиях(grid)
public class MainActivity extends AppCompatActivity {
    Application app = this.getApplication();
    private UsersViewModel mUsersViewModel = new UsersViewModel(app);
    final static String[] login = {""};
    static String login_string = "";
    Context context = this;

    private MainRecyclerViewAdapter adapter;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Устанавливаем градиентный фон для корневого представления
        View view = findViewById(R.id.ConstraintLayoutMainActivity);
        view.setBackground(AnimatedBackground.createGradient());

        try {

            mUsersViewModel.getAllUsers().observe(this, new Observer<List<LocalDatabase>>() {
                @Override
                public void onChanged(List<LocalDatabase> localDatabases) {
                    login[0] = localDatabases.get(0).userLogin;

                    Toast toast1 = new Toast(context);
                    Toast.makeText(context, " login /"+ login[0] +"/",Toast.LENGTH_SHORT ).show();
                    Log.i("run","ya pokakal");
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.i("run","ya obosralsya");
            ArrayList<BankCard> cards = new ArrayList<>();
            DataBase dataBase = new DataBase();
            Log.i("xd",login[0]);
            cards = dataBase.getBankCards(login[0]);
            CurrencyRate currencyRate = new CurrencyRate();

            currencyRate.addCurrencyRate("USD", 2);
            currencyRate.addCurrencyRate("EUR", 2);


            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = findViewById(R.id.RecyclerView1);

            recyclerView.setLayoutManager(layoutManager);
            adapter = new MainRecyclerViewAdapter(this, cards, currencyRate);

            recyclerView.setAdapter(adapter);


//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "Ну типа соси ",Toast.LENGTH_SHORT ).show();
//        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
//    }
    }
    //protected void onStart(Bundle savedInstanceState){

        //adapter = new MyRecyclerViewAdapter(this, viewColors, pages);
        //adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);
//        CurrencyRate currencyRate1=new CurrencyRate();
//        currencyRate1.addCurrencyRate("EUR",30);
//        float[] data=new float[currencyRate1.currencies.size()];
//        for(int i=0;i<data.length;i++){
//            data[i]=currencyRate1.currencies.get(data.length-i-1).value;
//        }
//        //data[3]=currencyRate1.currencies.get(3).value;
//       graph = findViewById(R.id.graph_view);
//       graph.setData(data);
   // }
}