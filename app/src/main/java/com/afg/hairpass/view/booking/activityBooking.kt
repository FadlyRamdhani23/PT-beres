package com.afg.hairpass.view.booking

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afg.hairpass.R
import com.afg.hairpass.model.bookingModel
import com.afg.hairpass.view.admin.BookingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_riwayat.*

class activityBooking : AppCompatActivity() {

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

                val bookingAdapter = BookingAdapter(listBooking)
                rvHistory.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = bookingAdapter
                    setHasFixedSize(true)
                }
                // Set aksi klik pada imageDelete
                bookingAdapter.setOnDeleteClickListener { id ->
                    hapus(id)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: $exception")
            }
    }
}
