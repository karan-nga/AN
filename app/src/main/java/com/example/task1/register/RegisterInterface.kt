package com.example.task1.register

import com.example.task1.register.registerModel.PostRegister
import com.example.task1.register.registerModel.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInterface{
@POST("signup")
fun userRegister(@Body postRegister:PostRegister): Call<RegisterModel>
}