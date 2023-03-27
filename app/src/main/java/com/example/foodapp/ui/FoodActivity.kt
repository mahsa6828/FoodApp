package com.example.foodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityFoodBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodActivity : AppCompatActivity() {
    private var _binding : ActivityFoodBinding? = null
    private val binding get() = _binding
    private lateinit var navHost: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        binding?.bottomNav?.setupWithNavController(navHost.navController)
        navHost.navController.addOnDestinationChangedListener{_,destination,_ ->
            if (destination.id == R.id.detailFragment){
                binding?.bottomNav?.visibility = View.GONE
            }
            else
            {
                binding?.bottomNav?.visibility = View.VISIBLE
            }
        }




    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }
}