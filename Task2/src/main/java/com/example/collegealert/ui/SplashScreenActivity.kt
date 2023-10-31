package com.example.collegealert.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.collegealert.R
import com.example.collegealert.ui.main.view.MainActivity
import java.util.Timer
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var animationLogo: ImageView
    private lateinit var title: TextView
    private val animationDuration: Long = 4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initComponents()
        setupAnimations()
        navigation()
    }

    private fun initComponents() {
        animationLogo = findViewById(R.id.splash_animation)
        title = findViewById(R.id.splash_title_tv)
    }

    private fun setupAnimations() {
        title.animate().apply {
            alpha(1F)
            duration = 1000
            start()
        }
    }

    private fun navigation() = Timer().schedule(animationDuration) {
        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        finish()
    }
}
