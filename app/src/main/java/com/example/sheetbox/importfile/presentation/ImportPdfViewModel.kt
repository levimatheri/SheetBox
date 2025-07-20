package com.example.sheetbox.importfile.presentation

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sheetbox.core.data.db.AppDatabase
import com.example.sheetbox.core.domain.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ImportPdfViewModel(application: Application) : AndroidViewModel(application) {
    private val _importResult = MutableStateFlow<Result<Score>?>(null)
    val importResult: StateFlow<Result<Score>?> = _importResult

    fun importPdf(uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Extract file name
                val fileName = getFileName(uri, contentResolver)
                val appContext = getApplication<Application>()
                val destFile = File(appContext.filesDir, fileName)

                // Copy PDF to app storage
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    FileOutputStream(destFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                // Insert into RoomDB (example: Score entity)
                val db = AppDatabase.getInstance(appContext)
                val score = Score(
                    id = java.util.UUID.randomUUID(),
                    title = fileName,
                    tags = emptyList(),
                    filePath = destFile.absolutePath,
                    pageCount = 1, // Set to 1 or extract from PDF if needed
                )

                db.scoreDao().insertScore(score)
                _importResult.value = Result.success(score)
            } catch (e: Exception) {
                _importResult.value = Result.failure(e)
            }
        }
    }

    private fun getFileName(uri: Uri, contentResolver: ContentResolver): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex >= 0) {
                return it.getString(nameIndex)
            }
        }
        throw IllegalArgumentException("Could not determine file name for uri: $uri")
    }
}