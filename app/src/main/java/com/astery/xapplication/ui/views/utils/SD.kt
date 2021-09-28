package com.astery.xapplication.ui.views.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat

/** helps to set drawable*/
class SD {
    companion object{
        fun setDrawable(view: ImageView, drawable:Int, context: Context){
            view.setImageDrawable(
                ResourcesCompat.getDrawable(
                    context.resources,
                    drawable,
                    context.theme
                )
            )
        }
    }
}