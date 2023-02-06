package com.example.task1.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.task1.MainActivity
import com.example.task1.TokenManager
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
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerButton.setOnClickListener {
            mail=binding.email.text.toString()
            password=binding.password.text.toString()
            binding.progress.isVisible=true
            binding.registerButton.isVisible=false
            retroBuilder(mail,password)
        }
    }

    private fun retroBuilder(mail: String, password: String) {
        tokenManager=TokenManager(this)
        val retrofitBuilder=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
                baseUrl(consts.BASE_URL).build()
        val loginUser=retrofitBuilder.create(LoginInterface::class.java)
       val callLogin= loginUser.userSignin(PostLoginData(mail ,password))
       callLogin.enqueue(object :Callback<LoginModel>{
           override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
               Log.d("tag",response.body().toString())
               Log.d("tag",response.code().toString())
                if(response.code()==200){
                    if(!response.body()?.user?.isVerfied!!){
                        binding.progress.isVisible=false
                        binding.registerButton.isVisible=true
                        Toast.makeText(this@Login,"First please verify your email",Toast.LENGTH_SHORT).show()
                    }
                    else if(response.body()?.user?.isVerfied!!){
                        binding.progress.isVisible=false
                        binding.registerButton.isVisible=true
                        tokenManager.saveToken(true)
                        if(tokenManager.getToken()==true){
                            startActivity(Intent(this@Login,MainActivity::class.java))
                            Toast.makeText(this@Login,"Login success",Toast.LENGTH_SHORT).show()
                        }
                    }


                }
              else if(response.code()==400){
                   Toast.makeText(this@Login,"User not exists or Invalid credentials",Toast.LENGTH_SHORT).show()
               }
           }

           override fun onFailure(call: Call<LoginModel>, t: Throwable) {
               binding.progress.isVisible=false
               binding.registerButton.isVisible=true
               Log.e("tag",t.message.toString())
               Toast.makeText(this@Login,"Something wrong happen",Toast.LENGTH_SHORT).show()
           }
       })
            binding.email.text.clear()
        binding.password.text.clear()
    }
}