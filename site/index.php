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
   // echo $password;
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

	$result=$mysql->query("SELECT `LOGIN`,`PASSWORD` FROM `login` WHERE `LOGIN` = '$login'");

	while($e=$result->fetch_assoc())
        	$output[]=$e;
	print(json_encode($output));

}

//Добавления записи в таблицу
//Тест http://localhost/index.php?action=insert&login=dima1337&password=12345
if($action == "insert"){

$result=$mysql->query("INSERT INTO `login` (`LOGIN`,`PASSWORD`) VALUES('$login','$password')");

}

//Очищение таблицы
//Тест http://localhost/index.php?action=delete
if($action == "delete"){

$mysql->query("TRUNCATE TABLE `login`");

}
?>