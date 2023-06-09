package com.afg.hairpass.view.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import com.afg.hairpass.view.history.RiwayatActivity
import com.afg.hairpass.view.history.activityBooking
import com.afg.hairpass.view.input.InputDataActivity
import com.afg.hairpass.view.jenis.cabangActivity
import com.afg.hairpass.view.login.LoginActivity
import com.afg.hairpass.view.main.MainActivity
import com.afg.hairpass.view.sesion.sesionManager
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity(){

    private lateinit var sesionManager: sesionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
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
            val intent = Intent(this@AdminActivity, activityBooking::class.java)

            startActivity(intent)
        }
        home.setOnClickListener { v: View? ->
            val intent = Intent(this@AdminActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
