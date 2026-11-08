package com.logimedtech.farmacia.model

import java.time.LocalDate

data class Abastecimiento(
    val id_abastecimiento: Int,
    val fecha: LocalDate,
    val cantidad_ingresada: Int,
    val codigo_producto: Int,
    val id_empleado: Int,
    val id_proveedor: Int
    )
