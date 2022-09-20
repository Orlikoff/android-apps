package com.orlik.converter.unitFragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import java.math.BigDecimal

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
        binding.volumeTvEdit.setText(viewModel.editValue.toPlainString())
        binding.volumeTvDisplay.setText(viewModel.resultValue.toPlainString())
        binding.autoCompleteTextViewVolume.setText(
            arrayAdapter.getItem(viewModel.convertFrom),
            false
        )
        binding.autoCompleteTextViewVolume2.setText(
            arrayAdapter.getItem(viewModel.convertTo),
            false
        )

        NumpadFragment.currentEditElement = binding.volumeTvEdit

        // Converting logic
        binding.volumeTvEdit.doOnTextChanged { _, _, _, _ ->
            recalculate()
        }

        // Units chooser logic
        binding.autoCompleteTextViewVolume.doOnTextChanged { _, _, _, _ ->
            viewModel.convertFrom =
                arrayAdapter.getPosition(binding.autoCompleteTextViewVolume.text.toString())
            recalculate()
        }
        binding.autoCompleteTextViewVolume2.doOnTextChanged { _, _, _, _ ->
            viewModel.convertTo =
                arrayAdapter.getPosition(binding.autoCompleteTextViewVolume2.text.toString())
            recalculate()
        }
        binding.btnExchangeVolume.setOnClickListener {
            exchangeButtonPressed(arrayAdapter)
        }

        // Copy to clipboard
        binding.volumeTvEdit.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "Converter data",
                    binding.volumeTvEdit.text.toString()
                )
            )
            Toast.makeText(requireContext(), "Copied!", Toast.LENGTH_SHORT).show()
        }
        binding.volumeTvDisplay.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "Converter data",
                    binding.volumeTvEdit.text.toString()
                )
            )
            Toast.makeText(requireContext(), "Copied!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun exchangeButtonPressed(arrayAdapter: CustomAdapter) {
        binding.volumeTvEdit.setText("0")
        binding.volumeTvDisplay.setText("0")
        val tmp = binding.autoCompleteTextViewVolume.text
        binding.autoCompleteTextViewVolume.setText(binding.autoCompleteTextViewVolume2.text, false)
        binding.autoCompleteTextViewVolume2.setText(tmp, false)

        viewModel.editValue = BigDecimal(0).stripTrailingZeros()
        viewModel.resultValue = BigDecimal(0).stripTrailingZeros()
        viewModel.convertFrom =
            arrayAdapter.getPosition(binding.autoCompleteTextViewVolume.text.toString())
        viewModel.convertTo =
            arrayAdapter.getPosition(binding.autoCompleteTextViewVolume2.text.toString())
    }

    private fun recalculate() {
        var initialValue = binding.volumeTvEdit.text.toString()
        if (initialValue.endsWith(".")) initialValue = initialValue.dropLast(1)
        if (initialValue.isEmpty()) initialValue = "0"

        val convertingKey =
            binding.autoCompleteTextViewVolume.text.toString() + binding.autoCompleteTextViewVolume2.text.toString()
        val result = UnitConverter.convertUnit(
            BigDecimal(initialValue).stripTrailingZeros(),
            convertingKey,
            ConvertingRules.volumeRules
        ).toString()

        viewModel.editValue = initialValue.toBigDecimal().stripTrailingZeros()
        viewModel.resultValue = result.toBigDecimal().stripTrailingZeros()

        binding.volumeTvDisplay.setText(result.toBigDecimal().toPlainString())
    }

}