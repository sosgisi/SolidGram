package com.example.bismillah

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bismillah.databinding.ActivityUploadFirebaseBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage

class UploadFirebaseActivity : AppCompatActivity() {

    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storagePostPicRef: StorageReference? = null
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    lateinit var binding: ActivityUploadFirebaseBinding
    private var imageReference = Firebase.storage.reference
    private var imageInstance = Firebase.storage
    private var currentFile: Uri? = null
    var storageRef = FirebaseStorage.getInstance()
    //val databaseReference = FirebaseDatabase.getInstance().reference.child("Post")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storagePostPicRef = FirebaseStorage.getInstance().reference.child("Posts Pictures")

        binding.btnPost.setOnClickListener {
            uploadImage()
        }

        binding.btnBrowse.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                imageLauncher.launch(it)
            }
        }

        binding.backBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun uploadImage(){
        when{
            currentFile == null -> Toast.makeText(this, "Image not selected", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(binding.firebaseCaption.text.toString()) -> Toast.makeText(this, "Please input caption", Toast.LENGTH_LONG).show()
            else ->{
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Posting")
                progressDialog.setMessage("Please wait...")
                progressDialog.show()

                val fileRef = storagePostPicRef!!.child(System.currentTimeMillis().toString() + ".jpg")
                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(currentFile!!)

                uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>> {task ->
                    if(!task.isSuccessful){
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener(OnCompleteListener<Uri> { task->
                    if(task.isSuccessful){
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()

                        var name = ""
                        var email = ""
                        fbAuth = FirebaseAuth.getInstance()
                        val currentUser = fbAuth.currentUser
                        dbRef = FirebaseDatabase.getInstance().getReference("Users")
                        dbRef.child(currentUser!!.uid).get().addOnSuccessListener {
                            name = it.child("username").value.toString()
                            email = it.child("email").value.toString()
                            val ref = FirebaseDatabase.getInstance().reference.child("Posts")
                            val postId = ref.push().key

                            val postMap = HashMap<String, Any>()
                            postMap["postid"] = postId!!
                            postMap["description"] = binding.firebaseCaption.text.toString().toLowerCase()
                            postMap["publisher"] = currentUser!!.uid
                            postMap["postimage"] = myUrl
                            postMap["username"] = name
                            postMap["email"] = email

                            ref.child(postId).updateChildren(postMap)
                            Toast.makeText(this, "Posting Successfull", Toast.LENGTH_LONG).show()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                            progressDialog.dismiss()
                        }

                    }else{
                        progressDialog.dismiss()
                    }
                })
            }
        }
    }

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == RESULT_OK) {
            result?.data?.data?.let{
                currentFile = it
                binding.firebaseImage.setImageURI(it)
            }
        }else{
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        }
    }

    /*binding.btnPost.setOnClickListener {
        println("checkpoint")
        //uploadImageToStorage("1")
        storageRef.getReference("latihanUpload").child(System.currentTimeMillis().toString())
            .putFile(currentFile!!)
            .addOnSuccessListener { task ->
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener {
                        val caption = findViewById<EditText>(R.id.caption).text.toString()
                        val postId = databaseReference.push().key
                        val dataMap = mapOf(
                            "image" to it.toString(),
                            "caption" to caption)

                        databaseReference.child(postId!!).updateChildren(dataMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                                startActivity(
                                    Intent(
                                        this@UploadFirebaseActivity,
                                        MainActivity::class.java
                                    )
                                )
                                println("checkpoint")
                            }
                            .addOnFailureListener { error ->
                                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                                println("checkpoint")
                            }
                        println("checkpoint")
                    }
                    .addOnFailureListener{error ->
                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                        error.printStackTrace()
                    }
                println("checkpoint")
            }
            .addOnFailureListener{error ->
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        println("checkpoint")
    }*/

    /*private fun uploadImageToStorage(fileName: String){
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Post")
        val postId = databaseReference.push().key
        val dataMap = mapOf(
            "postpict" to it.toString(),
            "caption" to caption)
        try{
            currentFile?.let {
                imageReference.child("latihanUpload/$fileName").putFile(it).addOnSuccessListener {databaseReference.child(postId!!).updateChildren(dataMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(
                                this@UploadFirebaseActivity,
                                MainActivity::class.java
                            )
                        )
                    }
                    .addOnFailureListener { error ->
                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()}
                    Toast.makeText(this, "Successfuly Uploaded!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
            imageInstance.getReference("latihanUpload").child(System.currentTimeMillis().toString()).putFile(currentFile!!)
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }*/
}

/*
private fun uploadImageToStorage(fileName: String){
    try{
        currentFile?.let {
            imageReference.child("latihanUpload/$fileName").putFile(it).addOnSuccessListener {
                Toast.makeText(this, "Successfuly Uploaded!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }
        }
        imageInstance.getReference("latihanUpload").child(System.currentTimeMillis().toString()).putFile(currentFile!!)
    }catch (e: Exception){
        e.printStackTrace()
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
    }
}*/
