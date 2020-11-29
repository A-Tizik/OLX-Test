package com.olx.autos.codechallenge.ui

import android.view.View
import com.olx.autos.codechallenge.R
import com.olx.autos.codechallenge.databinding.ItemLoadingBinding
import com.xwray.groupie.viewbinding.BindableItem

/**
 * Generic loading indicator for RecyclerView
 */
class LoadingItem: BindableItem<ItemLoadingBinding>() {
    override fun bind(viewBinding: ItemLoadingBinding, position: Int) {

    }

    override fun getLayout(): Int = R.layout.item_loading

    override fun initializeViewBinding(view: View) = ItemLoadingBinding.bind(view)

}