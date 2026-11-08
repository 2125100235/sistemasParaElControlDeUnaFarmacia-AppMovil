package com.logimedtech.farmacia.screens.almacen.inicio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.logimedtech.farmacia.ui.theme.FarmaciaTheme

@Composable
fun Inicio(){

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        MenuLateral()
        Text(
            text = "FarmaVive",
            style = FarmaciaTheme.typography.titleLarge
        )
        Text(
            text = "Farmavive",
            style = FarmaciaTheme.typography.bodySmall
        )
        Card() {
            Column() {
                Text(
                    text = "Producto"
                )
            }
        }
    }

}

@Composable
fun MenuLateral() {
    // Stub for MenuLateral
}

@Preview(showBackground = true)
@Composable
fun InicioPreview(){
    FarmaciaTheme{
        Inicio()
    }
}
