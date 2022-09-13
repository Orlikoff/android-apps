package com.orlik.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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
        binding.weightTvEdit.setText(viewModel.editValue.toString())
        binding.weightTvDisplay.setText(viewModel.resultValue.toString())
        binding.autoCompleteTextView.setText(arrayAdapter.getItem(viewModel.convertFrom), false)
        binding.autoCompleteTextView2.setText(arrayAdapter.getItem(viewModel.convertTo), false)

        // Converting logic
        binding.weightTvEdit.doOnTextChanged { _, _, _, _ ->
            recalculate(viewModel)
        }

        // Units chooser logic
        binding.autoCompleteTextView.doOnTextChanged { _, _, _, _ ->
            viewModel.convertFrom = arrayAdapter.getPosition(binding.autoCompleteTextView.text.toString())
            recalculate(viewModel)
        }
        binding.autoCompleteTextView2.doOnTextChanged { _, _, _, _ ->
            viewModel.convertTo = arrayAdapter.getPosition(binding.autoCompleteTextView2.text.toString())
            recalculate(viewModel)
        }

        return binding.root
    }

    private fun recalculate(viewModel: WeightViewModel){
        var initialValue = binding.weightTvEdit.text.toString()
        if (initialValue.endsWith(".")) initialValue = initialValue.dropLast(1)
        if (initialValue.isEmpty()) initialValue = "0"

        val convertingKey =
            binding.autoCompleteTextView.text.toString() + binding.autoCompleteTextView2.text.toString()
        val result = UnitConverter.convertUnit(
            initialValue.toFloat(),
            convertingKey,
            ConvertingRules.weightsRules
        ).toString()

        viewModel.editValue = initialValue.toFloat()
        viewModel.resultValue = result.toFloat()

        binding.weightTvDisplay.setText(result)
    }
}