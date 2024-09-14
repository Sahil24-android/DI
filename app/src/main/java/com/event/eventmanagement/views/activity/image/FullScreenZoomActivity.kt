package com.event.eventmanagement.views.activity.image

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.event.eventmanagement.R
import com.event.eventmanagement.apis.RetrofitClient
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso

class FullScreenZoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_screen_zoom)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val photoView: PhotoView = findViewById(R.id.photoView)
        val closeButton: ImageButton = findViewById(R.id.closeButton)

        val imageUrl = intent.getStringExtra("imageUrl")
        Picasso.get().load(
            RetrofitClient.BASE_URL.replace(
                "api/",""
            )+"images/gallery/"+ imageUrl
        ).into(photoView)

        closeButton.setOnClickListener {
            finish()
        }
    }
}