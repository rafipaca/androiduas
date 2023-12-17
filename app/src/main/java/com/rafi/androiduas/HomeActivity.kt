package com.rafi.androiduas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rafi.androiduas.data.AppContainer
import com.rafi.androiduas.data.DefaultAppContainer
import com.rafi.androiduas.data.TpqRepository
import com.rafi.androiduas.data.UserPreferencesRepository
import com.rafi.androiduas.data.UserRepository
import com.rafi.androiduas.data.UserState
import com.rafi.androiduas.fragments.AccountFragment
import com.rafi.androiduas.fragments.HomeFragment
import com.rafi.androiduas.model.User
import kotlinx.coroutines.launch

private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userState: UserState
    private lateinit var token: String
    private var isUser: Boolean = true
    private var isAdmin: Boolean = false
    private lateinit var user: User
    private lateinit var fab: FloatingActionButton
    private lateinit var navigationView: BottomNavigationView
    private lateinit var userRepository: UserRepository
    private lateinit var tpqRepository : TpqRepository
    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private val appContainer: AppContainer = DefaultAppContainer()
    private lateinit var mListView: ListView
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var tpqNames: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val application = application as MyApplication
        userPreferencesRepository = application.userPreferencesRepository
        userRepository = appContainer.userRepository
        tpqRepository = appContainer.tpqRepository

        // use arrayadapter and define an array
        val arrayAdapter: ArrayAdapter<String>
        tpqNames = mutableListOf()  // Inisialisasi tpqNames di sini

        mListView = findViewById(R.id.tpqlist)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tpqNames)
        mListView.adapter = arrayAdapter

        init()
    }


     fun init() {

        // Mendapatkan informasi user
        lifecycleScope.launch {
            try {
                userPreferencesRepository.user.collect { user ->
                    token = user.token
                    val isUser = user.isUser
                    val isAdmin = user.isAdmin


                }
            } catch (e: Exception) {
                Log.e(TAG, e.stackTraceToString())
            }
        }

         loadTpqList()

        // Inisialisasi Button
        fab = findViewById(R.id.fab)
        navigationView = findViewById(R.id.bottom_nav)

        // EventHandler fab
        fab.setOnClickListener {
            if (isUser) {
                val i = Intent(this@HomeActivity, AddTpqActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(
                    this@HomeActivity,
                    "Anda tidak memiliki akses. Silakan hubungi Admin",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
         navigationView.setOnNavigationItemSelectedListener { menuItem ->
             when (menuItem.itemId) {
                 R.id.item_home -> {
                     // Handle click on Home item
                     // Start HomeActivity
                     val intent = Intent(this@HomeActivity, HomeActivity::class.java)
                     startActivity(intent)
                     return@setOnNavigationItemSelectedListener true
                 }

                 R.id.item_account -> {
                     // Handle click on Account item
                     // Start EditUserActivity
                     val intent = Intent(this@HomeActivity, EditUserActivity::class.java)
                     startActivity(intent)
                     return@setOnNavigationItemSelectedListener true
                 }

                 else -> false
             }
         }
    }
    private fun loadTpqList() {
        lifecycleScope.launch {
            try {
                userPreferencesRepository.user.collect { user ->
                    token = user.token
                    val tpqList = tpqRepository.getAllTpq(token)

                    // Mengisi tpqNames dengan nama-nama TPQ
                    tpqNames.clear()
                    tpqNames.addAll(tpqList.map { it.name })
                    arrayAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading TPQ list", e)
                Toast.makeText(this@HomeActivity, "Gagal mendapatkan daftar TPQ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


