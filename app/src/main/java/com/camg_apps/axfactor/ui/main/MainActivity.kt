package com.camg_apps.axfactor.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.databinding.ActivityMainBinding

enum class ROL{
    USER,
    ADMIN
}

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AxFactor)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}