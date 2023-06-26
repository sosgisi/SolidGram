package com.example.bismillah

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detailed)

        val user = intent.getParcelableExtra<User>("user")
        if(user != null){
            val nameView : TextView = findViewById(R.id.detailedUsername)
            val imageView : ImageView = findViewById(R.id.detailedImage)
            val captionView : TextView = findViewById(R.id.detailedCaption)

            nameView.text = user.name
            imageView.setImageResource(user.image)
            captionView.text = user.caption
        }
    }
}