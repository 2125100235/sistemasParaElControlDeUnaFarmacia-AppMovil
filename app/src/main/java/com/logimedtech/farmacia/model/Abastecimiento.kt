package com.logimedtech.farmacia.model

import java.time.LocalDate

data class Abastecimiento(
    val idAbastecimiento: Int,
    val fecha: LocalDate,
    val cantidadIngresada: Int,
    val codigoProducto: Int,
    val idEmpleado: Int,
    val idProveedor: Int
    )
