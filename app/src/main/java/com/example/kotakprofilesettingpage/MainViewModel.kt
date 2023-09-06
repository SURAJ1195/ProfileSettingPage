package com.example.kotakprofilesettingpage

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private  val sharedPreferences: SharedPreferences
    ) : ViewModel() {
        val sharedPerf = sharedPreferences
        val sharedprefEdtior = sharedPreferences.edit()
        val imageUri  = mutableStateOf<Uri?>(Uri.EMPTY)




    fun getText(): String {
        return sharedPreferences.getString("text", "") ?: ""
    }

    fun setText(text: String) {
        sharedPreferences.edit().putString("text", text).apply()
    }

    //todo last name
    fun getLastName(): String {
        return sharedPreferences.getString("last_name", "") ?: ""
    }

    fun setLastName(text: String) {
        sharedPreferences.edit().putString("last_name", text).apply()
    }

    //mobile
    fun getMobileNumber(): String {
        return sharedPreferences.getString("Mobile", "") ?: ""
    }

    fun setMobile(text: String) {
        sharedPreferences.edit().putString("Mobile", text).apply()
    }
    //password

    fun getPassword(): String {
        return sharedPreferences.getString("password", "") ?: ""
    }
    fun setPassword(text: String) {
        sharedPreferences.edit().putString("password", text).apply()
    }

    //email
    fun getEmail(): String {
        return sharedPreferences.getString("email", "") ?: ""
    }
    fun setEmail(text: String) {
        sharedPreferences.edit().putString("email", text).apply()
    }

    //uri
    fun getUri():String? {

       return sharedPreferences.getString("uri","")
    }


    fun setUri(uri:String){
        sharedPreferences.edit().putString("uri",uri).apply()
    }

    fun profileDetails(user:MutableState<ProfileDetails>){

        val gson = Gson()
        val json = gson.toJson(user.value)
        sharedprefEdtior.putString("D",json).apply()
    }
}