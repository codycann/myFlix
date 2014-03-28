<?php
 
class DB_Functions {
 
    private $db;

    function __construct() {
        require_once 'DB_Connect.php';
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    function __destruct() {
    }
 
    public function register($email, $password) {
	$sql = sprintf("INSERT INTO login(email, password) VALUES('%s', '%s')", $email, $password);
	if (mysql_query($sql)) {
        return true;
    } else {
        return false;
    }
	}
 
    public function login($email, $password) {
	$sql = sprintf("SELECT * FROM `login` WHERE email = '%s' AND password = '%s'",$email, $password);
	if (mysql_query($sql)) {
        return true;
    } else {
        return false;
    }
	}
	public function query($query){
	$sql = sprintf(query);
	$result = mysql_query($sql);
	$rows = array();
	while($r = mysql_fetch_assoc($result)) {
		$rows[] = $r;
	}
	print json_encode($rows);
	}
}
?>