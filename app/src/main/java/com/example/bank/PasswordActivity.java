package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//TODO Сделать Toast с выводом данных sql
//TODO Подумать  что будет логином для регистрации
//ЕСЛИ НЕ РАБОТАЕТ СЕРВЕР НАДО ПРОВЕРИТЬ В DATABASE АДРЕС И ПОРТ, АККУРАТНО ОБНОВИТЬ БАЗУ ДАННЫХ
//В iNDEX.PHP ПРОВЕРИТЬ ПОДКЛЮЧЕНИЕ К БД И ПО НАДОБНОСТИ ДОБАВИТЬ ПОРТ, ЕСЛИ ЛОКАЛЬНЫЙ СЕРВЕР ВКЛЮЧИТЬ ВАЙФАЙ
//ПРИ ПОДРУБКЕ НА ХОСТ ИЗМЕНИТЬ ПОДКЛЮЧЕНИЕ К БАЗЕ В iNDEX.PHP,В DATABASE.JAVA ИЗМЕНИТЬ АДРЕС. СЛЕДИТЬ ЗА ПОРТАМИ!!1
public class PasswordActivity extends AppCompatActivity {
    HttpURLConnection conn;
static int count;
static String EnteredPass = "";
static final String CorrectPass = "12348";
public final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        DataBase dataBase=new DataBase();
        dataBase.selectDollarAndEuroCurrencyRate();
        ImageButton button1 = findViewById(R.id.image_Button1);
        ImageButton button2 = findViewById(R.id.image_Button2);
        ImageButton button3 = findViewById(R.id.image_Button3);
        ImageButton button4 = findViewById(R.id.image_Button4);
        ImageButton button5 = findViewById(R.id.image_Button5);
        ImageButton button6 = findViewById(R.id.image_Button6);
        ImageButton button7 = findViewById(R.id.image_Button7);
        ImageButton button8 = findViewById(R.id.image_Button8);
        ImageButton button9 = findViewById(R.id.image_Button9);
        ImageButton button0 = findViewById(R.id.image_Button0);
        ImageButton button_delete = findViewById(R.id.image_ButtonDelete);

        button1.setOnClickListener(OnClickListener);
        button2.setOnClickListener(OnClickListener);
        button3.setOnClickListener(OnClickListener);
        button4.setOnClickListener(OnClickListener);
        button5.setOnClickListener(OnClickListener);
        button6.setOnClickListener(OnClickListener);
        button7.setOnClickListener(OnClickListener);
        button8.setOnClickListener(OnClickListener);
        button9.setOnClickListener(OnClickListener);
        button0.setOnClickListener(OnClickListener);
        button_delete.setOnClickListener(OnClickListener);
        int count = 0;
    }

    /**
     * Listens two buttons
     **/
    View.OnClickListener OnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ImageView one = findViewById(R.id.imageView_setOne);
            ImageView two = findViewById(R.id.imageView_setTwo);
            ImageView three = findViewById(R.id.imageView_setThree);
            ImageView four = findViewById(R.id.imageView_setFour);
            ImageView five = findViewById(R.id.imageView_setFive);
            Resources resources = getResources();
            if (v.getId()==R.id.image_ButtonDelete)
            {
                if (count > 0) {
                    count--;
                    EnteredPass = removeLastChar(EnteredPass);
                }
            }
            else {
                count++;

            }
            switch (count) {
                case 0:
                    one.setImageResource(R.drawable.indicator);
                    two.setImageResource(R.drawable.indicator);
                    three.setImageResource(R.drawable.indicator);
                    four.setImageResource(R.drawable.indicator);
                    five.setImageResource(R.drawable.indicator);
                    EnteredPass = EnteredPass + v.getContentDescription();
                    Log.i("count",EnteredPass);
                    Log.d("count","count = 0");
                    break;
                case 1:
                    one.setImageResource(R.drawable.indicator_green);
                    two.setImageResource(R.drawable.indicator);
                    three.setImageResource(R.drawable.indicator);
                    four.setImageResource(R.drawable.indicator);
                    five.setImageResource(R.drawable.indicator);
                    EnteredPass = EnteredPass + v.getContentDescription();
                    Log.i("count",EnteredPass);
Log.d("count","count = 1");
                    //ЖЕСКО ТЕСТИРУЮ
                   // DataBase dataBase = new DataBase();
                    //dataBase.selectUsers("valerik228");
                    break;
                case 2:
                    one.setImageResource(R.drawable.indicator_green);
                    two.setImageResource(R.drawable.indicator_green);
                    three.setImageResource(R.drawable.indicator);
                    four.setImageResource(R.drawable.indicator);
                    five.setImageResource(R.drawable.indicator);
                    Log.d("count","count = 2");
                    EnteredPass = EnteredPass + v.getContentDescription();
                    Log.i("count",EnteredPass);
                    break;
                case 3:
                    one.setImageResource(R.drawable.indicator_green);
                    two.setImageResource(R.drawable.indicator_green);
                    three.setImageResource(R.drawable.indicator_green);
                    four.setImageResource(R.drawable.indicator);
                    five.setImageResource(R.drawable.indicator);
                    Log.d("count","count = 3");
                    EnteredPass = EnteredPass + v.getContentDescription();
                    Log.i("count",EnteredPass);
                    break;
                case 4:
                    one.setImageResource(R.drawable.indicator_green);
                    two.setImageResource(R.drawable.indicator_green);
                    three.setImageResource(R.drawable.indicator_green);
                    four.setImageResource(R.drawable.indicator_green);
                    five.setImageResource(R.drawable.indicator);
                    Log.d("count","count = 4");
                    EnteredPass = EnteredPass + v.getContentDescription();
                    Log.i("count",EnteredPass);
                    break;
                case 5:
                    one.setImageResource(R.drawable.indicator_green);
                    two.setImageResource(R.drawable.indicator_green);
                    three.setImageResource(R.drawable.indicator_green);
                    four.setImageResource(R.drawable.indicator_green);
                    five.setImageResource(R.drawable.indicator_green);
                    Log.d("count","count = 5");
                    EnteredPass = EnteredPass + v.getContentDescription();
                    Log.i("count",EnteredPass);
                    if (EnteredPass.equals(CorrectPass)) {
                        startMainActivity(v);
                        count = 0;
                        one.setImageResource(R.drawable.indicator);
                        two.setImageResource(R.drawable.indicator);
                        three.setImageResource(R.drawable.indicator);
                        four.setImageResource(R.drawable.indicator);
                        five.setImageResource(R.drawable.indicator);
                        EnteredPass = "";
                    }
                    else {
                        count = 0;
                        one.setImageResource(R.drawable.indicator);
                        two.setImageResource(R.drawable.indicator);
                        three.setImageResource(R.drawable.indicator);
                        four.setImageResource(R.drawable.indicator);
                        five.setImageResource(R.drawable.indicator);
                        EnteredPass = "";
                        Log.i("count",EnteredPass);
                        Log.d("count","count = 0");
                        Toast toast = new Toast(context);
                        toast.makeText(context, "Неверный пароль, введите пароль снова ",Toast.LENGTH_SHORT ).show();
                    }
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
    public static String removeLastChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }

}