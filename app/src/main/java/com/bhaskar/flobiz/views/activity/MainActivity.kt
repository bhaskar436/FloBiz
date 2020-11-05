package com.bhaskar.flobiz.views.activity


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhaskar.flobiz.R
import com.bhaskar.flobiz.view_models.ContentViewModel
import com.bhaskar.flobiz.views.adapter.ContentAdapter
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(),
    View.OnClickListener {

    private val viewModel: ContentViewModel by inject()
    private lateinit var adapter: ContentAdapter
    private lateinit var rvContents: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var backButton: ImageView
    private lateinit var pageTitle: TextView
    private var pastVisibleItems: Int = 0
    private var responseTotalCount: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var pageNo: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initViews()
        observeContentList()
        initAdapter()
        addOnScrollListener()
    }


    private fun initViews() {
        rvContents = findViewById(R.id.rv_contents)
        backButton = findViewById(R.id.back_button)
        pageTitle = findViewById(R.id.page_title)
        backButton.setOnClickListener(this)
    }


    private fun initAdapter() {
        adapter = ContentAdapter(this@MainActivity)
        linearLayoutManager = LinearLayoutManager(this)
        rvContents.layoutManager = linearLayoutManager
        rvContents.adapter = adapter
    }


    private fun addOnScrollListener() {
        rvContents.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.childCount
                    totalItemCount = linearLayoutManager.itemCount
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                    if (totalItemCount < responseTotalCount && visibleItemCount + pastVisibleItems >= totalItemCount) {
                        viewModel.getList(++pageNo)
                    }
                }
            }
        })
    }

    private fun observeContentList() {
        viewModel.getContentList().observe(this, {
            it?.let {
                adapter.addItems(it)
                if (pageNo == 1) {
                    pageTitle.text = it.page.title
                    responseTotalCount = it.page.totalContentItems.toInt()
                }
            }
        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_button -> {
                finish()
            }
        }
    }


}