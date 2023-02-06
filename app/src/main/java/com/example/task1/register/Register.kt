package com.example.task1.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.task1.TokenManager
import com.example.task1.constants.consts
import com.example.task1.databinding.ActivityRegisterBinding
import com.example.task1.login.Login
import com.example.task1.register.registerModel.PostRegister
import com.example.task1.register.registerModel.RegisterModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Register : AppCompatActivity() {
    lateinit var username:String
    lateinit var password:String
    lateinit var mail:String
    lateinit var tokenManager: TokenManager

    lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
        }

        binding.registerButton.setOnClickListener {
            username = binding.name.text.toString()
            mail = binding.email.text.toString()
            password = binding.password.text.toString()
            binding.progress.isVisible=true
            binding.registerButton.isVisible=false
            registerUser(username, mail, password)

        }
    }

    private fun registerUser(name: String, mail: String, password: String) {
        tokenManager=TokenManager(this)
        val retrofitBuilder= Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
        baseUrl(consts.BASE_URL).build()
        val registerUser=retrofitBuilder.create(RegisterInterface::class.java)
       val call= registerUser.userRegister(PostRegister(mail,name,password))
        call.enqueue(object :Callback<RegisterModel>{
            override fun onResponse(call: Call<RegisterModel>, response: Response<RegisterModel>) {
                Log.d("tag",response.body().toString())
                if(response.code()==201){
                    binding.progress.isVisible=false
                    binding.registerButton.isVisible=true
                    tokenManager.saveToken(false)
                    Toast.makeText(this@Register,"Account created.Please verify your account",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Register,Login::class.java))
                    finish()
                }
                else if(response.code()==400){
                    Toast.makeText(this@Register,"User already exists",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterModel>, t: Throwable) {

                Log.e("tag",t.message.toString())
                binding.progress.isVisible=false
                binding.registerButton.isVisible=true
                Toast.makeText(this@Register,"Something happen wrong",Toast.LENGTH_SHORT).show()
            }
        })


    }
}