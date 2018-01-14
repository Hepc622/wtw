<?php  
include "class.phpmailer.php";  
include "class.smtp.php";
$phone = $_POST['phone'];
$name = $_POST['name'];
$type = $_POST['type'];
$message = $_POST['message'];
$mail = new PHPMailer();
$mail->isSMTP();
$mail->CharSet = "utf8";
$mail->Host = "smtp.163.com";
$mail->SMTPAuth = true;
$mail->Username = "15111066861@163.com";
$mail->Password = "email163";
$mail->SMTPSecure = "ssl";
$mail->Port = 994;
$mail->setFrom("15111066861@163.com",$name);
$mail->addAddress("chenchuanwen@wtmes.com");
$mail->IsHTML(true);
$mail->Subject = '威拓威官网';
$mail->Body = $message."<br/> 名称：".$name."<br/>手机号：".$phone."<br/>咨询类别：".$type;
if(!$mail->send()){
  echo "Message could not be sent.";
  echo "Mailer Error: ".$mail->ErrorInfo;
}else{
  echo '1';
}
?>  