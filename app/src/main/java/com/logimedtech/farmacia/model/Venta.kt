package com.logimedtech.farmacia.model

import java.time.LocalDate

data class Venta(
    val numeroNota: Int,
    val fecha: LocalDate,
    val metodoPago: String,
    val totalPagar: Float,
    val codigoCliente: Int,
    val idEmpleado: Int
)
