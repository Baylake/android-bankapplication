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

/**
 * \brief класс реализует работу с базой данных
 *
 * При вызове методов select.... данные возвращаются в mapAnswer
 *
 * Если вызывать getBankCards то данные возвращаются через return метода
 */
public class DataBase {
    ///Адрес сайта, который обеспечивает доступ к базе данных
    private static final String SERVER_ADDRESS = "192.168.1.67";
    //192.168.1.67 192.168.0.112

    ///Порт сайта, который обеспечивает доступ к бд
    private static final String PORT = "80";

    ///Преобразованный в массив хеш мап ответ базы данных
    public ArrayList<HashMap<String, String>> mapAnswer;

    /**
     * Делает http запрос для пользователя с логином login
     *
     * Селектит данные о логине и пароле
     *
     * \param[in] login Строка, которая содержит логин пользователя
     *
     * \return Возвращается в mapAnswer в виде массива таких хеш мап [{user_password="ЗНАЧЕНИЕ", user_login="ЗНАЧЕНИЕ"}]
     */
    public void selectLogins(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_logins&login=" + login;
        startConnection(link);

    }

    /**
     * Делает http запрос для пользователя с логином login
     *
     * Селектит данные о пользователе
     *
     * \param[in] login Строка, которая содержит логин пользователя
     *
     * \return Возвращается в mapAnswer в виде массива таких хеш мап {user_first_name="ЗНАЧЕНИЕ", user_email="ЗНАЧЕНИЕ",
     * user_patronymic="ЗНАЧЕНИЕ", user_last_name="ЗНАЧЕНИЕ", user_cellphone_number="ЗНАЧЕНИЕ", user_passport_data="ЗНАЧЕНИЕ"}]
     */
    public void selectUsers(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_users&login=" + login;
        startConnection(link);
    }

    /**
     * Делает http запрос для пользователя с логином login
     *
     * Селектит данные о картах пользователя
     *
     * \param[in] login Строка, которая содержит логин пользователя
     *
     * \return Возвращается в mapAnswer в виде массива таких хеш мап [{cvv_code="ЗНАЧЕНИЕ", expire_date="ЗНАЧЕНИЕ", pin_code="ЗНАЧЕНИЕ",
     * pay_systems_supported_pay_system_name="ЗНАЧЕНИЕ", member_name="ЗНАЧЕНИЕ", card_id="ЗНАЧЕНИЕ"}]
     */
    public void selectCards(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_cards&login=" + login;
        startConnection(link);
    }

    /**
     * Делает http запрос для пользователя с логином login
     *
     * Селектит данные о балансе пользователя
     *
     * \param[in] login Строка, которая содержит логин пользователя
     *
     * \return Возвращается в mapAnswer в виде массива таких хеш мап [{balance="ЗНАЧЕНИЕ", cards_card_id="ЗНАЧЕНИЕ"}]
     */
    public void selectCardBalance(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_card_balance&login=" + login;
        startConnection(link);

    }

    /**
     * Делает http запрос для пользователя с логином login
     *
     * Селектит платежные системы
     *
     * \param[in] login Строка, которая содержит логин пользователя
     *
     * \return Возвращается в mapAnswer в виде массива таких хеш мап [{supported_pay_system_name="ЗНАЧЕНИЕ"}]
     */
    public void selectPaySystems(String login) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=select_pay_systems&login=" + login;
        startConnection(link);
    }

    /**
     * Делает http запрос который вставляет данные в таблицы users и logins
     *
     * \warning *захардкожена* тестовая запись
     *
     * \return не проверял, но должна возвращать mapAnswer с size=0
     *
     */
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
    }

    /**
     * Делает http запрос который очищает таблицы users и logins
     *
     * \warning давно не тестился, может не работать из-за триггеров в базе
     *
     * \return не проверял, но должна возвращать mapAnswer с size=0
     *
     * По логике его вообще нужно удалить, но пока пусть будет
     */
    public void truncateUsersAndLogins() {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=delete";
        startConnection(link);

    }


    /**
     * Преобразует строку с ответом базы данных в массив hash map
     *
     * Пример строки [{"year":"2023","balance":"5000"},{"year":"2024","balance":"1020"},{"year":"2025","balance":"1550"}]
     *
     * Строка содержит n записей. Будет создан массив из n элементов, в котором каждая запись представлена ввиде hash map
     *
     * Названия ключей в map будут соответсвовать полям в базе данных
     *
     * Ответы null базы данных учитывается в startConnection
     *
     * \param[in] answer Строка, которая содержит ответ от сервера
     *
     * \return возвращает массив хеш мап подобного вида [{cvv_code="ЗНАЧЕНИЕ", expire_date="ЗНАЧЕНИЕ", pin_code="ЗНАЧЕНИЕ"}]
     */
    private ArrayList<HashMap<String, String>> JsonToArrayListHashMaps(String answer) {
        Integer recordsCount = new Integer(0);
        ArrayList<Integer> startSubstring = new ArrayList<Integer>();
        ArrayList<Integer> stopSubstring = new ArrayList<Integer>();
        ArrayList<HashMap<String, String>> ans = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == '{') {
                recordsCount = recordsCount + 1;
                startSubstring.add(i);
            }
            if (answer.charAt(i) == '}') {
                stopSubstring.add(i);
            }
        }
        for (int i = 0; i < recordsCount; i++) {
            ans.add(oneRecordToHashMap(answer.substring(startSubstring.get(i), stopSubstring.get(i))));
        }
        return ans;
    }

    /**
     * Преобразует строку с одной записью в hash map
     *
     * Пример строки {"year":"2023","balance":"5000"}
     *
     * \param[in] answer Строка, которая содержит одну запись
     *
     * \return возвращает hash map подобного вида [{year="2023",balance="5000"}]
     */
    private HashMap<String, String> oneRecordToHashMap(String answer) {
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
        return oneRecordMapAnswer;
    }

    /**
     * Создает http соединение, получает данные, преобразует их и сохраняет в переменную mapAnswer
     *
     * Каждое соединение очищает mapAnswer
     *
     * Если ответ от базы данных null, то mapAnswer.size()=0
     *
     * \param[in] link Строка, которая содержит ссылку с http запросом
     */
    private void startConnection(String link) {
        Thread thr = new Thread(new Runnable() {
            String answer;
            HttpURLConnection connection;
            public void run() {
                // creates connection
                try {
                    Log.i("mysql",
                            "Open connection");
                    mapAnswer = new ArrayList<>();
                    connection = (HttpURLConnection) new URL(link).openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    connection.setDoInput(true);
                    connection.connect();

                } catch (Exception e) {
                    Log.i("mysql", "Error: " + e.getMessage());
                    return;
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
                        //mapAnswer = new ArrayList<>();
                    } else {
                        mapAnswer = JsonToArrayListHashMaps(answer);
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

    /**
     * Делает два http соединения для заполнения массива BankCard
     *
     * \param[in] login Строка, которая содержит логин пользователя
     *
     * \return Возвращает массив BankCard
     */
    public ArrayList<BankCard> getBankCards(String login) {
        ArrayList<BankCard> bankCards = new ArrayList<>();

        this.selectCards(login);

        for (int i = 0; i < this.mapAnswer.size(); i++) {
            BankCard tempCard = new BankCard();
            tempCard.cardNumber = this.mapAnswer.get(i).get("card_id");
            tempCard.cvvCode = this.mapAnswer.get(i).get("cvv_code");
            tempCard.date = this.mapAnswer.get(i).get("expire_date").replace(' ', '/');
            tempCard.pinCode = this.mapAnswer.get(i).get("pin_code");
            tempCard.paySystemName = this.mapAnswer.get(i).get("pay_systems_supported_pay_system_name");
            tempCard.memberName = this.mapAnswer.get(i).get("member_name");
            tempCard.cardNumber = this.mapAnswer.get(i).get("card_id");

            if (tempCard.paySystemName.equals("MIR")) {
                tempCard.smallImageResourceID= R.drawable.main_activity_card_mir;
            } else if (tempCard.paySystemName.equals("VISA")) {
                tempCard.smallImageResourceID = R.drawable.main_activity_card_visa;
            } else if (tempCard.paySystemName.equals("MASTER CARD")) {
                tempCard.smallImageResourceID = R.drawable.main_activity_card_master_card;
            }else{
                tempCard.smallImageResourceID = R.drawable.main_activity_card_unknown;
            }
            bankCards.add(tempCard);
        }

        this.selectCardBalance(login);
        for (int i = 0; i < bankCards.size(); i++) {
            for (int j = 0; j < bankCards.size(); j++) {
                if (this.mapAnswer.get(i).get("cards_card_id").equals(bankCards.get(j).cardNumber)) {
                    bankCards.get(j).setBalance(this.mapAnswer.get(i).get("balance"));
                }
            }
        }
        return bankCards;
    }
    /**
     * Тест. Проверяет работу всех методов select
     *
     * Если завершился успешно, выведется лог по тегом test, Test passed = true
     *
     * Если будет ошибка хотя бы в 1 методе, выведется строка с отметками завершенности методов
     *
     * Если все методы не прошли тест, то проблема в сервере/настройках адреса сети, портах...
     *
     * \param[in] login Строка, которая содержит логин пользователя
     *
     * \return Возвращает лог по тегом test, в котором выводится результаты выполнения теста
     */
    public void test_allSelectMethods_allSelectMethodsAreWorking(String login){
        //Arrange
        HashMap<String,Boolean> testChecks=new HashMap<>();
        //Act
        this.selectLogins(login);
        if(!mapAnswer.isEmpty()){
            testChecks.put("selectLogins",true);
        }
        else{
            testChecks.put("selectLogins",false);
        }
        this.selectCardBalance(login);
        if(!mapAnswer.isEmpty()){
            testChecks.put("selectCardBalance",true);
        }
        else{
            testChecks.put("selectCardBalance",false);
        }
        this.selectCards(login);
        if(!mapAnswer.isEmpty()){
            testChecks.put("selectCards",true);
        }
        else{
            testChecks.put("selectCards",false);
        }
        this.selectPaySystems(login);
        if(!mapAnswer.isEmpty()){
            testChecks.put("selectPaySystems",true);
        }
        else{
            testChecks.put("selectPaySystems",false);
        }
        this.selectUsers(login+"21");
        if(!mapAnswer.isEmpty()){
            testChecks.put("selectUsers",true);
        }
        else{
            testChecks.put("selectUsers",false);
        }
        if(testChecks.get("selectLogins")&&testChecks.get("selectCardBalance")&& testChecks.get("selectCards")
        &&testChecks.get("selectPaySystems")&&testChecks.get("selectUsers")){
            testChecks.put("testPassed",true);
        }
        else{
            testChecks.put("testPassed",false);
        }
        if(testChecks.get("testPassed")){
            Log.i("test","Test passed = "+testChecks.get("testPassed").toString());
        }
        else{
            Log.i("test","Test passed = "+testChecks.get("testPassed").toString());
            Log.i("test","Units = "+testChecks.toString());
        }

    }
    /**
     * Делает http запрос update
     *
     * Изменяет баланс с карт. Вычитает change из карты с номером cardIdFrom и добавляет на карту cardIdTo
     *
     * cardIdFrom=cardIdFrom-change,cardIdTo=cardIdTo+change
     *
     * \param[in] cardIdFrom Строка, которая содержит номер карты с которой переводятся деньги
     *
     * \param[in] cardIdTo Строка, которая содержит номер карты на которую переводятся деньги
     *
     * \param[in] change Строка, которая содержит на сколько нужно изменить баланс
     */
    public void transfer(String cardIdFrom,String cardIdTo,String change) {
        String link = "http://" + SERVER_ADDRESS + ":" + PORT + "/index.php?action=transfer&card_id_from="+cardIdFrom+"&card_id_to="+cardIdTo+"&change="+change;
        startConnection(link);

    }
}
