package com.afg.hairpass.view.admin

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afg.hairpass.R
import com.afg.hairpass.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_riwayat.*
import kotlinx.android.synthetic.main.daftar_pelanggan.*


class activityUser : AppCompatActivity() {


    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftar_pelanggan)

        db = FirebaseFirestore.getInstance()
        getData()
        setToolbar()
    }
    private fun setToolbar() {
        setSupportActionBar(toolbarz)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun getData(){
        db.collection("USERS")
            .get()
            .addOnSuccessListener {
                var listUser:ArrayList<UserModel> = ArrayList()
                listUser.clear()


                for (document in it){
                   listUser.add((UserModel(
                       document.id as String,
                          document.data.get("email")as String,
                          document.data.get("name")as String,
                          document.data.get("phoneNumber")as String
                   )))

                }

                    var userAdapter = userAdapter(listUser)
                rcList.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = userAdapter
                }
            }
            .addOnFailureListener{
                Log.d("TAG", "Error getting documents: ")
            }
    }
}