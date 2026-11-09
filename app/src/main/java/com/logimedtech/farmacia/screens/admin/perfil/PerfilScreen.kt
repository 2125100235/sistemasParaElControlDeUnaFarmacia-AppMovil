package com.logimedtech.farmacia.screens.admin.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.logimedtech.farmacia.ui.theme.FarmaciaTheme

@Composable
fun PerfilRoute(
    onLogout: () -> Unit,
    viewModel: PerfilViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    PerfilScreen(
        uiState = uiState,
        onCerrarSesion = { viewModel.cerrarSesion(onLogout) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    uiState: PerfilUiState,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil de Usuario") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.cargando -> CircularProgressIndicator()
                uiState.error != null -> Text(text = uiState.error ?: "", color = FarmaciaTheme.colorScheme.error)
                uiState.usuario != null -> {
                    val user = uiState.usuario!!
                    val nombreCompleto = "${user.nombre.orEmpty()} ${user.apellido_paterno.orEmpty()} ${user.apellido_materno.orEmpty()}".trim()
                    val inicial = user.nombre?.take(1)?.uppercase() ?: "U"

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar circular con la inicial
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(FarmaciaTheme.colorScheme.primaryContainer, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = inicial,
                                style = FarmaciaTheme.typography.headlineLarge,
                                color = FarmaciaTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = nombreCompleto, style = FarmaciaTheme.typography.titleLarge)

                        // Badge distintivo para el Rol
                        AssistChip(
                            onClick = { },
                            label = { Text(user.rol.orEmpty()) },
                            leadingIcon = { Icon(Icons.Default.Work, contentDescription = null) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (user.rol == "Administrador")
                                    FarmaciaTheme.colorScheme.primaryContainer
                                else
                                    FarmaciaTheme.colorScheme.secondaryContainer
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Tarjeta de detalles del empleado
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = FarmaciaTheme.colorScheme.primary)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                ItemDetalle(icon = Icons.Default.Badge, titulo = "ID Empleado", valor = "#${user.id_empleado ?: ""}")
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                ItemDetalle(icon = Icons.Default.Email, titulo = "Correo", valor = user.correo.orEmpty())
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                ItemDetalle(icon = Icons.Default.Phone, titulo = "Teléfono", valor = user.telefono.orEmpty())
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Botón de Cerrar Sesión
                        Button(
                            onClick = onCerrarSesion,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = FarmaciaTheme.colorScheme.error)
                        ) {
                            Icon(Icons.Default.ExitToApp, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cerrar Sesión")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemDetalle(icon: ImageVector, titulo: String, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = FarmaciaTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = titulo, style = FarmaciaTheme.typography.labelSmall, color = Color.Gray)
            Text(text = valor, style = FarmaciaTheme.typography.bodyMedium)
        }
    }
}
