package com.tdcolvin.cameraxdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.tdcolvin.cameraxdemo.ui.navigation.MainNavigation
import com.tdcolvin.cameraxdemo.ui.theme.CameraXDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraXDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                     MainNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}