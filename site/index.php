<?php

//инициализация для подключения бд
$mysql_host = "localhost"; // sql сервер
$mysql_user = "root"; // пользователь
$mysql_password = "root"; // пароль
$mysql_database = "bank"; // имя базы данных chat

//берем информацию из запроса
if (isset($_GET["action"])) {
    $action = $_GET['action'];
    //echo $action;
}

if (isset($_GET["login"])) {
    $login = $_GET['login'];
    //echo $login;
}

if (isset($_GET["password"])) {
    $password = $_GET['password'];
    //echo $password;
}
if (isset($_GET["first_name"])) {
    $first_name = $_GET['first_name'];
    //echo $first_name;
}
if (isset($_GET["last_name"])) {
    $last_name = $_GET['last_name'];
    //echo $last_name;
}
if (isset($_GET["patronymic"])) {
    $patronymic = $_GET['patronymic'];
    //echo $patronymic;
}
if (isset($_GET["passport_data"])) {
    $passport_data = $_GET['passport_data'];
    //echo $passport_data;
}
if (isset($_GET["cellphone_number"])) {
    $cellphone_number = $_GET['cellphone_number'];
    //echo $cellphone_number;
}
if (isset($_GET["email"])) {
    $email = $_GET['email'];
    //echo $email;
}

if (isset($_GET["currency_char_code"])) {
    $currency_char_code = $_GET['currency_char_code'];
    //echo $login;
}

if (isset($_GET["number_of_days"])) {
    $number_of_days = $_GET['number_of_days'];
    //echo $login;
}

//карта с которой снимается change при transfere
if (isset($_GET["card_id_from"])) {
    $card_id_from = $_GET['card_id_from'];
    //echo $card_id_from;
}
//карта на которую добавляется change при transfere
if (isset($_GET["card_id_to"])) {
    $card_id_to = $_GET['card_id_to'];
    //echo  $card_id_to;
}
//на сколько изменить баланс при transfer-е
if (isset($_GET["change"])) {
    $change = $_GET['change'];
    //echo  $change;
}

try {

//Подключение к Бд
    $mysql=new mysqli('localhost','root','root','bank',3307);
    if (!$mysql) {
        die("Connection failed: " . mysqli_connect_error());
    }
} catch (Exception $e) {
    echo 'Выброшено исключение: ',  $e->getMessage(), "\n";
}
//
//----------------
//
//Достаем данные из таблицы logins
//Тест http://localhost/index.php?action=select_login&login=valerik228
if($action == "select_logins"){

    $result=$mysql->query("SELECT `user_login`,`user_password` FROM `logins` WHERE `user_login` = '$login'");
    while($e=$result->fetch_assoc())
        $output[]=$e;
    print(json_encode($output));

}

//Достаем данные из таблицы users
//Тест http://localhost/index.php?action=select_login&login=valerik228
if($action == "select_users"){

    $result=$mysql->query("SELECT `user_first_name`,`user_last_name`,`user_patronymic`,`user_passport_data`,
       `user_cellphone_number`,`user_email` FROM `logins` INNER JOIN `users` ON
           (`logins`.`users_user_id`=`users`.`user_id`) WHERE `logins`.`user_login`='$login'");

    while($e=$result->fetch_assoc())
        $output[]=$e;
    print(json_encode($output));

}

if($action == "select_cards"){

    $result=$mysql->query("SELECT `card_id`,`pay_systems_supported_pay_system_name`,`member_name`,`expire_date`,`cvv_code`,
       `pin_code` FROM `cards` INNER JOIN `logins` ON
           (`cards`.`users_user_id`=`logins`.`users_user_id`) WHERE `logins`.`user_login`='$login'");

    while($e=$result->fetch_assoc())
        $output[]=$e;
    print(json_encode($output));
}

if($action == "select_pay_systems"){

    $result=$mysql->query("SELECT `supported_pay_system_name` FROM `pay_systems`");

    while($e=$result->fetch_assoc())
        $output[]=$e;
    print(json_encode($output));

}

if($action == "select_card_balance"){

    $result=$mysql->query("SELECT `card_balance`.`cards_card_id`,`card_balance`.`balance` FROM `card_balance` INNER JOIN `logins` ON
           (`card_balance`.`users_user_id`=`logins`.`users_user_id`) INNER JOIN `cards` ON (`cards`.`card_id`=`card_balance`.`cards_card_id`) WHERE `logins`.`user_login`='$login'");

    while($e=$result->fetch_assoc())
        $output[]=$e;
    print(json_encode($output));
}

//http://localhost/index.php?action=select_currency_rate&currency_char_code=USD&number_of_days=30
if($action == "select_currency_rate"){
    $date_max=date('Y-m-d', (time()));
    $date_min=date('Y-m-d', (time()-86400*$number_of_days));
    $result=$mysql->query("SELECT * FROM `currency` WHERE `char_code`='$currency_char_code' and (`date`>='$date_min' and `date`<='$date_max')");
    while($e=$result->fetch_assoc())
        $output[]=$e;
    print(json_encode($output));
}


//Добавления записи в таблицу
//Тест http://localhost/index.php?action=insert&first_name=Valery&last_name=Zhmyshenko&patronymic=Albertovich&passport_data=20221337228&cellphone_number=89009601337&email=valeraborov@yandex.ru&login=valerik228&password=1337
if($action == "insert"){
    $result=$mysql->query("INSERT INTO `users` (`user_first_name`,`user_last_name`,`user_patronymic`,`user_passport_data`,`user_cellphone_number`,`user_email`) VALUES('$first_name','$last_name','$patronymic','$passport_data','$cellphone_number','$email')");
    $user_id=$mysql->query("SELECT `user_id` FROM `users` WHERE `user_first_name`='$first_name'");
    $user_id=$user_id->fetch_assoc();
    $usr_id= $user_id['user_id'];
    $result2=$mysql->query("UPDATE `logins` SET `user_login`='$login',`user_password`='$password' WHERE `users_user_id`='$usr_id'");

}
//Тест http://localhost/index.php?action=transfer&card_id_from=2023217755681337&card_id_to=2025123456789012&change=10
if($action=="transfer"){
    $result=$mysql->query("UPDATE `card_balance` SET `balance`=`balance`-'$change' WHERE `cards_card_id`='$card_id_from'");
    $result2=$mysql->query("UPDATE `card_balance` SET `balance`=`balance`+'$change' WHERE `cards_card_id`='$card_id_to'");
}

//Очищение таблицы
//Тест http://localhost/index.php?action=delete
if($action == "delete"){

    $mysql->query("TRUNCATE TABLE `logins`");
    $mysql->query("TRUNCATE TABLE `users`");
}
?>