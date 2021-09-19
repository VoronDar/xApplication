package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astery.xapplication.R
import com.astery.xapplication.databinding.FragmentCalendarBinding
import com.astery.xapplication.ui.adapters.CalendarAdapter
import com.astery.xapplication.ui.adapters.units.DayUnit
import java.util.*
import kotlin.collections.ArrayList

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


        val units = getDayUnitList();

        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        val adapter = CalendarAdapter(units, requireContext())
        adapter.notifyDataSetChanged()

        adapter.setBlockListener { position ->
            val cal: Calendar = GregorianCalendar(
                Calendar.getInstance()[Calendar.YEAR],
                Calendar.getInstance()[Calendar.MONTH],
                units[position].day
            )
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 7, RecyclerView.HORIZONTAL, false)


        //clickToMove(thisBinding.firstButton, NavigationAction.ADD_EVENT, null);


        //val bundle = Bundle()
        //bundle.putString("id", "23123")
        //clickToMove(thisBinding.firstButton, NavigationAction.ADD_EVENT, bundle);
    }
    private fun getDayUnitList(): ArrayList<DayUnit>{
        val cal:Calendar = GregorianCalendar.getInstance();
        val units = ArrayList<DayUnit>();

        for (i in 1..cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            units.add(DayUnit(i, true))
        }

        val firstDay:Calendar=GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        for (i in Calendar.SUNDAY until firstDay.get(Calendar.DAY_OF_WEEK)){
            units.add(0, DayUnit(i, false))
        }

        return units;

    }

}