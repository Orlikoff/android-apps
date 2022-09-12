package com.orlik.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.orlik.converter.databinding.FragmentNumpadBinding
import com.orlik.converter.databinding.FragmentWeightBinding

class NumpadFragment : Fragment() {

    private var _binding: FragmentNumpadBinding? = null
    private val binding get() = _binding!!
    private var currentEditElement: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNumpadBinding.inflate(inflater, container, false)

        // Change editing to current mode
        currentEditElement= when (ModeTransfer.currentMode){
            Modes.Weight -> activity?.findViewById(R.id.weight_tv_edit)
            else -> activity?.findViewById(R.id.weight_tv_edit)
        }

        // Setup buttons for current mode
        numpadSetupNum(binding.btn0)
        numpadSetupNum(binding.btn1)
        numpadSetupNum(binding.btn2)
        numpadSetupNum(binding.btn3)
        numpadSetupNum(binding.btn4)
        numpadSetupNum(binding.btn5)
        numpadSetupNum(binding.btn6)
        numpadSetupNum(binding.btn7)
        numpadSetupNum(binding.btn8)
        numpadSetupNum(binding.btn9)

        // Show toast
        Toast.makeText(requireContext(), "GOOD", Toast.LENGTH_LONG).show()

        currentEditElement!!.text = "HI"

        return binding.root
    }

    private fun numpadSetupNum(button: MaterialButton){
        button.setOnClickListener {
            val result = currentEditElement!!.text.toString() + button.text.toString()
            currentEditElement!!.text = result
        }
    }
}