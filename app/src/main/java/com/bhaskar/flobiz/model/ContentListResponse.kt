package com.bhaskar.flobiz.model

import com.google.gson.annotations.SerializedName

data class ContentListResponse(
    val page: Page
)

data class Page(
    @SerializedName("page-num") val pageNum: String,
    @SerializedName("page-size") val pageSize: String,
    @SerializedName("content-items") val contentItems: ContentItems,
    @SerializedName("total-content-items") val totalContentItems: String,
    val title: String
)

data class ContentItems(
    val content: MutableList<ContentItem>
)

data class ContentItem(
    val name: String,
    @SerializedName("poster-image") val posterImage: String,
    val type: String
)

