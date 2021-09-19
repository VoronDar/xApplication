package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.astery.xapplication.ui.*
import com.astery.xapplication.ui.navigation.NavigationAction
import com.astery.xapplication.ui.navigation.NavigationTransition
import com.astery.xapplication.ui.navigation.ParentActivity
import com.astery.xapplication.ui.navigation.SharedAxisTransition
import com.google.android.material.transition.MaterialSharedAxis

abstract class XFragment : Fragment() {


    protected var _binding: ViewBinding? = null
    protected val binding get() = _binding!!


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** set on click listener to this view that change fragments*/
    protected fun clickToMove(view: View, type: NavigationAction, bundle:Bundle?){
        view.setOnClickListener { (activity as ParentActivity).move(type, bundle)}
    }

    /** wrap listener from viewModel to change fragment if the result - success*/
    protected fun getPreparedToMoveListener(type: NavigationAction, bundle:Bundle?): ResultListener {
        val listener = object: ResultListener(){
            override fun success() {
                (activity as ParentActivity).move(type, bundle);
            }
        };
        return listener;
    }

    fun setTransition(transition: NavigationTransition){
        when(transition::class.java.simpleName) {
            "SharedAxisTransition" -> {
                if (transition.isFirst) {
                    exitTransition = MaterialSharedAxis((transition as SharedAxisTransition).axis, transition.firstParent)
                    reenterTransition = MaterialSharedAxis(transition.axis, !transition.firstParent)
                } else {
                    enterTransition = MaterialSharedAxis((transition as SharedAxisTransition).axis,  transition.firstParent)
                    returnTransition = MaterialSharedAxis(transition.axis, transition.firstParent)
                }
            }
        }
    }

}