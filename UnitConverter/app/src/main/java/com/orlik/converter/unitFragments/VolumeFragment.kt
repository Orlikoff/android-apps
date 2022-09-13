package com.orlik.converter.unitFragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.orlik.converter.R
import com.orlik.converter.databinding.FragmentVolumeBinding
import com.orlik.converter.databinding.FragmentWeightBinding
import com.orlik.converter.mainActivityRelated.NumpadFragment
import com.orlik.converter.tools.ConvertingRules
import com.orlik.converter.tools.CustomAdapter
import com.orlik.converter.tools.UnitConverter
import com.orlik.converter.viewModels.VolumeViewModel
import com.orlik.converter.viewModels.WeightViewModel

class VolumeFragment : Fragment() {

    private var _binding: FragmentVolumeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VolumeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVolumeBinding.inflate(inflater, container, false)

        // Autocomplete text views setup
        val volumes = resources.getStringArray(R.array.volume_names)
        val arrayAdapter = CustomAdapter(requireContext(), volumes)
        binding.autoCompleteTextViewVolume.setAdapter(arrayAdapter)
        binding.autoCompleteTextViewVolume2.setAdapter(arrayAdapter)

        // View model setup
        viewModel = ViewModelProvider(requireActivity())[VolumeViewModel::class.java]

        // Initial value setup (rotate reload)
        binding.volumeTvEdit.setText(viewModel.editValue.toString())
        binding.volumeTvDisplay.setText(viewModel.resultValue.toString())
        binding.autoCompleteTextViewVolume.setText(arrayAdapter.getItem(viewModel.convertFrom), false)
        binding.autoCompleteTextViewVolume2.setText(arrayAdapter.getItem(viewModel.convertTo), false)

        NumpadFragment.currentEditElement = binding.volumeTvEdit

        // Converting logic
        binding.volumeTvEdit.doOnTextChanged { _, _, _, _ ->
            recalculate()
        }

        // Units chooser logic
        binding.autoCompleteTextViewVolume.doOnTextChanged { _, _, _, _ ->
            viewModel.convertFrom = arrayAdapter.getPosition(binding.autoCompleteTextViewVolume.text.toString())
            recalculate()
        }
        binding.autoCompleteTextViewVolume2.doOnTextChanged { _, _, _, _ ->
            viewModel.convertTo = arrayAdapter.getPosition(binding.autoCompleteTextViewVolume2.text.toString())
            recalculate()
        }

        return binding.root
    }

    private fun recalculate(){
        var initialValue = binding.volumeTvEdit.text.toString()
        if (initialValue.endsWith(".")) initialValue = initialValue.dropLast(1)
        if (initialValue.isEmpty()) initialValue = "0"

        val convertingKey =
            binding.autoCompleteTextViewVolume.text.toString() + binding.autoCompleteTextViewVolume2.text.toString()
        val result = UnitConverter.convertUnit(
            initialValue.toFloat(),
            convertingKey,
            ConvertingRules.volumeRules
        ).toString()

        viewModel.editValue = initialValue.toFloat()
        viewModel.resultValue = result.toFloat()

        binding.volumeTvDisplay.setText(result)
    }

}