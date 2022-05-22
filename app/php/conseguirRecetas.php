<?php
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse

$email = $_GET['email'];
$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	echo "false";
	exit();
}

$comprobacion="SELECT * FROM recetaComunidad";
$result= mysqli_query($con, $comprobacion);

if (!$result) 
{
    $result[] = array('resultado' => false);
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}

if(mysqli_num_rows($result)>0){
  while($row = $result->fetch_assoc()){
	$titulo=$row['titulo'];
	$ingredientes=$row['ingredientes'];
	$preparacion=$row['preparacion'];
	$usuario=$row['usuario'];
	$puntuacion=$row['puntuacion'];
	$receta[]=array('titulo'=>$titulo,'ingredientes'=>$ingredientes,'preparacion'=>$preparacion,'usuario'=>$usuario,'puntuacion'=>$puntuacion);
	
  }
    
}
else{
	$receta= "false";
}
echo json_encode($receta);

mysqli_close($con);

?>

