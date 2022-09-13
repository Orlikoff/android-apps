package com.orlik.converter.unitFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.orlik.converter.R
import com.orlik.converter.databinding.FragmentWeightBinding
import com.orlik.converter.mainActivityRelated.NumpadFragment
import com.orlik.converter.tools.ConvertingRules
import com.orlik.converter.tools.CustomAdapter
import com.orlik.converter.tools.UnitConverter
import com.orlik.converter.viewModels.WeightViewModel

class WeightFragment : Fragment() {

    private var _binding: FragmentWeightBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeightViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWeightBinding.inflate(inflater, container, false)

        // Autocomplete text views setup
        val weights = resources.getStringArray(R.array.weight_names)
        val arrayAdapter = CustomAdapter(requireContext(), weights)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter)

        // View model setup
        viewModel = ViewModelProvider(requireActivity())[WeightViewModel::class.java]

        // Initial value setup (rotate reload)
        binding.weightTvEdit.setText(viewModel.editValue.toString())
        binding.weightTvDisplay.setText(viewModel.resultValue.toString())
        binding.autoCompleteTextView.setText(arrayAdapter.getItem(viewModel.convertFrom), false)
        binding.autoCompleteTextView2.setText(arrayAdapter.getItem(viewModel.convertTo), false)

        NumpadFragment.currentEditElement = binding.weightTvEdit

        // Converting logic
        binding.weightTvEdit.doOnTextChanged { _, _, _, _ ->
            recalculate()
        }

        // Units chooser logic
        binding.autoCompleteTextView.doOnTextChanged { _, _, _, _ ->
            viewModel.convertFrom = arrayAdapter.getPosition(binding.autoCompleteTextView.text.toString())
            recalculate()
        }
        binding.autoCompleteTextView2.doOnTextChanged { _, _, _, _ ->
            viewModel.convertTo = arrayAdapter.getPosition(binding.autoCompleteTextView2.text.toString())
            recalculate()
        }

        return binding.root
    }

    private fun recalculate(){
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