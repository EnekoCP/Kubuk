<?php
$DB_SERVER="localhost"; #la dirección del servidor
$DB_USER="Xeverhorst001"; #el usuario para esa base de datos
$DB_PASS="*EyzCAv7UH"; #la clave para ese usuario
$DB_DATABASE="Xeverhorst001_kubuk"; #la base de datos a la que hay que conectarse




$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	echo "false";
	exit();
}

$funcion = $_GET['funcion'];
if($funcion=='send'){
	sendMessage($con);
}else if($funcion=='save'){
	saveToken($con);
}

function sendMessage($con){
	$fromToken = $_GET['fromToken'];
	$receta = $_GET['receta'];
	$puntuacion = $_GET['puntuacion'];
	$autor = $_GET['autor'];
	$user= $_GET['user'];

	$get="SELECT token FROM Usuario where email='$autor'";
	$result= mysqli_query($con, $get);
	if (!result){
		echo 'Ha ocurrido algún error: ' . mysqli_error($con);
		$resultado[] = array('resultado' => false);
	}else{
		while($row =$result->fetch_assoc()){
				$toToken=$row['token'];
		}
		$resultado[]=array('resultado' => $toToken);
	}

	$get="SELECT nombre FROM Usuario where email='$user'";
	$result= mysqli_query($con, $get);
	if (!result){
		echo 'Ha ocurrido algún error: ' . mysqli_error($con);
		$resultado[] = array('resultado' => false);
	}else{
		while($row =$result->fetch_assoc()){
				$userIzena=$row['nombre'];
		}
		$resultado[]=array('resultado' => $toToken);
	}
	mysqli_close($con);
	echo json_encode($resultado);

	$cabecera= array(
	'Authorization: key=AAAA01IusuY:APA91bFUnrNqXZsE8PX4Ld-BAITb2Lu1C-jVt_L0_GUpni7O_fBHKy_9-Hmcwf4ktiVYvgAr_oNeEPoAIL9cSqUb41ULw3WKAGYS2UAoQBQRhiTvI96eHntpNVhJZEWpO1kMN1qkyjAy',
	'Content-Type: application/json'
	);

	$msg= array(
	'to'=>  $toToken,
	'notification' => array (
	'body' => $userIzena.' ha valorado con '.$puntuacion.' puntos',
	'title' => 'Receta: '.$receta,
	'icon' => 'ic_stat_ic_notification',
	),

	);
	$msgJSON= json_encode ( $msg);

	$ch = curl_init(); #inicializar el handler de curl
	#indicar el destino de la petición, el servicio FCM de google
	curl_setopt( $ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
	#indicar que la conexión es de tipo POST
	curl_setopt( $ch, CURLOPT_POST, true );
	#agregar las cabeceras
	curl_setopt( $ch, CURLOPT_HTTPHEADER, $cabecera);
	#Indicar que se desea recibir la respuesta a la conexión en forma de string
	curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
	#agregar los datos de la petición en formato JSON
	curl_setopt( $ch, CURLOPT_POSTFIELDS, $msgJSON );
	#ejecutar la llamada
	$resultado= curl_exec( $ch );
	#cerrar el handler de curl
	curl_close( $ch );
	if (curl_errno($ch)) {
		print curl_error($ch);
	}
	echo $resultado;

}

function saveToken($con){

	$fromToken = $_GET['fromToken'];
	$user= $_GET['user'];

	$insert="UPDATE Usuario SET token = '$fromToken' WHERE email='$user' ";
	$result= mysqli_query($con, $insert);
	if (!result){
		echo 'Ha ocurrido algún error: ' . mysqli_error($con);
		$resultado[] = array('resultado' => false);
	}else{
		$resultado[] = array('resultado' => true);
	}
	mysqli_close($con);
	echo json_encode($resultado);

}



?>