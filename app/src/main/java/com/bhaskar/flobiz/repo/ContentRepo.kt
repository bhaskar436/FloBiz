package com.bhaskar.flobiz.repo

import android.content.Context
import com.bhaskar.flobiz.model.ContentListResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type


class ContentRepo(private var appContext: Context) {

    fun getContentList(pageNo: Int): ContentListResponse? {
        return try {
            val ins: InputStream = appContext.assets.open(getAssetNameBasedOnPage(pageNo))
            val size: Int = ins.available()
            val buffer = ByteArray(size)
            ins.read(buffer)
            ins.close()
            val jsonString = String(buffer)
            val listType: Type = object : TypeToken<ContentListResponse>() {}.type
            Gson().fromJson(jsonString, listType)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getAssetNameBasedOnPage(page: Int): String {
        return if (page == 1) {
            "CONTENTLISTINGPAGE-PAGE1.json"
        } else if (page == 2) {
            "CONTENTLISTINGPAGE-PAGE2.json"
        } else if (page == 3) {
            "CONTENTLISTINGPAGE-PAGE3.json"
        } else {
            ""
        }
    }
}