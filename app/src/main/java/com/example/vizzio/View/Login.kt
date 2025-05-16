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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Users");

        binding.logbtn.setOnClickListener {

            val username = binding.logname.text.toString()
            val userpassword = binding.logpass.text.toString()
            if(username.isNotEmpty() && userpassword.isNotEmpty()){
            loginuser(username,userpassword)

             }
             else{
             Toast.makeText(this@Login,"this fields are mandatory",Toast.LENGTH_SHORT).show()

             }

        }

        binding.registertxt2.setOnClickListener {

            startActivity(Intent(this@Login,Register::class.java))
            }


    }


     private fun loginuser(username:String,userpassword:String){
         databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(
         object : ValueEventListener {
             override fun onDataChange(datasnapshot: DataSnapshot) {
             if(datasnapshot.exists()){
             for(usersnapshot in datasnapshot.children){
             val userData = usersnapshot.getValue(UserData::class.java)
             if(userData!=null && userData.userpassword == userpassword){
             Toast.makeText(this@Login,"Login succesful",Toast.LENGTH_SHORT).show()
             startActivity(Intent(this@Login,fragmentswitcher::class.java))
             finish()
             return
             }
             }
             }
             else{
             Toast.makeText(this@Login,"Login failed",Toast.LENGTH_SHORT).show()
             }
             }

             override fun onCancelled(error: DatabaseError) {
             Toast.makeText(this@Login,"database error: $error",Toast.LENGTH_SHORT).show()
             }
             })
         }
}


