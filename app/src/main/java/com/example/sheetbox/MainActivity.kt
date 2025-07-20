package com.example.sheetbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sheetbox.core.domain.Score
import com.example.sheetbox.ui.theme.SheetBoxTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sheetbox.importfile.presentation.ImportPdfViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SheetBoxTheme {
                val scores = remember { mutableStateListOf<Score>() } // TODO: Replace with real data source
                val importViewModel: ImportPdfViewModel = viewModel()
                val context = LocalContext.current
                val pdfPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.OpenDocument(),
                    onResult = { uri ->
                        if (uri != null) {
                            importViewModel.importPdf(uri, context.contentResolver)
                        }
                    }
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Your Scores") },
                        )
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // 'Your scores' content goes here
                        // TODO: Display user's scores here

                        Spacer(modifier = Modifier.weight(1f))

                        // FloatingActionButton for Import
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    pdfPickerLauncher.launch(arrayOf("application/pdf"))
                                },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Import PDF"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}