package com.example.bismillah.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.bismillah.R
import com.example.bismillah.UploadFirebaseActivity

class UploadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload, container, false)
        val button : Button = view.findViewById(R.id.btn_to_upload_firebase)
        button.setOnClickListener{
            val uploadFirebase = UploadFirebaseActivity()
            startActivity(Intent(context, UploadFirebaseActivity::class.java))
        }

        return view
    }

}