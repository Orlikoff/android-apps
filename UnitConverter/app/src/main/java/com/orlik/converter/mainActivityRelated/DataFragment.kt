package com.orlik.converter.mainActivityRelated

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.orlik.converter.R
import com.orlik.converter.unitFragments.WeightFragment
import com.orlik.converter.databinding.FragmentDataBinding
import com.orlik.converter.unitFragments.DistanceFragment
import com.orlik.converter.unitFragments.VolumeFragment
import com.orlik.converter.viewModels.DataViewModel
import com.orlik.converter.viewModels.FragmentField

class DataFragment : Fragment() {

    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDataBinding.inflate(inflater, container, false)

        // ViewModel setup
        viewModel = ViewModelProvider(this)[DataViewModel::class.java]

        // Rotate safe initialize of fragment view
        performFragmentTransaction(viewModel.currentFragment)

        // Radio group setup
        binding.radioGroup.setOnCheckedChangeListener { _, i ->
            when (i){
                R.id.rbWeight -> performFragmentTransaction(FragmentField.Weight)
                R.id.rbDistance -> performFragmentTransaction(FragmentField.Distance)
                R.id.rbVolume -> performFragmentTransaction(FragmentField.Volume)
            }
        }


        return binding.root
    }

    private fun performFragmentTransaction(fragmentField: FragmentField){
        when (fragmentField){
            FragmentField.Weight -> {
                val ft = activity?.supportFragmentManager?.beginTransaction()
                ft?.replace(R.id.fragmentContainerView, WeightFragment())
                ft?.commit()
            }
            FragmentField.Distance -> {
                val ft = activity?.supportFragmentManager?.beginTransaction()
                ft?.replace(R.id.fragmentContainerView, DistanceFragment())
                ft?.commit()
            }
            FragmentField.Volume -> {
                val ft = activity?.supportFragmentManager?.beginTransaction()
                ft?.replace(R.id.fragmentContainerView, VolumeFragment())
                ft?.commit()
            }
        }
        viewModel.currentFragment = fragmentField
    }
}