package com.example.myfavorplaces.ui.utils

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfavorplaces.data.Category
import com.example.myfavorplaces.data.CategoryType
import com.example.myfavorplaces.ui.MyFavorAppHomeScreen
import com.example.myfavorplaces.ui.MyFavorAppViewModel

@Composable
fun MyFavorApp(
	windowSize: WindowWidthSizeClass,
	modifier: Modifier = Modifier,
)
{
	val navigationType: MyFavorAppNavigationType
	val contentType: MyFavorAppContentType
	val viewModel: MyFavorAppViewModel = viewModel()
	val myFavorUiState = viewModel.uiState.collectAsState().value
	
	when (windowSize) {
		WindowWidthSizeClass.Compact -> {
			navigationType = MyFavorAppNavigationType.BOTTOM_NAVIGATION
			contentType = MyFavorAppContentType.LIST_ONLY
		}
		WindowWidthSizeClass.Medium -> {
			navigationType = MyFavorAppNavigationType.NAVIGATION_RAIL
			contentType = MyFavorAppContentType.LIST_ONLY
		}
		WindowWidthSizeClass.Expanded -> {
			navigationType = MyFavorAppNavigationType.PERMANENT_NAVIGATION_DRAWER
			contentType = MyFavorAppContentType.LIST_AND_DETAIL
		}
		else -> {
			navigationType = MyFavorAppNavigationType.BOTTOM_NAVIGATION
			contentType = MyFavorAppContentType.LIST_ONLY
		}
	}
	MyFavorAppHomeScreen(
		navigationType = navigationType,
		contentType = contentType,
		myFavorUiState = myFavorUiState,
		onTabPressed = { categoryType: CategoryType ->
			viewModel.updateCurrentCategory(categoryType = categoryType)
			viewModel.resetHomeScreenStates()
		},
		onCategoryCardPressed = { cat: Category ->
			viewModel.updateDetailsScreenStates(
				cat = cat
			)
		},
		onDetailScreenBackPressed = {
			viewModel.resetHomeScreenStates()
		},
		modifier = modifier,
		)
}