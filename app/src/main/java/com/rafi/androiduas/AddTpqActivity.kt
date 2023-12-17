package com.rafi.androiduas

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rafi.androiduas.data.AppContainer
import com.rafi.androiduas.data.DefaultAppContainer
import com.rafi.androiduas.data.TpqRepository
import com.rafi.androiduas.data.UserPreferencesRepository
import com.rafi.androiduas.data.UserRepository
import com.rafi.androiduas.data.UserState
import com.rafi.androiduas.model.CreateTpqForm
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rafi.androiduas.service.TpqService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "AddTpqActivity"

class AddTpqActivity : AppCompatActivity() {

    private lateinit var txtNamaTpq: EditText
    private lateinit var txtAlamat: EditText
    private lateinit var txtNoKontak: EditText
    private lateinit var btnAddInventaris: Button
    private lateinit var token: String

    private lateinit var appContainer: AppContainer
    private lateinit var userRepository: UserRepository
    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private lateinit var tpqRepository : TpqRepository
    private lateinit var userState: UserState

    private val dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user_preferences"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tpq)

        // Inisialisasi kontainer dan repository
        appContainer = DefaultAppContainer()
        userRepository = appContainer.userRepository
        tpqRepository = appContainer.tpqRepository
        userPreferencesRepository = UserPreferencesRepository(dataStore)
        // Panggil init() pada saat onCreate
        init()
    }

    private fun init() {
        // Inisialisasi UI components
        txtNamaTpq = findViewById(R.id.txtNamaTpq)
        txtAlamat = findViewById(R.id.txtAlamat)
        txtNoKontak = findViewById(R.id.txtNokontak)
        btnAddInventaris = findViewById(R.id.btnAddInventaris)

        // Set listener untuk button tambah
        btnAddInventaris.setOnClickListener {
            validateAndCreateTpq()
        }

        // Dapatkan informasi pengguna dan inisialisasi userState
        lifecycleScope.launch {
            try {
                userPreferencesRepository.user.collect { user ->
                     token = user.token
//                     isUser = user.isUser
//                     isAdmin = user.isAdmin
                }
            } catch (e: Exception) {
                Log.e(TAG, e.stackTraceToString())
            }
        }

    }

    private fun validateAndCreateTpq() {
        Log.d(TAG, "validateAndCreateTpq: Start")

        val namaTpq = txtNamaTpq.text.toString().trim()
        val alamat = txtAlamat.text.toString().trim()
        val noKontak = txtNoKontak.text.toString().trim()

        Log.d(TAG, "validateAndCreateTpq: namaTpq=$namaTpq, alamat=$alamat, noKontak=$noKontak")

        if (namaTpq.isEmpty() || alamat.isEmpty() || noKontak.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        // Tambahkan log untuk melihat isi token
        Log.d(TAG, "validateAndCreateTpq: token=$token")

        val createTpqForm = CreateTpqForm(namaTpq, alamat, noKontak)

        Log.d(TAG, "validateAndCreateTpq: Before launch")

        // Panggil fungsi createTpq dari repository
        lifecycleScope.launch {
            try {
                // Gantilah "token" dengan cara mendapatkan token autentikasi yang sesuai dengan implementasi Anda
                tpqRepository.createTpq(token, createTpqForm)
                Toast.makeText(this@AddTpqActivity, "TPQ berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                finish() // Tutup activity setelah berhasil menambahkan TPQ
            } catch (e: Exception) {
                Toast.makeText(this@AddTpqActivity, "Gagal menambahkan TPQ. Coba lagi nanti.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        Log.d(TAG, "validateAndCreateTpq: End")
    }
}
