package com.example.bank;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrencyRate {
    public ArrayList<MyCurrency> currencies;

    public CurrencyRate() {
        currencies=new ArrayList<>();
    }

    class MyCurrency {
        public Integer numCode;
        public String charCode;
        public Integer nominal;
        public String name;
        public Float value;
        public String date;
        public Integer image;

        public MyCurrency(Integer numCode, String charCode, Integer nominal, String name, Float value, String date, Integer image) {
            this.numCode = numCode;
            this.charCode = charCode;
            this.nominal = nominal;
            this.name = name;
            this.value = value;
            this.date = date;
            this.image = image;
        }

        @Override
        public String toString() {
            return "MyCurrency{" +
                    "numCode=" + numCode +
                    ", charCode='" + charCode + '\'' +
                    ", nominal=" + nominal +
                    ", name='" + name + '\'' +
                    ", value=" + value +
                    ", date='" + date + '\'' +
                    ", image=" + image +
                    '}';
        }
    }

    //        public Integer numCode;
//        public String charCode;
//        public Integer nominal;
//        public String name;
//        public Float value;
//        public String date;
//
    public void addCurrencyRate(String currencyCharCode, Integer numberOfDays) {
        DataBase dataBase = new DataBase();
        dataBase.selectCurrencyRate(currencyCharCode, numberOfDays);
        for (int i = 0; i < dataBase.mapAnswer.size(); i++) {
            Integer numCode = Integer.parseInt(dataBase.mapAnswer.get(i).get("num_code"));
            String charCode = dataBase.mapAnswer.get(i).get("char_code");
            Integer nominal = Integer.parseInt(dataBase.mapAnswer.get(i).get("nominal"));
            String name = dataBase.mapAnswer.get(i).get("name");
            Float value = Float.parseFloat(dataBase.mapAnswer.get(i).get("value"));
            String date = dataBase.mapAnswer.get(i).get("date").replace('-', '.');
            Integer image = getImageId(charCode);
            MyCurrency temp = new MyCurrency(numCode, charCode, nominal, name, value,date,image);
            currencies.add(temp);
        }
        currencies.toString();
    }
    void clear(){
        currencies.clear();
    }
    Integer getImageId(String charCode){
        Integer imageId=0;
        if(charCode.equals("AUD")){
            imageId=R.drawable.currency_rate_activity_aud;
        }
        if(charCode.equals("AZN")){
            imageId=R.drawable.currency_rate_activity_azn;
        }
        if(charCode.equals("GBP")){
            imageId=R.drawable.currency_rate_activity_gbp;
        }
        if(charCode.equals("AMD")){
            imageId=R.drawable.currency_rate_activity_amd;
        }
        if(charCode.equals("BYN")){
            imageId=R.drawable.currency_rate_activity_byn;
        }
        if(charCode.equals("BGN")){
            imageId=R.drawable.currency_rate_activity_bgn;
        }
        if(charCode.equals("BRL")){
            imageId=R.drawable.currency_rate_activity_brl;
        }
        if(charCode.equals("HUF")){
            imageId=R.drawable.currency_rate_activity_huf;
        }
        if(charCode.equals("VND")){
            imageId=R.drawable.currency_rate_activity_vnd;
        }
        if(charCode.equals("HKD")){
            imageId=R.drawable.currency_rate_activity_hkd;
        }
        if(charCode.equals("GEL")){
            imageId=R.drawable.currency_rate_activity_gel;
        }
        if(charCode.equals("DKK")){
            imageId=R.drawable.currency_rate_activity_dkk;
        }
        if(charCode.equals("AED")){
            imageId=R.drawable.currency_rate_activity_aed;
        }
        if(charCode.equals("USD")){
            imageId=R.drawable.currency_rate_activity_usd;
        }
        if(charCode.equals("EUR")){
            imageId=R.drawable.currency_rate_activity_eur;
        }
        if(charCode.equals("EGP")){
            imageId=R.drawable.currency_rate_activity_egp;
        }
        if(charCode.equals("INR")){
            imageId=R.drawable.currency_rate_activity_inr;
        }
        if(charCode.equals("IDR")){
            imageId=R.drawable.currency_rate_activity_idr;
        }
        if(charCode.equals("KZT")){
            imageId=R.drawable.currency_rate_activity_kzt;
        }
        if(charCode.equals("CAD")){
            imageId=R.drawable.currency_rate_activity_cad;
        }
        if(charCode.equals("QAR")){
            imageId=R.drawable.currency_rate_activity_qar;
        }
        if(charCode.equals("KGS")){
            imageId=R.drawable.currency_rate_activity_kgs;
        }
        if(charCode.equals("CNY")){
            imageId=R.drawable.currency_rate_activity_cny;
        }
        if(charCode.equals("MDL")){
            imageId=R.drawable.currency_rate_activity_mdl;
        }
        if(charCode.equals("NZD")){
            imageId=R.drawable.currency_rate_activity_nzd;
        }
        if(charCode.equals("NOK")){
            imageId=R.drawable.currency_rate_activity_nok;
        }
        if(charCode.equals("PLN")){
            imageId=R.drawable.currency_rate_activity_pln;
        }
        if(charCode.equals("RON")){
            imageId=R.drawable.currency_rate_activity_ron;
        }
        if(charCode.equals("XDR")){
            imageId=R.drawable.currency_rate_activity_xdr;
        }
        if(charCode.equals("SGD")){
            imageId=R.drawable.currency_rate_activity_sgd;
        }
        if(charCode.equals("TJS")){
            imageId=R.drawable.currency_rate_activity_tjs;
        }
        if(charCode.equals("THB")){
            imageId=R.drawable.currency_rate_activity_thb;
        }
        if(charCode.equals("TRY")){
            imageId=R.drawable.currency_rate_activity_try;
        }
        if(charCode.equals("TMT")){
            imageId=R.drawable.currency_rate_activity_tmt;
        }
        if(charCode.equals("UZS")){
            imageId=R.drawable.currency_rate_activity_uzs;
        }
        if(charCode.equals("UAH")){
            imageId=R.drawable.currency_rate_activity_uah;
        }
        if(charCode.equals("CZK")){
            imageId=R.drawable.currency_rate_activity_czk;
        }
        if(charCode.equals("SEK")){
            imageId=R.drawable.currency_rate_activity_sek;
        }
        if(charCode.equals("CHF")){
            imageId=R.drawable.currency_rate_activity_chf;
        }
        if(charCode.equals("RSD")){
            imageId=R.drawable.currency_rate_activity_rsd;
        }
        if(charCode.equals("ZAR")){
            imageId=R.drawable.currency_rate_activity_zar;
        }
        if(charCode.equals("KRW")){
            imageId=R.drawable.currency_rate_activity_krw;
        }
        if(charCode.equals("JPY")){
            imageId=R.drawable.currency_rate_activity_jpy;
        }

        return imageId;
    }

}
