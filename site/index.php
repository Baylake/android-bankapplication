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
//Подключение к Бд
$mysql=new mysqli('localhost','root','root','bank');
if (!$mysql) {
    die("Connection failed: " . mysqli_connect_error());
}
//----------------

//Достаем данные из таблицы(надо допилить чтобы бралась 1 запись)
//Тест http://localhost/index.php?action=select
if($action == "select"){

    $result=$mysql->query("SELECT `user_login`,`user_password` FROM `logins` WHERE `user_login` = '$login'");

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

//Очищение таблицы
//Тест http://localhost/index.php?action=delete
if($action == "delete"){

    $mysql->query("TRUNCATE TABLE `logins`");
    $mysql->query("TRUNCATE TABLE `users`");
}
?>