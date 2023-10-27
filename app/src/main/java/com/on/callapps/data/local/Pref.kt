package com.on.callapps.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {

    private var pref = context.getSharedPreferences("", MODE_PRIVATE)

    fun saveNumVolume(key: String,volume:Int) {
        pref.edit().putInt(key, volume).apply()
    }

    fun getNameVolume(key: String): Int? {
        return pref.getInt(key,0)
    }

    fun saveBool(){

    }


    companion object {
        const val SHARE_PREF = "pref"

//        const val KEY_ONE = "one"
//        const val KEY_TWO = "two"
//        const val KEY_THREE = "three"
//        const val KEY_FOUR = "four"
    }
}
