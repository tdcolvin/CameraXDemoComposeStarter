package com.tdcolvin.cameraxdemo.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toFile

fun Uri.shareAsImage(context: Context) {
    val contentUri = FileProvider.getUriForFile(context, "com.tdcolvin.cameraxdemo.fileprovider", toFile())
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, contentUri)
        type = "image/jpeg"
    }
    context.startActivity(Intent.createChooser(shareIntent, null))
}