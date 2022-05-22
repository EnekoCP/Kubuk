<?php


 if(isset($_GET["user"]) && isset($_GET["name"])) {

        $user = $_GET["user"];
        $name = $_GET["name"];
        
        $con = mysqli_connect("localhost", "Xeverhorst001", "*EyzCAv7UH", "Xeverhorst001_kubuk") or die("Error al conectarse");

        $query= "DELETE FROM misRecetas WHERE usuario='{$user}' AND titulo ='{$name}'"; 
        $result= mysqli_query($con,$query); 

        if(!$result) {
            echo'Ha ocurrido algún error: '. mysqli_error($con);
            exit;

        } else{
            
        mysqli_close($conexion);
        $results["user"]=$user;
        $results["name"]=$name;
        $json['datos'][]=$results;
        echo json_encode($json);
            

        }

}

?>
