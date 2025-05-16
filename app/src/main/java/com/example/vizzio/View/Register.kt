package com.example.vizzio.View;

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vizzio.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.regbtn.setOnClickListener {
            val email = binding.reguser.text.toString()
            val password = binding.regpass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Login::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.logintxt.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}
