<?php
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
 
	$username = $_POST['username'];
	$password = $_POST['password'];
	$type = $_POST['type'];
	
	if(strcnp($type,"login"){
	echo db.login($email, $password);
	}

	if(strcnp($type,"register"){
	echo db.register($email, $password);
	}
	else echo "Invalid Type";
 
	mysql_close();
?>