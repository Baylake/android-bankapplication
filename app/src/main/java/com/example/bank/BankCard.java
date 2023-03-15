package com.example.bank;

import java.util.Date;
//TODO проверка валидности(регулярные выражения)
public class BankCard {
    String cardNumber = "";
    String paySystemName = "";
    String memberName = "";
    String date;
    String cvvCode = "";
    String pinCode = "";

    public BankCard(String cardNumber, String paySystemName, String memberName, String date, String cvvCode, String pinCode) {
        this.cardNumber = cardNumber;
        this.paySystemName = paySystemName;
        this.memberName = memberName;
        this.date = date;
        this.cvvCode = cvvCode;
        this.pinCode = pinCode;
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

    @Override
    public String toString() {
        return "BankCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", paySystemName='" + paySystemName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", date=" + date +
                ", cvvCode='" + cvvCode + '\'' +
                ", pinCode='" + pinCode + '\'' +
                '}';
    }
}