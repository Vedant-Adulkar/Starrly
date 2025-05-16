package com.example.vizzio.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vizzio.Model.UserData
import com.example.vizzio.R
import com.example.vizzio.databinding.ActivityLoginBinding
import com.example.vizzio.databinding.ActivityRegisterBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Users")

        binding.regbtn.setOnClickListener {


             val username = binding.reguser.text.toString()
             val userpassword = binding.regpass.text.toString()
             if(username.isNotEmpty() && userpassword.isNotEmpty()){
             signupuser(username,userpassword)
             }
             else{
             Toast.makeText(this@Register,"this fields are mandatory",Toast.LENGTH_SHORT).show()

             }

        }
        binding.logintxt.setOnClickListener {
            startActivity(Intent(this@Register,Login::class.java))
        }

    }


     private fun signupuser(username:String,userpassword:String){
         databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object :
         ValueEventListener {
             override fun onDataChange(datasnapshot: DataSnapshot) {
             if(!datasnapshot.exists()){
             val id = databaseReference.push().key//this kep on pushing a primary key which is 1,2,3
             val userData = UserData(id,username,userpassword)

             databaseReference.child(id!!).setValue(userData)

             Toast.makeText(this@Register, "Signed up", Toast.LENGTH_SHORT).show()
             startActivity(Intent(this@Register,Login::class.java))
             finish()
             }
             else{
             Toast.makeText(this@Register,"User already exist",Toast.LENGTH_SHORT).show()
             }

             }

             override fun onCancelled(error: DatabaseError) {
             Toast.makeText(this@Register, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
             }
             })
         }

}