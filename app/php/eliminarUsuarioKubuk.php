<?php
# Configurar conexion
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse


	#Comprobamos si se han recibido los parametros
	if (isset($_GET['email'])){
		
		$email = $_GET['email'];
		
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
				$query1 = "DELETE FROM Usuario WHERE email = '{$email}'";
				$delete1 = mysqli_query($con,$query1);
				$query2 = "DELETE FROM misRecetas WHERE usuario = '{$email}'";
				$delete2 = mysqli_query($con,$query2);
				$query3 = "DELETE FROM recetaComunidad WHERE usuario = '{$email}'";
				$delete3 = mysqli_query($con,$query3);
				$query4 = "DELETE FROM listacompra WHERE email = '{$email}'";
				$delete4 = mysqli_query($con,$query4);
				$query5 = "DELETE FROM likes WHERE usuario = '{$email}'";
				$delete5 = mysqli_query($con,$query5);
				$query6 = "DELETE FROM likes WHERE autor = '{$email}'";
				$delete6 = mysqli_query($con,$query6);
				if($delete1 && ($delete2 || $delete3 || $delete4 || $delete5 || $delete6)){
					echo 'Eliminado_done';
					mysqli_close($con);
				}
				else{
					echo 'Eliminado_notdone';
				}
			}else{
				echo 'Emailnoexiste_notdone';
			}
		}else{
			echo 'Error_consulta_1';
		}
			
	} else{
		echo 'Error WS';
	}

?>