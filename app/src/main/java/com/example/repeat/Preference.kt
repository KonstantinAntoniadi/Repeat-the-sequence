package com.example.repeat

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Preference(activity: Activity) {

    var prefs: SharedPreferences? = activity.getPreferences(Context.MODE_PRIVATE)

    fun updateMax(max: Int) {
        prefs!!.edit().putInt("max_level", max).apply()
    }

    @JvmName("getMaxLevel1")
    fun getMaxLevel(): Int {
        return prefs!!.getInt("max_level", 0)
    }
}