package com.bhaskar.flobiz.prefs

interface SharedPrefHelper {
    fun setShouldShowAd(value: Boolean)
    fun getShouldShowAd(): Boolean
}