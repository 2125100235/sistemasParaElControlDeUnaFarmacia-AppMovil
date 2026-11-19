package com.logimedtech.farmacia.screens.almacen.inventario

import com.logimedtech.farmacia.model.Producto

data class InventarioUiState(
    val listaProductos: List<Producto> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null,
    val textoBusqueda: String = "",
    val mostrarDialogoFormulario: Boolean = false,
    val productoAEditar: Producto? = null,
    val productoAEliminar: Producto? = null
)