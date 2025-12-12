package com.example.sidhant_verma_demo.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sidhant_verma_demo.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    private val fragmentOrdersBinding by lazy {
        FragmentOrdersBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return fragmentOrdersBinding.root
    }

}