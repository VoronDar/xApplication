package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.astery.xapplication.R
import com.astery.xapplication.databinding.FragmentAddEventBinding
import com.astery.xapplication.ui.adapters.CalendarAdapter
import com.astery.xapplication.ui.viewmodels.CalendarViewModel

class AddEventFragment : XFragment() {


    private lateinit var thisBinding: FragmentAddEventBinding

    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var cAdapter: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentAddEventBinding.inflate(inflater, container, false)
        thisBinding = binding as FragmentAddEventBinding
        return binding.root
    }


    override fun setListeners() {
        TODO("Not yet implemented")
    }

    override fun setViewModelListeners() {
        TODO("Not yet implemented")
    }

    override fun prepareAdapters() {
        TODO("Not yet implemented")
    }

    override fun getTitle(): String {
        return resources.getString(R.string.title_new_event)
    }

    override fun requireSearch(): Boolean {
        return true
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}