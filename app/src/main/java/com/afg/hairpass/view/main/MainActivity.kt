package com.afg.hairpass.view.main



import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.afg.hairpass.view.history.RiwayatActivity
import com.afg.hairpass.view.history.activityBooking
import com.afg.hairpass.view.input.InputDataActivity
import com.afg.hairpass.view.jenis.cabangActivity
import com.afg.hairpass.view.login.LoginActivity
import com.afg.hairpass.view.sesion.sesionManager
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var sesionManager: sesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sesionManager = sesionManager(this)
        if(!sesionManager.isLoggedIn()) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        imageProfile.setOnClickListener { v: View? ->
            sesionManager.logout()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        val email = sesionManager.getUserEmail().toString()
        setText(email)
    }


    private fun setText(email:String?){
        db= FirebaseFirestore.getInstance()
        if(email!=null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                    name.text = it.get("name").toString()
                    cvInput.setOnClickListener { v: View? ->
                        val intent = Intent(this@MainActivity, InputDataActivity::class.java)
                        startActivity(intent)
                    }

                    cvInfo.setOnClickListener { v: View? ->
                        val intent = Intent(this@MainActivity, cabangActivity::class.java)
                        startActivity(intent)
                    }

                    cvHistory.setOnClickListener { v: View? ->
                        val intent = Intent(this@MainActivity, activityBooking::class.java)
                        intent.putExtra("email",email)
                        startActivity(intent)
                    }
                }
        }
    }
}