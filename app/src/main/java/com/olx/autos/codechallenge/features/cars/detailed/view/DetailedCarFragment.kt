package com.olx.autos.codechallenge.features.cars.detailed.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.olx.autos.codechallenge.R
import com.olx.autos.codechallenge.Resource
import com.olx.autos.codechallenge.databinding.FragmentDetailedBinding
import com.olx.autos.codechallenge.features.cars.detailed.viewmodel.DetailedCarViewModel
import com.olx.autos.codechallenge.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailedCarFragment : Fragment(R.layout.fragment_detailed) {
    private val binding by viewBinding(FragmentDetailedBinding::bind)
    private val viewModel: DetailedCarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scope = MainScope()
        viewModel.carFlow.onEach {
            when(it) {
                is Resource.Success -> {
                    binding.root.displayedChild = 0
                    binding.carName.text = it.data.name
                    binding.mileage.text = it.data.mileage.toString()
                    binding.carImage.load(it.data.image)
                }
                is Resource.Loading -> {
                    binding.root.displayedChild = 1
                }
                is Resource.Error -> {
                    binding.root.displayedChild = 2
                    binding.errorLayout.retry.setOnClickListener { viewModel.onCarRefresh() }
                }
            }
        }.launchIn(scope)

    }
}