package ru.turbopro.cubsapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ru.turbopro.cubsapp.R
import ru.turbopro.cubsapp.data.CubsAppSessionManager
import ru.turbopro.cubsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNav()
    }

    private fun setUpNav() {
        val navFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.homeBottomNavigation, navFragment.navController)

        navFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.shopFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.cartFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.ordersFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.orderSuccessFragment -> setBottomNavVisibility(View.VISIBLE)
                else -> setBottomNavVisibility(View.GONE)
            }
        }

        val sessionManager = CubsAppSessionManager(this.applicationContext)
        if (sessionManager.isUserSeller()) {
            binding.homeBottomNavigation.menu.removeItem(R.id.cartFragment)
        }else {
            binding.homeBottomNavigation.menu.removeItem(R.id.ordersFragment)
        }
    }

    private fun setBottomNavVisibility(visibility: Int) {
        binding.homeBottomNavigation.visibility = visibility
    }
}