package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
import com.astery.xapplication.ui.navigation.FragmentNavController
import com.astery.xapplication.ui.viewmodels.CalendarViewModel
import com.google.android.material.transition.MaterialSharedAxis
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : XFragment() {

    private lateinit var thisBinding:FragmentCalendarBinding

    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var cAdapter: CalendarAdapter
    private lateinit var eAdapter: EventAdapter

    private lateinit var now:Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        thisBinding = binding as FragmentCalendarBinding
        return binding.root
    }


    override fun setListeners(){
        thisBinding.backIcon.setOnClickListener { changeEventPresent(false) }


        clickToMove(thisBinding.date, FragmentNavController.GET_A_TIP, null)
    }


    override fun prepareAdapters(){
        // days
        val units = getDayUnitList()
        cAdapter = CalendarAdapter(units)
        cAdapter.notifyDataSetChanged()
        cAdapter.setBlockListener { position ->
            viewModel.changeDay(units[position].day)
        }


        thisBinding.recyclerView.adapter = cAdapter
        thisBinding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 7, RecyclerView.HORIZONTAL, false)


        // events for one day
        eAdapter = EventAdapter(null, context)
        eAdapter.notifyDataSetChanged()
        eAdapter.setBlockListener { position ->
            if (position == 0)
                getPreparedToMoveListener(FragmentNavController.ADD_EVENT, null).done(true)
            else {
                viewModel.selectedEvent.value = position
                changeEventPresent(true)
            }
        }

        thisBinding.eventRecycler.adapter = eAdapter
        thisBinding.eventRecycler.layoutManager =
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)


    }

    override fun setViewModelListeners() {
        viewModel.repository = (requireActivity().application as App).container.repository

        viewModel.selectedDay.observe(viewLifecycleOwner, {
            now = it
            thisBinding.date.text = getString(R.string.calendar_date, now.get(Calendar.DAY_OF_MONTH),
                viewModel.getMonth(now.get(Calendar.MONTH), context), now.get(Calendar.YEAR))
            cAdapter.setSelectedDay(now.get(Calendar.DAY_OF_MONTH))
            viewModel.updateEvents()

        })

        viewModel.events.observe(viewLifecycleOwner,{
            eAdapter.setUnits(it as java.util.ArrayList<Event>?)
        })

    }

    /** get units for calendar */
    private fun getDayUnitList(): ArrayList<DayUnit>{
        val units = ArrayList<DayUnit>()
        val cal:Calendar = viewModel.selectedDay.value!!
        for (i in 1..cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            units.add(DayUnit(i, true))
        }

        val firstDay:Calendar=GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1)
        for (i in Calendar.SUNDAY until firstDay.get(Calendar.DAY_OF_WEEK)){
            units.add(0, DayUnit(i, false))
        }

        return units
    }

    /** open or close event */
    private fun changeEventPresent(isOpen: Boolean){

        if (isOpen){
            viewModel.getTemplate { success ->
                if (success) {



                    val event =viewModel.events.value?.get(viewModel.selectedEvent.value!!)!!


                    val bundle = Bundle()
                    bundle.putSerializable("event", event)
                    clickToMove(thisBinding.eventContent.getATip, FragmentNavController.GET_A_TIP, bundle)

                    thisBinding.eventContent.eventName.text =
                       event.template.name


                    thisBinding.eventContent.eventDescription.text = event.template.description

                    var properties = ""
                    if (event.eventDescription != null)
                        for (i in event.template.questions){
                            properties += i.selectedAnswer.text + "\n\n"
                        }

                    thisBinding.eventContent.eventProperties.text = properties

                    if (event.isTips)
                        thisBinding.eventContent.getATip.visibility = VISIBLE
                    else
                        thisBinding.eventContent.getATip.visibility = GONE



                    val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, true)
                    TransitionManager.beginDelayedTransition(thisBinding.fragmentRoot, sharedAxis)


                    thisBinding.eventRecycler.visibility = GONE
                    thisBinding.eventContainer.visibility = VISIBLE
                    thisBinding.backIcon.visibility = VISIBLE
                }
            }


        } else{
            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, false)

            TransitionManager.beginDelayedTransition(thisBinding.fragmentRoot, sharedAxis)

            thisBinding.eventRecycler.visibility = VISIBLE
            thisBinding.eventContainer.visibility =  GONE

            thisBinding.backIcon.visibility = View.INVISIBLE

        }
    }

    override fun getTitle(): String {
        if (this::now.isInitialized)
            return getString(R.string.title_calendar,
                viewModel.getMonth(now.get(Calendar.MONTH), context), now.get(Calendar.YEAR))
        return "";
    }
}