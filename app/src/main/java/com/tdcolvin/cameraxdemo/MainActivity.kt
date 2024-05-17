package com.tdcolvin.cameraxdemo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.tdcolvin.cameraxdemo.ui.permission.WithPermission
import com.tdcolvin.cameraxdemo.ui.theme.CameraXDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraXDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WithPermission(
                        modifier = Modifier.padding(innerPadding),
                        permission = Manifest.permission.CAMERA
                    ) {
                        CameraAppScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun CameraAppScreen() {
    //Start here!
}

@Preview(widthDp = 400, heightDp = 700, showSystemUi = true)
@Composable
fun PreviewCameraApp() {
    CameraXDemoTheme {
        CameraAppScreen()
    }
}

fun Color.Companion.random(): Color =
    Color(
        red = Math.random().toFloat(),
        green = Math.random().toFloat(),
        blue = Math.random().toFloat()
    )

fun Context.shareImage(uri: Uri) {
    val contentUri = FileProvider.getUriForFile(this, "com.tdcolvin.cameraxdemo.fileprovider", uri.toFile())
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, contentUri)
        type = "image/jpeg"
    }
    startActivity(Intent.createChooser(shareIntent, null))
}