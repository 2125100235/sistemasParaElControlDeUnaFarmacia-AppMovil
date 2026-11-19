package com.logimedtech.farmacia.model

import com.google.gson.annotations.SerializedName

data class Producto(
    val codigo: Int = 0,
    val nombre: String = "",
    @SerializedName("tipo_medicamento") val tipoMedicamento: String = "OTC / Libre Venta",
    val laboratorio: String = "",
    val lote: String = "",
    @SerializedName("fecha_caducidad") val fechaCaducidad: String = "",
    val existencia: Int = 0,
    @SerializedName("precio_venta") val precioVenta: Double = 0.0,
    @SerializedName("id_proveedor") val idProveedor: Int? = null,
    val imagen: String = "placeholderFotoUsuario.png"
)

data class ProductosResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    val datos: List<Producto> = emptyList()
)

data class OperacionResponse(
    val exito: Boolean = false,
    val mensaje: String = ""
)