package com.example.testapplication.storage

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPreference {
    private var mSharedPref: SharedPreferences? = null

    //Key Value In Database
    const val STUDENT_NAME = "STUDENT_NAME"
    const val STUDENT_AGE = "STUDENT_AGE"
    const val IS_PRESENT = "IS_PRESENT"

    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref =
            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    //Save String Value
    fun saveString(key: String?, value: String?) {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.putString(key, value)
        prefsEditor?.commit()
    }

    //Return String Value
    fun returnString(key: String?, defValue: String?): String? {
        return mSharedPref?.getString(key, defValue)
    }

    //Save Boolean Value
    fun saveBoolean(key: String?, value: Boolean) {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.putBoolean(key, value)
        prefsEditor?.commit()
    }

    //Return Boolean Value
    fun returnBoolean(key: String?, defValue: Boolean): Boolean? {
        return mSharedPref?.getBoolean(key, defValue)
    }

    //Save Int Value
    fun saveInt(key: String?, value: Int) {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.putInt(key, value)
        prefsEditor?.commit()
    }

    //Return Int Value
    fun returnInt(key: String?, defValue: Int): Int? {
        return mSharedPref?.getInt(key, defValue)
    }
}