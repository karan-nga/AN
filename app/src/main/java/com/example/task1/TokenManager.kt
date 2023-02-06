package com.example.task1

import android.content.Context
import com.example.task1.constants.consts.PREFS_TOKEN_FILE
import com.example.task1.constants.consts.USER_TOKEN

class TokenManager(context:Context) {private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(USER_TOKEN, token)
        editor.apply()

    }
    fun getToken():Boolean?{
        return prefs.getBoolean(USER_TOKEN,false)
    }
}
