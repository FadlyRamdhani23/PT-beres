package com.afg.hairpass.view.input

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.afg.hairpass.R
import com.afg.hairpass.utils.FunctionHelper.rupiahFormat

import com.afg.hairpass.view.login.LoginActivity
import com.afg.hairpass.session.sesionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_input_data.*
import java.text.SimpleDateFormat
import java.util.*

class InputDataActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var strNama: String
    private lateinit var waw: String
    private lateinit var wiw: String
    private lateinit var strTanggal: String
    private lateinit var strAlamat: String
    private lateinit var strCatatan: String
    private lateinit var strKategoriSelected: String
    private lateinit var strHargaSelected: String
    private lateinit var strKategori: Array<String>
    private lateinit var strHarga: Array<String>
    private lateinit var sesionManager: sesionManager
    private var selectedSeat: String = ""
    private var isSeatSelected = false
    private var countTotal = 0
    private var countBerat = 0
    private var countHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)
        sesionManager = sesionManager(this)
        if(!sesionManager.isLoggedIn()) {
            val intent = Intent(this@InputDataActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        val email = sesionManager.getUserEmail().toString()
        db = FirebaseFirestore.getInstance()
        setToolbar()
        setInitLayout()
        setInputData()
//        disableJam()
        setText(email)
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

    private fun setText(email:String?){
        db= FirebaseFirestore.getInstance()
        if(email!=null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                    if(email != "admin@gmail.com"){
                        inputNama.setText(it.get("name").toString())
                    }
                }
        }
    }

//    private fun disableJam(){
//        tvJam.visibility = View.GONE
//        inputJam.visibility = View.GONE
//        jam9.isEnabled = false
//        jam9.setImageResource(R.drawable.jam9a)
//        jam10.isEnabled = false
//        jam10.setImageResource(R.drawable.jam10a)
//        jam11.isEnabled = false
//        jam11.setImageResource(R.drawable.jam11a)
//        jam12.isEnabled = false
//        jam12.setImageResource(R.drawable.jam12a)
//        jam13.isEnabled = false
//        jam13.setImageResource(R.drawable.jam13a)
//        jam14.isEnabled = false
//        jam14.setImageResource(R.drawable.jam14a)
//        jam15.isEnabled = false
//        jam15.setImageResource(R.drawable.jam15a)
//        jam16.isEnabled = false
//        jam16.setImageResource(R.drawable.jam16a)
//        jam17.isEnabled = false
//        jam17.setImageResource(R.drawable.jam17a)
//        jam18.isEnabled = false
//        jam18.setImageResource(R.drawable.jam18a)
//        jam19.isEnabled = false
//        jam19.setImageResource(R.drawable.jam19a)
//        jam20.isEnabled = false
//        jam20.setImageResource(R.drawable.jam20a)
//    }

    @SuppressLint("SuspiciousIndentation")
    private fun updateHourTextViews(selectedDate: Date) {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.time = selectedDate

        // Lakukan pembaruan tampilan jam berdasarkan tanggal yang dipilih
        if (selectedCalendar.before(Calendar.getInstance())) {
            cekcjam.visibility = View.GONE
            // Tampilkan semua jam dalam keadaan tidak aktif
                jam9.setImageResource(R.drawable.jam9)
                jam10.setImageResource(R.drawable.jam10)
                jam11.setImageResource(R.drawable.jam11)
                jam12.setImageResource(R.drawable.jam12)
                jam13.setImageResource(R.drawable.jam13)
                jam14.setImageResource(R.drawable.jam14)
                jam15.setImageResource(R.drawable.jam15)
                jam16.setImageResource(R.drawable.jam16)
                jam17.setImageResource(R.drawable.jam17)
                jam18.setImageResource(R.drawable.jam18)
                jam19.setImageResource(R.drawable.jam19)
                jam20.setImageResource(R.drawable.jam20)

        } else {
                jam9.setImageResource(R.drawable.jam9)
                jam10.setImageResource(R.drawable.jam10)
                jam11.setImageResource(R.drawable.jam11)
                jam12.setImageResource(R.drawable.jam12)
                jam13.setImageResource(R.drawable.jam13)
                jam14.setImageResource(R.drawable.jam14)
                jam15.setImageResource(R.drawable.jam15)
                jam16.setImageResource(R.drawable.jam16)
                jam17.setImageResource(R.drawable.jam17)
                jam18.setImageResource(R.drawable.jam18)
                jam19.setImageResource(R.drawable.jam19)
                jam20.setImageResource(R.drawable.jam20)
            jam9.isEnabled = true
            jam10.isEnabled = true
            jam11.isEnabled = true
            jam12.isEnabled = true
            jam13.isEnabled = true
            jam14.isEnabled = true
            jam15.isEnabled = true
            jam16.isEnabled = true
            jam17.isEnabled = true
            jam18.isEnabled = true
            jam19.isEnabled = true
            jam20.isEnabled = true
        }

        // Aktifkan jam-jam jika tanggal dipilih adalah hari esok
        val today = Calendar.getInstance()
        val tomorrow = Calendar.getInstance()
        val aw = tomorrow.add(Calendar.DAY_OF_MONTH, 1)
        if (selectedCalendar.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) &&
            selectedCalendar.get(Calendar.MONTH) == tomorrow.get(Calendar.MONTH) &&
            selectedCalendar.get(Calendar.DAY_OF_MONTH) == tomorrow.get(Calendar.DAY_OF_MONTH)
        ) {
            val db = FirebaseFirestore.getInstance()
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(tomorrow.time)
            val collectionRef = db.collection("BOOKING")
            val query1 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","09:00").limit(1)
            query1.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam9.isEnabled = false
                    jam9.setImageResource(R.drawable.jam9a)
                }
            }
            val query2 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","10:00").limit(1)
            query2.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam10.isEnabled = false
                    jam10.setImageResource(R.drawable.jam10a)
                }
            }
            val query3 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","11:00").limit(1)
            query3.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam11.isEnabled = false
                    jam11.setImageResource(R.drawable.jam11a)
                }
            }
            val query4 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","12:00").limit(1)
            query4.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam12.isEnabled = false
                    jam12.setImageResource(R.drawable.jam12a)
                }
            }
            val query5 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","13:00").limit(1)
            query5.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam13.isEnabled = false
                    jam13.setImageResource(R.drawable.jam13a)
                }
            }
            val query6 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","14:00").limit(1)
            query6.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam14.isEnabled = false
                    jam14.setImageResource(R.drawable.jam14a)
                }
            }
            val query7 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","15:00").limit(1)
            query7.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam15.isEnabled = false
                    jam15.setImageResource(R.drawable.jam15a)
                }
            }
            val query8 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","16:00").limit(1)
            query8.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam16.isEnabled = false
                    jam16.setImageResource(R.drawable.jam16a)
                }
            }
            val query9 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","17:00").limit(1)
            query9.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam17.isEnabled = false
                    jam17.setImageResource(R.drawable.jam17a)
                }
            }
            val query10 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","18:00").limit(1)
            query10.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam18.isEnabled = false
                    jam18.setImageResource(R.drawable.jam18a)
                }
            }
            val query11 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","19:00").limit(1)
            query11.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam19.isEnabled = false
                    jam19.setImageResource(R.drawable.jam19a)
                }
            }
            val query12 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","20:00").limit(1)
            query12.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam20.isEnabled = false
                    jam20.setImageResource(R.drawable.jam20a)
                }
            }

        }else if (selectedCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            selectedCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
            selectedCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
        ) {
            cekcjam.visibility = View.GONE
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            if (currentHour >= 9) {
                jam9.setImageResource(R.drawable.jam9a)
            }
            if (currentHour >= 10) {
                jam10.setImageResource(R.drawable.jam10a)
            }
            if (currentHour >= 11) {
                jam11.setImageResource(R.drawable.jam11a)
            }
            if (currentHour >= 12) {
                jam12.setImageResource(R.drawable.jam12a)
            }
            if (currentHour >= 13) {
                jam13.setImageResource(R.drawable.jam13a)
            }
            if (currentHour >= 14) {
                jam14.setImageResource(R.drawable.jam14a)
            }
            if (currentHour >= 15) {
                jam15.setImageResource(R.drawable.jam15a)
            }
            if (currentHour >= 16) {
                jam16.setImageResource(R.drawable.jam16a)
            }
            if (currentHour >= 17) {
                jam17.setImageResource(R.drawable.jam17a)
            }
            if (currentHour >= 18) {
                jam18.setImageResource(R.drawable.jam18a)
            }
            if (currentHour >= 19) {
                jam19.setImageResource(R.drawable.jam19a)
            }
            if (currentHour >= 20) {
                jam20.setImageResource(R.drawable.jam20a)
            }


            jam9.isEnabled = currentHour < 9
            jam10.isEnabled = currentHour < 10
            jam11.isEnabled = currentHour < 11
            jam12.isEnabled = currentHour < 12
            jam13.isEnabled = currentHour < 13
            jam14.isEnabled = currentHour < 14
            jam15.isEnabled = currentHour < 15
            jam16.isEnabled = currentHour < 16
            jam17.isEnabled = currentHour < 17
            jam18.isEnabled = currentHour < 18
            jam19.isEnabled = currentHour < 19
            jam20.isEnabled = currentHour < 20

            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(today.time)
            val db = FirebaseFirestore.getInstance()
            val collectionRef = db.collection("BOOKING")
            val query1 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","09:00").limit(1)
            query1.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam9.isEnabled = false
                    jam9.setImageResource(R.drawable.jam9a)
                }
            }
            val query2 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","10:00").limit(1)
            query2.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam10.isEnabled = false
                    jam10.setImageResource(R.drawable.jam10a)
                }
            }
            val query3 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","11:00").limit(1)
            query3.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam11.isEnabled = false
                    jam11.setImageResource(R.drawable.jam11a)
                }
            }
            val query4 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","12:00").limit(1)
            query4.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam12.isEnabled = false
                    jam12.setImageResource(R.drawable.jam12a)
                }
            }
            val query5 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","13:00").limit(1)
            query5.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam13.isEnabled = false
                    jam13.setImageResource(R.drawable.jam13a)
                }
            }
            val query6 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","14:00").limit(1)
            query6.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam14.isEnabled = false
                    jam14.setImageResource(R.drawable.jam14a)
                }
            }
            val query7 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","15:00").limit(1)
            query7.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam15.isEnabled = false
                    jam15.setImageResource(R.drawable.jam15a)
                }
            }
            val query8 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","16:00").limit(1)
            query8.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam16.isEnabled = false
                    jam16.setImageResource(R.drawable.jam16a)
                }
            }
            val query9 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","17:00").limit(1)
            query9.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam17.isEnabled = false
                    jam17.setImageResource(R.drawable.jam17a)
                }
            }
            val query10 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","18:00").limit(1)
            query10.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam18.isEnabled = false
                    jam18.setImageResource(R.drawable.jam18a)
                }
            }
            val query11 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","19:00").limit(1)
            query11.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam19.isEnabled = false
                    jam19.setImageResource(R.drawable.jam19a)
                }
            }
            val query12 = collectionRef.whereEqualTo("tanggal",formattedDate).whereEqualTo("Jam","20:00").limit(1)
            query12.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    jam20.isEnabled = false
                    jam20.setImageResource(R.drawable.jam20a)
                }
            }
        }else{
            jam9.isEnabled = false
            jam10.isEnabled = false
            jam11.isEnabled = false
            jam12.isEnabled = false
            jam13.isEnabled = false
            jam14.isEnabled = false
            jam15.isEnabled = false
            jam16.isEnabled = false
            jam17.isEnabled = false
            jam18.isEnabled = false
            jam19.isEnabled = false
            jam20.isEnabled = false
        }
    }
    private fun setInitLayout() {
        strKategori = resources.getStringArray(R.array.kategori_cabang)
        strHarga = resources.getStringArray(R.array.harga_orang)


        val arrayBahasa = ArrayAdapter(this@InputDataActivity, android.R.layout.simple_list_item_1, strKategori)
        arrayBahasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spKategori.setAdapter(arrayBahasa)

        jam9.setOnClickListener {
            selectedSeat = "09:00" // Pemilihan jam 09:00
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam9.setImageResource(R.drawable.jam9)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam9.setImageResource(R.drawable.jam9g)
                isSeatSelected = true
            }
        }

        jam10.setOnClickListener {
            selectedSeat = "10:00"
            if (isSeatSelected) {
                jam10.setImageResource(R.drawable.jam10)
                isSeatSelected = false
            } else {
                jam10.setImageResource(R.drawable.jam10g)
                isSeatSelected = true
            }
        }
        jam11.setOnClickListener {
            selectedSeat = "11:00"
            if (isSeatSelected) {
                jam11.setImageResource(R.drawable.jam11)
                isSeatSelected = false
            } else {
                jam11.setImageResource(R.drawable.jam11g)
                isSeatSelected = true
            }
        }
        jam12.setOnClickListener {
            selectedSeat = "12:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam12.setImageResource(R.drawable.jam12)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam12.setImageResource(R.drawable.jam18g)
                isSeatSelected = true
            }
        }
        jam13.setOnClickListener {
            // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
            selectedSeat = "13:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam13.setImageResource(R.drawable.jam13)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam13.setImageResource(R.drawable.jam13g)
                isSeatSelected = true
            }
        }
        jam14.setOnClickListener {
            // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
            selectedSeat = "14:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam14.setImageResource(R.drawable.jam14)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam14.setImageResource(R.drawable.jam14g)
                isSeatSelected = true
            }
        }
        jam15.setOnClickListener {
            // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
            selectedSeat = "15:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam15.setImageResource(R.drawable.jam15)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam15.setImageResource(R.drawable.jam15g)
                isSeatSelected = true
            }
        }
        jam16.setOnClickListener {
            // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
            selectedSeat = "16:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam16.setImageResource(R.drawable.jam16)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam16.setImageResource(R.drawable.jam16g)
                isSeatSelected = true
            }
        }
        jam17.setOnClickListener {
            // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
            selectedSeat = "17:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam17.setImageResource(R.drawable.jam17)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam17.setImageResource(R.drawable.jam17g)
                isSeatSelected = true
            }
        }
        jam18.setOnClickListener {
            // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
            selectedSeat = "18:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam18.setImageResource(R.drawable.jam18)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam18.setImageResource(R.drawable.jam12g)
                isSeatSelected = true
            }
        }
        jam19.setOnClickListener {
            // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
            selectedSeat = "19:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam19.setImageResource(R.drawable.jam19)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam19.setImageResource(R.drawable.jam19g)
                isSeatSelected = true
            }
        }
        jam20.setOnClickListener {
            // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
            selectedSeat = "20:00" // Contoh memilih kursi A1
            if (isSeatSelected) {
                // Kode untuk mengubah gambar ke gambar awal
                jam20.setImageResource(R.drawable.jam20)
                isSeatSelected = false
            } else {
                // Kode untuk memilih kursi, misalnya menampilkan dialog konfirmasi atau mengubah tampilan kursi terpilih
                jam20.setImageResource(R.drawable.jam20g)
                isSeatSelected = true
            }
        }
        spKategori.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                strKategoriSelected = parent.getItemAtPosition(position).toString()
                strHargaSelected = strHarga[position]
                countHarga = strHargaSelected.toInt()
                if (inputBerat.getText().toString() != "") {
                    countBerat = inputBerat.getText().toString().toInt()
                    setTotalPrice(countBerat)
                } else {
                    inputHarga.setText(rupiahFormat(countHarga))
                }
            }


            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                spKategori.setEnabled(false)
            }
        })

        inputBerat.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(editable: Editable) {
                inputBerat.removeTextChangedListener(this)
                if (editable.length > 0) {
                    setTotalPrice(countBerat)
                } else {
                    inputHarga.setText(rupiahFormat(countHarga))
                }
                inputBerat.addTextChangedListener(this)
            }
        })

        btnTanggal.setOnClickListener {
            val now = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this@InputDataActivity,
                { _, year, month, dayOfMonth ->
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(Calendar.YEAR, year)
                    selectedCalendar.set(Calendar.MONTH, month)
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val selectedDate = selectedCalendar.time

                    inputTanggal.setText(SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(selectedDate))
                    updateHourTextViews(selectedDate) // Panggil fungsi updateHourTextViews dengan selectedDate sebagai parameter
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )

            // Batasi pemilihan tanggal hanya pada tanggal sekarang dan seterusnya
            datePickerDialog.datePicker.minDate = now.timeInMillis

            val midnightTomorrow = Calendar.getInstance()
            midnightTomorrow[Calendar.HOUR_OF_DAY] = 0
            midnightTomorrow[Calendar.MINUTE] = 0
            midnightTomorrow[Calendar.SECOND] = 0
            midnightTomorrow.add(Calendar.DAY_OF_MONTH, 1)
            datePickerDialog.datePicker.maxDate = midnightTomorrow.timeInMillis

            datePickerDialog.show()
        }




    }

    private fun setTotalPrice(berat: Int) {
        countTotal = countHarga * berat
        inputHarga.setText(rupiahFormat(countTotal))
    }


    private fun setInputData() {
        btnCheckout.setOnClickListener { v: View? ->
            strNama = inputNama.text.toString()
            strTanggal = inputTanggal.text.toString()
            strAlamat = inputJam.setText(selectedSeat).toString()
            strCatatan = inputTambahan.text.toString()
            waw = countTotal.toString()
            wiw = countBerat.toString()


            if (strNama.isEmpty() or strTanggal.isEmpty() or strAlamat.isEmpty() or (strKategori.size == 0) or (countBerat == 0) or (countHarga == 0) ) {

                Toast.makeText(
                    this@InputDataActivity,
                    "Data tidak boleh ada yang kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val booking= hashMapOf(
                    "name" to strNama,
                    "tanggal" to strTanggal,
                    "cabang" to strKategoriSelected,
                    "Jam" to selectedSeat,
                    "catatan" to strCatatan,
                    "Jumlah" to waw,
                    "berat" to wiw,
                )
                val Booking=db.collection("BOOKING")
                Booking.add(booking)

                Toast.makeText(
                    this@InputDataActivity,
                    "Pesanan Anda sedang diproses, cek di menu riwayat",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

}