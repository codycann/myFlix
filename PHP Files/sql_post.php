<?php
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
 
	$query = $_POST['query'];

	
	if(!db.query($query)){
		echo "failed";
	}

 
	mysql_close();
?>