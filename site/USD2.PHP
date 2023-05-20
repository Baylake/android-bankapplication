<?
# File: usd2.php, v.1.0/20010803
# Скрипт для вывода информера по поводу курса доллара, установленного ЦБР
# (c) 2001, Mikhail Turenko, http://www.turenko.net, <mikhail@turenko.net>
# Использование: <IMG src="usd.php" width="88" height="41" border="0"/>
# Выводит PNG, размер выбирается автоматически
# строка "dd/mm/yyyy: 1USD=29.30RUR (+0.02)"

# Базовый URL скрипта на cbr.ru
$scripturl = 'http://www.cbr.ru/scripts/XML_dynamic.asp';

# Начальная дата для запроса  (сегодня - 2 дня)
$date_1=date('d/m/Y', time()-172800);

# Конечная дата (чтобы учитывать завтра добавьте параметр time()+86400)
$date_2=date('d/m/Y');

# Таким образом, мы получим данные либо за 2, либо за 3 последних дня.
# За 2 - если на "сегодня" курс еще не выставили, иначе - за 3

# Код валюты в архиве данных cbr.ru
$currency_code='R01235';

# URL для запроса данных
$requrl = "{$scripturl}?date_req1={$date_1}&date_req2={$date_2}&VAL_NM_RQ={$currency_code}";

$doc = file($requrl);
$doc = implode($doc, '');

# инициализируем массив
$r = array();

# ищем <ValCurs>...</ValCurs>
if(preg_match("/<ValCurs.*?>(.*?)<\/ValCurs>/is", $doc, $m))
	# а потом ищем все вхождения <Record>...</Record>
	preg_match_all("/<Record(.*?)>(.*?)<\/Record>/is", $m[1], $r, PREG_SET_ORDER);

$m = array();	# его уже использовали, реинициализируем
$d = array();	# этот тоже проинициализируем

# Сканируем на предмет самых нужных цифр
for($i=0; $i<count($r); $i++) {
	if(preg_match("/Date=\"(\d{2})\.(\d{2})\.(\d{4})\"/is", $r[$i][1],$m)) {
		$dv = "{$m[1]}/{$m[2]}/{$m[3]}"; # Приводим дату в норм. вид
		if(preg_match("/<Nominal>(.*?)<\/Nominal>.*?<Value>(.*?)<\/Value>/is", $r[$i][2], $m)) {
			$m[2] = preg_replace("/,/",".",$m[2]);
			$d[] = array($dv, $m[1], $m[2]);
			}
		}
	}

$last = array_pop($d);				# последний известный день
$prev = array_pop($d);				# предпосл. известный день
$date = $last[0];				# отображаемая дата
$rate = sprintf("%.2f",$last[2]);		# отображаемый курс
# отображаемое изменение курса, например, "+0.02"
$delta = (($last[2]>$prev[2])?"+":"").sprintf("%.2f",$last[2]-$prev[2]);

#echo("{$date}: 1USD={$rate}RUR ({$delta})<BR>");

$string = "{$date}: 1USD={$rate}RUR ({$delta})";
$h = ImageFontHeight(2)+2;
$w = ImageFontWidth(2)*strlen($string)+2;

header("Content-type: image/png"); # Отдаем HTTP-заголовок с типом данных
# Создаем пустое изображение
$im = @ImageCreate($w, $h) or die("Cannot do ImageCreate()");

# Создаем всякие цвета
$bg = ImageColorAllocate($im, 255, 255, 255);
$fg = ImageColorAllocate($im, 0, 0, 0);
$fg2 = ImageColorAllocate($im, 120, 0, 0);
$bdr = ImageColorAllocate($im, 224,224,224);
$bdr2 = ImageColorAllocate($im, 160,160,160);

ImageColorTransparent($im, $bg);
ImageString($im, 2, 1, 1, $string, $fg);

# Отдаем изображение на выход
ImagePNG($im);

# Освобождаем память из-под
ImageDestroy($im);

?>