package com.aug.smartkharch.ui.components

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.aug.smartkharch.R
import java.time.LocalDate
import java.util.Calendar

@Composable
fun CalenderDatePicker(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    minDateDaysAgo: Int = 7,
    showPicker: Boolean,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(showPicker) {
        if (showPicker) {
            val todayCal = Calendar.getInstance()
            val minDateCal = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, -minDateDaysAgo)
            }
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
                    onDismissRequest()
                },
                selectedDate.year,
                selectedDate.monthValue - 1,
                selectedDate.dayOfMonth
            ).apply {
                datePicker.minDate = minDateCal.timeInMillis
                datePicker.maxDate = todayCal.timeInMillis
                setOnCancelListener { onDismissRequest() }
                show()
            }
        }
    }
}
