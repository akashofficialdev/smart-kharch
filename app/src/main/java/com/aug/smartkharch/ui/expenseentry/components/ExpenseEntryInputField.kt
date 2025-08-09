package com.aug.smartkharch.ui.expenseentry.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun ExpenseEntryInputField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    maxChars: Int? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClear: (() -> Unit)? = null,
) {
    // show char counter if maxChars provided
    val charCounter: (@Composable () -> Unit)? = if (maxChars != null) {
        {
            Text(
                text = "${value.length} / $maxChars",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    } else null

    OutlinedTextField(
        value = value,
        onValueChange = {
            if (maxChars == null || it.length <= maxChars) {
                onValueChange(it)
            }
        },
        modifier = modifier
            .heightIn(min = 56.dp),
        label = { Text(stringResource(labelRes)) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        isError = isError,
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (trailingIcon != null) {
                trailingIcon()
            } else if (onClear != null && value.isNotEmpty()) {
                IconButton(onClick = { onClear() }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        },
        supportingText = {
            if (charCounter != null) charCounter()
        },
        visualTransformation = visualTransformation,
    )
}

