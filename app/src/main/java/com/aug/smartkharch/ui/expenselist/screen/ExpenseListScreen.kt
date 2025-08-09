package com.aug.smartkharch.ui.expenselist.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.aug.smartkharch.R
import com.aug.smartkharch.ui.expenselist.components.CalenderDatePicker
import com.aug.smartkharch.ui.expenselist.components.EmptyExpensesView
import com.aug.smartkharch.ui.expenselist.state.ExpenseListUiState
import com.aug.smartkharch.ui.expenselist.viewmodel.ExpenseListViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel,
    onAddExpenseClick: () -> Unit,
    onReportClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    var selectedFilterCategory by remember { mutableStateOf<String?>(null) }

    val titleText = if (selectedDate == LocalDate.now()) {
        stringResource(R.string.today)
    } else {
        selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    }

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        CalenderDatePicker(
            selectedDate = selectedDate,
            onDateSelected = { date -> viewModel.onDateSelected(date) },
            minDateDaysAgo = 7,
            showPicker = showDatePicker,
            onDismissRequest = { showDatePicker = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titleText) },
                actions = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                    }
                    IconButton(onClick = { onReportClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_bar_chart),
                            contentDescription = stringResource(R.string.view_weekly_report)
                        )
                    }
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (viewModel.isDarkTheme.collectAsState().value) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddExpenseClick()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { padding ->
        when (state) {
            is ExpenseListUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ExpenseListUiState.Empty -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyExpensesView()
                }
            }

            is ExpenseListUiState.Success -> {
                ExpenseListContent(
                    expenses = (state as ExpenseListUiState.Success).expenses,
                    selectedCategory = selectedFilterCategory,
                    onCategorySelected = { selectedFilterCategory = it },
                    modifier = Modifier.padding(padding)
                )
            }

            is ExpenseListUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.something_went_wrong))
                }
            }
        }
    }
}
