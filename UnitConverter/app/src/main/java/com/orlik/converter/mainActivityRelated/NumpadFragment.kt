package com.orlik.converter.mainActivityRelated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.orlik.converter.tools.ModeTransfer
import com.orlik.converter.tools.Modes
import com.orlik.converter.R
import com.orlik.converter.databinding.FragmentNumpadBinding

class NumpadFragment : Fragment() {

    private var _binding: FragmentNumpadBinding? = null
    private val binding get() = _binding!!
//    private var currentEditElement: TextView? = null

    companion object {
        var currentEditElement: TextView? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNumpadBinding.inflate(inflater, container, false)

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
        numpadSetupNum(binding.btnDot)
        numpadSetupClear(binding.btnClear)

        return binding.root
    }

    private fun numpadSetupNum(button: MaterialButton) {
        button.setOnClickListener {
            var result = currentEditElement!!.text.toString()
            var symbol = button.text.toString()

            if (result == "0" && symbol == "0") symbol = ""
            else if (result == "0" && symbol != "0") result = ""

            if (result.contains(".") && symbol == ".") symbol = ""

            if (result.isEmpty() && symbol == ".") symbol = "0."

            result += symbol
            currentEditElement!!.text = result
        }
    }

    private fun numpadSetupClear(button: MaterialButton) {
        button.setOnClickListener {
            var result = currentEditElement!!.text.toString().dropLast(1)
            if (result == "") result = "0"

            currentEditElement!!.text = result
        }
    }
}