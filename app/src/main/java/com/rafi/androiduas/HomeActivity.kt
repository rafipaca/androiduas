package com.rafi.androiduas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rafi.androiduas.data.AppContainer
import com.rafi.androiduas.data.DefaultAppContainer
import com.rafi.androiduas.data.UserPreferencesRepository
import com.rafi.androiduas.data.UserRepository
import com.rafi.androiduas.data.UserState
import com.rafi.androiduas.fragments.AccountFragment
import com.rafi.androiduas.fragments.HomeFragment
import com.rafi.androiduas.model.User
import kotlinx.coroutines.launch

private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {
    private lateinit var userState: UserState
    private lateinit var token: String
    private var isUser: Boolean = true
    private var isAdmin: Boolean = false
    private lateinit var user: User
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fab: FloatingActionButton
    private lateinit var navigationView: BottomNavigationView
    private lateinit var userRepository: UserRepository
    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private val appContainer: AppContainer = DefaultAppContainer()
    private val dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user_preferences"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
        userRepository = appContainer.userRepository

        // Inisialisasi fragmentManager
        fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val homeFragment = HomeFragment()  // Create an instance of HomeFragment

        fragmentTransaction.replace(
            R.id.frameHomeContainer,
            homeFragment,
            HomeFragment::class.java.simpleName
        )
        fragmentTransaction.commit()
        init()
    }


     fun init() {
        // Mendapatkan informasi user
        lifecycleScope.launch {
            try {
                userPreferencesRepository.user.collect { user ->
                    val token = user.token
                    val isUser = user.isUser
                    val isAdmin = user.isAdmin
                }
            } catch (e: Exception) {
                Log.e(TAG, e.stackTraceToString())
            }
        }

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
    }
}

        /*
        EventHandler navigationView
        navigationView.setOnNavigationItemSelectedListener { item ->
        when (item.itemId) {
        R.id.item_home -> {
        val account = fragmentManager.findFragmentByTag(
        AccountFragment::class.java.simpleName
        )
        if (account != null) {
        fragmentManager.beginTransaction().hide(
        fragmentManager.findFragmentByTag(
        AccountFragment::class.java.simpleName
        )!!
        ).commit()
        fragmentManager.beginTransaction().show(
        fragmentManager.findFragmentByTag(
        HomeFragment::class.java.simpleName
        )!!
        ).commit()
        }
        }
        R.id.item_account -> {
        val account = fragmentManager.findFragmentByTag(
        AccountFragment::class.java.simpleName
        )
        fragmentManager.beginTransaction().hide(
        fragmentManager.findFragmentByTag(
        HomeFragment::class.java.simpleName
        )!!
        ).commit()
        if (account != null) {
        fragmentManager.beginTransaction().show(
        fragmentManager.findFragmentByTag(
        AccountFragment::class.java.simpleName
        )!!
        ).commit()
        } else {
        fragmentManager.beginTransaction().add(
        R.id.fragmentContainer, AccountFragment(),
        AccountFragment::class.java.simpleName
        ).commit()
        }
        }
        }
        */
//    }

