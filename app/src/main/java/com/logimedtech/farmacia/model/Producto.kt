package com.logimedtech.farmacia.model

import java.time.LocalDate

data class Producto(
    val codigo: Int,
    val nombre: String,
    val tipo_medicamento: String,
    val laboratorio: String,
    val fecha_Ingreso: LocalDate,
    val fecha_caducidad: LocalDate,
    val existencia: Int,
    val precio_venta: Float,
    val id_proveedor: Int,
    val estado: Int
)
