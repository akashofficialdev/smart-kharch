package com.aug.smartkharch.ui.expenseentry

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aug.smartkharch.R
import com.aug.smartkharch.ui.components.CategoryDropdown
import com.aug.smartkharch.ui.components.ExpenseEntryInputField
import com.aug.smartkharch.ui.components.ReceiptImagePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEntryScreen(
    onBackClick : () -> Unit,
    viewModel: ExpenseEntryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val totalSpentToday by viewModel.totalSpentToday.collectAsState()

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    var notes by remember { mutableStateOf("") }
    var receiptImageUri by remember { mutableStateOf<Uri?>(null) }

    val errorMessage = when (val state = uiState) {
        is ExpenseEntryUiState.Error -> when(state.error) {
            ExpenseEntryError.EmptyTitle -> stringResource(R.string.error_title_empty)
            ExpenseEntryError.InvalidAmount -> stringResource(R.string.error_invalid_amount)
        }
        else -> ""
    }

    LaunchedEffect(uiState) {
        if (uiState is ExpenseEntryUiState.Saved) {
            Toast.makeText(context, context.getString(R.string.expense_added), Toast.LENGTH_LONG).show()
            onBackClick()
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = stringResource(
                        R.string.total_spent_today,
                        "%.2f".format(totalSpentToday)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }


            ExpenseEntryInputField(
                value = title,
                onValueChange = { title = it },
                labelRes = R.string.title,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                singleLine = true,
                onClear = { title = "" }
            )

            ExpenseEntryInputField(
                value = amount,
                onValueChange = { input ->
                    val filtered = input.filter { it.isDigit() || it == '.' }
                    amount = filtered
                },
                maxChars = 6,
                labelRes = R.string.amount,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                onClear = { amount = "" }
            )

            CategoryDropdown(selectedCategory = category, onCategorySelected = { category = it })

            ExpenseEntryInputField(
                value = notes,
                onValueChange = { notes = it },
                labelRes = R.string.notes_optional,
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxChars = 100
            )

            ReceiptImagePicker(
                imageUri = receiptImageUri,
                onImagePicked = { receiptImageUri = it }
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    viewModel.addExpense(title, amount.toDoubleOrNull() ?: 0.0, category, notes)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save_expense), modifier = Modifier.padding(10.dp))
            }

            when (uiState) {
                is ExpenseEntryUiState.Error -> {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }
        }
    }
}
