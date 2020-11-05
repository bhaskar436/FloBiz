package com.bhaskar.flobiz.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhaskar.flobiz.model.ContentListResponse
import com.bhaskar.flobiz.repo.ContentRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class ContentViewModel() : ViewModel(), KoinComponent {
    private var listResponse: MutableLiveData<ContentListResponse> = MutableLiveData()
    private val repo: ContentRepo by inject()

    init {
        getList(pageNo = 1)
    }

    fun getList(pageNo: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repo.getContentList(pageNo)
                listResponse.postValue(response)
            }
        }
    }

    fun getContentList(): LiveData<ContentListResponse> {
        return listResponse
    }
}