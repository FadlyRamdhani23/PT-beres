package com.afg.hairpass.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import com.afg.hairpass.view.admin.AdminActivity
import com.afg.hairpass.view.input.InputDataActivity
import com.afg.hairpass.view.main.MainActivity
import com.afg.hairpass.view.sesion.sesionManager

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailEt
import kotlinx.android.synthetic.main.activity_login.passET

class LoginActivity : AppCompatActivity(){

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sesionManager: sesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginUser()
        setLogin()
        firebaseAuth = FirebaseAuth.getInstance()

        sesionManager= sesionManager(this)
        if(sesionManager.isLoggedIn()){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun setLogin() {
        signUpqz.setOnClickListener { v: View? ->
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun loginUser() {
        signin.setOnClickListener { v: View? ->
            val email = emailEt.text.toString()
            val pass = passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        if(email == "admin@gmail.com"){
                            sesionManager.createLoginSession(email)
                            val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                            startActivity(intent)
                            finish()

                        }else {
                            sesionManager.createLoginSession(email)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }
                    } else {

                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
        }




}