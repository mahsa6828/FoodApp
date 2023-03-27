package com.example.foodapp.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodapp.data.model.Meal
import com.example.foodapp.databinding.ItemFoodsBinding
import javax.inject.Inject

class FoodsListAdapter @Inject constructor(): RecyclerView.Adapter<FoodsListAdapter.FoodsHolder>() {
    private lateinit var binding : ItemFoodsBinding
    private var foodsList = emptyList<Meal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsHolder {
        binding = ItemFoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodsHolder()
    }

    override fun getItemCount(): Int = foodsList.size

    override fun onBindViewHolder(holder: FoodsHolder, position: Int) {
        holder.bind(foodsList[position])
    }

    fun setData(list:List<Meal>) {
        foodsList = list
    }

    private var onItemClickListener: ((Meal) -> Unit)? = null

    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }

    inner class FoodsHolder : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Meal){
            binding.apply {
                itemFoodsImage.load(item.strMealThumb){
                    crossfade(true)
                    crossfade(500)
                }
                itemFoodsTitle.text = item.strMeal
                //category
                if(item.strCategory.isNullOrEmpty().not()){
                    itemFoodsCategory.text = item.strCategory
                    itemFoodsCategory.visibility = View.VISIBLE
                }
                else
                {
                    itemFoodsCategory.visibility = View.GONE
                }
                //area
                if(item.strArea.isNullOrEmpty().not()){
                    itemFoodsArea.text = item.strArea
                    itemFoodsArea.visibility = View.VISIBLE
                }
                else
                {
                    itemFoodsArea.visibility = View.GONE
                }
                //source
                if (item.strSource != null){
                    itemFoodsCount.visibility = View.VISIBLE
                }
                else
                {
                    itemFoodsCount.visibility = View.GONE
                }

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }

            }
        }

    }
}