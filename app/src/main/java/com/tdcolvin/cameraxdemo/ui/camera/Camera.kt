package com.tdcolvin.cameraxdemo.ui.camera

import android.content.ContentValues
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TakePictureScreen(modifier: Modifier = Modifier) {
    val lifecyleOwner = LocalLifecycleOwner.current
    val baseContext = LocalContext.current

    var cameraController by remember { mutableStateOf<LifecycleCameraController?>(null) }

    fun takePhoto() {
        val name = SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSSS", Locale.UK).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraXDemo")
        }

        cameraController?.takePicture(
            ImageCapture.OutputFileOptions.Builder(
                baseContext.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build(),
            ContextCompat.getMainExecutor(baseContext),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(baseContext, "Image captured", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(baseContext, "Image capture failed", Toast.LENGTH_SHORT).show()
                    Log.e("cameraxdemo", "Image capture failed", exception)
                }

            }
        )
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                PreviewView(context)
            },
            update = { previewView ->
                cameraController = LifecycleCameraController(previewView.context).apply {
                    bindToLifecycle(lifecyleOwner)
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                }
                previewView.controller = cameraController
            },
            modifier = Modifier.fillMaxSize()
        )

        Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = ::takePhoto) {
            Text("Capture")
        }
    }


}