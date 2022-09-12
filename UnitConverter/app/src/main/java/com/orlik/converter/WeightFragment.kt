package com.orlik.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.orlik.converter.databinding.FragmentWeightBinding

class WeightFragment : Fragment() {

    private var _binding: FragmentWeightBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWeightBinding.inflate(inflater, container, false)

        // Autocomplete text views setup
        val weights = resources.getStringArray(R.array.weight_names)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, weights)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter)

        // View model setup
        val viewModel = ViewModelProvider(this)[WeightViewModel::class.java]

        // Initial value setup (rotate reload)
        binding.weightTvEdit.text = viewModel.editValue.toString()
        binding.weightTvDisplay.text = viewModel.resultValue.toString()
        binding.autoCompleteTextView.setText(arrayAdapter.getItem(viewModel.convertFrom), false)
        binding.autoCompleteTextView2.setText(arrayAdapter.getItem(viewModel.convertTo), false)

        // Binding setup

        return binding.root
    }
}