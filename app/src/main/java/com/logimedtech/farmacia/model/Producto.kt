package com.logimedtech.farmacia.model

import java.time.LocalDate

data class Producto(
    val codigo: Int,
    val nombre: String,
    val tipoMedicamento: String,
    val laboratorio: String,
    val fechaIngreso: LocalDate,
    val fechaCaducidad: LocalDate,
    val existencia: Int,
    val precioVenta: Float,
    val idProveedor: Int,
    val estado: Int
)
