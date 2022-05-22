<?php
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse

$email = $_GET['email'];
$elemento = $_GET['titulo'];
$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	echo "false";
	exit();
}

$comprobacion="SELECT imagen1,imagen2,imagen3 FROM recetaComunidad where usuario='$email' and titulo='$elemento'";
$result= mysqli_query($con, $comprobacion);

if (!$result) 
{
    $result[] = array('resultado' => false);
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}

if(mysqli_num_rows($result)>0){
  while($row = $result->fetch_assoc()){
	$i1=$row['imagen1'];
	$i2=$row['imagen2'];
	$i3=$row['imagen3'];
	$receta[]=array('imagen1'=>$i1,'imagen2'=>$i2,'imagen3'=>$i3);
	
  }
    
}
else{
	$receta= "false";
}
echo json_encode($receta);

mysqli_close($con);

?>

