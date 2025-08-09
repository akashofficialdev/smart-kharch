package com.aug.smartkharch.ui.expenseentry.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aug.smartkharch.data.local.entity.Expense
import com.aug.smartkharch.domain.repository.ExpenseRepository
import com.aug.smartkharch.ui.expenseentry.state.ExpenseEntryError
import com.aug.smartkharch.ui.expenseentry.state.ExpenseEntryFormState
import com.aug.smartkharch.ui.expenseentry.state.ExpenseEntryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExpenseEntryViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpenseEntryUiState>(ExpenseEntryUiState.Idle)
    val uiState: StateFlow<ExpenseEntryUiState> = _uiState

    private val _formState = MutableStateFlow(ExpenseEntryFormState())
    val formState: StateFlow<ExpenseEntryFormState> = _formState.asStateFlow()


    val totalSpentToday = repository.getTotalSpentOnDate(LocalDate.now())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)


    fun onTitleChange(title: String) {
        _formState.update { it.copy(title = title) }
    }

    fun onAmountChange(amount: String) {
        val filtered = amount.filter { it.isDigit() || it == '.' }
        _formState.update { it.copy(amount = filtered) }
    }

    fun onCategoryChange(category: String) {
        _formState.update { it.copy(category = category) }
    }

    fun onNotesChange(notes: String) {
        _formState.update { it.copy(notes = notes) }
    }

    fun onReceiptPicked(uri: Uri?) {
        _formState.update { it.copy(receiptImageUri = uri) }
    }



    fun addExpense() {
        val current = _formState.value
        if (current.title.isBlank()) {
            _uiState.value = ExpenseEntryUiState.Error(ExpenseEntryError.EmptyTitle)
            return
        }
        if (current.amount.isEmpty() || current.amount.toDouble() <= 0) {
            _uiState.value = ExpenseEntryUiState.Error(ExpenseEntryError.InvalidAmount)
            return
        }

        _uiState.value = ExpenseEntryUiState.Saving

        viewModelScope.launch {
            repository.addExpense(
                Expense(
                    title = current.title,
                    amount = current.amount.toDouble(),
                    category = current.category,
                    notes = current.notes,
                    date = System.currentTimeMillis()
                )
            )
            _uiState.value = ExpenseEntryUiState.Saved
        }
    }
}
