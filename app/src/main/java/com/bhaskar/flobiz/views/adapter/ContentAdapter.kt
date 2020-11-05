package com.bhaskar.flobiz.views.adapter


import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bhaskar.flobiz.R
import com.bhaskar.flobiz.model.ContentItem
import com.bhaskar.flobiz.model.ContentListResponse
import com.bhaskar.flobiz.prefs.LocalData
import org.koin.core.KoinComponent
import org.koin.core.inject


class ContentAdapter(private var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), KoinComponent {

    private var data: MutableList<ContentItem> = ArrayList()
    private val content = "content"
    private val ad = "ad"
    private val contentType = 0
    private val adType = 1
    private val prefs: LocalData by inject()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == contentType) {
            val listItem =
                LayoutInflater.from(parent.context).inflate(R.layout.content_cell, parent, false)
            ContentViewHolder(listItem)
        } else {
            val listItem =
                LayoutInflater.from(parent.context).inflate(R.layout.ad_cell, parent, false)
            AdViewHolder(listItem)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contentItem = data[position]
        when (holder) {
            is ContentViewHolder -> {
                holder.bindData(contentItem)
            }
            is AdViewHolder -> {
                holder.bindData(contentItem, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].type == content) {
            contentType
        } else {
            adType
        }
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var poster: ImageView = itemView.findViewById(R.id.poster) as ImageView
        private var posterName: TextView = itemView.findViewById(R.id.poster_name) as TextView

        fun bindData(item: ContentItem) {
            poster.setImageDrawable(getDrawableByName(item.posterImage))
            posterName.text = item.name
        }
    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var adImage: ImageView = itemView.findViewById(R.id.iv_ad) as ImageView
        private var adRemove: ImageView = itemView.findViewById(R.id.iv_remove) as ImageView
        private var adText: TextView = itemView.findViewById(R.id.ad_text) as TextView
        fun bindData(item: ContentItem, position: Int) {
            adImage.setImageDrawable(getDrawableByName(item.posterImage))
            adText.text = item.name
            adRemove.setOnClickListener {
                data.remove(item)
                notifyItemRemoved(position)
                prefs.setShouldShowAd(false)
            }
        }

    }

    fun addItems(content: ContentListResponse) {
        if (!prefs.getShouldShowAd() && content.page.contentItems.content[content.page.contentItems.content.size - 1].type == ad) {
            content.page.contentItems.content.removeAt(content.page.contentItems.content.size-1)
        }
        data.addAll(content.page.contentItems.content)
        notifyItemRangeChanged(data.size, content.page.contentItems.content.size)

    }

    /*fun clearItems() {
        data.clear()
        notifyDataSetChanged()
    }*/

    private fun getDrawableByName(name: String?): Drawable? {
        val resourceId: Int = context.resources
            .getIdentifier(name?.split(".")?.get(0), "drawable", context.packageName)
        return try {
            ContextCompat.getDrawable(context, resourceId)
        } catch (e: Resources.NotFoundException) {
            ContextCompat.getDrawable(context, R.drawable.placeholder_for_missing_posters)
        }

    }
}