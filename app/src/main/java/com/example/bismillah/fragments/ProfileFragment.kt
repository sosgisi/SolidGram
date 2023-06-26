package com.example.bismillah.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bismillah.LoginActivity
import com.example.bismillah.R
import com.example.bismillah.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.values
import java.lang.reflect.Array.get

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        dbRef.child(currentUser!!.uid).get().addOnSuccessListener {
            var name = it.child("username").value
            var email = it.child("email").value
            binding.tvName.text = name.toString()
            binding.tvEmail.text = email.toString()
        }

        binding.btnSignout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}