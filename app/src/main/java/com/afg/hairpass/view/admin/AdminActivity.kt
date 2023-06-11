package com.afg.hairpass.view.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import com.afg.hairpass.view.booking.activityBooking
import com.afg.hairpass.view.input.InputDataActivity
import com.afg.hairpass.view.login.LoginActivity
import com.afg.hairpass.view.main.MainActivity
import com.afg.hairpass.session.sesionManager
import com.afg.hairpass.view.booking.activityBooking2
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity(){

    private lateinit var sesionManager: sesionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        // Inisialisasi session manager
        sesionManager = sesionManager(this)
        val email = sesionManager.getUserEmail().toString()
        // Periksa jika pengguna belum login
        if (!sesionManager.isLoggedIn()) {
            val intent = Intent(this@AdminActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        out.setOnClickListener { v: View? ->
            sesionManager.logout()
            val intent = Intent(this@AdminActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setInitLayout()

    }
    private fun setInitLayout() {
        imgCukur.setOnClickListener { v: View? ->
            val intent = Intent(this@AdminActivity, InputDataActivity::class.java)
            startActivity(intent)
        }

        imgProfile.setOnClickListener { v: View? ->
            val intent = Intent(this@AdminActivity, activityUser::class.java)
            startActivity(intent)
        }

        imgHistory.setOnClickListener { v: View? ->
            val intent = Intent(this@AdminActivity, activityBooking2::class.java)

            startActivity(intent)
        }
        home.setOnClickListener { v: View? ->
            val intent = Intent(this@AdminActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
