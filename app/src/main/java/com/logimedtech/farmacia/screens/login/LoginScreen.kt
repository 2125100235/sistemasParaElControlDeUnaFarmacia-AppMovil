package com.logimedtech.farmacia.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.logimedtech.farmacia.model.Empleado
import com.logimedtech.farmacia.ui.theme.FarmaciaTheme

@Composable
fun LoginRoute(
    onLoginSuccess: (Empleado) -> Unit,
    viewModel: LoginViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.usuario) {
        uiState.usuario?.let { usuarioLogueado ->
            onLoginSuccess(usuarioLogueado)
        }
    }
    LoginScreen(
        uiState = uiState,
        onCorreoChanged = viewModel::onCorreoChanged,
        onClaveChanged = viewModel::onClaveChanged,
        onIniciarSesion = viewModel::IniciarSesion
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onCorreoChanged: (String) -> Unit,
    onClaveChanged: (String) -> Unit,
    onIniciarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            style = FarmaciaTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))
        // Campo del correo
        OutlinedTextField(
            value = uiState.correo,
            onValueChange = onCorreoChanged,
            label = { Text("Correo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo de la clave
        OutlinedTextField(
            value = uiState.clave,
            onValueChange = onClaveChanged,
            label = { Text("Clave") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de error
        if(uiState.error != null){
            Text(
                text = uiState.error,
                color = FarmaciaTheme.colorScheme.error,
                style = FarmaciaTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Pantalla de carga o botón de inicio de sesión
        if(uiState.cargando){
            CircularProgressIndicator()
        }
        else{
            Button(onClick = onIniciarSesion,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.correo.isNotBlank() && uiState.clave.isNotBlank()){
                Text("Acceder")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    FarmaciaTheme {
        LoginScreen(
            uiState = LoginUiState(),
            onCorreoChanged = {},
            onClaveChanged = {},
            onIniciarSesion = {}
        )
    }
}