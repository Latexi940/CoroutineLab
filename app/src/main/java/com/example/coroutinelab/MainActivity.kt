package com.example.coroutinelab

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.Exception
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myUrl = URL("https://placekitten.com/g/400/600")
        lifecycleScope.launch(Dispatchers.Main) {
            if(isNetworkAvailable()) {
                try {
                    val res = getImg(myUrl)
                    showImg(res)
                }catch (e: Exception){
                    Log.d("MAIN", "Error while fetching the image: $e")
                }
            }
        }
    }

    private suspend fun getImg(url: URL): Bitmap = withContext(Dispatchers.IO) {
        val image = url.content as InputStream
        return@withContext BitmapFactory.decodeStream(image)
    }

    private fun showImg(i: Bitmap) {
        imageView.setImageBitmap(i)
    }

    private fun isNetworkAvailable(): Boolean = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).isDefaultNetworkActive
}