package com.tdcolvin.cameraxdemo.ui.permission

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PermissionRequiredScreen(modifier: Modifier = Modifier, onPermissionGranted: () -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            onPermissionGranted()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Button(
            modifier = modifier.align(Alignment.Center),
            onClick = { launcher.launch(Manifest.permission.CAMERA) }
        ) {
            Text("Grant camera permission")
        }
    }
}