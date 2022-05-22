<?php

$json=array();

 if(isset($_POST["User"])) {

        $user = $_POST["user"];
        $name = $_POST["name"];
        
        $con = mysqli_connect("localhost", "Xeverhorst001", "*EyzCAv7UH", "Xeverhorst001_kubuk") or die("Error al conectarse");

        $query= "SELECT titulo, preparacion, ingredientes FROM misRecetas WHERE usuario='{$user}' AND titulo ='{$name}'"; 
        $result= mysqli_query($con,$query); 

        if(!$result) {
            echo'Ha ocurrido algún error: '. mysqli_error($con);
            exit;

        } else{

            mysqli_close($con);
            
           
            $results["titulo"]='';
            $results["descripcion"]='';
            $results["ingredientes"]='';

            $json['datos'][]=$results;
            echo json_encode($json);

        }

}

?>
