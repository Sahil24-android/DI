package com.event.eventmanagement

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieAnimationView
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.auth.LoginActivity
import com.event.eventmanagement.views.auth.RegistrationActivity
import com.google.android.material.imageview.ShapeableImageView

class SplashScreen : AppCompatActivity() {
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        preferenceManager = PreferenceManager(this)

        val lottieAnimationView = findViewById<ShapeableImageView>(R.id.lottieAnimatedView)
//
        Handler(Looper.getMainLooper()).postDelayed({
            val scaleXAnimator = ObjectAnimator.ofFloat(lottieAnimationView, "scaleX", 0.4f, 1f)
            val scaleYAnimator = ObjectAnimator.ofFloat(lottieAnimationView, "scaleY", 0.4f, 1f)

            // Set the duration for the animation (1500 milliseconds)
            scaleXAnimator.duration = 1000
            scaleYAnimator.duration = 1000

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
        val scaleXAnimator = ObjectAnimator.ofFloat(lottieAnimationView, "scaleX", originalScaleX, 0.4f)
        val scaleYAnimator = ObjectAnimator.ofFloat(lottieAnimationView, "scaleY", originalScaleY, 0.4f)

        // Set the duration for the animation (1500 milliseconds)
        scaleXAnimator.duration = 1000
        scaleYAnimator.duration = 1000

        // Start the animation
        scaleXAnimator.start()
        scaleYAnimator.start()
    }
}