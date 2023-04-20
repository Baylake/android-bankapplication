package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.HttpURLConnection;

//TODO Сделать Toast с выводом данных sql
//TODO Подумать  что будет логином для регистрации
//ЕСЛИ НЕ РАБОТАЕТ СЕРВЕР НАДО ПРОВЕРИТЬ В DATABASE АДРЕС И ПОРТ, АККУРАТНО ОБНОВИТЬ БАЗУ ДАННЫХ
//В iNDEX.PHP ПРОВЕРИТЬ ПОДКЛЮЧЕНИЕ К БД И ПО НАДОБНОСТИ ДОБАВИТЬ ПОРТ, ЕСЛИ ЛОКАЛЬНЫЙ СЕРВЕР ВКЛЮЧИТЬ ВАЙФАЙ
//ПРИ ПОДРУБКЕ НА ХОСТ ИЗМЕНИТЬ ПОДКЛЮЧЕНИЕ К БАЗЕ В iNDEX.PHP,В DATABASE.JAVA ИЗМЕНИТЬ АДРЕС. СЛЕДИТЬ ЗА ПОРТАМИ!!1
public class PasswordActivity extends AppCompatActivity {
    HttpURLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(OnClickListener);
        button2.setOnClickListener(OnClickListener);

    }

    /**
     * Listens two buttons
     **/
    View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView one = findViewById(R.id.imageView_setOne);
            ImageView two = findViewById(R.id.imageView_setTwo);
            ImageView three = findViewById(R.id.imageView_setTwo);
            ImageView four = findViewById(R.id.imageView_setTwo);
            ImageView five = findViewById(R.id.imageView_setTwo);
            int count = 0;
            switch (count) {
                case 1:
                    one.setImageResource(R.drawable.);

                    //ЖЕСКО ТЕСТИРУЮ
                    DataBase dataBase = new DataBase();
                    dataBase.selectUsers("valerik228");
                    break;

                case 5:
                    startMainActivity(v);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Starts main activity
     **/
    public void startMainActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}