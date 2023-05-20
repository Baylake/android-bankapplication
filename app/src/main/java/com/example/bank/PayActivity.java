package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.TextWatcherAdapter;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {
    private Spinner spinner;
    private String login;
    private ArrayList<BankCard> bankCards;
    private EditText amount, cardNumber;
    private TextView userInfo, endTransaction;
    private Button transfer;
    private Integer selectedCardNumber, clickNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getSupportActionBar().hide();
        Bundle arguments = getIntent().getExtras();
        login = arguments.get("login").toString();
        // Устанавливаем градиентный фон для корневого представления
        View view = findViewById(R.id.ConstraintLayoutPayActivity);
        view.setBackground(AnimatedBackground.createGradient());
        DataBase dataBase = new DataBase();
        bankCards = dataBase.getBankCards(login);
        amount = findViewById(R.id.editTextAmount);
        cardNumber = findViewById(R.id.editTextCardNumber);
        spinner = findViewById(R.id.spinner3);
        userInfo = findViewById(R.id.userInformaiton);
        userInfo.setText("");
        endTransaction = findViewById(R.id.endTransaction);
        endTransaction.setText("");
        transfer = findViewById(R.id.buttonTransfer2);
        CashInAdapter cashInAdapter1 = new CashInAdapter(getApplicationContext(), bankCards);
        spinner.setAdapter(cashInAdapter1);
        spinner.setOnItemSelectedListener(itemSelectedListener1);
        transfer.setOnClickListener(buttonsOnClickListener);
        clickNumber = 0;
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clickNumber = 0;
                transfer.setBackgroundColor(getColor(R.color.blue));
                userInfo.setText("");
                endTransaction.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clickNumber = 0;
                transfer.setBackgroundColor(getColor(R.color.blue));
                userInfo.setText("");
                endTransaction.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedCardNumber = i;
            clickNumber = 0;
            transfer.setBackgroundColor(getColor(R.color.blue));
            userInfo.setText("");
            endTransaction.setText("");

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    View.OnClickListener buttonsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {//проверить на то что edit text не пустой
            if (view.getId() == R.id.buttonTransfer2) {
                //Продолжать тут
                //Выбраны не одинаковые карты
                String text = amount.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Не введено сколько перевести!", Toast.LENGTH_LONG).show();
                } else {
                    Integer change = Integer.parseInt(text);
                    Integer balanceCardFrom = Integer.parseInt(bankCards.get(selectedCardNumber).balance);
                    //Если баланс на карте меньше чем хочет перевести
                    if (balanceCardFrom < change) {
                        Toast.makeText(getApplicationContext(), "Недостаточно средств!", Toast.LENGTH_LONG).show();
                    } else {//Если баланс на карте больше или равен change
                        if (bankCards.get(selectedCardNumber).cardNumber.equals(cardNumber.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Выбраны одинаковые карты!", Toast.LENGTH_LONG).show();
                        } else {
                            DataBase dataBase = new DataBase();
                            dataBase.selectCardExists(cardNumber.getText().toString());
                            if (!dataBase.mapAnswer.isEmpty()) {
                                if (clickNumber == 1) {
                                    dataBase.transfer(bankCards.get(selectedCardNumber).cardNumber, cardNumber.getText().toString(), amount.getText().toString());
                                    startMainActivity(view);
                                }
                                if (clickNumber == 0) {
                                    String userID = dataBase.mapAnswer.get(0).get("users_user_id");
                                    dataBase.selectUserName(userID);
                                    Log.i("EXISTS", dataBase.mapAnswer.toString());
                                    String firstName = dataBase.mapAnswer.get(0).get("user_first_name");
                                    String lastName = dataBase.mapAnswer.get(0).get("user_last_name");
                                    String patronymic = dataBase.mapAnswer.get(0).get("user_patronymic");
                                    userInfo.setText("Получатель: " + lastName + " " + firstName + " " + patronymic);
                                    endTransaction.setText("Чтобы завершить транзакцию, нажмите еще раз на кнопку ПЕРЕВЕСТИ");
                                    clickNumber++;
                                    transfer.setBackgroundColor(getColor(R.color.red));
                                }


                            } else {
                                Toast.makeText(getApplicationContext(), "Такого пользователя нет", Toast.LENGTH_LONG).show();
                            }
                        }

                        //startAllCardsActivity(view);
                    }
                }
            }
            //if(view.getId()==R.id.buttonSwap){
            //  spinner1.setSelection(cardNumberTo);
            // spinner2.setSelection(cardNumberFrom);
            // }

        }


    };
    public void startMainActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}