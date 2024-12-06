package com.tdcolvin.cameraxdemo

import android.Manifest
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
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
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