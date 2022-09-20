package com.orlik.converter.unitFragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.orlik.converter.R
import com.orlik.converter.databinding.FragmentWeightBinding
import com.orlik.converter.mainActivityRelated.NumpadFragment
import com.orlik.converter.tools.ConvertingRules
import com.orlik.converter.tools.CustomAdapter
import com.orlik.converter.tools.UnitConverter
import com.orlik.converter.viewModels.WeightViewModel
import java.math.BigDecimal

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
        binding.weightTvEdit.setText(viewModel.editValue.toPlainString())
        binding.weightTvDisplay.setText(viewModel.resultValue.toPlainString())
        binding.autoCompleteTextView.setText(arrayAdapter.getItem(viewModel.convertFrom), false)
        binding.autoCompleteTextView2.setText(arrayAdapter.getItem(viewModel.convertTo), false)

        NumpadFragment.currentEditElement = binding.weightTvEdit

        // Converting logic
        binding.weightTvEdit.doOnTextChanged { _, _, _, _ ->
            recalculate()
        }

        // Units chooser logic
        binding.autoCompleteTextView.doOnTextChanged { _, _, _, _ ->
            viewModel.convertFrom =
                arrayAdapter.getPosition(binding.autoCompleteTextView.text.toString())
            recalculate()
        }
        binding.autoCompleteTextView2.doOnTextChanged { _, _, _, _ ->
            viewModel.convertTo =
                arrayAdapter.getPosition(binding.autoCompleteTextView2.text.toString())
            recalculate()
        }
        binding.btnExchangeWeight.setOnClickListener {
            exchangeButtonPressed(arrayAdapter)
        }

        // Copy to clipboard
        binding.weightTvEdit.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "Converter data",
                    binding.weightTvEdit.text.toString()
                )
            )
            Toast.makeText(requireContext(), "Copied!", Toast.LENGTH_SHORT).show()
        }
        binding.weightTvDisplay.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "Converter data",
                    binding.weightTvEdit.text.toString()
                )
            )
            Toast.makeText(requireContext(), "Copied!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun exchangeButtonPressed(arrayAdapter: CustomAdapter) {
        binding.weightTvEdit.setText("0")
        binding.weightTvDisplay.setText("0")
        val tmp = binding.autoCompleteTextView.text
        binding.autoCompleteTextView.setText(binding.autoCompleteTextView2.text, false)
        binding.autoCompleteTextView2.setText(tmp, false)

        viewModel.editValue = BigDecimal(0).stripTrailingZeros()
        viewModel.resultValue = BigDecimal(0).stripTrailingZeros()
        viewModel.convertFrom =
            arrayAdapter.getPosition(binding.autoCompleteTextView.text.toString())
        viewModel.convertTo =
            arrayAdapter.getPosition(binding.autoCompleteTextView2.text.toString())
    }

    private fun recalculate() {
        var initialValue = binding.weightTvEdit.text.toString()
        if (initialValue.endsWith(".")) initialValue = initialValue.dropLast(1)
        if (initialValue.isEmpty()) initialValue = "0"

        val convertingKey =
            binding.autoCompleteTextView.text.toString() + binding.autoCompleteTextView2.text.toString()
        val result = UnitConverter.convertUnit(
            BigDecimal(initialValue).stripTrailingZeros(),
            convertingKey,
            ConvertingRules.weightsRules
        ).toString()

        viewModel.editValue = initialValue.toBigDecimal().stripTrailingZeros()
        viewModel.resultValue = result.toBigDecimal().stripTrailingZeros()

        binding.weightTvDisplay.setText(result.toBigDecimal().toPlainString())
    }

}