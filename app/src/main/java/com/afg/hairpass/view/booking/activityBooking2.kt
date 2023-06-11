package com.afg.hairpass.view.booking

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afg.hairpass.R
import com.afg.hairpass.model.bookingModel
import com.afg.hairpass.session.sesionManager
import com.afg.hairpass.view.admin.BookingAdapter
import com.afg.hairpass.view.admin.BookingAdapter2
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_input_data.*
import kotlinx.android.synthetic.main.activity_riwayat.*
import kotlinx.android.synthetic.main.activity_riwayat.toolbar
import kotlinx.android.synthetic.main.activity_riwayat2.*
import kotlinx.android.synthetic.main.list_item_riwayat.*
import kotlinx.android.synthetic.main.list_item_riwayat2.*


class activityBooking2 : AppCompatActivity() {
    private lateinit var sesionManager: sesionManager
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat2)
        sesionManager = sesionManager(this)
        val email = sesionManager.getUserEmail().toString()
        db = FirebaseFirestore.getInstance()
        getData()
        setToolbar()

    }


    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapus(id: String) {
        db.collection("BOOKING")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                getData()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal menghapus data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getData() {
        db.collection("BOOKING")
            .get()
            .addOnSuccessListener { result ->
                val listBooking: ArrayList<bookingModel> = ArrayList()

                for (document in result) {
                    val booking = bookingModel(
                        document.id as String,
                        document.data["Jam"] as String,
                        document.data["Jumlah"] as String,
                        document.data["berat"] as String,
                        document.data["cabang"] as String,
                        document.data["catatan"] as String,
                        document.data["name"] as String,
                        document.data["tanggal"] as String
                    )
                    listBooking.add(booking)
                }

                val bookingAdapter2 = BookingAdapter2(listBooking)
                rvHistoryz.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = bookingAdapter2
                    setHasFixedSize(true)
                }

                // Set aksi klik pada imageDelete
                bookingAdapter2.setOnDeleteClickListener { id ->
                    val currentUserEmail = sesionManager.getUserEmail().toString()
                    if (currentUserEmail != "admin@gmail.com") {
                        imageDelete2.visibility = View.GONE
                    } else {
                        hapus(id)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: $exception")
            }
    }
}
