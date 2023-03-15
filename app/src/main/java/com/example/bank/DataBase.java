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
    private static final String SERVER_ADDRESS = "192.168.0.112";
    private HttpURLConnection connection;

    //TODO Удалить startLoop когда вопросы по базе данных уйдут
    public void startLoop() {

        Thread thr = new Thread(new Runnable() {

            // ansver = ответ на запрос
            // lnk = линк с параметрами
            String ansver, lnk = "http://" + SERVER_ADDRESS + ":80/index.php?action=select";

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
    public void selectLoginAndPassword(String login) {

        Thread thr = new Thread(new Runnable() {

            String answer, link = "http://" + SERVER_ADDRESS + ":80/index.php?action=select&login=" + login;

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
        mapAnswer.put(key, value);
        return mapAnswer;
    }

    //inserts in tables users and logins
    //TODO Remove hardcoded record
    public void insertIntoUsersAndLogins() {
        HashMap<String, String> map = new HashMap<>();

        String firstName = "Valery";
        map.put("first name", firstName);

        String lastName = "Zhmyshenko";
        map.put("last name", lastName);

        String patronymic = "Albertovich";
        map.put("patronymic", patronymic);

        String passportData = "2022133722";
        map.put("passport data", passportData);

        String cellphoneNumber = "89009601337";
        map.put("cellphone number", cellphoneNumber);

        String email = "valeraborov@yandex.ru";
        map.put("email", email);

        String login = "valerik228";
        map.put("login", login);

        String password = "1337";
        map.put("password", password);


        Thread thr = new Thread(new Runnable() {

            String answer;
            String link = "http://" + SERVER_ADDRESS + ":80/index.php?action=insert&first_name="
                    + map.get("first name") + "&last_name=" + map.get("last name") + "&patronymic="
                    + map.get("patronymic") + "&passport_data=" + map.get("passport data")
                    + "&cellphone_number=" + map.get("cellphone number") + "&email=" + map.get("email")
                    + "&login=" + map.get("login") + "&password=" + map.get("password");

            public void run() {
                // создаем соединение ---------------------------------->
                try {
                    Log.i("mysql", link);
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
                    HashMap<String, String> mapAnswer = JsonToHashMap(answer);
                    Log.i("mysql", "mapAnswer\n" + mapAnswer);
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

    public void truncateUsersAndLogins() {


        Thread thr = new Thread(new Runnable() {

            String answer;
            String link = "http://" + SERVER_ADDRESS + ":80/index.php?action=delete";

            public void run() {
                // создаем соединение ---------------------------------->
                try {
                    Log.i("mysql", link);
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
                    HashMap<String, String> mapAnswer = JsonToHashMap(answer);
                    Log.i("mysql", "mapAnswer\n" + mapAnswer);
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
}
