package com.afg.hairpass.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import com.afg.hairpass.view.admin.AdminActivity
import com.afg.hairpass.view.main.MainActivity
import com.afg.hairpass.session.sesionManager

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailEt
import kotlinx.android.synthetic.main.activity_login.passET

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sesionManager: sesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi objek FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Inisialisasi session manager
        sesionManager = sesionManager(this)
        val email = sesionManager.getUserEmail().toString()
        // Periksa jika pengguna sudah login sebelumnya
        if (sesionManager.isLoggedIn()) {
            if (email == "admin@gmail.com") {
                // Navigasi ke AdminActivity
                val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Navigasi ke MainActivity
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Mengatur aksi saat tombol signUpqz diklik
        signUpa.setOnClickListener { v: View? ->
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Mengatur aksi saat tombol signin diklik
        signIn.setOnClickListener { v: View? ->
            val email = emailEt.text.toString()
            val pass = passET.text.toString()

            // Memeriksa apakah email dan password tidak kosong
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login berhasil

                        // Cek apakah pengguna adalah admin
                        if (email == "admin@gmail.com") {
                            // Buat session login untuk admin
                            sesionManager.createLoginSession(email)

                            // Navigasi ke AdminActivity
                            val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Buat session login untuk pengguna
                            sesionManager.createLoginSession(email)

                            // Navigasi ke MainActivity
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        // Login gagal

                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}