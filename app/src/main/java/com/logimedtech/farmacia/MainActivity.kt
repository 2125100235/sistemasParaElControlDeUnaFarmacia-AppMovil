package com.logimedtech.farmacia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.logimedtech.farmacia.navigation.AppNavigation
import com.logimedtech.farmacia.ui.theme.FarmaciaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FarmaciaTheme {
                AppNavigation()
            }
        }
    }
}
