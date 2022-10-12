package com.camg_apps.axfactor.common

import android.content.Context

class MyUtils {

    companion object{
        fun getUserUid(context: Context): String{
            val sharedPref = context.getSharedPreferences("PREFERENCE_FILE_KEY",Context.MODE_PRIVATE)
            return sharedPref.getString("uid", "")?: ""
        }
    }
}