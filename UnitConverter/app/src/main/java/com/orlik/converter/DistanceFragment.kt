package com.orlik.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.orlik.converter.databinding.FragmentDistanceBinding
import com.orlik.converter.databinding.FragmentWeightBinding

class DistanceFragment : Fragment() {

    private var _binding: FragmentDistanceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDistanceBinding.inflate(inflater, container, false)

        val weights = resources.getStringArray(R.array.weight_names)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, weights)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter)

        return binding.root
    }
}