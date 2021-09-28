package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.astery.xapplication.R
import com.astery.xapplication.architecture.App
import com.astery.xapplication.databinding.PageItemBinding
import com.astery.xapplication.pojo.Item
import com.astery.xapplication.ui.viewmodels.ItemPageViewModel

class ItemFragment : XFragment() {

    private lateinit var thisBinding:PageItemBinding

    private val viewModel: ItemPageViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = PageItemBinding.inflate(inflater, container, false)
        thisBinding = binding as PageItemBinding
        return binding.root
    }


    override fun prepareAdapters(){}

    override fun setViewModelListeners() {
        viewModel.repository = (requireActivity().application as App).container.repository
        viewModel.item.value = arguments?.getSerializable("item") as Item

        viewModel.item.observe(viewLifecycleOwner,
            { t ->
                thisBinding.itemName.text = t.name
                thisBinding.itemContent.text = t.text

                for (advice in t.advises) {
                    val view: View = layoutInflater.inflate(R.layout.unit_advice, null)
                    (view.findViewById<View>(R.id.advice_text) as TextView).text = advice.text
                    thisBinding.tipsLayout.addView(view)
                }
                //thisBinding.itemImage.setImageBitmap(t.image)
            })

        viewModel.loadItem()

    }

    override fun setListeners() {
    }

    override fun getTitle(): String {
        return resources.getString(R.string.title_tips)
    }

    override fun requireSearch(): Boolean {
        return false
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}