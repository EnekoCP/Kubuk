<?php
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse
$usuario = $_GET['usuario'];
$marcado = $_GET['marcado'];
$elemento = $_GET['elemento'];

$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	echo "false";
	exit();
}

$comprobacion="UPDATE listacompra SET marcado= '$marcado' WHERE email='$usuario' and elemento='$elemento'";
$result= mysqli_query($con, $comprobacion);

if (!$result) 
{
    $result[] = array('resultado' => false);
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}

mysqli_close($con);

?>

