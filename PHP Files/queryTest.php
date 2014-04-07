<?php
 
    function connect() {
		$conn = mysql_connect('localhost', "root", "moviepass");
		mysql_select_db("myflix", $conn);
    }
 
 
	function query($query){
	$sql = sprintf($query);
	$result = mysql_query($sql);
	if($result == TRUE){
		if(mysql_num_rows($result) > 0){
			$rows = array();
			while($r = mysql_fetch_assoc($result)) {
				$rows[] = $r;
			}
			print json_encode($rows);
			}
	}
	}
	connect();
	$query = 'SELECT * FROM movies';
	if(isset($_POST['query'])){
		$query = $_POST['query'];
	}
	if(!query($query)){
		$Error = array('error' => 'failed');
		print json_encode($Error);
	}
	mysql_close();
?>