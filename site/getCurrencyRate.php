<?php


try {

    try {

        //Подключение к Бд
        $mysql=new mysqli('localhost','root','root','bank',3307);
        if (!$mysql) {
            die("Connection failed: " . mysqli_connect_error());
        }
    } catch (Exception $e) {
        echo 'Выброшено исключение: ',  $e->getMessage(), "\n";
    }

    $result=$mysql->query("DELETE FROM `currency`");
    for($i=1;$i<31;$i++){
        $day=86400;
        $url_date=date('d/m/Y', (time()-($day*$i)));
        echo "Date: $url_date\n";


    $url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=".$url_date;
    $xml = simplexml_load_file($url);
    //print_r($xml);

    $date = $xml["Date"]->__toString();
    $newdate=$date[6] . $date[7] . $date[8] . $date[9] . '-' . $date[3] . $date[4] . '-' . $date[0] . $date[1];
    echo $newdate."\n";
    foreach ($xml->Valute as $valute) {
        // Получаем атрибут ID и значения NumCode, CharCode, Nominal, Name и Value
        $id = $valute["ID"];
        $numcode = $valute->NumCode->__toString();
        $charcode = $valute->CharCode->__toString();
        $nominal = $valute->Nominal->__toString();
        $name = $valute->Name->__toString();
        $value = $valute->Value->__toString();
        $value=str_replace(",", ".", $value);

         //Выводим информацию о валюте на экран
//        echo "ID: $id\n";
//        echo "NumCode: $numcode\n";
//        echo "CharCode: $charcode\n";
//        echo "Nominal: $nominal\n";
//        echo "Name: $name\n";
//        echo "Value: $value\n";
//        echo "\n";
        $result=$mysql->query("INSERT INTO `currency` (`num_code`,`char_code`,`nominal`,`name`,`value`,`date`) VALUES('$numcode','$charcode','$nominal','$name','$value','$newdate')");


    }

    }

} catch (Exception $e) {
    echo 'Выброшено исключение: ',  $e->getMessage(), "\n";
}




?>



