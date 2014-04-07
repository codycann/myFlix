<?php
 
class DB_Functions {
 
    private $db;

	
	function DB_Functions(){
	    //require_once 'myflix/DB_Connect.php';

        //$this->db = new DB_Connect();
        //$this->db->connect();
	}

    function __construct() {
        require_once 'myflix/DB_Connect.php';
        $this->db = new DB_Connect();
        $this->db->connect();
		$conn = mysql_connect('localhost', "root", "moviepass");
		mysql_select_db("myflix", $conn);
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
	$sql = sprintf($query);
	$result = mysql_query($sql);
	if($result === TRUE){
		if(mysql_num_rows($result) > 0){
			$rows = array();
			while($r = mysql_fetch_assoc($result)) {
				$rows[] = $r;
			}
			$output = json_encode($rows);
			print $output;
			//return true;
		
		}
		//else{
			//$Error = array('error' => 'empty')
			//print json_encode($Error);
			//return true;
		//}
	//}
	//else{
	//	return false
	}
}	
}
?>