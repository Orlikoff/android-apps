package com.orlik.calculator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.orlik.calculator.R
import com.orlik.calculator.databinding.FragmentDisplayBinding
import com.orlik.calculator.databinding.FragmentSimpleNumpadBinding

class DisplayFragment : Fragment() {

    private lateinit var _binding: FragmentDisplayBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // binding setup
        _binding = FragmentDisplayBinding.inflate(inflater, container, false)

        // disabling the keyboard
        binding.numberDisplay.showSoftInputOnFocus = false

        // setting up the edit field
        SimpleNumpadFragment.field = binding.numberDisplay
        SimpleNumpadFragment.display = binding.answerDisplay

        // disabling focus, when is being edited
        binding.numberDisplay.doOnTextChanged { _, _, _, _ ->
            binding.numberDisplay.clearFocus()
        }

        return binding.root
    }

}