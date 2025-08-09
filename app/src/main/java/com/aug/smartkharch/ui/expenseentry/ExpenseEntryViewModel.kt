package com.aug.smartkharch.ui.expenseentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aug.smartkharch.data.local.entity.Expense
import com.aug.smartkharch.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExpenseEntryViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpenseEntryUiState>(ExpenseEntryUiState.Idle)
    val uiState: StateFlow<ExpenseEntryUiState> = _uiState

    val totalSpentToday = repository.getTotalSpentOnDate(LocalDate.now())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)



    fun addExpense(title: String, amount: Double, category: String, notes: String?) {
        if (title.isBlank()) {
            _uiState.value = ExpenseEntryUiState.Error(ExpenseEntryError.EmptyTitle)
            return
        }
        if (amount <= 0) {
            _uiState.value = ExpenseEntryUiState.Error(ExpenseEntryError.InvalidAmount)
            return
        }

        _uiState.value = ExpenseEntryUiState.Saving

        viewModelScope.launch {
            repository.addExpense(
                Expense(
                    title = title,
                    amount = amount,
                    category = category,
                    notes = notes,
                    date = System.currentTimeMillis()
                )
            )
            _uiState.value = ExpenseEntryUiState.Saved
        }
    }
}
