package com.on.callapps.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {

    private var pref = context.getSharedPreferences(SHARE_PREF, MODE_PRIVATE)

    fun saveNumVolume(key: String, volume: Int) {
        pref.edit().putInt(key, volume).apply()
    }
    fun getNameVolume(key: String): Int {
        return pref.getInt(key, 0)
    }
    var saveContact: Int
        set(value) = pref.edit().putInt(ARGS_CONTACT, value).apply()
        get() = pref.getInt(ARGS_CONTACT, 1)

    var interAd: Int
        set(value) = pref.edit().putInt(ARGS_INTER_AD, value).apply()
        get() = pref.getInt(ARGS_INTER_AD, 0)


    companion object {
        const val SHARE_PREF = "pref"
        const val ARGS_CONTACT = "contact"
        const val ARGS_INTER_AD = "inter"
    }
}
