package com.example.dogapp

import android.app.Application
import com.example.dogimageloader.DogImageLib

class DogApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Init lib instance
        DogImageLib.init(context = this)
    }
}