<?php
# Configurar conexion
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse


	#Comprobamos si se han recibido los parametros
	if (isset($_GET['nombre']) && isset($_GET['email']) && isset($_GET['passwd'])){
		
		$nombre = $_GET['nombre'];
		$email = $_GET['email'];
		$contra = $_GET['passwd'];
		$contra=password_hash($contra, PASSWORD_DEFAULT);
		
		# Se establece la conexión:
		$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
		
		#Comprobamos conexión
		if (mysqli_connect_errno($con)) {
			echo 'Error de conexion: ' . mysqli_connect_error();
			exit();
		}
		
		$consulta= "SELECT email FROM Usuario WHERE email = '{$email}'";
		$resultado=mysqli_query($con,$consulta);
		
		if($resultado){
			$registro = mysqli_fetch_row($resultado);
			if ($registro[0] == $email){
				echo 'Registro_emailexiste';
			}else{
				$query = "INSERT INTO Usuario(nombre,email,password) VALUES ('{$nombre}','{$email}','{$contra}')";
				$insert = mysqli_query($con,$query);
				
				if($insert){
					echo 'Registro_done';
					mysqli_close($con);
				}
				else{
					echo 'Registro_notdone';
				}
				
			}
		}else{
			echo 'Error_consulta_1';
		}
			
	} else{
		echo 'Error WS';
	}

?>