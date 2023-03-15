package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//TODO Сделать Toast с выводом данных sql
//TODO Подумать  что будет логином для регистрации
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
            TextView answer = findViewById(R.id.textView4);
            TextView suka = findViewById(R.id.textView);
            EditText Number1 = findViewById(R.id.editTextNumber2);
            EditText Number2 = findViewById(R.id.editTextNumber4);
            switch (v.getId()) {
                case R.id.button:
                    Integer int1;
                    int1 = Integer.parseInt(Number1.getText().toString());
                    Integer int2;
                    int2 = Integer.parseInt(Number2.getText().toString());
                    Integer ans = int1 + int2;
                    answer.setText(ans.toString());
                    //ЖЕСКО ТЕСТИРУЮ
                    DataBase dataBase = new DataBase();
                    dataBase.selectLoginAndPassword("valerik228");
                    //ЖЕСКО ТЕСТИРУЮ
                    break;

                case R.id.button2:

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