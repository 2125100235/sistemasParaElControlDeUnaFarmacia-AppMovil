package com.logimedtech.farmacia

import java.time.LocalDate

data class Producto(
    val codigo: Int,
    val nombre: String,
    val precio: String,
    val tipo: Double,
    val laboratorio: String,
    val fechaIngreso: LocalDate,
    val fechaCaducidad: LocalDate
)
