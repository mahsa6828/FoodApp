package com.example.foodapp.ui.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodapp.R
import com.example.foodapp.data.model.Category
import com.example.foodapp.data.model.Meal
import com.example.foodapp.databinding.ItemCategoriesBinding
import javax.inject.Inject

class CategoryAdapter @Inject constructor() : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    private lateinit var binding : ItemCategoriesBinding
    private var categoryList = emptyList<Category>()
    private var selectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder()
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categoryList[position])
        holder.setIsRecyclable(false)
    }



    inner class CategoryHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Category){
            binding.apply {
               itemCategoriesImg.load(item.strCategoryThumb)
               itemCategoriesTxt.text = item.strCategory

                root.setOnClickListener {
                    selectedItem = adapterPosition
                    notifyDataSetChanged()
                    onItemClickListener?.let {
                        it(item)
                    }
                }
                //Change color
                if (selectedItem == adapterPosition) {
                    root.setBackgroundResource(R.drawable.bg_rounded_selcted)
                } else {
                    root.setBackgroundResource(R.drawable.bg_rounded_white)
                }
            }
        }

    }

    private var onItemClickListener: ((Category) -> Unit)? = null

    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<Category>) {
//        val moviesDiffUtil = MoviesDiffUtils(categoryList, data)
//        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        categoryList = data
//        diffUtils.dispatchUpdatesTo(this)
    }

//    class MoviesDiffUtils(private val oldItem: List<Category>, private val newItem: List<Category>) : DiffUtil.Callback() {
//        override fun getOldListSize(): Int {
//            return oldItem.size
//        }
//
//        override fun getNewListSize(): Int {
//            return newItem.size
//        }
//
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return oldItem[oldItemPosition] === newItem[newItemPosition]
//        }
//
//        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return oldItem[oldItemPosition] === newItem[newItemPosition]
//        }
//    }
}