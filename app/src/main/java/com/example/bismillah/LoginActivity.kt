package com.example.bismillah

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.bismillah.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebaseAuth:FirebaseAuth
//    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.moveSignup.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

//                        val pairs: Array<Pair<View, String>> = arrayOf(
//                            Pair(image, "logo_image"),
//                            Pair(logo, "logo_text")
//                        )
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, *pairs)
//                            startActivity(intent, options.toBundle())
//                        }

//                        Pair[] pairs = new Pair[2];
//                        pairs[0] = new Pair<View, String>(binding.image, "logo_image");
//                        pairs[1] = new Pair<View, String>(binding.logo, "logo_text");
//                        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                            ActivityOptions options = ActivityOptions . makeSceneTransitionAnimation (this, pairs);
//                            startActivity(intent, options.toBundle())
//                        }

                    }else{
                        Toast.makeText(this, "Incorrect email address or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}