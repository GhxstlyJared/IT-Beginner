package com.example.diplom2022.models

import android.content.Context
import android.content.SharedPreferences

class SharedPref (context : Context){
    private var settingSharedPref: SharedPreferences =
        context.getSharedPreferences("settingsPreferences",Context.MODE_PRIVATE)

    fun setDarkThemeState(state : Boolean?){
        val editor : SharedPreferences.Editor = settingSharedPref.edit()
        editor.putBoolean("Dark Mode", state!!)
        editor.commit()
    }

    fun loadDarkThemeState() : Boolean?{
        return settingSharedPref.getBoolean("Dark Mode",false)
    }

    fun setFavoriteState(state : Boolean?){
        val editor : SharedPreferences.Editor = settingSharedPref.edit()
        editor.putBoolean("FavoriteState", state!!)
        editor.commit()
    }

    fun loadFavoriteState() : Boolean?{
        return settingSharedPref.getBoolean("FavoriteState",false)
    }
}
