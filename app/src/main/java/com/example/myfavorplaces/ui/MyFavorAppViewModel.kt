package com.example.myfavorplaces.ui

import androidx.lifecycle.ViewModel
import com.example.myfavorplaces.data.Category
import com.example.myfavorplaces.data.CategoryType
import com.example.myfavorplaces.data.local.LocalCategoriesDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyFavorAppViewModel: ViewModel() {
	
	private val _uiState = MutableStateFlow(MyFavorAppUiState())
	val uiState: StateFlow<MyFavorAppUiState> = _uiState
	
	init {
		initializeUIState()
	}
	
	private fun initializeUIState() {
		var categories: Map<CategoryType, List<Category>> =
			LocalCategoriesDataProvider.allCategories.groupBy { it.categoryType }
		_uiState.value =
			MyFavorAppUiState(
				categories = categories,
			)
	}
	
	fun updateDetailsScreenStates(cat: Category) {
		_uiState.update {
			it.copy(
				isShowingHomepage = false
			)
		}
	}
	
	fun resetHomeScreenStates() {
		_uiState.update {
			it.copy(
				isShowingHomepage = true
			)
		}
	}
	
	fun updateCurrentCategory(categoryType: CategoryType) {
		_uiState.update {
			it.copy(
				currentCategory = categoryType
			)
		}
	}
}