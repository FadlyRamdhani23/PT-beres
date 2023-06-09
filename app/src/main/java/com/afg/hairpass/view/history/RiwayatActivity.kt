package com.afg.hairpass.view.history

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afg.hairpass.R
import com.afg.hairpass.model.ModelDatabase
import com.afg.hairpass.view.history.RiwayatAdapter.RiwayatAdapterCallback
import com.afg.hairpass.viewmodel.RiwayatViewModel
import kotlinx.android.synthetic.main.activity_riwayat.*

import java.util.*

class RiwayatActivity : AppCompatActivity(), RiwayatAdapterCallback {

    private var modelDatabaseList: MutableList<ModelDatabase> = ArrayList()
    private lateinit var riwayatAdapter: RiwayatAdapter
    private lateinit var riwayatViewModel: RiwayatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)
        setToolbar()
        setInitLayout()
        setViewModel()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun setInitLayout() {
        tvNotFound.visibility = View.GONE
        riwayatAdapter = RiwayatAdapter(this, modelDatabaseList, this)
        rvHistory.setHasFixedSize(true)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = riwayatAdapter
    }

    private fun setViewModel() {
        riwayatViewModel = ViewModelProviders.of(this).get(RiwayatViewModel::class.java)

        riwayatViewModel.dataBank.observe(this) { modelDatabases: List<ModelDatabase> ->
            if (modelDatabases.isEmpty()) {
                tvNotFound.visibility = View.VISIBLE
                rvHistory.visibility = View.GONE
            } else {
                tvNotFound.visibility = View.GONE
                rvHistory.visibility = View.VISIBLE
            }
            riwayatAdapter.setDataAdapter(modelDatabases)
        }
    }

    override fun onDelete(modelDatabase: ModelDatabase?) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Hapus riwayat ini?")
        alertDialogBuilder.setPositiveButton("Ya, Hapus") { _: DialogInterface?, _: Int ->
            val uid = modelDatabase!!.uid
            riwayatViewModel.deleteDataById(uid)
            Toast.makeText(this@RiwayatActivity, "Data yang dipilih sudah dihapus", Toast.LENGTH_SHORT).show()
        }

        alertDialogBuilder.setNegativeButton("Batal") { dialogInterface: DialogInterface, _: Int -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}