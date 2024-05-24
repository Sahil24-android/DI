package com.event.eventmanagement.extras

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import com.airbnb.lottie.LottieAnimationView
import com.event.eventmanagement.R

class CustomProgressDialog(context: Context) : AlertDialog(context) {


        private lateinit var animationView: LottieAnimationView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_custom_progress)
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            animationView = findViewById(R.id.animation_view)
            animationView.setAnimation(R.raw.loading_animation)
            animationView.playAnimation()
            animationView.loop(true)
        }

}