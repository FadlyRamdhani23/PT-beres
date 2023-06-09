package com.afg.hairpass.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.afg.hairpass.database.DatabaseClient.Companion.getInstance
import com.afg.hairpass.database.dao.DatabaseDao
import com.afg.hairpass.model.ModelDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers



class RiwayatViewModel(application: Application) : AndroidViewModel(application) {

    private var totalSaldo: LiveData<Int>
    var dataBank: LiveData<List<ModelDatabase>>
    private var databaseDao: DatabaseDao?

    fun deleteDataById(uid: Int) {
        Completable.fromAction {
            databaseDao?.deleteSingleData(uid)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    init {
        databaseDao = getInstance(application)?.appDatabase?.databaseDao()
        dataBank = databaseDao!!.getAll()
        totalSaldo = databaseDao!!.getSaldo()
    }

}