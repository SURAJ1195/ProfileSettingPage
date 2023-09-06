package com.example.kotakprofilesettingpage

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun getBitmapFromUri(contentResolver: ContentResolver, uri: Uri): Bitmap? {
    var inputStream: InputStream? = null
    try {
        inputStream = contentResolver.openInputStream(uri)
        if (inputStream != null) {
            return BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}

fun saveBitmapToFile(bitmap: Bitmap, context: Context): Uri? {
    val imagesFolder = File(context.cacheDir, "images")
    if (!imagesFolder.exists()) {
        imagesFolder.mkdirs()
    }
    val imageFile = File(imagesFolder, "image.jpg")

    val fos = FileOutputStream(imageFile)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
    fos.flush()
    fos.close()

    return Uri.fromFile(imageFile)
}