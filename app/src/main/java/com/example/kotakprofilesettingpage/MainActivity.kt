package com.example.kotakprofilesettingpage

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotakprofilesettingpage.ui.theme.KotakProfileSettingPageTheme
import com.stevdzasan.messagebar.rememberMessageBarState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionToRequest = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotakProfileSettingPageTheme {
                val MultiplePermissionResultLauncher = rememberLauncherForActivityResult(

                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = {
                        permissionToRequest.forEach {permission ->
                            if(it[permission]!= null){
                                if(it[permission]!!){
                                    Toast.makeText(this@MainActivity, permission + "is granted",Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(this@MainActivity,
                                        "please give the $permission",Toast.LENGTH_SHORT).show()

                                }
                            }

                        }
                    })
                LaunchedEffect(key1 = true){
                    MultiplePermissionResultLauncher.launch(
                        permissionToRequest
                    )
                }

                    val messageBarState = rememberMessageBarState()
                ProfileScreen(messageBarState = messageBarState ) {
                }
            }
        }
    }
}





