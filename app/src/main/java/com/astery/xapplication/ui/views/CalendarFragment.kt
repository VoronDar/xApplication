package com.astery.xapplication.ui.views

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.animation.ValueAnimator.REVERSE
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
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
import com.astery.xapplication.ui.navigation.ParentActivity
import com.astery.xapplication.ui.viewmodels.CalendarViewModel
import com.astery.xapplication.ui.views.utils.VS
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
        bind = FragmentCalendarBinding.inflate(inflater, container, false)
        thisBinding = binding as FragmentCalendarBinding
        return binding.root
    }


    override fun setListeners(){
        thisBinding.backIcon.setOnClickListener { changeEventPresent(false) }
        clickToMove(thisBinding.noCardInfo, FragmentNavController.ADD_EVENT, addEventBundle)
    }

    override fun prepareAdapters(){
        // days
        val units = getDayUnitList()
        cAdapter = CalendarAdapter(units, context)
        cAdapter.notifyDataSetChanged()
        cAdapter.setBlockListener { position ->
            viewModel.changeDay(cAdapter.units[position].day)
        }


        thisBinding.recyclerView.adapter = cAdapter
        thisBinding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 7, RecyclerView.HORIZONTAL, false)


        // events for one day
        eAdapter = EventAdapter(null, context)
        eAdapter.notifyDataSetChanged()
        eAdapter.setBlockListener { position ->
            if (position == 0)
                getPreparedToMoveListener(FragmentNavController.ADD_EVENT, addEventBundle.getBundle()).done(true)
            else {
                viewModel.selectedEvent.value = position
                changeEventPresent(true)
            }
        }

        thisBinding.eventRecycler.adapter = eAdapter
        thisBinding.eventRecycler.layoutManager =
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)


    }

    @SuppressLint("Recycle")
    override fun setViewModelListeners() {
        viewModel.repository = (requireActivity().application as App).container.repository

        viewModel.selectedDay.observe(viewLifecycleOwner, {
            now = it

            thisBinding.date.text = getString(R.string.calendar_date, now.get(Calendar.DAY_OF_MONTH),
                viewModel.getMonth(now.get(Calendar.MONTH), context), now.get(Calendar.YEAR))
            cAdapter.setSelectedDay(now.get(Calendar.DAY_OF_MONTH))
            viewModel.updateEvents()

            super.setTitle()

        })

        viewModel.events.observe(viewLifecycleOwner,{

            val noEvents = it.size == 1

            // go from anywhere to page without events
            if (thisBinding.noCardInfo.visibility != VS.get(noEvents)){
                val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, (noEvents))
                TransitionManager.beginDelayedTransition(thisBinding.fragmentRoot, sharedAxis)
                thisBinding.eventRecycler.visibility = VS.get(!noEvents)
                thisBinding.eventContainer.visibility = VS.get(false)
                thisBinding.noCardInfo.visibility = VS.get(noEvents)
            }
            // go from eventInfo to page with events
            else if (thisBinding.eventContainer.visibility == VISIBLE){

                val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, (false))
                TransitionManager.beginDelayedTransition(thisBinding.fragmentRoot, sharedAxis)
                thisBinding.eventRecycler.visibility = VS.get(true)
                thisBinding.eventContainer.visibility = VS.get(false)
            }
            // go from page without event to page without events (blink)
            else if (thisBinding.noCardInfo.visibility == VISIBLE && noEvents){
                val alphaAnimator = ValueAnimator.ofFloat(1f, 0.75f)
                alphaAnimator.addUpdateListener { animator ->
                    val value = animator.animatedValue as Float
                    thisBinding.noCardInfo.alpha = value
                }
                alphaAnimator.repeatMode = REVERSE
                alphaAnimator.repeatCount = 1

                val transitionAnimator = ValueAnimator.ofFloat(0f, 25f)
                transitionAnimator.addUpdateListener { animator ->
                    val value = animator.animatedValue as Float
                    thisBinding.noCardInfo.translationY = value
                }
                transitionAnimator.repeatMode = REVERSE
                transitionAnimator.repeatCount = 1

                val valueAnimator = AnimatorSet()
                valueAnimator.play(alphaAnimator).with(transitionAnimator)
                valueAnimator.interpolator = AccelerateDecelerateInterpolator()
                valueAnimator.duration = 150
                valueAnimator.start()
            }

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

                    thisBinding.eventContent.eventName.text = event.template.name
                    thisBinding.eventContent.eventDescription.text = event.template.description

                    var properties = ""
                    if (event.eventDescription != null)
                        for (i in event.template.questions){
                            properties += i.selectedAnswer.text + "\n\n"
                        }

                    thisBinding.eventContent.eventProperties.text = properties
                    thisBinding.eventContent.getATip.visibility = VS.get(event.isTips)

                    showEventContainer(true)
                }
            }


        } else showEventContainer(false)
    }

    /** swap event list and event info */
    private fun showEventContainer(show:Boolean){
        val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, show)
        TransitionManager.beginDelayedTransition(thisBinding.fragmentRoot, sharedAxis)


        thisBinding.eventRecycler.visibility = VS.get(!show)
        thisBinding.eventContainer.visibility = VS.get(show)
        thisBinding.backIcon.visibility = VS.get(show)
        }

    override fun getTitle(): String {
        if (this::now.isInitialized)
            return getString(R.string.title_calendar,
                viewModel.getMonth(now.get(Calendar.MONTH), context), now.get(Calendar.YEAR))
        return ""
    }

    override fun requireSearch(): Boolean {
        return false
    }

    override fun onBackPressed(): Boolean {
        if (thisBinding.eventContainer.visibility == VISIBLE){
            showEventContainer(false)
            return true
        }
        return false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        changeMonthListener.close = true
        (activity as ParentActivity).showMenuNav(false, changeMonthListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeMonthListener.close = false
        (activity as ParentActivity).showMenuNav(true, changeMonthListener)
    }

    private val changeMonthListener:MenuNavListener = object: MenuNavListener() {
        override fun click(back: Boolean) {
            viewModel.changeMonth(!back)
            cAdapter.units = getDayUnitList()
            cAdapter.setSelectedDay(1)
        }
    }


    /** return bundle for creating a new event */
    private val addEventBundle: BundleGettable = object: BundleGettable {
        override fun getBundle(): Bundle {
            val bundle = Bundle()
            bundle.putSerializable("date", now)
            return bundle
        }
    }



    abstract class MenuNavListener{
        var close = false
        abstract fun click(back:Boolean)
    }

}