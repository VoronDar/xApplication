package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astery.xapplication.R
import com.astery.xapplication.architecture.App
import com.astery.xapplication.databinding.FragmentTipsBinding
import com.astery.xapplication.pojo.Event
import com.astery.xapplication.ui.adapters.TipsAdapter
import com.astery.xapplication.ui.navigation.FragmentNavController
import com.astery.xapplication.ui.viewmodels.TipsViewModel

class TipsFragment : XFragment() {

    private lateinit var thisBinding:FragmentTipsBinding

    private val viewModel: TipsViewModel by viewModels()
    private lateinit var cAdapter: TipsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTipsBinding.inflate(inflater, container, false)
        thisBinding = binding as FragmentTipsBinding
        return binding.root
    }


    override fun prepareAdapters(){
        cAdapter = TipsAdapter(null, this)
        cAdapter.notifyDataSetChanged()
        cAdapter.setBlockListener { position ->
            val bundle = Bundle()
            bundle.putSerializable("item", viewModel.getItemByPositionInUnits(position))
            getPreparedToMoveListener(FragmentNavController.ITEM_PAGE, bundle).success()
        }


        thisBinding.recyclerView.adapter = cAdapter
        thisBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

    }

    override fun setViewModelListeners() {
        viewModel.repository = (requireActivity().application as App).container.repository
        viewModel.event = arguments?.getSerializable("event") as Event
        viewModel.getTips()

        viewModel.units.observe(viewLifecycleOwner,
            { t -> cAdapter.setUnits(t); })
    }

    override fun setListeners() {
    }

    override fun getTitle(): String {
        return resources.getString(R.string.title_tips)
    }
}