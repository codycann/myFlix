<?php
    require_once 'myflix/db_functions.php';
    $db = new DB_Functions();
	$query = 'SELECT * FROM movies';
	if(isset($_POST['query'])){
		$query = $_POST['query'];
	}
	if(!db.query($query)){
		$Error = array('error' => 'failed');
		print json_encode($Error);
	}
	mysql_close();
?>