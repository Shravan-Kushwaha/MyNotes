package com.example.mynotes.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mynotes.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.navigationBarColor = Color.parseColor("#FFFFFF")
        window.statusBarColor = Color.parseColor("#FFFFFF")
        setContentView(R.layout.activity_splash)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}