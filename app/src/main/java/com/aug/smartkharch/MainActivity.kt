package com.aug.smartkharch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.aug.smartkharch.ui.expenselist.viewmodel.ExpenseListViewModel
import com.aug.smartkharch.ui.navigation.AppNavGraph
import com.aug.smartkharch.ui.theme.SmartKharchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val expenseListViewModel: ExpenseListViewModel = hiltViewModel()
            val isDarkTheme by expenseListViewModel.isDarkTheme.collectAsState()
            val window = this.window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val insetsController = WindowInsetsControllerCompat(window, window.decorView)
            insetsController.isAppearanceLightStatusBars = !isDarkTheme

            SmartKharchTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                AppNavGraph(navController, expenseListViewModel)
            }
        }
    }
}