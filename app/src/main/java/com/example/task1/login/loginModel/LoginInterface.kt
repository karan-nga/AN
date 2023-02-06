package com.example.task1.login.loginModel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginInterface {
   @POST("signin")
   fun userSignin(
       @Body loginPost:PostLoginData
   ): Call<LoginModel>
}