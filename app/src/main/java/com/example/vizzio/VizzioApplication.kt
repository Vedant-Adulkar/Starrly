package com.example.vizzio

import android.app.Application
import com.google.firebase.FirebaseApp

class VizzioApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
} 