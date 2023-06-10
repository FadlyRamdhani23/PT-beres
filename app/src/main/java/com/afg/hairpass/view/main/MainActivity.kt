package com.afg.hairpass.view.main



import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.afg.hairpass.view.booking.activityBooking
import com.afg.hairpass.view.input.InputDataActivity
import com.afg.hairpass.view.cabang.cabangActivity
import com.afg.hairpass.view.login.LoginActivity
import com.afg.hairpass.session.sesionManager
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var sesionManager: sesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi session manager
        sesionManager = sesionManager(this)
        val email = sesionManager.getUserEmail().toString()
        // Periksa jika pengguna belum login
        if (!sesionManager.isLoggedIn()) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Logout pengguna
        imageProfile.setOnClickListener { v: View? ->
            // Logout pengguna
            sesionManager.logout()

            // Navigasi ke aktivitas login
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Ambil email pengguna dari session manager

        // Set teks pada tampilan dengan menggunakan email
        setText(email)
    }

    private fun setText(email: String?) {
        db = FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener { document ->
                    // Mengatur teks pada tampilan "name" dengan nilai dari dokumen
                    name.text = document.get("name").toString()

                    // Menghandle klik pada cvInput
                    cvInput.setOnClickListener { v: View? ->
                        val intent = Intent(this@MainActivity, InputDataActivity::class.java)
                        startActivity(intent)
                    }

                    // Menghandle klik pada cvInfo
                    cvInfo.setOnClickListener { v: View? ->
                        val intent = Intent(this@MainActivity, cabangActivity::class.java)
                        startActivity(intent)
                    }

                    // Menghandle klik pada cvHistory
                    cvHistory.setOnClickListener { v: View? ->
                        val intent = Intent(this@MainActivity, activityBooking::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    }
                }
        }
    }
}