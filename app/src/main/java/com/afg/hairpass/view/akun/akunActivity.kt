package com.afg.hairpass.view.akun

import android.content.Intent
import android.os.Bundle

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import com.afg.hairpass.session.sesionManager
import com.afg.hairpass.view.login.LoginActivity
import com.afg.hairpass.view.main.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_akun.*


class akunActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var sesionManager: sesionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun)
        outa.setOnClickListener { v: View? ->
            sesionManager.logout()
            val intent = Intent(this@akunActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        toolbaras.setOnClickListener { v: View? ->
            val intent = Intent(this@akunActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        db = FirebaseFirestore.getInstance()
        sesionManager = sesionManager(this)
        val email = sesionManager.getUserEmail().toString()
        if(!sesionManager.isLoggedIn()) {
            val intent = Intent(this@akunActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setText(email)

    }
    private fun setText(email:String?){
        db= FirebaseFirestore.getInstance()
        if(email!=null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                    lblName.setText(it.get("name").toString())
                    lblEmail.setText(it.get("email").toString())
                    lblPhone.setText(it.get("phoneNumber").toString())

                }
        }
    }
}