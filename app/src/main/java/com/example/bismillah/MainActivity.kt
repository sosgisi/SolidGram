package com.example.bismillah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bismillah.fragments.HomeFragment
import com.example.bismillah.fragments.ProfileFragment
import com.example.bismillah.fragments.UploadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFrag = HomeFragment()
        val uploadFrag = UploadFragment()
        val profileFrag = ProfileFragment()

        makeCurrentFragment(homeFrag)
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.home_nav -> makeCurrentFragment(homeFrag)
                R.id.upload_nav -> makeCurrentFragment(uploadFrag)
                R.id.profile_nav -> makeCurrentFragment(profileFrag)
            }
            true
        }
    }
    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}