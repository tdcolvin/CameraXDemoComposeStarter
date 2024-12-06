package com.tdcolvin.cameraxdemo.ui.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.tdcolvin.cameraxdemo.ui.shareAsImage
import java.io.File

@Composable
fun TakePictureScreenAdvanced(
    modifier: Modifier = Modifier
) {
    var zoomLevel by remember { mutableFloatStateOf(0.5f) }
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) }
    val imageCaptureUseCase = remember { ImageCapture.Builder().build() }

    val localContext = LocalContext.current

    Box(modifier = modifier) {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            zoomLevel = zoomLevel,
            lensFacing = lensFacing,
            imageCaptureUseCase = imageCaptureUseCase
        )

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(onClick = { lensFacing = CameraSelector.LENS_FACING_FRONT }) {
                    Text("Front camera")
                }
                Button(onClick = { lensFacing = CameraSelector.LENS_FACING_BACK }) {
                    Text("Back camera")
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Zoom: ", modifier = Modifier.background(Color.White), color = Color.Black)
                Button(onClick = { zoomLevel = 0f }) {
                    Text("0.00")
                }
                Button(onClick = { zoomLevel = 0.5f }) {
                    Text("0.50")
                }
                Button(onClick = { zoomLevel = 0.75f }) {
                    Text("0.75")
                }
                Button(onClick = { zoomLevel = 1.0f }) {
                    Text("1.00")
                }
            }
            Button(onClick = {
                val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File(localContext.externalCacheDir, "image.jpg"))
                    .build()
                val callback = object: ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        outputFileResults.savedUri?.shareAsImage(localContext)
                    }

                    override fun onError(exception: ImageCaptureException) {
                    }
                }
                imageCaptureUseCase.takePicture(outputFileOptions, ContextCompat.getMainExecutor(localContext), callback)
            }) {
                Text("Take Picture")
            }
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    lensFacing: Int,
    zoomLevel: Float,
    imageCaptureUseCase: ImageCapture
) {
    /*
       TODO:

       1. Create a PreviewView
       -----------------------
       Because PreviewView isn't a Composable, we need to wrap it in an AndroidView.
       This is a Composable which displays an older-style Android Views widget.

       Hint:
         AndroidView(
            modifier = ...
            factory = { context ->
               <instantiate your PreviewView here>
            }
         )


       Now run the code. You should find it shows a blank screen.
       Why's that? Because you haven't linked the PreviewView up to a PreviewUseCase...


       2. Create a PreviewUseCase
       --------------------------
       This tells the PreviewView you created earlier what to display.

       Hint: remember {  } it!
       The class you need is androidx.camera.core.Preview.Builder. You don't need any options,
       just build() it.


       3. Draw onto the PreviewView
       ----------------------------
       Then, tell your PreviewUseCase to draw onto the surface of the PreviewView.
       Hint: set the use case's surface provider to be the PreviewView's surface provider:

       previewUseCase.setSurfaceProvider(...)

       You only need to do this once, when you are creating the PreviewView.


       Now run the code again. Another blank screen?
       This time, it's because you need to bind it all together using a CameraProvider
       instance.


       4. Get a CameraProvider instance.
       ---------------------------------
       The specific kind of CameraProvider we're going to get is a ProcessCameraProvider.
       We ask for it using:
       val providerFuture = ProcessCameraProvider.getInstance(localContext)
       providerFuture.addListener({ ... }, ContextCompat.getMainExecutor(localContext))

       When the listener fires, it means you can use providerFuture.get() to return
       the ProcessCameraProvider.

       How can you run all the code in this step just once when the CameraPreview
       composable appears in the composition?
       Hint: LaunchedEffect(...) {  }


       5. Bind it all together
       -----------------------

       Build a CameraSelector:
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(...)
                .build()

       and then bind it all together using the CameraProvider, passing in the CameraSelector
       you've just created:

            cameraProvider.bindToLifecycle(
                localContext as LifecycleOwner,
                cameraSelector,
                previewUseCase, imageCaptureUseCase
            )


      Run it! You should now have a working preview!!!

      See if you can make the zoom and camera selector buttons work.
     */
}