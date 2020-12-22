package com.apolis.todoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.apolis.todoapp.R
import com.apolis.todoapp.helpers.openActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            openActivity(this, MainActivity::class.java, null)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}