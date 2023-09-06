package com.example.kotakprofilesettingpage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.kotakprofilesettingpage.ui.theme.redd
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ProfileScreen(messageBarState: MessageBarState, onBackPressed: () -> Unit) {



    val model:MainViewModel = hiltViewModel()
    ContentWithMessageBar(
        messageBarState = messageBarState,
    ) {
        ProfileContent(onBackPressed = onBackPressed,model)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(onBackPressed: () -> Unit,model:MainViewModel) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = redd)
                .padding(horizontal = 30.dp)
                .padding(top = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onBackPressed.invoke()
                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                CustomTextField(
                    title = "User Profile",
                    color = Color.White,
                    font = R.font.frutiger_bold
                )
            }
        }


    }) {
        val context = LocalContext.current





        var showDialog = remember { mutableStateOf(false) }


        val name = remember {
            mutableStateOf(model.getText())
        }
        val lastName = remember {
            mutableStateOf(model.getLastName())
        }

        val mobile = remember {
            mutableStateOf(model.getMobileNumber())
        }

        val password = remember {
            mutableStateOf(model.getPassword())
        }

        val email = remember {
            mutableStateOf(model.getEmail())
        }
        val imageUri  = remember {

            mutableStateOf<Uri?>(Uri.EMPTY)

        }

        val fireUri = remember {
            mutableStateOf(model.getUri()?.toUri())
        }

        val loadingState = remember {
            mutableStateOf(false)
        }



        LaunchedEffect(key1 = imageUri.value){
            Toast.makeText(context,"${imageUri.value}",Toast.LENGTH_SHORT).show()
            Log.d("suraj"," launch ${imageUri.value}")
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = redd)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (showDialog.value) {
            SelectImageDialog(state = showDialog){

                //*********todo this place**********
                imageUri.value = null
                imageUri.value = it
                if(it.path != null && it.path!!.isNotEmpty() && it.path != ""){
                    loadingState.value = true
                    uploadDocsInfirebase(it,context,loadingState){
                        Toast.makeText(context,"$it",Toast.LENGTH_SHORT).show()
                        Log.d("suraj","fire uri $it")
                        fireUri.value = null
                        fireUri.value = it
                        model.setUri(it.toString())
                        loadingState.value = true
                    }
                }


                val bitmap = decodeImageFromUri(context, imageUri.value!!)
                val imageString = bitmapToBase64(bitmap)
                Log.d("suraj","${imageString}")
                model.setUri(imageString)

            }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                VSpace(size = 20.dp)

                IconButton(modifier = Modifier
                    .size(100.dp)
                    .clip(
                        CircleShape
                    )
                    .border(width = 2.dp, color = Color.White, shape = CircleShape),onClick = { showDialog.value =true }) {

                    if(fireUri.value?.path?.isNotEmpty() == true){
//
//                       val bitmap = model.getUri()
//                        if (bitmap != null) {
//                            Image(bitmap = bitmap.asImageBitmap(), modifier = Modifier
//                                .size(90.dp)
//                                .clip(
//                                    CircleShape
//                                )
//                                .border(width = 2.dp, color = Color.White, shape = CircleShape), contentDescription =null )
//

                        Image(
                            painter = rememberAsyncImagePainter(model = fireUri.value,



                                onState = {
                                    when(it){
                                        AsyncImagePainter.State.Empty -> {

                                        }
                                        is AsyncImagePainter.State.Error -> {

                                        }
                                        is AsyncImagePainter.State.Loading -> {
                                           loadingState.value = true
                                        }
                                        is AsyncImagePainter.State.Success -> {
                                            loadingState.value = false
                                        }
                                    }
                                }


                                ),

                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Gray, CircleShape),
                            contentScale = ContentScale.Crop,
                            contentDescription = "" )

                        if(loadingState.value == true){
                            CircularProgressIndicator()
                        }
                    }
                    else{
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            modifier = Modifier
                                .size(90.dp)
                                .clip(
                                    CircleShape
                                )
                                .border(width = 2.dp, color = Color.White, shape = CircleShape),
                            contentDescription = "Profile"
                        )
                    }

                }

                VSpace(size = 10.dp)
                CustomTextField(
                    title = "Agent ID - XXXX",
                    font = R.font.roboto_bold,
                    color = Color.White
                )
                VSpace(size = 3.dp)
                CustomTextField(
                    title = "Last Login - 23 Jun, 2023 12:06AM",
                    color = Color.White.copy(alpha = 0.5f),
                    textSize = 12.dp,
                    font = R.font.roboto_regular
                )

            }
            Column {
                Box(contentAlignment = Alignment.BottomCenter) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(30.dp)
                            )
                            .height(580.dp)
                            .clip(shape = RoundedCornerShape(30.dp))
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                            )
                            .height(565.dp)
                            .clip(shape = RoundedCornerShape(30.dp))
                            .padding(horizontal = 38.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        //todo field



                        TextField(
                            value = name.value.trim(),
                            onValueChange = {
                                if(it.trim()==""){
                                    Toast.makeText(context,"name can't empty",Toast.LENGTH_SHORT).show()
                                }
                                model.setText(it.trim())
                                name.value = it.trim()


                            },
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxWidth(),
                            placeholder = {
                                Text(text = "First Name")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                disabledTextColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
                                cursorColor = Color.Black,
                                containerColor = Color.White
                            ),
                            singleLine = true,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                if(name.value.trim() == ""){
                                    Toast.makeText(context,"please provide a right name",Toast.LENGTH_SHORT).show()
                                }
                            })
                        )

                        
                        VSpace(size = 10.dp)
                        TextField(
                            value = lastName.value.trim() ,
                            onValueChange = {
                                model.setLastName(it.trim())
                                lastName.value = it.trim()

                            },
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxWidth(),
                            placeholder = {
                                Text(text = "Last Name")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                disabledTextColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
                                cursorColor = Color.Black,
                                containerColor = Color.White
                            ),
                            singleLine = true,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                if(lastName.value.trim() == ""){
                                    Toast.makeText(context,"please provide a right name",Toast.LENGTH_SHORT).show()
                                }
                            })
                        )
                        VSpace(size = 10.dp)

                        TextField(
                            value = mobile.value.trim().take(10) ,
                            onValueChange = {
                                model.setMobile(it.trim())
                                mobile.value = it.trim().take(10)

                            },
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxWidth(),
                            placeholder = {
                                Text(text = "Mobile Number")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                disabledTextColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
                                cursorColor = Color.Black, containerColor = Color.White
                            ),
                            singleLine = true,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions(onDone = {
                                if(mobile.value.trim() == "" ){
                                    Toast.makeText(context,"please provide a right number",Toast.LENGTH_SHORT).show()
                                }
                            })
                        )

                        VSpace(size = 10.dp)

                        TextField(
                            value = password.value.trim() ,
                            onValueChange = {
                                model.setPassword(it.trim())
                                password.value = it.trim()

                            },
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxWidth(),
                            placeholder = {
                                Text(text = "Password")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                disabledTextColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
                                cursorColor = Color.Black, containerColor = Color.White
                            ),
                            singleLine = true,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
                            keyboardActions = KeyboardActions(onDone = {
                                if(password.value.trim() == "" ){
                                    Toast.makeText(context,"please provide a right password",Toast.LENGTH_SHORT).show()
                                }
                            })
                        )

                        VSpace(size = 10.dp)

                        TextField(
                            value = email.value.trim() ,
                            onValueChange = {
                                model.setEmail(it.trim())
                                email.value = it.trim()

                            },
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxWidth(),
                            placeholder = {
                                Text(text = "Email")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                disabledTextColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
                                cursorColor = Color.Black, containerColor = Color.White
                            ),
                            singleLine = true,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
                            keyboardActions = KeyboardActions(onDone = {
                                if(email.value.trim() == "" ){
                                    Toast.makeText(context,"please provide a right email",Toast.LENGTH_SHORT).show()
                                }
                            })
                        )

                }
                }

            }

        }
    }
}

@Composable
fun VSpace(size: Dp) {

    Spacer(modifier = Modifier.size(size))
}


 fun uploadDocsInfirebase(documentUri: Uri?,context: Context,loadingState:MutableState<Boolean> ,onUploaded:(uri:Uri?) -> Unit = {}) {
     val firebaseStorage: FirebaseStorage =
         FirebaseStorage.getInstance("gs://kotak-profile-details.appspot.com/")
     val storageReference = firebaseStorage.reference

     val filepath: StorageReference = storageReference.child(
         "demoIsu/${
             "userName"
         }" + "/" + "profilepic/suraj"
     )

     filepath.putFile(documentUri!!).addOnSuccessListener { task ->

         if (task.task.isSuccessful) {
             // The upload is complete; now retrieve the download URL
             filepath.downloadUrl
                 .addOnSuccessListener { uri ->
                     // Successfully retrieved the download URL
                     val downloadUrl = uri.toString()
                     onUploaded(uri)
                     loadingState.value = false
                     Toast.makeText(context, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                 }
                 .addOnFailureListener { exception ->
                     // Handle the failure to retrieve the download URL
                     Toast.makeText(
                         context,
                         "Failed to retrieve download URL: ${exception.message}",
                         Toast.LENGTH_SHORT
                     ).show()
                 }
         } else {
             // Handle the upload failure
             loadingState.value = false
             Toast.makeText(
                 context,
                 "Upload failed",
                 Toast.LENGTH_SHORT
             ).show()
         }
     }
 }
fun Context.createImageFile():File{
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_"+ timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".png",
        externalCacheDir
    )
    return image
}
fun decodeImageFromUri(context: Context, uri: Uri): Bitmap? {
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun bitmapToBase64(bitmap: Bitmap?): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

