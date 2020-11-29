package com.olx.autos.codechallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.olx.autos.codechallenge.features.cars.list.view.CarsFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single activity approach is used here, where the only Activity's responsibility
 * is just navigating to the first screen of the app
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            replace<CarsFragment>(R.id.fragment_container_view)
        }
    }
}