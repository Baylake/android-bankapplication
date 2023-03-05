package com.example.bank;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
//TODO Вынести адрес сервера как константу!
//TODO Adds insert method
//TODO Adds delete method
public class DataBase {
    private HttpURLConnection connection;


    public void startLoop() {

        Thread thr = new Thread(new Runnable() {

            // ansver = ответ на запрос
            // lnk = линк с параметрами
            String ansver, lnk="http://192.168.0.103:80/index.php?action=select";
            //String ansver, lnk="http://192.168.0.103:80/index.php?action=insert&login=megadima&password=12345";
            //String ansver, lnk="http://192.168.0.103:80/index.php?action=delete";
            public void run() {
                // создаем соединение ---------------------------------->
                try {
                    Log.i("chat",
                            "+ FoneService --------------- ОТКРОЕМ СОЕДИНЕНИЕ");

                    connection = (HttpURLConnection) new URL(lnk)
                            .openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    connection.setDoInput(true);
                    connection.connect();

                } catch (Exception e) {
                    Log.i("chat", "+ FoneService ошибка: " + e.getMessage());
                }
                // получаем ответ ---------------------------------->
                try {
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String bfr_st = null;
                    while ((bfr_st = br.readLine()) != null) {
                        sb.append(bfr_st);
                    }

                    Log.i("chat", "+ FoneService - полный ответ сервера:\n"
                            + sb.toString());
                    // сформируем ответ сервера в string
                    // обрежем в полученном ответе все, что находится за "]"
                    // это необходимо, т.к. json ответ приходит с мусором
                    // и если этот мусор не убрать - будет невалидным
                    ansver = sb.toString();
                    ansver = ansver.substring(0, ansver.indexOf("]") + 1);

                    is.close(); // закроем поток
                    br.close(); // закроем буфер

                } catch (Exception e) {
                    Log.i("chat", "+ FoneService ошибка: " + e.getMessage());
                } finally {
                    connection.disconnect();
                    Log.i("chat",
                            "+ FoneService --------------- ЗАКРОЕМ СОЕДИНЕНИЕ");
                }



            }
        });

        thr.setDaemon(true);
        thr.start();

    }
    //TODO Split server answer in some map collection
    //TODO Check null answer
    //Returns data from mysql database
    public void select(String login) {

        Thread thr = new Thread(new Runnable() {

            String answer, link="http://192.168.0.103:80/index.php?action=select&login="+login;
            public void run() {
                // создаем соединение ---------------------------------->
                try {
                    Log.i("mysql",
                            "Open connection");

                    connection = (HttpURLConnection) new URL(link).openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    connection.setDoInput(true);
                    connection.connect();

                } catch (Exception e) {
                    Log.i("mysql", "Error: " + e.getMessage());
                }
                // получаем ответ ---------------------------------->
                try {
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String bfr_st = null;
                    while ((bfr_st = br.readLine()) != null) {
                        sb.append(bfr_st);
                    }

                    Log.i("mysql", "Server answer:\n" + sb.toString());
                    // сформируем ответ сервера в string
                    // обрежем в полученном ответе все, что находится за "]"
                    // это необходимо, т.к. json ответ приходит с мусором
                    // и если этот мусор не убрать - будет невалидным
                    answer = sb.toString();
                    HashMap<String,String> mapAnswer=JsonToHashMap(answer);
                    Log.i("mysql","mapAnswer\n"+mapAnswer);
                    is.close(); // закроем поток
                    br.close(); // закроем буфер

                } catch (Exception e) {
                    Log.i("mysql", "Error: " + e.getMessage());
                } finally {
                    connection.disconnect();
                    Log.i("mysql", "Close connection");
                }



            }
        });

        thr.setDaemon(true);
        thr.start();

    }
    //Returns server answer in Map structure
    HashMap<String,String> JsonToHashMap(String answer){
        HashMap<String,String> mapAnswer=new HashMap<String,String>();
        String key=new String();
        String value=new String();
        boolean flag=false;
        for(int i=0;i<answer.length();i++){
            if((answer.charAt(i)!='[')&&(answer.charAt(i)!=']')&&(answer.charAt(i)!='{')
                    &&(answer.charAt(i)!='}')&&(answer.charAt(i)!='"')){
                if(answer.charAt(i)==':'){
                    flag=true;
                }
                else{
                    if(answer.charAt(i)==','){
                        flag=false;
                        mapAnswer.put(key,value);
                        key="";
                        value="";
                    }
                    else{
                        if(flag==false){
                            key+=answer.charAt(i);
                        }
                        else{
                            value+=answer.charAt(i);
                        }
                    }

                }


            }
        }
        mapAnswer.put(key,value);
        return mapAnswer;
    }

}
