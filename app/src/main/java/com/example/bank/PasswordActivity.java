package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.List;

//TODO Сделать Toast с выводом данных sql
//TODO Подумать  что будет логином для регистрации
//TODO Исправить везде верстку
//TODO Разобраться с паролем
//ЕСЛИ НЕ РАБОТАЕТ СЕРВЕР НАДО ПРОВЕРИТЬ В DATABASE АДРЕС И ПОРТ, АККУРАТНО ОБНОВИТЬ БАЗУ ДАННЫХ
//В iNDEX.PHP ПРОВЕРИТЬ ПОДКЛЮЧЕНИЕ К БД И ПО НАДОБНОСТИ ДОБАВИТЬ ПОРТ, ЕСЛИ ЛОКАЛЬНЫЙ СЕРВЕР ВКЛЮЧИТЬ ВАЙФАЙ
//ПРИ ПОДРУБКЕ НА ХОСТ ИЗМЕНИТЬ ПОДКЛЮЧЕНИЕ К БАЗЕ В iNDEX.PHP,В DATABASE.JAVA ИЗМЕНИТЬ АДРЕС. СЛЕДИТЬ ЗА ПОРТАМИ!!1
public class PasswordActivity extends AppCompatActivity {
    HttpURLConnection conn;
static int count;
    private UsersViewModel mUsersViewModel;
static String EnteredPass = "";
    private UsersRepository mRepository;
static String CorrectPass = "12348";
public final Context context = this;
String login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().hide();
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

        ButtonTouchListener buttonTouchListener = new ButtonTouchListener(R.color.white, R.color.grey, this);
// Устанавливаем слушатель касания для кнопки
        button1.setOnTouchListener(buttonTouchListener);
        button2.setOnTouchListener(buttonTouchListener);
        button3.setOnTouchListener(buttonTouchListener);
        button4.setOnTouchListener(buttonTouchListener);
        button5.setOnTouchListener(buttonTouchListener);
        button6.setOnTouchListener(buttonTouchListener);
        button7.setOnTouchListener(buttonTouchListener);
        button8.setOnTouchListener(buttonTouchListener);
        button9.setOnTouchListener(buttonTouchListener);
        button0.setOnTouchListener(buttonTouchListener);
        button_delete.setOnTouchListener(buttonTouchListener);
        int count = 0;
//        Log.i("database","before");
//        mRepository = new UsersRepository(app);
//        Log.i("database","after");
        Button button_delete_db = findViewById(R.id.button_delete);
        button_delete_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsersViewModel.deleteAllUsers();
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        Application app = this.getApplication();
        Log.i("database","before");
        //mRepository = new UsersRepository(app);
        FragmentManager manager = getSupportFragmentManager();
        MyDialogFragment dialog = new MyDialogFragment();
        mUsersViewModel = new UsersViewModel(app);
        try {
            mUsersViewModel.getAllUsers().observe(this, new Observer<List<LocalDatabase>>() {
                @Override
                public void onChanged(List<LocalDatabase> localDatabases) {
                    int xd = localDatabases.size();
                    try{if (!localDatabases.isEmpty()){dialog.cancel();}}
                    catch(NullPointerException e){}
                    String log;
                    try {log = localDatabases.get(0).userLogin;}
                    catch (IndexOutOfBoundsException e){log="";}
                    login=log;
                    DataBase db=new DataBase();
                    db.selectPassword(login);
                    try {CorrectPass=db.mapAnswer.get(0).get("user_password");}
                    catch (IndexOutOfBoundsException e){}
                    Toast toast1 = new Toast(context);
                    toast1.makeText(context, "size " + xd +" login "+ log,Toast.LENGTH_SHORT ).show();

                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


//        buttonEnter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                UsersViewModel user = new UsersViewModel(app);
//                String log = dialog.login.getText().toString();
//                String passw = dialog.pass.getText().toString();
//                LocalDatabase user_insert = new LocalDatabase(1, log,passw);
//                user.insert(user_insert);
//                String entered = user.getAllUsers().toString();
//                Toast toast1 = new Toast(context);
//                Toast.makeText(context, entered,Toast.LENGTH_SHORT ).show();
//            }
//        });
        dialog.show(manager, "myDialog");
        Log.i("database","after");
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
//                    DataBase dataBase=new DataBase();
//                    dataBase.selectLogins();

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
                        Toast.makeText(context, "Неверный пароль, введите пароль снова ",Toast.LENGTH_SHORT ).show();
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
        intent.putExtra("login", login);
        startActivity(intent);
    }
    public static String removeLastChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }

}