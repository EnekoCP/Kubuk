<?php
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse


if((isset($_POST["titulo"]))&&(isset($_POST["ingredientes"]))&&(isset($_POST["preparacion"]))&&(isset($_POST["usuario"]))) {

	$titulo = $_POST['titulo'];
	$ingredientes = $_POST['ingredientes'];
	$preparacion = $_POST['preparacion'];
	$usuario = $_POST['usuario'];

	$conexion = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
	if (!$conexion){
		die("Conexion fallida: " . mysqli_connect_error());
	}

        $consulta="INSERT INTO recetaComunidad (titulo,ingredientes,preparacion,usuario,puntuacion) VALUES ('".$titulo."','".$ingredientes."','".$preparacion."','".$usuario."','0')"; 
        if (mysqli_query($conexion,$consulta)){
		echo("insercion realizada: ".$consulta);
	}
	else{
		echo "Error: " . $consulta . " -|- " . mysqli_error($conexion);
	}
        
        mysqli_close($conexion);

}

?>	