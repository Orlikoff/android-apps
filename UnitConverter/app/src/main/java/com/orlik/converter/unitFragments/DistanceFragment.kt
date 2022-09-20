package com.orlik.converter.unitFragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
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
import java.math.BigDecimal

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
        binding.distanceTvEdit.setText(viewModel.editValue.toPlainString())
        binding.distanceTvDisplay.setText(viewModel.resultValue.toPlainString())
        binding.autoCompleteTextViewDistance.setText(
            arrayAdapter.getItem(viewModel.convertFrom),
            false
        )
        binding.autoCompleteTextViewDistance2.setText(
            arrayAdapter.getItem(viewModel.convertTo),
            false
        )

        NumpadFragment.currentEditElement = binding.distanceTvEdit

        // Converting logic
        binding.distanceTvEdit.doOnTextChanged { _, _, _, _ ->
            recalculate()
        }

        // Units chooser logic
        binding.autoCompleteTextViewDistance.doOnTextChanged { _, _, _, _ ->
            viewModel.convertFrom =
                arrayAdapter.getPosition(binding.autoCompleteTextViewDistance.text.toString())
            recalculate()
        }
        binding.autoCompleteTextViewDistance2.doOnTextChanged { _, _, _, _ ->
            viewModel.convertTo =
                arrayAdapter.getPosition(binding.autoCompleteTextViewDistance2.text.toString())
            recalculate()
        }
        binding.btnExchangeDistance.setOnClickListener {
            exchangeButtonPressed(arrayAdapter)
        }

        // Copy to clipboard
        binding.distanceTvEdit.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "Converter data",
                    binding.distanceTvEdit.text.toString()
                )
            )
            Toast.makeText(requireContext(), "Copied!", Toast.LENGTH_SHORT).show()
        }
        binding.distanceTvDisplay.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "Converter data",
                    binding.distanceTvEdit.text.toString()
                )
            )
            Toast.makeText(requireContext(), "Copied!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun exchangeButtonPressed(arrayAdapter: CustomAdapter) {
        binding.distanceTvEdit.text.clear()
        binding.distanceTvEdit.text.append("0")
        binding.distanceTvDisplay.text.clear()
        binding.distanceTvDisplay.text.append("0")
        val tmp = binding.autoCompleteTextViewDistance.text
        binding.autoCompleteTextViewDistance.setText(
            binding.autoCompleteTextViewDistance2.text,
            false
        )
        binding.autoCompleteTextViewDistance2.setText(tmp, false)

        viewModel.editValue = BigDecimal(0).stripTrailingZeros()
        viewModel.resultValue = BigDecimal(0).stripTrailingZeros()
        viewModel.convertFrom =
            arrayAdapter.getPosition(binding.autoCompleteTextViewDistance.text.toString())
        viewModel.convertTo =
            arrayAdapter.getPosition(binding.autoCompleteTextViewDistance2.text.toString())
    }

    private fun recalculate() {
        var initialValue = binding.distanceTvEdit.text.toString()
        if (initialValue.endsWith(".")) initialValue = initialValue.dropLast(1)
        if (initialValue.isEmpty()) initialValue = "0"

        val convertingKey =
            binding.autoCompleteTextViewDistance.text.toString() + binding.autoCompleteTextViewDistance2.text.toString()
        val result = UnitConverter.convertUnit(
            BigDecimal(initialValue).stripTrailingZeros(),
            convertingKey,
            ConvertingRules.distanceRules
        ).toString()

        viewModel.editValue = initialValue.toBigDecimal().stripTrailingZeros()
        viewModel.resultValue = result.toBigDecimal().stripTrailingZeros()

        binding.distanceTvDisplay.setText(result.toBigDecimal().toPlainString())
    }

}