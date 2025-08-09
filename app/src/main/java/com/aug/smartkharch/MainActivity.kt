package com.aug.smartkharch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.aug.smartkharch.ui.navigation.AppNavGraph
import com.aug.smartkharch.ui.theme.SmartKharchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartKharchTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}