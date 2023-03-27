package com.example.foodapp.utils

import android.graphics.drawable.DrawableContainer
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R

fun Spinner.setUpSpinnerData(list:MutableList<out Any>,callback:(String) -> Unit){
        val adapter = ArrayAdapter(context, R.layout.item_spinner,list)
        adapter.setDropDownViewResource(R.layout.item_spinner_list)
        this.adapter = adapter
        this.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                callback(list[p2].toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
fun View.isVisible(isShownLoading:Boolean,container: View){
    if (isShownLoading){
        this.visibility = View.VISIBLE
        container.visibility = View.INVISIBLE
    }
    else
    {
        this.visibility = View.INVISIBLE
        container.visibility = View.VISIBLE
    }

}

fun RecyclerView.setUpRecyclerView(layoutManager: RecyclerView.LayoutManager,adapter:RecyclerView.Adapter<*>){
    this.layoutManager = layoutManager
    this.setHasFixedSize(true)
    this.isNestedScrollingEnabled = false
    this.adapter = adapter
}
