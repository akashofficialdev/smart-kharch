package com.aug.smartkharch.ui.expenseentry.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aug.smartkharch.R
import com.aug.smartkharch.ui.expenseentry.components.CategoryDropdown
import com.aug.smartkharch.ui.expenseentry.components.ReceiptImagePicker
import com.aug.smartkharch.ui.expenseentry.components.TotalSpentCard
import com.aug.smartkharch.ui.expenseentry.state.ExpenseEntryError
import com.aug.smartkharch.ui.expenseentry.state.ExpenseEntryUiState
import com.aug.smartkharch.ui.expenseentry.viewmodel.ExpenseEntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEntryScreen(
    onBackClick: () -> Unit,
    viewModel: ExpenseEntryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val totalSpentToday by viewModel.totalSpentToday.collectAsState()
    val formState by viewModel.formState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val errorMessage = when (val state = uiState) {
        is ExpenseEntryUiState.Error -> when (state.error) {
            ExpenseEntryError.EmptyTitle -> context.getString(R.string.error_title_empty)
            ExpenseEntryError.InvalidAmount -> context.getString(R.string.error_invalid_amount)
        }
        else -> ""
    }

    LaunchedEffect(Unit) {
        snapshotFlow { uiState }
            .collect { state ->
                if (state is ExpenseEntryUiState.Saved) {
                    Toast.makeText(context, R.string.expense_added, Toast.LENGTH_LONG).show()
                    onBackClick()
                }
            }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_expense)) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TotalSpentCard(totalSpentToday)

            TitleField(
                value = formState.title,
                onValueChange = viewModel::onTitleChange
            )

            AmountField(
                value = formState.amount,
                onValueChange = viewModel::onAmountChange
            )

            CategoryDropdown(
                selectedCategory = formState.category,
                onCategorySelected = viewModel::onCategoryChange
            )

            NotesField(
                value = formState.notes,
                onValueChange = viewModel::onNotesChange,
                keyboardController = keyboardController
            )

            ReceiptImagePicker(
                imageUri = formState.receiptImageUri,
                onImagePicked = viewModel::onReceiptPicked
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = viewModel::addExpense,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save_expense), modifier = Modifier.padding(10.dp))
            }

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}