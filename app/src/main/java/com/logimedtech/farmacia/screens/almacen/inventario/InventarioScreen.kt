package com.logimedtech.farmacia.screens.almacen.inventario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.logimedtech.farmacia.model.Producto
import com.logimedtech.farmacia.ui.theme.FarmaciaTheme

@Composable
fun InventarioRoute(viewModel: InventarioViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InventarioScreen(
        uiState = uiState,
        onBusquedaChange = viewModel::enCambioTextoBusqueda,
        onAgregarClick = viewModel::abrirFormularioCrear,
        onEditarClick = viewModel::abrirFormularioEditar,
        onEliminarClick = viewModel::confirmarEliminar,
        onGuardarProducto = viewModel::guardarProducto,
        onCerrarFormulario = viewModel::cerrarFormulario,
        onConfirmarEliminar = viewModel::ejecutarEliminacion,
        onCancelarEliminar = viewModel::cancelarEliminar
    )
}

@Composable
fun InventarioScreen(
    uiState: InventarioUiState,
    onBusquedaChange: (String) -> Unit,
    onAgregarClick: () -> Unit,
    onEditarClick: (Producto) -> Unit,
    onEliminarClick: (Producto) -> Unit,
    onGuardarProducto: (String, String, String, String, String, Int, Double) -> Unit,
    onCerrarFormulario: () -> Unit,
    onConfirmarEliminar: () -> Unit,
    onCancelarEliminar: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregarClick,
                containerColor = FarmaciaTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = "Inventario de Productos", style = FarmaciaTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(12.dp))

            // Buscador
            OutlinedTextField(
                value = uiState.textoBusqueda,
                onValueChange = onBusquedaChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar por nombre o código...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.cargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                // Mostrará el motivo real si falla la base de datos o la red
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = uiState.error!!,
                        style = FarmaciaTheme.typography.bodyMedium,
                        color = FarmaciaTheme.colorScheme.error
                    )
                }
            } else if (uiState.listaProductos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay productos registrados.", style = FarmaciaTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.listaProductos, key = { it.codigo }) { producto ->
                        ProductoItemCard(
                            producto = producto,
                            onEditar = { onEditarClick(producto) },
                            onEliminar = { onEliminarClick(producto) }
                        )
                    }
                }
            }
        }
    }

    // Modal de Creación / Edición
    if (uiState.mostrarDialogoFormulario) {
        FormularioProductoDialog(
            producto = uiState.productoAEditar,
            onDismiss = onCerrarFormulario,
            onGuardar = onGuardarProducto
        )
    }

    // Modal de Confirmación para Eliminar
    if (uiState.productoAEliminar != null) {
        AlertDialog(
            onDismissRequest = onCancelarEliminar,
            title = { Text("Eliminar Producto") },
            text = { Text("¿Deseas eliminar '${uiState.productoAEliminar.nombre}'?") },
            confirmButton = {
                TextButton(onClick = onConfirmarEliminar) { Text("Eliminar", color = FarmaciaTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = onCancelarEliminar) { Text("Cancelar") }
            }
        )
    }
}

@Composable
fun ProductoItemCard(
    producto: Producto,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = FarmaciaTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nombre, style = FarmaciaTheme.typography.titleMedium)
                Text(text = "Lab: ${producto.laboratorio} | Tipo: ${producto.tipoMedicamento}", style = FarmaciaTheme.typography.labelSmall)
                Text(text = "Stock: ${producto.existencia} u. | Caducidad: ${producto.fechaCaducidad}", style = FarmaciaTheme.typography.bodySmall)
                Text(text = "$${producto.precioVenta}", style = FarmaciaTheme.typography.titleMedium, color = FarmaciaTheme.colorScheme.primary)
            }
            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = FarmaciaTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun FormularioProductoDialog(
    producto: Producto?,
    onDismiss: () -> Unit,
    onGuardar: (String, String, String, String, String, Int, Double) -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var tipo by remember { mutableStateOf(producto?.tipoMedicamento ?: "OTC / Libre Venta") }
    var laboratorio by remember { mutableStateOf(producto?.laboratorio ?: "") }
    var lote by remember { mutableStateOf(producto?.lote ?: "") }
    var caducidad by remember { mutableStateOf(producto?.fechaCaducidad ?: "") }
    var existencia by remember { mutableStateOf(producto?.existencia?.toString() ?: "0") }
    var precio by remember { mutableStateOf(producto?.precioVenta?.toString() ?: "0.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (producto == null) "Nuevo Producto" else "Editar Producto") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(value = laboratorio, onValueChange = { laboratorio = it }, label = { Text("Laboratorio") })
                OutlinedTextField(value = lote, onValueChange = { lote = it }, label = { Text("Lote") })
                OutlinedTextField(value = caducidad, onValueChange = { caducidad = it }, label = { Text("Caducidad (YYYY-MM-DD)") })
                OutlinedTextField(value = existencia, onValueChange = { existencia = it }, label = { Text("Existencia") })
                OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio Venta") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val stockInt = existencia.toIntOrNull() ?: 0
                val precioDouble = precio.toDoubleOrNull() ?: 0.0
                onGuardar(nombre, tipo, laboratorio, lote, caducidad, stockInt, precioDouble)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}