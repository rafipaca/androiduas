package com.rafi.androiduas

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rafi.androiduas.data.UserPreferencesRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")

class MyApplication : Application() {

    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }

}