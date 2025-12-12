package com.example.sidhant_verma_demo.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.sidhant_verma_demo.R
import com.example.sidhant_verma_demo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val activityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityHomeBinding.root)
        setNavControllerToBottomView()
    }

    private fun setNavControllerToBottomView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        activityHomeBinding.bottomNavigation.setupWithNavController(navController)
    }
}