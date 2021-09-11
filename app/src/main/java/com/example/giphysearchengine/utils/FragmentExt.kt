package com.example.giphysearchengine.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.findNavController(): NavController? =
    try {
        NavHostFragment.findNavController(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }