<?php
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse


$funcion=$_GET['funcion'];
$email = $_GET['email'];
$receta = $_GET['receta'];
$autor = $_GET['autor'];

$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	echo "false";
	exit();
}
if ($funcion=='set'){
	$puntuacion=$_GET['puntuacion'];
	insert($email, $autor,$receta,$puntuacion,$con);

}else if ($funcion=='get'){
	get($email, $autor,$receta,$con);
}


function insert($email, $autor,$receta,$puntuacion,$con){

	$comprobacion="SELECT * FROM likes where usuario='$email' and autor='$autor' and receta='$receta'";
	$result= mysqli_query($con, $comprobacion);

	if (!$result) 
	{
	    $resultado[] = array('resultado' => false);
		echo 'Ha ocurrido algún error: ' . mysqli_error($con);
	}

	if(mysqli_num_rows($result)>0){
				$updatelike="UPDATE likes set puntuacion='$puntuacion' where receta='$receta' and autor='$autor' and usuario='$email'";
				$result= mysqli_query($con, $updatelike);
				if (!$result) 
					{
					    $resultado[] = array('resultado' => "update false");
						echo 'Ha ocurrido algún error: ' . mysqli_error($con);
					}else{
						$resultado[] = array('resultado' => "update true");
					}

	}else{
			$insertlike="INSERT INTO likes (receta,autor,usuario,puntuacion) values('$receta','$autor','$email','$puntuacion')";
			$result= mysqli_query($con, $insertlike);

			if (!$result) 
					{
					    $resultado[] = array('resultado' => "insert false");
						echo 'Ha ocurrido algún error: ' . mysqli_error($con);
					}else{
						$resultado[] = array('resultado' => "insert true");
					}
	}

	$getPuntuacion="SELECT count(*) as kop, sum(puntuacion) as puntos from likes where receta='$receta' and autor='$autor'";
	$result= mysqli_query($con, $getPuntuacion);

	while($row = $result->fetch_assoc()){
		$kop=$row['kop'];
		$puntos=$row['puntos'];
	}

	$puntuacionFinal=$puntos / $kop;

	$updatePuntuacion="UPDATE recetaComunidad set puntuacion='$puntuacionFinal' where titulo='$receta' and usuario='$autor'";
	$result= mysqli_query($con, $updatePuntuacion);


	if (!$result) 
	{
	    $resultado[] = array('resultado' => false);
		echo 'Ha ocurrido algún error: ' . mysqli_error($con);
	}else{
		$resultado[] = array('resultado' => true);
	}

		
	mysqli_close($con);
	echo json_encode($resultado);
}


function get($email, $autor,$receta,$con){
	$get="SELECT puntuacion FROM likes where usuario='$email' and autor='$autor' and receta='$receta'";
	$result= mysqli_query($con, $get);
	if (!result){
		echo 'Ha ocurrido algún error: ' . mysqli_error($con);
    $resultado[] = array('resultado' => false);
	}else{
		while($row =$result->fetch_assoc()){
				$valoracion=$row['puntuacion'];
		}
		$resultado[]=array('resultado' => $valoracion);
	}
	mysqli_close($con);
	echo json_encode($resultado);
}






?>
