package com.aug.smartkharch.ui.expenseentry.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.aug.smartkharch.R
import com.aug.smartkharch.ui.expenseentry.components.ExpenseEntryInputField

@Composable
fun AmountField(
    value: String,
    onValueChange: (String) -> Unit
) {
    ExpenseEntryInputField(
        value = value,
        onValueChange = { input ->
            val filtered = input.filter { it.isDigit() || it == '.' }
            onValueChange(filtered)
        },
        maxChars = 6,
        labelRes = R.string.amount,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        onClear = { onValueChange("") }
    )
}

@Composable
fun TitleField(
    value: String,
    onValueChange: (String) -> Unit
) {
    ExpenseEntryInputField(
        value = value,
        onValueChange = { onValueChange(it) },
        labelRes = R.string.title,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        onClear = { onValueChange("") }
    )
}

@Composable
fun NotesField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    ExpenseEntryInputField(
        value = value,
        onValueChange = { onValueChange(it) },
        labelRes = R.string.notes_optional,
        modifier = Modifier.fillMaxWidth(),
        singleLine = false,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        maxChars = 100
    )
}