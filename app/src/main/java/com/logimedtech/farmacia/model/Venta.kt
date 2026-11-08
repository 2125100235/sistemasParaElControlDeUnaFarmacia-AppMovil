package com.logimedtech.farmacia.model

import java.time.LocalDate

data class Venta(
    val numero_nota: Int,
    val fecha: LocalDate,
    val metodo_pago: String,
    val total_pagar: Float,
    val codigo_cliente: Int,
    val id_empleado: Int
)
