package com.orlik.calculator

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.orlik.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding setup
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // science button
        binding.science?.setOnClickListener {
            Toast.makeText(this, "Buy full version", Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(
            this,
            "MIND THE APP FLOATING POINT PRECISION - 16 SYMBOLS",
            Toast.LENGTH_LONG
        ).show()
    }
}