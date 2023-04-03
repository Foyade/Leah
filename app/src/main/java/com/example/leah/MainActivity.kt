package com.example.leah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var quoteView: TextView
    lateinit var icon: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById<Button>(R.id.quote_button)
        quoteView = findViewById<TextView>(R.id.QuoteView)
        icon = findViewById<ImageView>(R.id.iconImage)

        button.setOnClickListener { quoteView
            getQuote()
            getIconUrl()
        }

    }

    private fun getQuote() {
        val client = AsyncHttpClient()

        client["https://api.chucknorris.io/jokes/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Quote", "response successful")
                val quote = json.jsonObject?.getString("value")
                quoteView.setText(quote)
            }



            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Quote Error", errorResponse)
            }
        }]
    }

    private fun getIconUrl() {
        val client = AsyncHttpClient()
            client["https://randomfox.ca/floof/", object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                    Log.d("Icon", "response successful")
                    val iconUrl = json.jsonObject?.getString("image")
                    Glide.with(this@MainActivity)
                        .load(iconUrl)
                        .fitCenter()
                        .into(icon)
                }

                override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                    Log.d("Icon Error", errorResponse)
                }
            }]
    }

    }
