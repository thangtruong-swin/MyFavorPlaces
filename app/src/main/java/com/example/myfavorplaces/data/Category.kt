package com.example.myfavorplaces.data

import androidx.annotation.DrawableRes

data class Category(
	val id: Int,
	val name: String,
	val address: String,
	val description: String,
	val categoryType: CategoryType = CategoryType.CoffeeShops,
	val categoryInfo: String? = null,
	@DrawableRes val avatar: Int
)
