package com.afg.hairpass.view.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.afg.hairpass.R
import com.afg.hairpass.model.bookingModel
import kotlinx.android.synthetic.main.list_item_riwayat.view.*


class BookingAdapter(private val bookings: List<bookingModel>) :
    RecyclerView.Adapter<BookingHolder>() {

    private var onDeleteClickListener: ((String) -> Unit)? = null

    // Fungsi untuk mengatur aksi klik pada tombol hapus
    fun setOnDeleteClickListener(listener: (String) -> Unit) {
        onDeleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_riwayat, parent, false)
        return BookingHolder(view)
    }

    override fun onBindViewHolder(holder: BookingHolder, position: Int) {
        holder.bindBooking(bookings[position])

        // Mengatur aksi klik pada tombol hapus
        holder.itemView.imageDelete.setOnClickListener {
            onDeleteClickListener?.invoke(bookings[position].id)
        }
    }

    override fun getItemCount(): Int = bookings.size
}

class BookingHolder(view: View) : RecyclerView.ViewHolder(view) {

    var tvNama = view.tvNama
    var tvDate = view.tvDate
    var tvKategori = view.tvKategori
    var tvBerat = view.tvBerat
    var tvjam = view.tvjam
    var tvSaldo = view.tvSaldo

    fun bindBooking(booking: bookingModel){
        tvNama.text = booking.name
        tvDate.text = booking.tanggal
        tvKategori.text = booking.cabang
        tvBerat.text ="Jumlah" + booking.berat + "Orang"
        tvjam.text = booking.jam
        tvSaldo.text = "Harga " + booking.jumlah
    }
}