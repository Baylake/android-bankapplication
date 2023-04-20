package com.example.bank;

import android.content.res.Resources;

import java.util.Date;

//TODO проверка валидности(регулярные выражения)
//TODO сделать переменную, которая будет хранить id картинки карты(или даже двух картинок маленькую и большую)
//TODO передавать логин по приложению (а то логин повсюду захардкожен)
//TODO нарисовать маленькую и большую картинку для карт MIR VISA MASTERCARD!
//TODO пройтись по всем xml и переименовать элементы
//Золотая-мир, Зеленая мастеркард, Синяя-visa
public class BankCard {
    String cardNumber = "";
    String paySystemName = "";
    String memberName = "";
    String date = "";
    String cvvCode = "";
    String pinCode = "";
    String balance = "";

    Integer smallImageResourceID;

    public BankCard(String cardNumber, String paySystemName, String memberName, String date, String cvvCode, String pinCode, String balance,Integer smallImageResourceID) {
        this.cardNumber = cardNumber;
        this.paySystemName = paySystemName;
        this.memberName = memberName;
        this.date = date;
        this.cvvCode = cvvCode;
        this.pinCode = pinCode;
        this.balance = balance;
        this.smallImageResourceID=smallImageResourceID;
        if (this.paySystemName.equals("MIR")) {
            this.smallImageResourceID = R.drawable.main_activity_card_mir;
        } else if (this.paySystemName.equals("VISA")) {
            this.smallImageResourceID = R.drawable.main_activity_card_visa;
        } else if (this.paySystemName.equals("MASTER CARD")) {
            this.smallImageResourceID = R.drawable.main_activity_card_master_card;
        }else{
            this.smallImageResourceID = R.drawable.main_activity_card_unknown;
        }

    }

    public BankCard() {

    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPaySystemName() {
        return paySystemName;
    }

    public void setPaySystemName(String paySystemName) {
        this.paySystemName = paySystemName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void clear() {
        cardNumber = "";
        paySystemName = "";
        memberName = "";
        date = "";
        cvvCode = "";
        pinCode = "";
        balance = "";
        smallImageResourceID = R.drawable.main_activity_card_master_card;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", paySystemName='" + paySystemName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", date='" + date + '\'' +
                ", cvvCode='" + cvvCode + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", balance='" + balance + '\'' +
                ", smallImageResourceID=" + smallImageResourceID +
                '}';
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }
    public void setSmallImageResourceID(int smallImageResourceID) {
        this.smallImageResourceID = smallImageResourceID;
    }
}
