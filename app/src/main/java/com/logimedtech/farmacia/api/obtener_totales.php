<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "../php/conexion.php";

// Consultas acumuladas
$sqlDia = "SELECT IFNULL(SUM(total_pagar), 0) AS total FROM venta WHERE DATE(fecha) = CURDATE()";
$sqlSemana = "SELECT IFNULL(SUM(total_pagar), 0) AS total FROM venta WHERE YEARWEEK(fecha, 1) = YEARWEEK(CURDATE(), 1)";
$sqlMes = "SELECT IFNULL(SUM(total_pagar), 0) AS total FROM venta WHERE MONTH(fecha) = MONTH(CURDATE()) AND YEAR(fecha) = YEAR(CURDATE())";

$totalDia = (float)$conexion->query($sqlDia)->fetch_assoc()['total'];
$totalSemana = (float)$conexion->query($sqlSemana)->fetch_assoc()['total'];
$totalMes = (float)$conexion->query($sqlMes)->fetch_assoc()['total'];

echo json_encode([
    "total_dia" => $totalDia,
    "total_semana" => $totalSemana,
    "total_mes" => $totalMes
]);
?>