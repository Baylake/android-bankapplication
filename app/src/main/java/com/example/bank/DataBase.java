package com.example.bank;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

public class DataBase {
    private static final String SERVER_ADDRESS = "192.168.0.112";
    public static final String PORT = "80";
    public ArrayList<HashMap<String, String>> mapAnswer;
    private HttpURLConnection connection;

    //Selects data from logins table and assigns to mapAnswer
    public void selectLogins(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_logins&login=" + login;
        startConnection(link);

    }

    //Selects data from users table and assigns to mapAnswer
    public void selectUsers(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_users&login=" + login;
        startConnection(link);
    }

    //Selects data from cards table and assigns to mapAnswer
    public void selectCards(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_cards&login=" + login;
        startConnection(link);
    }

    //Selects data from logins card_balance and assigns to mapAnswer
    public void selectCardBalance(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_card_balance&login=" + login;
        startConnection(link);
       // Log.i("mysql",mapAnswer.toString());
    }

    //Selects data from pay_systems table and assigns to mapAnswer
    public void selectPaySystems(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_pay_systems&login=" + login;
        startConnection(link);
    }

    //Inserts data into users and logins tables
    //TODO Remove hardcoded record
    public void insertIntoUsersAndLogins() {
        HashMap<String, String> map = new HashMap<>();

        String firstName = "Valery1";
        map.put("user_first_name", firstName);

        String lastName = "Zhmyshenko";
        map.put("user_last_name", lastName);

        String patronymic = "Albertovich";
        map.put("user_patronymic", patronymic);

        String passportData = "2022133722";
        map.put("user_passport_data", passportData);

        String cellphoneNumber = "89009601337";
        map.put("user_cellphone_number", cellphoneNumber);

        String email = "valeraborov@yandex.ru";
        map.put("user_email", email);

        String login = "valerik228";
        map.put("user_login", login);

        String password = "1337";
        map.put("user_password", password);

        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=insert&first_name="
                + map.get("user_first_name") + "&last_name=" + map.get("user_last_name") + "&patronymic="
                + map.get("user_patronymic") + "&passport_data=" + map.get("user_passport_data")
                + "&cellphone_number=" + map.get("user_cellphone_number") + "&email=" + map.get("user_email")
                + "&login=" + map.get("user_login") + "&password=" + map.get("user_password");

        startConnection(link);
       // Log.i("mysql",mapAnswer.toString());
    }

    //Truncate users and logins tables
    public void truncateUsersAndLogins() {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=delete";
        startConnection(link);
      //  Log.i("mysql",mapAnswer.toString());

    }

    //Returns server answer in Map structure
    ArrayList<HashMap<String, String>> JsonToArrayListHashMaps(String answer) {
        Integer recordsCount=new Integer(0);
        ArrayList<Integer> startSubstring=new ArrayList<Integer>();
        ArrayList<Integer> stopSubstring=new ArrayList<Integer>();
        ArrayList<HashMap<String, String>> ans=new ArrayList<HashMap<String, String>>();

        for(int i=0;i<answer.length();i++){
            if(answer.charAt(i)=='{'){
                recordsCount=recordsCount+1;
                startSubstring.add(i);
            }
            if(answer.charAt(i)=='}'){
                stopSubstring.add(i);
            }
        }
        //Log.i("mysql","recordAnswer="+recordsCount);
        for(int i=0;i<recordsCount;i++){
            ans.add(oneRecordToHashMap(answer.substring(startSubstring.get(i),stopSubstring.get(i))));
        }
        return ans;
    }

    //Submethod JsonToArrayListHashMaps returns one hash map
    private HashMap<String,String> oneRecordToHashMap(String answer){
        HashMap<String, String> oneRecordMapAnswer = new HashMap<String, String>();
        String key = new String();
        String value = new String();
        boolean flag = false;
        for (int i = 0; i < answer.length(); i++) {
            if ((answer.charAt(i) != '[') && (answer.charAt(i) != ']') && (answer.charAt(i) != '{')
                    && (answer.charAt(i) != '}') && (answer.charAt(i) != '"')) {
                if (answer.charAt(i) == ':') {
                    flag = true;
                } else {
                    if (answer.charAt(i) == ',') {
                        flag = false;
                        oneRecordMapAnswer.put(key, value);
                        key = "";
                        value = "";
                    } else {
                        if (flag == false) {
                            key += answer.charAt(i);
                        } else {
                            value += answer.charAt(i);
                        }
                    }
                }
            }
        }
        oneRecordMapAnswer.put(key, value);
       // Log.i("mysql","oneRecord="+oneRecordMapAnswer);
        return oneRecordMapAnswer;
    }

    //Сreates a connection to the database
    private void startConnection(String link) {
        Thread thr = new Thread(new Runnable() {
            String answer;

            public void run() {
                // creates connection
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
                // receives a response
                try {
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String bfr_st = null;
                    while ((bfr_st = br.readLine()) != null) {
                        sb.append(bfr_st);
                    }
                    // converts string to array list hash maps

                    answer = sb.toString();
                    Log.i("mysql", "Server answer:" + answer);
                    if (answer.equals("null")) {
                        mapAnswer=new ArrayList<>();
                    } else {
                        mapAnswer =JsonToArrayListHashMaps(answer);
                    }
                    Log.i("mysql", "mapAnswer\n" + mapAnswer);
                    is.close(); // closes the stream
                    br.close(); // closes the buffer

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
        try {
            thr.join();
        } catch (InterruptedException e) {

            System.out.printf("%s has been interrupted", thr.getName());
        }
    }


}
