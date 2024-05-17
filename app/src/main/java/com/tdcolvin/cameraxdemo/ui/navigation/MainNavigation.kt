package com.tdcolvin.cameraxdemo.ui.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tdcolvin.cameraxdemo.ui.camera.TakePictureScreenAdvanced
import com.tdcolvin.cameraxdemo.ui.permission.PermissionRequiredScreen

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var permissionGranted by remember {
        mutableStateOf (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }

    if (!permissionGranted) {
        PermissionRequiredScreen(modifier = modifier, permission = Manifest.permission.CAMERA) { permissionGranted = true }
    }
    else {
        TakePictureScreenAdvanced(
            modifier = modifier
        )
    }
}