package com.example.wersja_1_roslinki

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_strona_w.*


class strona_internetowa : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_strona_w)

        webViewSetup()
    }

    fun webViewSetup(){

        wb_webView.webViewClient = WebViewClient()
        wb_webView.apply {
            loadUrl("https://google.com/")
        }
    }

    override fun onBackPressed() {
        if(wb_webView.canGoBack()) wb_webView.goBack()
        else super.onBackPressed()
    }
}