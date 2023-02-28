package com.example.myfavorplaces.ui

import com.example.myfavorplaces.data.Category
import com.example.myfavorplaces.data.CategoryType

data class MyFavorAppUiState(
	val categories: Map<CategoryType, List<Category>> = emptyMap(),
	val currentCategory: CategoryType = CategoryType.CoffeeShops,
	var isShowingHomepage: Boolean = true,
	var tempName: String? = "",
)
{
	val currentFavorsCategory: List<Category> by lazy { categories[currentCategory]!! }
}