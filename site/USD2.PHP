<?
# File: usd2.php, v.1.0/20010803
# ������ ��� ������ ��������� �� ������ ����� �������, �������������� ���
# (c) 2001, Mikhail Turenko, http://www.turenko.net, <mikhail@turenko.net>
# �������������: <IMG src="usd.php" width="88" height="41" border="0"/>
# ������� PNG, ������ ���������� �������������
# ������ "dd/mm/yyyy: 1USD=29.30RUR (+0.02)"

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

#echo("{$date}: 1USD={$rate}RUR ({$delta})<BR>");

$string = "{$date}: 1USD={$rate}RUR ({$delta})";
$h = ImageFontHeight(2)+2;
$w = ImageFontWidth(2)*strlen($string)+2;

header("Content-type: image/png"); # ������ HTTP-��������� � ����� ������
# ������� ������ �����������
$im = @ImageCreate($w, $h) or die("Cannot do ImageCreate()");

# ������� ������ �����
$bg = ImageColorAllocate($im, 255, 255, 255);
$fg = ImageColorAllocate($im, 0, 0, 0);
$fg2 = ImageColorAllocate($im, 120, 0, 0);
$bdr = ImageColorAllocate($im, 224,224,224);
$bdr2 = ImageColorAllocate($im, 160,160,160);

ImageColorTransparent($im, $bg);
ImageString($im, 2, 1, 1, $string, $fg);

# ������ ����������� �� �����
ImagePNG($im);

# ����������� ������ ��-���
ImageDestroy($im);

?>