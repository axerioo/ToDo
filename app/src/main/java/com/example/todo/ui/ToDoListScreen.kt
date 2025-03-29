// @file:Suppress("FunctionName")
package com.example.todolistapp.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.DataSource
import com.example.todo.data.Task
import com.example.todo.ui.theme.ToDoTheme

@Composable
fun ToDoListScreen(
    modifier: Modifier = Modifier,
    onEdit: (Task) -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) } // State for dialog visibility
    var taskToDelete: Task? = remember { null } // Track the task to delete

    Surface(
        modifier = modifier.padding(8.dp)
    ) {
        Column {
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                items(items = DataSource.getTasks()) { task ->
                    TaskItem(
                        task = task,
                        onEditClick = { onEdit(task) },
                        onDelete = {
                            showDeleteDialog = true
                            taskToDelete = task
                        },
                        onCheckedChange = { DataSource.updateTask(task.copy(isCompleted = it)) }
                    )
                }
            }
            if (showDeleteDialog && taskToDelete != null) {
                DeleteConfirmationDialog(
                    task = taskToDelete!!,
                    onConfirm = {
                        DataSource.deleteTask(taskToDelete!!)
                        taskToDelete = null
                        showDeleteDialog = false
                    },
                    onDismiss = {
                        taskToDelete = null
                        showDeleteDialog = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ToDoListScreenPreview() {
    ToDoTheme {
        ToDoListScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    task: Task,
    onEditClick: () -> Unit,
    onDelete: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = { expanded = !expanded },
                onLongClick = onDelete // Long press to delete
            ),
        color = if (task.isCompleted) MaterialTheme.colorScheme.inversePrimary
                else MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (task.isImportant) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color.Red, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

//                Box do oznaczenia ważności;
//                Box do oznaczenia ukończenia, jako nakładka kolorem szarym;

                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onCheckedChange(it) }
                )
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        Icons.TwoTone.Edit,
                        contentDescription = stringResource(R.string.edit_task)
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.expand),
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    task: Task,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.delete_task_title)) },
        text = { Text(stringResource(R.string.delete_msg, task.name)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.delete), color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Preview
@Composable
fun DeleteConfirmationDialogPreview() {
    ToDoTheme {
        DeleteConfirmationDialog(
            task = Task("Task 1", "Description of task 1"),
            onConfirm = {},
            onDismiss = {}
        )
    }
}
