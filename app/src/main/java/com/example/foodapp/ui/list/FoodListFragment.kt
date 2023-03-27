package com.example.foodapp.ui.list

import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.drawable.CrossfadeDrawable
import coil.load
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentFoodListBinding
import com.example.foodapp.ui.list.adapter.CategoryAdapter
import com.example.foodapp.ui.list.adapter.FoodsListAdapter
import com.example.foodapp.utils.CheckConnection
import com.example.foodapp.utils.di.MyResponse
import com.example.foodapp.utils.isVisible
import com.example.foodapp.utils.setUpRecyclerView
import com.example.foodapp.utils.setUpSpinnerData
import com.example.foodapp.viewmodels.FoodListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FoodListFragment : Fragment() {
    private var _binding : FragmentFoodListBinding? = null
    private val binding get() = _binding

    private val viewModel:FoodListViewModel by viewModels()

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    @Inject
    lateinit var connection: CheckConnection

    @Inject
    lateinit var foodsListAdapter: FoodsListAdapter

    enum class PageState {EMPTY,NETWORK,NONE}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodListBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel.loadRandomFood()
            viewModel.randomFood.observe(viewLifecycleOwner){
                 it[0].let {meal ->
                     imgHeader.load(meal.strMealThumb){
                         crossfade(true)
                         crossfade(500)
                     }
                 }
            }
            //spinner
            viewModel.loadFilterList()
            viewModel.filterList.observe(viewLifecycleOwner){
                filterSpiner.setUpSpinnerData(it){selectItem ->
                    viewModel.loadFoodList(selectItem)
                }
            }
            //categories
            viewModel.loadCategories()
            viewModel.categoriesFood.observe(viewLifecycleOwner){
                when(it.status){
                    MyResponse.Status.LOADING -> {
                        homeCategoryLoading.isVisible(true,categoryList)
                    }
                    MyResponse.Status.SUCCESS -> {
                        homeCategoryLoading.isVisible(false,categoryList)
                        categoryAdapter.setData(it.data!!.categories)
                        categoryList.setUpRecyclerView(LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false),categoryAdapter)
                    }
                    MyResponse.Status.ERROR -> {
                        homeCategoryLoading.isVisible(false,categoryList)
                    }

                }
            }
            categoryAdapter.setOnItemClickListener {
                viewModel.loadFoodByCategory(it.strCategory)
                Toast.makeText(requireContext(),"yes",Toast.LENGTH_SHORT).show()
            }
            //loading
            viewModel.loading.observe(viewLifecycleOwner){
                homeCategoryLoading.isVisible(it,categoryList)
                homeFoodsLoading.isVisible(it,foodsList)
            }
            //food list
            viewModel.loadFoodList("A")
            viewModel.foodList.observe(viewLifecycleOwner){
                when(it.status){
                    MyResponse.Status.LOADING -> {
                        homeFoodsLoading.isVisible(true,foodsList)
                    }
                    MyResponse.Status.SUCCESS -> {
                        homeFoodsLoading.isVisible(false,foodsList)
                        if (it.data?.meals != null){
                            if (it.data.meals.isNotEmpty()){
                                checkConnectionOrEmpty(false,PageState.NONE)
                                foodsListAdapter.setData(it.data.meals)
                                foodsList.setUpRecyclerView(LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false),foodsListAdapter)
                            }
                        }
                        else{
                            checkConnectionOrEmpty(true,PageState.EMPTY)
                        }
                    }
                    MyResponse.Status.ERROR -> {
                        homeFoodsLoading.isVisible(false,foodsList)
                    }

                }
            }
            // search food
            edtSearch.addTextChangedListener {
                if (it.toString().length>2){
                    viewModel.loadFoodBySearch(it.toString())
                }
            }
            //click food items
            foodsListAdapter.setOnItemClickListener {
                val direction = FoodListFragmentDirections.actionHomeFragmentToDetailFragment(it.idMeal!!.toInt())
                findNavController().navigate(direction)
            }
            //internet
            connection.observe(viewLifecycleOwner){
                if (it){
                    checkConnectionOrEmpty(false,PageState.NONE)
                }
                else{
                    checkConnectionOrEmpty(true,PageState.NETWORK)
                }
            }
        }
    }

    private fun checkConnectionOrEmpty(isShownError:Boolean,state:PageState){
        binding?.apply {
            if (isShownError){
                homeDisLay.isVisible(true,contentLayout)
                when(state){
                   PageState.EMPTY -> {
                       statusLay.disImg.setImageResource(R.drawable.box)
                       statusLay.disTxt.text = "list is empty"
                   }
                   PageState.NETWORK -> {
                       statusLay.disImg.setImageResource(R.drawable.disconnect)
                       statusLay.disTxt.text = "check your internet connection"
                   }
                   else -> {}
                }
            }
            else
            {
                homeDisLay.isVisible(false,contentLayout)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

}