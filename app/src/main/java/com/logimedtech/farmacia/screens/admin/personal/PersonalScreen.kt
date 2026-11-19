package com.logimedtech.farmacia.screens.admin.personal

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
import com.logimedtech.farmacia.model.Empleado

@Composable
fun PersonalRoute(viewModel: PersonalViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PersonalScreen(
        uiState = uiState,
        onBusquedaChange = viewModel::enCambioTextoBusqueda,
        onAgregarClick = viewModel::abrirFormularioCrear,
        onEditarClick = viewModel::abrirFormularioEditar,
        onEliminarClick = viewModel::confirmarEliminar,
        onGuardarEmpleado = viewModel::guardarEmpleado,
        onCerrarFormulario = viewModel::cerrarFormulario,
        onConfirmarEliminar = viewModel::ejecutarEliminacion,
        onCancelarEliminar = viewModel::cancelarEliminar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalScreen(
    uiState: PersonalUiState,
    onBusquedaChange: (String) -> Unit,
    onAgregarClick: () -> Unit,
    onEditarClick: (Empleado) -> Unit,
    onEliminarClick: (Empleado) -> Unit,
    onGuardarEmpleado: (String, String, String, String, String, String, String) -> Unit,
    onCerrarFormulario: () -> Unit,
    onConfirmarEliminar: () -> Unit,
    onCancelarEliminar: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Personal") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Empleado")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.textoBusqueda,
                onValueChange = onBusquedaChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar empleado...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.cargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.error, color = MaterialTheme.colorScheme.error)
                }
            } else if (uiState.listaEmpleados.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay empleados registrados.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.listaEmpleados, key = { it.id_empleado }) { empleado ->
                        EmpleadoItemCard(
                            empleado = empleado,
                            onEditar = { onEditarClick(empleado) },
                            onEliminar = { onEliminarClick(empleado) }
                        )
                    }
                }
            }
        }
    }

    if (uiState.mostrarDialogoFormulario) {
        FormularioEmpleadoDialog(
            empleado = uiState.empleadoAEditar,
            onDismiss = onCerrarFormulario,
            onGuardar = onGuardarEmpleado
        )
    }

    if (uiState.empleadoAEliminar != null) {
        AlertDialog(
            onDismissRequest = onCancelarEliminar,
            title = { Text("Eliminar Empleado") },
            text = { Text("¿Deseas dar de baja a ${uiState.empleadoAEliminar.nombre}?") },
            confirmButton = {
                TextButton(onClick = onConfirmarEliminar) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = onCancelarEliminar) { Text("Cancelar") }
            }
        )
    }
}

@Composable
fun EmpleadoItemCard(empleado: Empleado, onEditar: () -> Unit, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${empleado.nombre} ${empleado.apellido_paterno}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Rol: ${empleado.rol}", style = MaterialTheme.typography.labelMedium)
                Text(text = "Correo: ${empleado.correo}", style = MaterialTheme.typography.bodySmall)
            }

            // Botón de editar (siempre aparece)
            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }

            // Convertimos el rol a minúsculas para ignorar mayúsculas y minúsculas originales
            val rolEmpleado = empleado.rol.lowercase()

            // Validamos que no sea "admin" ni "administrador"
            if (rolEmpleado != "admin" && rolEmpleado != "administrador") {
                IconButton(onClick = onEliminar) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun FormularioEmpleadoDialog(
    empleado: Empleado?,
    onDismiss: () -> Unit,
    onGuardar: (String, String, String, String, String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf(empleado?.nombre ?: "") }
    var apePaterno by remember { mutableStateOf(empleado?.apellido_paterno ?: "") }
    var apeMaterno by remember { mutableStateOf(empleado?.apellido_materno ?: "") }
    var correo by remember { mutableStateOf(empleado?.correo ?: "") }
    var telefono by remember { mutableStateOf(empleado?.telefono ?: "") }
    var rol by remember { mutableStateOf(empleado?.rol ?: "Empleado") }
    var clave by remember { mutableStateOf("") } // La contraseña siempre inicia vacía

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (empleado == null) "Nuevo Empleado" else "Editar Empleado") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(value = apePaterno, onValueChange = { apePaterno = it }, label = { Text("Apellido Paterno") })
                OutlinedTextField(value = apeMaterno, onValueChange = { apeMaterno = it }, label = { Text("Apellido Materno") })
                OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo Electrónico") })
                OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") })
                OutlinedTextField(value = rol, onValueChange = { rol = it }, label = { Text("Rol (Admin/Empleado)") })
                OutlinedTextField(
                    value = clave,
                    onValueChange = { clave = it },
                    label = { Text(if (empleado == null) "Contraseña" else "Nueva Contraseña (Opcional)") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onGuardar(nombre, apePaterno, apeMaterno, correo, telefono, rol, clave) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}