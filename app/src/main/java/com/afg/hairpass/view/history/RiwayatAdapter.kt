package com.afg.hairpass.view.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.afg.hairpass.R
import com.afg.hairpass.model.ModelDatabase
import com.afg.hairpass.utils.FunctionHelper.rupiahFormat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.list_item_riwayat.view.*


class RiwayatAdapter(
    var mContext: Context,
    modelInputList: MutableList<ModelDatabase>,
    adapterCallback: RiwayatAdapterCallback
) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var modelDatabase: MutableList<ModelDatabase>
    var mAdapterCallback: RiwayatAdapterCallback

    fun setDataAdapter(items: List<ModelDatabase>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_riwayat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: ModelDatabase = modelDatabase[position]
        holder.tvNama.setText(data.namaPengguna)
        holder.tvDate.setText(data.tanggal)
        holder.tvKategori.text = data.cabang
        holder.tvBerat.text = "Jumlah " + data.berat.toString() + " Orang"
        holder.tvjam.setText(data.alamat)



        if (data.berat < 3) {
            holder.tvSaldo.text = "Harga " + rupiahFormat(data.harga)
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red))
            holder.tvStatus.text = "Tidak Dapat Potongan Harga"
        } else {
            holder.tvSaldo.text = "Harga " + rupiahFormat(data.harga - 5000)
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
            holder.tvStatus.text = "Sudah di konfirmasi"
        }
    }

    override fun getItemCount(): Int {
        return modelDatabase.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView = itemView.tvNama
        var tvDate: TextView = itemView.tvDate
        var tvKategori: TextView = itemView.tvKategori
        var tvBerat: TextView = itemView.tvBerat
        var tvSaldo: TextView = itemView.tvSaldo
        var tvStatus: TextView = itemView.tvStatus
        var imageDelete: ImageView = itemView.imageDelete
        var tvjam: TextView = itemView.tvjam

        init {
            imageDelete.setOnClickListener { view: View? ->
                val modelLaundry: ModelDatabase = modelDatabase[adapterPosition]
                mAdapterCallback.onDelete(modelLaundry)
            }
        }
    }

    interface RiwayatAdapterCallback {
        fun onDelete(modelDatabase: ModelDatabase?)
    }

    init {
        modelDatabase = modelInputList
        mAdapterCallback = adapterCallback
    }

}