package com.orlik.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orlik.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding setup
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}