package com.example.todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.Task

/**
 * A composable function that displays the Add/Edit screen.
 * @param modifier The modifier to apply to this layout.
 * @param task The task to edit, if null a new task will be created.
 * @param onSave The callback that is called when the task is saved.
 * @param onCancel The callback that is called when the task is canceled.
 */
@Composable
fun AddEditScreen(
    modifier: Modifier = Modifier,
    task: Task? = null,
    onSave: (Task) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    var name by remember { mutableStateOf(task?.name ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var isImportant by remember { mutableStateOf(task?.isImportant == true) }
    var validationError by remember { mutableStateOf(false) }

    fun validateInput(name: String, description: String): Boolean {
        return name.isNotBlank() && description.isNotBlank() && name.length >= 3 && description.length >= 3
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = if (task == null) "Add Task" else "Edit Task",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            OutlinedTextFieldWithClearAndError(
                value = name,
                onValueChange = { name = it },
                label = stringResource(R.string.name_label),
                errorMessage = stringResource(R.string.name_error),
                isError = validationError && name.isBlank(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextFieldWithClearAndError(
                value = description,
                onValueChange = { description = it },
                label = stringResource(R.string.description_label),
                errorMessage = stringResource(R.string.description_error),
                isError = validationError && description.isBlank(),
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            SwitchWithText(
                text = stringResource(R.string.important_label),
                checked = isImportant,
                modifier = Modifier.padding(end = 16.dp),
                onCheckedChange = { isImportant = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onCancel() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = stringResource(R.string.cancel))
                }

                Button(
                    onClick = {
                        validationError = !validateInput(name, description)
                        if (!validationError) {
                            onSave(
                                Task(
                                    name = name,
                                    description = description,
                                    isImportant = isImportant,
                                    isCompleted = task?.isCompleted ?: false,
                                    taskId = task?.taskId ?: System.currentTimeMillis()
                                )
                            )
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}

/**
 * A composable function that displays an [OutlinedTextField] with a clear button and an error message.
 * @param value The value of the text field.
 * @param onValueChange The callback that is called when the value changes.
 * @param label The label of the text field.
 * @param errorMessage The error message to display.
 * @param isError Whether the text field is in an error state.
 * @param modifier The modifier to apply to the text field.
 * @param singleLine Whether the text field should be single line.
 */
@Composable
fun OutlinedTextFieldWithClearAndError(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = singleLine,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onValueChange("")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(
                            R.string.clear_content_description,
                            label
                        )
                    )
                }
            }
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
        },
        modifier = modifier
    )
}

/**
 * A composable function that displays a [Switch] with a text label.
 * @param text The text to display next to the switch.
 * @param checked The checked state of the switch.
 * @param modifier The modifier to apply to the row.
 * @param onCheckedChange The callback that is called when the switch is toggled.
 */
@Composable
fun SwitchWithText(
    text: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text)
        Spacer(modifier = Modifier.width(8.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
