package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.View
import com.astery.xapplication.ui.viewmodels.CalendarViewModel

class TipsFragment : XFragment() {

    //private lateinit var thisBinding:FragmentTipsBinding

    //private val viewModel: CalendarViewModel by viewModels()
    //private lateinit var cAdapter: TipsAdapter

    /*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //_binding = FragmentTipsBinding.inflate(inflater, container, false)
        //thisBinding = binding as FragmentTipsBinding
        //return binding.root
        return null
    }

     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //prepareAdapters()
        //setViewModelListeners(viewModel)

    }


    /** prepare recyclerviews and adapters to view */
    private fun prepareAdapters(){


        /*
        // days
        val units = getDayUnitList();
        cAdapter = TipsAdapter(units, this)
        cAdapter.notifyDataSetChanged()
        cAdapter.setBlockListener { position ->
            viewModel.changeDay(units[position].day)
        }


        thisBinding.recyclerView.adapter = cAdapter
        thisBinding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 7, RecyclerView.HORIZONTAL, false)


         */


    }

    /** set listeners to view's item*/
    private fun setViewModelListeners(viewModel: CalendarViewModel) {
        /*

        viewModel.repository = (requireActivity().application as App).container.repository

        viewModel.selectedDay.observe(viewLifecycleOwner, {
            thisBinding.date.text = getString(R.string.calendar_date, it.get(Calendar.DAY_OF_MONTH),
                viewModel.getMonth(it.get(Calendar.MONTH), context), it.get(Calendar.YEAR))
            cAdapter.setSelectedDay(it.get(Calendar.DAY_OF_MONTH))
            viewModel.updateEvents();

        })

        viewModel.events.observe(viewLifecycleOwner,{
            eAdapter.setUnits(it as java.util.ArrayList<Event>?)
        })

         */
    }

}