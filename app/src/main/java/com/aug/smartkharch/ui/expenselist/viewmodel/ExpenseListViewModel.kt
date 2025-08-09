package com.aug.smartkharch.ui.expenselist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aug.smartkharch.domain.repository.ExpenseRepository
import com.aug.smartkharch.ui.expenselist.state.ExpenseListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpenseListUiState>(ExpenseListUiState.Loading)
    val uiState: StateFlow<ExpenseListUiState> = _uiState

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }

    init {
        viewModelScope.launch {
            _selectedDate.flatMapLatest { date ->
                repository.getExpensesByDate(date)
            }.collect { expenses ->
                _uiState.value = if (expenses.isEmpty()) {
                    ExpenseListUiState.Empty
                } else {
                    ExpenseListUiState.Success(expenses.reversed())
                }
            }
        }
    }
}
