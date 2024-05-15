package com.tdcolvin.cameraxdemo.ui.camera

import android.util.Log
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

@Composable
fun TakePictureScreenAdvanced(
    modifier: Modifier = Modifier
) {
    var zoom by remember { mutableFloatStateOf(0.5f) }

    Box(modifier = modifier) {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            zoom = zoom,
            lensFacing = CameraSelector.LENS_FACING_BACK
        )

        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(onClick = { zoom = 1.0f }) {
                Text("Zoom 1.0")
            }
            Button(onClick = { zoom = 0.75f }) {
                Text("Zoom 0.75")
            }
            Button(onClick = { zoom = 0.5f }) {
                Text("Zoom 0.5")
            }
            Button(onClick = { zoom = 0f }) {
                Text("Zoom 0.0")
            }
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    lensFacing: Int = CameraSelector.LENS_FACING_FRONT,
    zoom: Float = 0.5f
) {
    val baseContext = LocalContext.current

    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    var cameraControl by remember { mutableStateOf<CameraControl?>(null) }
    val preview = remember { Preview.Builder().build() }

    LaunchedEffect(zoom) {
        cameraControl?.setLinearZoom(zoom)
    }

    LaunchedEffect(null) {
        val providerFuture = ProcessCameraProvider.getInstance(baseContext)
        providerFuture.addListener({
            cameraProvider = providerFuture.get()
        }, ContextCompat.getMainExecutor(baseContext))
    }
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context -> PreviewView(context) },
        update = { previewView ->
            cameraProvider?.let { cameraProvider ->
                Log.v("cameraxdemo", "Rebuilding camera")
                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(lensFacing)
                    .build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val camera = cameraProvider.bindToLifecycle(baseContext as LifecycleOwner, cameraSelector, preview)
                cameraControl = camera.cameraControl
                cameraControl?.setLinearZoom(zoom)
            }
        }
    )
}