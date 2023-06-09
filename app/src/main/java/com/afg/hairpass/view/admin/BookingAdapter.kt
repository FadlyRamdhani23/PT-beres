package com.afg.hairpass.view.admin

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.afg.hairpass.R
import com.afg.hairpass.model.bookingModel
import com.afg.hairpass.utils.FunctionHelper
import kotlinx.android.synthetic.main.list_item_riwayat.view.*


class BookingAdapter (private val Bookings:List<bookingModel>): RecyclerView.Adapter<BookingHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHolder {
        return BookingHolder(
            View.inflate(parent.context,R.layout.list_item_riwayat, null)
        )
    }

    override fun onBindViewHolder(holder: BookingHolder, position: Int) {
        holder.bindBooking(Bookings[position])
    }

    override fun getItemCount(): Int = Bookings.size


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