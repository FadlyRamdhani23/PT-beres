package com.afg.hairpass.view.booking

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afg.hairpass.R
import com.afg.hairpass.model.bookingModel
import com.afg.hairpass.view.admin.BookingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_riwayat.*

class activityBooking : AppCompatActivity(){

    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        db = FirebaseFirestore.getInstance()
        getData()
        setToolbar()
    }
    private fun setToolbar() {
        setSupportActionBar(toolbar)
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
        db.collection("BOOKING")
            .get()
            .addOnSuccessListener {
                var listBooking:ArrayList<bookingModel> = ArrayList()
                listBooking.clear()


                for (document in it){
                    listBooking.add((bookingModel(
                        document.id as String,
                        document.data.get("Jam")as String,
                        document.data.get("Jumlah")as String,
                        document.data.get("berat")as String,
                        document.data.get("cabang")as String,
                        document.data.get("catatan")as String,
                        document.data.get("name")as String,
                        document.data.get("tanggal")as String,

                    )))

                }

                var BookingAdapter = BookingAdapter(listBooking)
                rvHistory.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = BookingAdapter
                    rvHistory.setHasFixedSize(true)

                }
            }
            .addOnFailureListener{
                Log.d("TAG", "Error getting documents: ")
            }
    }
}
