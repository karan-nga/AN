package com.example.task1.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.task1.constants.consts
import com.example.task1.databinding.ActivityLoginBinding
import com.example.task1.login.loginModel.LoginInterface
import com.example.task1.login.loginModel.LoginModel
import com.example.task1.login.loginModel.PostLoginData
import com.example.task1.login.loginModel.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {
    lateinit var mail:String
    lateinit var password:String
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mail=binding.email.text.toString()
        password=binding.pass.text.toString()
        binding.registerButton.setOnClickListener {
            retroBuilder(mail, password)
        }
    }

    private fun retroBuilder(mail: String, password: String) {
        val retrofitBuilder=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
                baseUrl(consts.BASE_URL).build()
        val loginUser=retrofitBuilder.create(LoginInterface::class.java)
       val callLogin= loginUser.userSignin(PostLoginData(mail ,password))
       callLogin.enqueue(object :Callback<LoginModel>{
           override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
               Log.d("tag",response.body().toString())
           }

           override fun onFailure(call: Call<LoginModel>, t: Throwable) {
               Log.e("tag",t.message.toString())
           }
       })

    }
}