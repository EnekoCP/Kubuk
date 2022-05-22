<?php
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse

$email = $_GET['email'];
$elemento = $_GET['elemento'];

$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	$fin="false";
}
else{

	$comprobacion="SELECT * FROM listacompra WHERE elemento='$elemento' and email='$email";	
	
	$result= mysqli_query($con, $comprobacion);

	if(mysqli_num_rows($result)==0){

	  $q="INSERT INTO listacompra (elemento,email,marcado) VALUES ('$elemento','$email','false')";
	  $result= mysqli_query($con, $q);
	  #$fin="true";
	  #echo $fin;
	  echo $result;
	  #echo json_encode(true);
	    
	}
	else{
		echo "ya estaba";
	}
	
}

?>	