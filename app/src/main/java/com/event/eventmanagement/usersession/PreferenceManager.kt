package com.event.eventmanagement.usersession

import android.content.Context
import android.content.SharedPreferences
import com.event.eventmanagement.views.auth.datasource.DataUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferenceManager(context: Context) {


    companion object {
        private const val PREF_NAME = "UserSession"
        private const val IMG = "img"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val ID = "vendor_id"
        private const val TOKEN ="token"
        private const val USERDATA = "userData"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    fun setLogin(isLogin:Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLogin)
        editor.apply()
    }

    fun setVendorId(id:Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(ID, id)
        editor.apply()
    }
    fun setImageRes(img:String) {
        val editor = sharedPreferences.edit()
        editor.putString(IMG, img)
        editor.apply()
    }

    fun getImageUrl():String? {
        return sharedPreferences.getString(IMG,"")
    }

    fun getVendorId():Int{
        return sharedPreferences.getInt(ID,0)
    }



    fun setToken(token:String){
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN,token)
        editor.apply()
    }

    fun getToken():String? {
        return sharedPreferences.getString(TOKEN,"")
    }


    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun setUserData(user: DataUser) {
        val editor = sharedPreferences.edit()
        val jsonString = gson.toJson(user)
        editor.putString(USERDATA, jsonString)
        editor.apply()
    }

    fun getUserData(): DataUser? {
        val jsonString = sharedPreferences.getString(USERDATA, null) ?: return null
        return gson.fromJson(jsonString, object : TypeToken<DataUser>() {}.type)
    }



    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}