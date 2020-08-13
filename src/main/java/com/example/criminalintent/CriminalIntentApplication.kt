package com.example.criminalintent

import android.app.Application

class CriminalIntentApplication : Application() {

    // The application instance  is created when the app launches
    // and destroyed when your app process is destroyed
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}