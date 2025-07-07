package com.example.sheetbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sheetbox.core.domain.Score
import com.example.sheetbox.ui.theme.SheetBoxTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.lifecycle.lifecycleScope
import com.example.sheetbox.core.data.db.AppDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SheetBoxTheme {
                var menuExpanded by remember { mutableStateOf(false) }
                val scores = remember { mutableStateListOf<Score>() } // TODO: Replace with real data source

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Your Scores") },
                            actions = {
                                IconButton(onClick = { menuExpanded = true }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                                }
                                DropdownMenu(
                                    expanded = menuExpanded,
                                    onDismissRequest = { menuExpanded = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Import") },
                                        onClick = {
                                            menuExpanded = false
                                            // TODO: Trigger PDF import logic here
                                        }
                                    )
                                    // Add more menu items here if needed
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    val db = (application as SheetBoxApp).db
                                    db.scoreDao().insertScore(
                                        Score(
                                            title = "Test Score",
                                            composer = "Test Composer",
                                            filePath = "/test/path.pdf",
                                            pageCount = 1,
                                            lastOpened = null
                                        )
                                    )
                                }
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("Add Test Score")
                        }

                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(scores) { score ->
                                Text(text = score.toString(), style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}