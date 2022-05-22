<?php
$hostname="localhost";
$database="Xeverhorst001_kubuk";
$username="Xeverhorst001";
$password="*EyzCAv7UH";
$json=array();
   
 if((isset($_POST["name"]))&&(isset($_POST["user"]))&&(isset($_POST["descripcion"]))&&(isset($_POST["ingredientes"]))) {
        
        $user=$_POST['user'];
        $newName=$_POST['newName'];
        $name=$_POST['name'];
        $descripcion=$_POST['descripcion'];
        $ingredientes=$_POST['ingredientes'];

       
       
        $conexion = mysqli_connect($hostname,$username,$password,$database);

        $consulta="UPDATE misRecetas SET titulo='{$newName}',preparacion='{$descripcion}',ingredientes='{$ingredientes}' WHERE usuario='{$user}' AND titulo ='{$name}'"; 
        $resultado = mysqli_query($conexion,$consulta);

        
        mysqli_close($conexion);

       
       
}
?>
