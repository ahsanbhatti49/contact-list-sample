package com.contactlist.currency.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.contactlist.currency.R
import com.contactlist.currency.ui.vm.MainViewModel
import com.contactlist.currency.base.BaseActivity
import com.contactlist.currency.data.api.network.Status
import com.contactlist.currency.databinding.ActivityMainBinding
import com.contactlist.currency.ui.adapter.RvContactListAdapter
import com.contactlist.currency.utils.extensions.gone
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),
    android.widget.SearchView.OnQueryTextListener {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var contactListAdapter: RvContactListAdapter
    override fun createBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCurrencyList()
        setupListeners()
        setupDataObserver()

    }

    private fun setupDataObserver() {
        mainViewModel._uiState.observe(this) {
            when (it) {
                is Status.LOADING -> binding.contactList.showProgressView()
                is Status.SUCCESS -> {
                    binding.contactList.gone()
                    mainViewModel.contactList.value?.let { it1 ->
                        contactListAdapter.refereshList(
                            it1
                        )
                    }
                }
                is Status.ERROR -> {
                    binding.contactList.gone()
                    binding.contactList.showEmptyStateView()
                }
                else -> {}
            }
        }
    }

    private fun setupListeners() {
        contactListAdapter.onContactDeleteClicked = {
            mainViewModel.deleteContact(it)

        }
        contactListAdapter.onContactClicked = {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, Gson().toJson(it))
            }
            startActivity(Intent.createChooser(intent, getString(R.string.share_contact)))
        }
    }

    private fun initCurrencyList() {
        binding.contactList.setProgressView(binding.progressView.progressView)
        binding.contactList.setEmptyView(binding.emptyView.emptyView)
        binding.contactList.layoutManager = LinearLayoutManager(this)
        contactListAdapter = RvContactListAdapter()
        binding.contactList.adapter = contactListAdapter
        binding.searchLayout.searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        contactListAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        contactListAdapter.filter.filter(newText)
        return false
    }

}