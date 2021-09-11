package com.example.giphysearchengine.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giphysearchengine.R
import com.example.giphysearchengine.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}