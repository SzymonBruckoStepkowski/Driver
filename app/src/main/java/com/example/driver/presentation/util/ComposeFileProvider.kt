package com.example.driver.presentation.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.driver.R
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
) {
    companion object {
        private const val FILE_CHILD = "files"
        private const val FILE_PREFIX = "file_"
        private const val DEFAULT_FILE_SUFFIX = ".jpg"
        private const val PDF_FILE_SUFFIX = ".pdf"
        private const val AUTHORITY_PROVIDER = ".fileprovider"

        fun getFilePath(context: Context) = "${context.cacheDir}/${FILE_CHILD}/"

        fun getFileUri(context: Context, fileSuffix: String = DEFAULT_FILE_SUFFIX): Uri {
            val directory = File(context.cacheDir, FILE_CHILD)
            directory.mkdirs()
            val file = File.createTempFile(FILE_PREFIX, fileSuffix, directory)
            val authority = context.packageName + AUTHORITY_PROVIDER
            return getUriForFile(context, authority, file)
        }

        fun getPdfFileUriIfRequired(context: Context, sourceUri: Uri): Uri? {
            val type = context.contentResolver.getType(sourceUri)
            return when (type) {
                "application/pdf" -> getFileUri(context, PDF_FILE_SUFFIX)
                else -> null
            }
        }

        fun copyFile(context: Context, sourceUri: Uri, destinationUri: Uri) {
            var bis: BufferedInputStream? = null
            var bos: BufferedOutputStream? = null
            try {
                bis = BufferedInputStream(context.contentResolver.openInputStream(sourceUri))
                bos = BufferedOutputStream(FileOutputStream(getFilePath(context) + destinationUri.lastPathSegment))
                val buf = ByteArray(1024)
                bis.read(buf)
                do {
                    bos.write(buf)
                } while (bis.read(buf) != -1)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    bis?.close()
                    bos?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        fun clearCache(context: Context) {
            context.cacheDir.deleteRecursively()
        }
    }
}