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

	$comprobacion="DELETE FROM `listacompra` WHERE elemento='$elemento' and email='$email'";
	$result= mysqli_query($con, $comprobacion);
	echo $result;
	
}

?>	