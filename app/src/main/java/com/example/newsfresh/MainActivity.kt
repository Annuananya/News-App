package com.example.newsfresh

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import org.json.JSONObject
import androidx.recyclerview.widget.RecyclerView.Adapter as Adapter1
import androidx.recyclerview.widget.RecyclerView.LayoutManager as LayoutManager1
import androidx.recyclerview.widget.RecyclerView.LayoutManager as RecyclerViewLayoutManager

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)
        fetchData()
        val adapter = NewsListAdapter( this)
        recyclerview.adapter = mAdapter
        if(this::mAdapter.isInitialized) {
        }else{

        }

    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=004c055b37274cc78e6a7a063663dc29"
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")



                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)

            },
            Response.ErrorListener {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)
    }



    override fun onItemClicked(item: News) {
       val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }


}