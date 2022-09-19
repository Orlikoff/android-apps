package com.orlik.converter.unitFragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.orlik.converter.viewModels.DistanceViewModel
import com.orlik.converter.R
import com.orlik.converter.databinding.FragmentDistanceBinding
import com.orlik.converter.databinding.FragmentWeightBinding
import com.orlik.converter.mainActivityRelated.NumpadFragment
import com.orlik.converter.tools.ConvertingRules
import com.orlik.converter.tools.CustomAdapter
import com.orlik.converter.tools.UnitConverter
import com.orlik.converter.viewModels.WeightViewModel

class DistanceFragment : Fragment() {

    private var _binding: FragmentDistanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DistanceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDistanceBinding.inflate(inflater, container, false)

        // Autocomplete text views setup
        val distances = resources.getStringArray(R.array.distance_names)
        val arrayAdapter = CustomAdapter(requireContext(), distances)
        binding.autoCompleteTextViewDistance.setAdapter(arrayAdapter)
        binding.autoCompleteTextViewDistance2.setAdapter(arrayAdapter)

        // View model setup
        viewModel = ViewModelProvider(requireActivity())[DistanceViewModel::class.java]

        // Initial value setup (rotate reload)
        binding.distanceTvEdit.setText(viewModel.editValue.toString())
        binding.distanceTvDisplay.setText(viewModel.resultValue.toString())
        binding.autoCompleteTextViewDistance.setText(arrayAdapter.getItem(viewModel.convertFrom), false)
        binding.autoCompleteTextViewDistance2.setText(arrayAdapter.getItem(viewModel.convertTo), false)

        NumpadFragment.currentEditElement = binding.distanceTvEdit

        // Converting logic
        binding.distanceTvEdit.doOnTextChanged { _, _, _, _ ->
            recalculate()
        }

        // Units chooser logic
        binding.autoCompleteTextViewDistance.doOnTextChanged { _, _, _, _ ->
            viewModel.convertFrom = arrayAdapter.getPosition(binding.autoCompleteTextViewDistance.text.toString())
            recalculate()
        }
        binding.autoCompleteTextViewDistance2.doOnTextChanged { _, _, _, _ ->
            viewModel.convertTo = arrayAdapter.getPosition(binding.autoCompleteTextViewDistance2.text.toString())
            recalculate()
        }
        binding.btnExchangeDistance.setOnClickListener {
            exchangeButtonPressed(arrayAdapter)
        }

        return binding.root
    }

    private fun exchangeButtonPressed(arrayAdapter: CustomAdapter){
        val temp = binding.distanceTvEdit.text
        binding.distanceTvEdit.text = binding.distanceTvDisplay.text
        binding.distanceTvDisplay.text = temp
        val tmp = binding.autoCompleteTextViewDistance.text
        binding.autoCompleteTextViewDistance.setText(binding.autoCompleteTextViewDistance2.text, false)
        binding.autoCompleteTextViewDistance2.setText(tmp, false)

        viewModel.editValue = binding.distanceTvEdit.text.toString().toFloat()
        viewModel.resultValue = binding.distanceTvDisplay.text.toString().toFloat()
        viewModel.convertFrom = arrayAdapter.getPosition(binding.autoCompleteTextViewDistance.text.toString())
        viewModel.convertTo = arrayAdapter.getPosition(binding.autoCompleteTextViewDistance2.text.toString())
    }

    private fun recalculate(){
        var initialValue = binding.distanceTvEdit.text.toString()
        if (initialValue.endsWith(".")) initialValue = initialValue.dropLast(1)
        if (initialValue.isEmpty()) initialValue = "0"

        val convertingKey =
            binding.autoCompleteTextViewDistance.text.toString() + binding.autoCompleteTextViewDistance2.text.toString()
        val result = UnitConverter.convertUnit(
            initialValue.toFloat(),
            convertingKey,
            ConvertingRules.distanceRules
        ).toString()

        viewModel.editValue = initialValue.toFloat()
        viewModel.resultValue = result.toFloat()

        binding.distanceTvDisplay.setText(result)
    }

}