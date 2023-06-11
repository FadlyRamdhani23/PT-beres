package com.afg.hairpass.view.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.afg.hairpass.R
import com.afg.hairpass.model.bookingModel
import kotlinx.android.synthetic.main.list_item_riwayat.view.*
import kotlinx.android.synthetic.main.list_item_riwayat2.view.*


class BookingAdapter2(private val bookings: List<bookingModel>) :
    RecyclerView.Adapter<BookingHolder2>() {

    private var onDeleteClickListener: ((String) -> Unit)? = null

    // Fungsi untuk mengatur aksi klik pada tombol hapus
    fun setOnDeleteClickListener(listener: (String) -> Unit) {
        onDeleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHolder2 {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_riwayat2, parent, false)

        return BookingHolder2(view)
    }

    override fun onBindViewHolder(holder: BookingHolder2, position: Int) {
        holder.bindBooking(bookings[position])

        // Mengatur aksi klik pada tombol hapus
        holder.itemView.imageDelete2.setOnClickListener {
            onDeleteClickListener?.invoke(bookings[position].id)
        }
    }

    override fun getItemCount(): Int = bookings.size
}

class BookingHolder2(view: View) : RecyclerView.ViewHolder(view) {

    var tvNama1 = view.tvNama1
    var tvDate1 = view.tvDate1
    var tvKategori1 = view.tvKategori1
    var tvBerat1 = view.tvBerat1
    var tvjam1 = view.tvjam1
    var tvSaldo1 = view.tvSaldo1

    fun bindBooking(booking: bookingModel){

        tvNama1.text = booking.name
        tvDate1.text = booking.tanggal
        tvKategori1.text = booking.cabang
        tvBerat1.text ="Jumlah" + booking.berat + "Orang"
        tvjam1.text = booking.jam
        tvSaldo1.text = "Harga " + booking.jumlah



    }


}