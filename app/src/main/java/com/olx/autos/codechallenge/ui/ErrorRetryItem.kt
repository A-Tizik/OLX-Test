package com.olx.autos.codechallenge.ui

import android.view.View
import com.olx.autos.codechallenge.R
import com.olx.autos.codechallenge.databinding.ItemErrorRetryBinding
import com.xwray.groupie.viewbinding.BindableItem

/**
 * Generic error indicator for RecyclerView, with a retry lambda
 */
class ErrorRetryItem(val error: String, val retry: ()->Unit): BindableItem<ItemErrorRetryBinding>() {
    override fun bind(viewBinding: ItemErrorRetryBinding, position: Int) {
        viewBinding.error.text = error
        viewBinding.retry.setOnClickListener { retry() }
    }

    override fun getLayout(): Int = R.layout.item_error_retry

    override fun initializeViewBinding(view: View) = ItemErrorRetryBinding.bind(view)

}