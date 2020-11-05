package com.bhaskar.flobiz.prefs

import android.content.Context
import android.content.SharedPreferences

class LocalData(context: Context) : SharedPrefHelper {

    private var preferences: SharedPreferences? = null

    init {
        preferences = context.getSharedPreferences(PrefConstants.FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun setShouldShowAd(value: Boolean) {
        preferences?.edit()?.putBoolean(PrefConstants.SHOULD_SHOW_AD, value)?.apply()
    }

    override fun getShouldShowAd(): Boolean {
        return preferences?.getBoolean(PrefConstants.SHOULD_SHOW_AD, true)!!
    }
}