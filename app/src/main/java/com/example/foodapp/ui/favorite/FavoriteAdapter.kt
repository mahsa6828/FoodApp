package com.example.foodapp.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodapp.data.database.FoodEntity
import com.example.foodapp.databinding.ItemFoodsBinding
import javax.inject.Inject


class FavoriteAdapter @Inject constructor() : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    private lateinit var binding : ItemFoodsBinding
    private var favoriteList = emptyList<FoodEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        binding = ItemFoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteHolder()
    }

    override fun getItemCount(): Int = favoriteList.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    fun setData(list:List<FoodEntity>) {
        favoriteList = list
    }

    private var onItemClickListener: ((FoodEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (FoodEntity) -> Unit) {
        onItemClickListener = listener
    }

    inner class FavoriteHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:FoodEntity){
            binding.apply {
                itemFoodsImage.load(item.img){
                    crossfade(true)
                    crossfade(500)
                }
                itemFoodsTitle.text = item.title

                itemFoodsCategory.visibility = View.GONE
                itemFoodsArea.visibility = View.GONE
                itemFoodsCount.visibility = View.GONE

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }

            }
        }

    }
}