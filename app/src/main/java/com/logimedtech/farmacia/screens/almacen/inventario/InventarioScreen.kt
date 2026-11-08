package com.logimedtech.farmacia.screens.almacen.inventario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun InventarioRoute(viewModel: InventarioViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InventarioScreen(uiState = uiState)
}

@Composable
fun InventarioScreen(uiState: InventarioUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Inventario")
    }
}