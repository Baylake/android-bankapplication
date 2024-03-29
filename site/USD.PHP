<?
# File: usd.php, v.1.0/20010803
# ������ ��� ������ ��������� �� ������ ����� �������, �������������� ���
# (c) 2001, Mikhail Turenko, http://www.turenko.net, <mikhail@turenko.net>
# �������������: <IMG src="usd.php" width="88" height="41" border="0"/>
# ������� PNG 88x41

# ������� URL ������� �� cbr.ru
$scripturl = 'http://www.cbr.ru/scripts/XML_dynamic.asp';

# ��������� ���� ��� �������  (������� - 2 ���)
$date_1=date('d/m/Y', time()-172800);

# �������� ���� (����� ��������� ������ �������� �������� time()+86400)
$date_2=date('d/m/Y');

# ����� �������, �� ������� ������ ���� �� 2, ���� �� 3 ��������� ���.
# �� 2 - ���� �� "�������" ���� ��� �� ���������, ����� - �� 3

# ��� ������ � ������ ������ cbr.ru
$currency_code='R01235';

# URL ��� ������� ������
$requrl = "{$scripturl}?date_req1={$date_1}&date_req2={$date_2}&VAL_NM_RQ={$currency_code}";

$doc = file($requrl);
$doc = implode($doc, '');

# �������������� ������
$r = array();

# ���� <ValCurs>...</ValCurs>
if(preg_match("/<ValCurs.*?>(.*?)<\/ValCurs>/is", $doc, $m))
	# � ����� ���� ��� ��������� <Record>...</Record>
	preg_match_all("/<Record(.*?)>(.*?)<\/Record>/is", $m[1], $r, PREG_SET_ORDER);

$m = array();	# ��� ��� ������������, ����������������
$d = array();	# ���� ���� �����������������

# ��������� �� ������� ����� ������ ����
for($i=0; $i<count($r); $i++) {
	if(preg_match("/Date=\"(\d{2})\.(\d{2})\.(\d{4})\"/is", $r[$i][1],$m)) {
		$dv = "{$m[1]}/{$m[2]}/{$m[3]}"; # �������� ���� � ����. ���
		if(preg_match("/<Nominal>(.*?)<\/Nominal>.*?<Value>(.*?)<\/Value>/is", $r[$i][2], $m)) {
			$m[2] = preg_replace("/,/",".",$m[2]);
			$d[] = array($dv, $m[1], $m[2]);
			}
		}
	}

$last = array_pop($d);				# ��������� ��������� ����
$prev = array_pop($d);				# ��������. ��������� ����
$date = $last[0];				# ������������ ����
$rate = sprintf("%.2f",$last[2]);		# ������������ ����
# ������������ ��������� �����, ��������, "+0.02"
$delta = (($last[2]>$prev[2])?"+":"").sprintf("%.2f",$last[2]-$prev[2]);

#echo("$date: $rate ($delta)<BR>");

header("Content-type: image/png"); # ������ HTTP-��������� � ����� ������
# ������� ������ �����������
$im = @ImageCreate(88, 41) or die("Cannot do ImageCreate()");

# ������� ������ �����
$bg = ImageColorAllocate($im, 240, 255, 233);
$fg = ImageColorAllocate($im, 0, 0, 0);
$fg2 = ImageColorAllocate($im, 120, 0, 0);
$bdr = ImageColorAllocate($im, 224,224,224);
$bdr2 = ImageColorAllocate($im, 160,160,160);

# ������ ������ ������ - ����
$x = (88-ImageFontWidth(2)*strlen($date))/2;
ImageString($im, 2, $x, 2, $date, $fg);

# ������ ������ ������
ImageString($im, 2, 25, 14, "USD/RUR", $fg2);

# ������ ������ ������ - ���� � ���������
ImageString($im, 2, 6, 25, "$rate ($delta)", $fg2);

# ������ �����
ImageRectangle($im, 0, 0, ImageSX($im)-2, ImageSY($im)-2, $bdr);
ImageLine($im,ImageSX($im)-1,1,ImageSX($im)-1, ImageSY($im)-1,$bdr2);
ImageLine($im,1,ImageSY($im)-1,ImageSX($im)-1, ImageSY($im)-1,$bdr2);

# ������ ����������� �� �����
ImagePNG($im);

# ����������� ������ ��-���
ImageDestroy($im);

?>