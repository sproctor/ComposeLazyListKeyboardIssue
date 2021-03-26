package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

val words = listOf(
    "red",
    "blue",
    "green",
    "purple",
    "yellow",
    "black",
    "amber",
    "clear",
    "leather",
    "mirror",
    "ink",
    "navy",
    "white",
    "gray",
    "grey",
    "emerald",
    "smoky",
    "tan",
    "magenta",
    "orange",
    "pink",
    "coral"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val wordsState = remember { mutableStateOf(words) }
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    EditableList(words = wordsState.value)
                }
            }
        }
    }
}

@Composable
fun EditableList(
    words: List<String>,
) {
    val editing = remember { mutableStateOf<Int?>(null)}
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(words) { index, word ->
            WordRow(
                modifier = Modifier.padding(16.dp),
                text = word,
                editing = editing.value == index,
                save = { editing.value = null },
                edit = {
                    editing.value = index
                }
            )
        }
    }
}

@Composable
fun WordRow(
    modifier: Modifier = Modifier,
    text: String, editing: Boolean,
    save: (String) -> Unit,
    edit: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (editing) {
            val focusRequester = FocusRequester()
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                value = text,
                onValueChange = {  },
                label = { Text(text = "Word") },
                singleLine = true
            )
            IconButton(onClick = {
                save(text)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
            LaunchedEffect(editing) {
                if (editing) {
                    focusRequester.requestFocus()
                }
            }
        } else {
            Text(
                modifier = Modifier.weight(1f),
                text = text
            )
            IconButton(onClick = {
                edit()
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        EditableList(words = words)
    }
}
