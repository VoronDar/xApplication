package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astery.xapplication.databinding.FragmentCalendarBinding
import com.astery.xapplication.ui.navigation.NavigationAction

class CalendarFragment : XFragment() {

    private lateinit var thisBinding:FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        thisBinding = binding as FragmentCalendarBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clickToMove(thisBinding.firstButton, NavigationAction.ADD_EVENT, null);


        val bundle = Bundle()
        bundle.putString("id", "23123")

        clickToMove(thisBinding.firstButton, NavigationAction.ADD_EVENT, bundle);
    }

}