package com.contactlist.currency.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.contactlist.currency.ui.vm.MainViewModel
import com.contactlist.currency.base.BaseActivity
import com.contactlist.currency.databinding.ActivityMainBinding
import com.contactlist.currency.ui.adapter.RvCurrencyRateAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var currencyListAdapter: RvCurrencyRateAdapter
    override fun createBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCurrencyList()
        binding.btnConvert.setOnClickListener {

        }
        mainViewModel.startParsing(assets)
    }

    private fun initCurrencyList() {
        apply {
            binding.currencyList.setProgressView(binding.progressView.progressView)
            binding.currencyList.setEmptyView(binding.emptyView.emptyView)
            binding.currencyList.layoutManager = LinearLayoutManager(this)
            currencyListAdapter = RvCurrencyRateAdapter()
            binding.currencyList.adapter = currencyListAdapter
        }

    }

}