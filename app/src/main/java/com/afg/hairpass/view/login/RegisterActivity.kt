package com.afg.hairpass.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.emailEt
import kotlinx.android.synthetic.main.activity_register.passET


class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        setLogin()
        registerUser()
    }

    private fun setLogin() {
        logina.setOnClickListener { v: View? ->
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registerUser() {

        signUpz.setOnClickListener { v: View? ->
            val name = nameEt.text.toString()
            val phoneNumber = PhonenumberEt.text.toString()
            val email = emailEt.text.toString()
            val pass = passET.text.toString()
            val confirmPass = confirmPassEt.text.toString()
            val user= hashMapOf(
                "name" to name,
                "phoneNumber" to phoneNumber,
                "email" to email,
            )
            val User=db.collection("USERS")
            val query = User.whereEqualTo("email", email).get()
                .addOnSuccessListener {
                    if (it.isEmpty) {
                        if (name.isNotEmpty() && phoneNumber.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                            if (pass == confirmPass) {
                                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        User.document(email).set(user)
                                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this, "Password Not Matched !!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Email Already Exists !!", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

}