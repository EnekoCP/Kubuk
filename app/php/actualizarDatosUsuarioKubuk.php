<?php
# Configurar conexion
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse

#Comprobamos si se han recibido los parametros
	if (isset($_GET['email']) && isset($_GET['passwd'])){

		$email = $_GET['email'];
		$contra = $_GET['passwd'];
		
		# Se establece la conexión:
		$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
		
		#Comprobamos conexión
		if (mysqli_connect_errno($con)) {
			echo 'Error de conexion: ' . mysqli_connect_error();
			exit();
		}
		
		$update= "UPDATE Usuario SET password = '{$contra}' WHERE email = '{$email}'";
		$resultado=mysqli_query($con,$update);
		
		if($resultado){
			echo 'Modificacion_done';
			mysqli_close($con);
		}
		else{
			echo 'Modificacion_notdone';
		}
			
	} else{
		echo 'Error WS';
	}

?>