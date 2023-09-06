package com.example.kotakprofilesettingpage

import android.net.Uri

data class ProfileDetails(
    val firstName : String? = "",
    val lastName : String? = "",
    val mobileNumber : String? = "",
    val password : String? ="",
    val email : String? = "",
    val uri : Uri? = Uri.EMPTY
)
