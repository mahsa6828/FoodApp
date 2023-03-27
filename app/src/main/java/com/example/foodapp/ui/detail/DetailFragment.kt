package com.example.foodapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.foodapp.R
import com.example.foodapp.data.database.FoodEntity
import com.example.foodapp.databinding.FragmentDetailBinding
import com.example.foodapp.ui.detail.player.PlayerActivity
import com.example.foodapp.ui.list.FoodListFragment
import com.example.foodapp.utils.CheckConnection
import com.example.foodapp.utils.VIDEO_ID
import com.example.foodapp.utils.di.MyResponse
import com.example.foodapp.utils.isVisible
import com.example.foodapp.viewmodels.FoodDetailViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding

    private val args:DetailFragmentArgs by navArgs()
    private var foodId = 0

    @Inject
    lateinit var connection : CheckConnection

    @Inject
    lateinit var entity : FoodEntity

    private val viewModel:FoodDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View{
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding!!.root
       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            foodId = args.foodId
            //back
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
            viewModel.getDetailFood(foodId)
            viewModel.detailFood.observe(viewLifecycleOwner){
                when(it.status){
                    MyResponse.Status.LOADING -> {
                        detailLoading.isVisible(true,detailContentLay)
                    }
                    MyResponse.Status.SUCCESS -> {
                        detailLoading.isVisible(false,detailContentLay)
                        //set data
                        it.data?.meals?.get(0)?.let {itMeal->
                            //set entity
//                            entity.id = itMeal.idMeal!!.toInt()
//                            entity.img = itMeal.strMealThumb.toString()
//                            entity.title = itMeal.strMeal.toString()

                            foodCoverImage.load(itMeal.strMealThumb){
                                crossfade(true)
                                crossfade(500)
                            }
                            foodCategoryTxt.text= itMeal.strCategory
                            foodAreaTxt.text= itMeal.strArea
                            foodTitleTxt.text = itMeal.strMeal
                            foodDescTxt.text = itMeal.strInstructions
                            //source
                            if (itMeal.strSource != null){
                                foodSourceImg.visibility = View.VISIBLE
                                foodSourceImg.setOnClickListener {
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itMeal.strSource)))
                                }
                            }
                            else
                            {
                                foodSourceImg.visibility = View.GONE
                            }
                            //Youtube
                            if (itMeal.strYoutube != null){
                                foodPlayImg.visibility = View.VISIBLE
                                foodPlayImg.setOnClickListener {
                                    val videoId = itMeal.strYoutube.split("=")[1]
                                    Intent(requireContext(),PlayerActivity::class.java).also {itIntent ->
                                        itIntent.putExtra(VIDEO_ID,videoId)
                                        startActivity(itIntent)
                                    }

                                }
                            }
                            else
                            {
                                foodPlayImg.visibility = View.GONE
                            }
                            //Json Array
                            val jsonData = JSONObject(Gson().toJson(it.data))
                            val meals = jsonData.getJSONArray("meals")
                            val meal = meals.getJSONObject(0)
                            //Ingredient
                            for (i in 1..15) {
                                val ingredient = meal.getString("strIngredient$i")
                                if (ingredient.isNullOrEmpty().not()) {
                                    ingredientsTxt.append("$ingredient\n")
                                }
                            }
                            //Measure
                            for (i in 1..15) {
                                val measure = meal.getString("strMeasure$i")
                                if (measure.isNullOrEmpty().not()) {
                                    measureTxt.append("$measure\n")
                                }
                            }
                        }

                    }
                    MyResponse.Status.ERROR -> {
                        detailLoading.isVisible(true,detailContentLay)

                    }
                }
            }
            viewModel.isFavorite.observe(viewLifecycleOwner){
                if (it)
                {
                   imgFav.setColorFilter(ContextCompat.getColor(requireContext(),R.color.tartOrange))
                }
                else
                {
                    imgFav.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
                }
            }
            imgFav.setOnClickListener {
                viewModel.isExists(foodId,entity)
            }
            //internet
            connection.observe(viewLifecycleOwner){
                if (it){
                    checkConnectionOrEmpty(false,FoodListFragment.PageState.NONE)
                }
                else
                {
                    checkConnectionOrEmpty(true,FoodListFragment.PageState.NETWORK)
                }
            }

        }
    }
    private fun checkConnectionOrEmpty(isShownError:Boolean,state: FoodListFragment.PageState){
        binding?.apply {
            if (isShownError){
                homeDisLay.isVisible(true,detailContentLay)
                when(state){
                    FoodListFragment.PageState.EMPTY -> {
                        statusLay.disImg.setImageResource(R.drawable.box)
                        statusLay.disTxt.text = "list is empty"
                    }
                    FoodListFragment.PageState.NETWORK -> {
                        statusLay.disImg.setImageResource(R.drawable.disconnect)
                        statusLay.disTxt.text = "check your internet connection"
                    }
                    else -> {}
                }
            }
            else
            {
                homeDisLay.isVisible(false,detailContentLay)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

}