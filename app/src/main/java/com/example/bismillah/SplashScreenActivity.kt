package com.example.bismillah

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.bismillah.databinding.ActivityLoginBinding

class SplashScreenActivity : Activity() {

    companion object {
        private const val SPLASH_SCREEN: Long = 5000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)

        // Animations
        val topAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        // Hooks
        val image: ImageView = findViewById(R.id.imageView)
        val logo: TextView = findViewById(R.id.textView)
        val slogan: TextView = findViewById(R.id.textView2)

        image.startAnimation(topAnim)
        logo.startAnimation(bottomAnim)
        slogan.startAnimation(bottomAnim)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)

//            val pairs: Array<Pair<View, String>> = arrayOf(
//                Pair(image, "logo_image"),
//                Pair(logo, "logo_text")
//            )
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, *pairs)
//                startActivity(intent, options.toBundle())
//            }

            startActivity(intent)
            finish()
        }, SPLASH_SCREEN)
    }
}
