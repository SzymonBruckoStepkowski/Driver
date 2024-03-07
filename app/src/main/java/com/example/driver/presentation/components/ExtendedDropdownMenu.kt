package com.example.driver.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun ExtendedDropdownMenu(
    modifier: Modifier = Modifier,
    list: List<String>,
    value: String,
    onValueChange: (Int) -> Unit,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            value = value,
            onValueChange = {
                onValueChange(list.indexOf(it))
            },
            label = { Text(label) },
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            list.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(list.indexOf(label))
                        expanded = false
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}