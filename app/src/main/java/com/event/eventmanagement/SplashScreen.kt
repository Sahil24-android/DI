package com.event.eventmanagement

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.airbnb.lottie.LottieAnimationView
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.auth.LoginActivity
import com.event.eventmanagement.views.auth.RegistrationActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class SplashScreen : AppCompatActivity() {
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        preferenceManager = PreferenceManager(this)

        val lottieAnimationView = findViewById<ShapeableImageView>(R.id.lottieAnimatedView)
        val textView: MaterialTextView = findViewById(R.id.textView)

        val spannable = SpannableString("Product By\nCLUEMATRIX TECHNOLOGIES PRIVATE LIMITED")

// Set "Hello" to red
        spannable.setSpan(ForegroundColorSpan(resources.getColor(R.color.disable)), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

// Set "World" to blue
        spannable.setSpan(ForegroundColorSpan(resources.getColor(R.color.button_background1)), 11, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannable
//
        Handler(Looper.getMainLooper()).postDelayed({
            val scaleXAnimator = ObjectAnimator.ofFloat(lottieAnimationView, "scaleX", 0.3f, 1.5f)
            val scaleYAnimator = ObjectAnimator.ofFloat(lottieAnimationView, "scaleY", 0.3f, 1.5f)

            // Set the duration for the animation (1500 milliseconds)
            scaleXAnimator.duration = 800
            scaleYAnimator.duration = 800

            scaleXAnimator.start()
            scaleYAnimator.start()
        },1100)


        Handler(Looper.getMainLooper()).postDelayed({
            if (preferenceManager.isLoggedIn()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        },1500)


        val originalScaleX = lottieAnimationView.scaleX
        val originalScaleY = lottieAnimationView.scaleY

        // Scale the animation from original size to 0
        val scaleXAnimator = ObjectAnimator.ofFloat(lottieAnimationView, "scaleX", originalScaleX, 0.3f)
        val scaleYAnimator = ObjectAnimator.ofFloat(lottieAnimationView, "scaleY", originalScaleY, 0.3f)

        // Set the duration for the animation (1500 milliseconds)
        scaleXAnimator.duration = 800
        scaleYAnimator.duration = 800

        // Start the animation
        scaleXAnimator.start()
        scaleYAnimator.start()
    }
}