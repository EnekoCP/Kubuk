<?php
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_tareamanager"; #la base de datos a la que hay que conectarse

$titulo = $GET['titulo'];
$ingredientes = $GET['ingredientes'];
$preparacion = $GET['preparacion'];
$usuario = $GET['usuario'];

$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	$fin="false";
}
else{

	if(mysqli_num_rows($result)==0){
	    $q="INSERT INTO recetaComunidad (titulo,ingredientes,preparacion,usuario) VALUES ('$titulo','$ingredientes','$preparacion','$usuario')";
	    $result= mysqli_query($con, $q);
	    echo "true";
	    
	}
	
}

?>	