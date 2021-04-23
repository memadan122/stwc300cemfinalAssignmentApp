package com.madan.bookhotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var ivSplash: ImageView
    private lateinit var tvText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ivSplash =findViewById(R.id.ivSplash)
        tvText = findViewById(R.id.tvText)

        //IO and Main
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            startActivity(
                Intent(this@SplashActivity,
                LoginActivity::class.java)
            )
        }
        finish()
    }
}