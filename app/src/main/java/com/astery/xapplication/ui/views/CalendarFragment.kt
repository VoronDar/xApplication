package com.astery.xapplication.ui.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.astery.xapplication.R
import com.astery.xapplication.architecture.App
import com.astery.xapplication.databinding.FragmentCalendarBinding
import com.astery.xapplication.pojo.Event
import com.astery.xapplication.ui.adapters.CalendarAdapter
import com.astery.xapplication.ui.adapters.EventAdapter
import com.astery.xapplication.ui.adapters.units.DayUnit
import com.astery.xapplication.ui.navigation.NavigationAction
import com.astery.xapplication.ui.viewmodels.CalendarViewModel
import com.google.android.material.transition.MaterialSharedAxis
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : XFragment() {

    private lateinit var thisBinding:FragmentCalendarBinding

    private val viewModel: CalendarViewModel by activityViewModels()
    private lateinit var cAdapter: CalendarAdapter
    private lateinit var eAdapter: EventAdapter

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


        prepareAdapters()
        setViewModelListeners(viewModel)
        setListeners()

        //clickToMove(thisBinding.firstButton, NavigationAction.ADD_EVENT, null);


        //val bundle = Bundle()
        //bundle.putString("id", "23123")
        //clickToMove(thisBinding.firstButton, NavigationAction.ADD_EVENT, bundle);


    }

    private fun setListeners(){
        thisBinding.backIcon.setOnClickListener { changeEventPresent(false) }
    }

    private fun prepareAdapters(){
        val units = getDayUnitList();
        cAdapter = CalendarAdapter(units)

        cAdapter.notifyDataSetChanged()
        cAdapter.setBlockListener { position ->
            viewModel.changeDay(units[position].day)
            //adapter.setUnits(getDayUnitList())
        }

        thisBinding.recyclerView.adapter = cAdapter
        thisBinding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 7, RecyclerView.HORIZONTAL, false)


        eAdapter = EventAdapter(null, context);
        eAdapter.notifyDataSetChanged()
        eAdapter.setBlockListener { position ->
            if (position == 0)
                getPreparedToMoveListener(NavigationAction.ADD_EVENT, null).success()
            else {
                viewModel.selectedEvent.value = position;
                changeEventPresent(true);
            }
            //adapter.setUnits(getDayUnitList())
        }

        thisBinding.eventRecycler.adapter = eAdapter
        thisBinding.eventRecycler.layoutManager =
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)

    }

    private fun setViewModelListeners(viewModel: CalendarViewModel) {

        viewModel.setRepository((requireActivity().application as App).container.repository)

        viewModel.selectedDay.observe(viewLifecycleOwner, {
            thisBinding.date.text = getString(R.string.calendar_date, it.get(Calendar.DAY_OF_MONTH),
                viewModel.getMonth(it.get(Calendar.MONTH), context), it.get(Calendar.YEAR))
            cAdapter.setSelectedDay(it.get(Calendar.DAY_OF_MONTH))
            viewModel.updateEvents();

        })

        viewModel.events.observe(viewLifecycleOwner,{
            Log.i("main", it.toString());
            eAdapter.setUnits(it as java.util.ArrayList<Event>?)
        })
    }

    private fun getDayUnitList(): ArrayList<DayUnit>{
        val units = ArrayList<DayUnit>();



        val cal:Calendar = viewModel.selectedDay.value!!
        for (i in 1..cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            units.add(DayUnit(i, true))
        }

        val firstDay:Calendar=GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        for (i in Calendar.SUNDAY until firstDay.get(Calendar.DAY_OF_WEEK)){
            units.add(0, DayUnit(i, false))
        }

        return units;
    }
    private fun changeEventPresent(isOpen: Boolean){
        if (isOpen){
            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, true)

            TransitionManager.beginDelayedTransition(thisBinding.fragmentRoot, sharedAxis)

            thisBinding.eventRecycler.visibility = View.GONE
            thisBinding.eventContainer.visibility =  View.VISIBLE

            thisBinding.backIcon.visibility = View.VISIBLE;


            //thisBinding.eventContent.itemImage.setImageDrawable()
            thisBinding.eventContent.eventName.text = viewModel.events.value?.get(viewModel.selectedEvent.value!!)!!.id



        } else{
            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, false)

            TransitionManager.beginDelayedTransition(thisBinding.fragmentRoot, sharedAxis)

            thisBinding.eventRecycler.visibility = View.VISIBLE
            thisBinding.eventContainer.visibility =  View.GONE

            thisBinding.backIcon.visibility = View.INVISIBLE

        }
    }

}