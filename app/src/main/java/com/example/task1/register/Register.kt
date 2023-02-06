package com.example.task1.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.task1.R
import com.example.task1.constants.consts
import com.example.task1.databinding.ActivityRegisterBinding
import com.example.task1.register.registerModel.PostRegister
import com.example.task1.register.registerModel.RegisterModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Register : AppCompatActivity() {
    lateinit var name:String
    lateinit var password:String
    lateinit var mail:String

    lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        name = binding.name.text.toString()
        mail = binding.email.text.toString()
        password = binding.pass.text.toString()
        binding.registerButton.setOnClickListener {
            registerUser(name, mail, password)

        }
    }

    private fun registerUser(name: String, mail: String, password: String) {
        val retrofitBuilder= Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
        baseUrl(consts.BASE_URL).build()
        val registerUser=retrofitBuilder.create(RegisterInterface::class.java)
       val call= registerUser.userRegister(PostRegister(mail,name,password))
        call.enqueue(object :Callback<RegisterModel>{
            override fun onResponse(call: Call<RegisterModel>, response: Response<RegisterModel>) {
                Log.d("tag",response.body().toString())
            }

            override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                Log.e("tag",t.message.toString())
            }
        })


    }
}