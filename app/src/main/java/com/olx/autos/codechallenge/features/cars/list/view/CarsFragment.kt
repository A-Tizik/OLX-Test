package com.olx.autos.codechallenge.features.cars.list.view

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.olx.autos.codechallenge.R
import com.olx.autos.codechallenge.Resource
import com.olx.autos.codechallenge.databinding.FragmentCarsBinding
import com.olx.autos.codechallenge.databinding.ItemCarBinding
import com.olx.autos.codechallenge.features.cars.detailed.view.DetailedCarFragment
import com.olx.autos.codechallenge.features.cars.detailed.viewmodel.DetailedCarViewModel
import com.olx.autos.codechallenge.features.cars.repositories.Car
import com.olx.autos.codechallenge.features.cars.list.viewmodel.CarsViewModel
import com.olx.autos.codechallenge.ui.ErrorRetryItem
import com.olx.autos.codechallenge.ui.LoadingItem
import com.olx.autos.codechallenge.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CarsFragment : Fragment(R.layout.fragment_cars) {
    private val binding by viewBinding(FragmentCarsBinding::bind)
    private val viewModel: CarsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.carsRecycler.adapter = adapter
        binding.carsRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.carMake.addTextChangedListener { text: Editable? ->
            text?.toString()?.let {
                viewModel.onCarMakeChangedFilter(it)
            }
        }

        binding.carName.addTextChangedListener { text: Editable? ->
            text?.toString()?.let {
                viewModel.onCarNameChangedFilter(it)
            }
        }

        val scope = MainScope()
        viewModel.filteredCarsFlow
            .map {
                when (it) {
                    is Resource.Success -> it.data.map { CarItem(it,::onCarClicked) }
                    is Resource.Loading -> listOf(LoadingItem())
                    is Resource.Error -> listOf(
                        ErrorRetryItem(
                            it.message,
                            viewModel::onCarsRefresh
                        )
                    )
                }
            }
            .onEach { adapter.update(it) }
            .launchIn(scope)
    }

    fun onCarClicked(car: Car) {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fragment_container_view,DetailedCarFragment().apply {
                arguments = Bundle().apply {
                    putString(DetailedCarViewModel.ARGUMENT_TAG,car.id)
                }
            })
            addToBackStack(null)
        }
    }
}

/**
 * Simple item for Car list adapter
 * [Car] parameter can also be a separate data class that is mapped from [Car],
 * that way Clean Code principle is more strictly adhered to
 */
data class CarItem(val car: Car,val onClicked:(Car)->Unit) : BindableItem<ItemCarBinding>() {
    override fun bind(viewBinding: ItemCarBinding, position: Int) {
        viewBinding.name.text = car.name
        viewBinding.make.text = car.make.label
        viewBinding.carImage.load(car.image)
        viewBinding.details.setOnClickListener { onClicked(car) }
    }

    override fun getLayout(): Int = R.layout.item_car

    override fun initializeViewBinding(view: View) = ItemCarBinding.bind(view)

}