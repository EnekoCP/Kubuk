<?php
$hostname="localhost";
$database="Xeverhorst001_kubuk";
$username="Xeverhorst001";
$password="*EyzCAv7UH";
$json=array();
   
 if((isset($_POST["imagen1"]))&&(isset($_POST["imagen2"]))&&(isset($_POST["imagen3"]))
        &&(isset($_POST["observaciones"]))&&(isset($_POST["name"]))&&(isset($_POST["user"]))&&(isset($_POST["descripcion"]))&&(isset($_POST["ingredientes"]))) {
        
        $user=$_POST['user'];
        $name=$_POST['name'];
        $descripcion=$_POST['descripcion'];
        $ingredientes=$_POST['ingredientes'];

        $foto1=$_POST['imagen1'];
        $foto2=$_POST['imagen2'];
        $foto3=$_POST['imagen3'];
        $observa=$_POST['observaciones'];
       
        
        $conexion = mysqli_connect($hostname,$username,$password,$database);

        $consulta="INSERT INTO misRecetas(usuario, titulo, preparacion, ingredientes, puntuacion, imagen1,imagen2,imagen3,observaciones) VALUES ('{$user}','{$name}','{$descripcion}', '{$ingredientes}' ,'0','{$foto1}' ,'{$foto2}','{$foto3}','{$observaciones}')";
        $resultado = mysqli_query($conexion,$consulta);

        
        mysqli_close($conexion);

        $results["user"]=$user;
        $results["name"]=$name;
        $results["descripcion"]=$descripcion;
        $results["ingredientes"]=$ingredientes;
        
        $json['datos'][]=$results;
        echo json_encode($json);
       
}
?>
