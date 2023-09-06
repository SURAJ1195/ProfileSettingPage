package com.example.kotakprofilesettingpage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext

//@Composable
//fun SelectImageDialog(state : MutableState<Boolean>, onUriReceived : (uri: Uri) -> Unit = {}){
//
//    val context = LocalContext.current
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicturePreview(),
//    ){
//        if(it!= null){
//            val uri = saveBitmapToFile(it,context)
//            if(uri!=null){
//
//            }
//        }
//    }
//
//
//}



import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mycameraview.CameraActivity


@Composable
fun SelectImageDialog(state: MutableState<Boolean>, onImageSelected: (Uri) -> Unit) {

    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onImageSelected(it)
                state.value = false
            }
        }

    val outSideImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data != null && result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.extras?.getParcelable<Uri>("photoUri")
                uri?.let {
                    onImageSelected(uri)
                    state.value = false
                }

            }
        }


    val takePicture =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                val uri = saveBitmapToFile(bitmap, context)
                if (uri != null) {
                    onImageSelected(uri)
                }
                state.value = false
            }
        }

    Dialog(onDismissRequest = { state.value = false }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Upload Image", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.background)
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    launcher.launch("image/*")
                }
            ) {
                Text("Capture Photo")
            }

            Spacer(Modifier.height(16.dp))


            Button(
                onClick = {
                    outSideImageLauncher.launch(Intent(context, CameraActivity::class.java))
                }
            ) {
                Text("From Gallery")
            }
        }





        }
    }
