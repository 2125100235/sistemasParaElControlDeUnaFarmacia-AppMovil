<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "../php/conexion.php";

$consulta = "SELECT codigo, nombre, existencia, precio_venta FROM producto WHERE estado = 1";
$resultado = $conexion->query($consulta);

$productos = [];
if ($resultado) {
    while ($fila = $resultado->fetch_assoc()) {
        // Formatear tipos de datos para JSON
        $fila['codigo'] = (int)$fila['codigo'];
        $fila['existencia'] = (int)$fila['existencia'];
        $fila['precio_venta'] = (float)$fila['precio_venta'];
        $productos[] = $fila;
    }
}

echo json_encode($productos);
?>