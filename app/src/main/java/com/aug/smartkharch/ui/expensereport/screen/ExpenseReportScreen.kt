package com.aug.smartkharch.ui.expensereport.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aug.smartkharch.R
import com.aug.smartkharch.data.model.BarChartData
import com.aug.smartkharch.ui.expensereport.components.CategoryTotalSection
import com.aug.smartkharch.ui.expensereport.components.DailyTotalsSection
import com.aug.smartkharch.ui.expensereport.state.ExpenseReportUiState
import com.aug.smartkharch.ui.expensereport.viewmodel.ExpenseReportViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseReportScreen(
    onBackClick: () -> Unit,
    viewModel: ExpenseReportViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading) {
        if (isLoading){
            delay(2000)
            isLoading = false
            Toast.makeText(context, context.getString(R.string.report_downloaded),Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.weekly_report)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        when (state) {
            is ExpenseReportUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ExpenseReportUiState.Success -> {
                val report = state as ExpenseReportUiState.Success
                val dailyChartData = report.dailyTotals.map { (day, total) -> BarChartData(day, total) }
                val categoryChartData = report.categoryTotals.map { (cat, total) -> BarChartData(cat, total) }
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    DailyTotalsSection(dailyChartData)

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    CategoryTotalSection(categoryChartData)
                    Spacer(Modifier.height(32.dp))
                    Button(
                        onClick = {
                            isLoading = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isLoading){
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        }else{
                            Text(stringResource(R.string.export_as_pdf))
                        }

                    }
                }
            }

            is ExpenseReportUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text((state as ExpenseReportUiState.Error).message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

