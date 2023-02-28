package com.example.myfavorplaces.ui

import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myfavorplaces.R
import com.example.myfavorplaces.data.Category
import com.example.myfavorplaces.data.CategoryType
import com.example.myfavorplaces.ui.utils.MyFavorAppContentType
import com.example.myfavorplaces.ui.utils.MyFavorAppDetailScreen
import com.example.myfavorplaces.ui.utils.MyFavorAppNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFavorAppHomeScreen(
	navigationType: MyFavorAppNavigationType,
	contentType: MyFavorAppContentType,
	myFavorUiState: MyFavorAppUiState,
	onTabPressed: (CategoryType) -> Unit,
	onCategoryCardPressed: (Category) -> Unit,
	onDetailScreenBackPressed: () -> Unit,
	modifier: Modifier = Modifier,
	) {
	val navigationItemContentList = listOf(
		NavigationItemContent(
			categoryType = CategoryType.CoffeeShops,
			icon = Icons.Default.Coffee,
			text = stringResource(id = R.string.CoffeeShops)
		),
		NavigationItemContent(
			categoryType = CategoryType.KidPlaces,
			icon = Icons.Default.Cookie,
			text = stringResource(id = R.string.KidPlaces)
		),
		NavigationItemContent(
			categoryType = CategoryType.Parks,
			icon = Icons.Default.AddChart,
			text = stringResource(id = R.string.Parks)
		),
		NavigationItemContent(
			categoryType = CategoryType.Restaurants,
			icon = Icons.Default.BreakfastDining,
			text = stringResource(id = R.string.Restaurants)
		),
			NavigationItemContent(
			categoryType = CategoryType.ShoppingCenters,
			icon = Icons.Default.DesignServices,
			text = stringResource(id = R.string.ShoppingCenters)
		),
	)
	if (navigationType == MyFavorAppNavigationType.PERMANENT_NAVIGATION_DRAWER) {
		PermanentNavigationDrawer(
			drawerContent = {
				PermanentDrawerSheet(Modifier.width(240.dp)) {
					NavigationDrawerContent(
						selectedDestination = myFavorUiState.currentCategory,
						onTabPressed = onTabPressed,
						navigationItemContentList = navigationItemContentList
					)
				}
			},
		) {
			Log.i("MyFavorListAndDetailContent", "PERMANENT_NAVIGATION_DRAWER")
			MyFavorAppContent(
				navigationType = navigationType,
				contentType = contentType,
				myFavorUiState = myFavorUiState,
				onTabPressed = onTabPressed,
				onCategoryCardPressed = onCategoryCardPressed,
				navigationItemContentList = navigationItemContentList,
				modifier = modifier
			)
		}
	}
	else {
		Log.i("MyFavorListAndDetailContent", "NOT IN PERMANENT_NAVIGATION_DRAWER")
		if(myFavorUiState.isShowingHomepage){
			MyFavorAppContent(
				myFavorUiState = myFavorUiState,
				navigationType = navigationType,
				contentType = contentType,
				modifier = modifier,
				onTabPressed = onTabPressed,
				navigationItemContentList = navigationItemContentList,
				onCategoryCardPressed = onCategoryCardPressed,
			)
		}
		else{
			MyFavorAppDetailScreen(
				myFavorUiState = myFavorUiState,
				isFullScreen = true,
				modifier = modifier,
				onBackPressed = onDetailScreenBackPressed
			)
		}
	}
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationDrawerContent(
	selectedDestination: CategoryType,
	onTabPressed: (CategoryType) -> Unit,
	navigationItemContentList: List<NavigationItemContent>,
	modifier: Modifier = Modifier
) {
	Column(
		modifier
			.wrapContentWidth()
			.fillMaxHeight()
			.background(MaterialTheme.colorScheme.inverseOnSurface)
			.padding(5.dp)
	){
//		NavigationDrawerHeader(modifier)
		for (navItem in navigationItemContentList) {
			NavigationDrawerItem(
				selected = selectedDestination == navItem.categoryType,
				label = {
					Text(
						text = navItem.text,
						modifier = Modifier.padding(horizontal = 8.dp)
					)
				},
				icon = {
					Icon(
						imageVector = navItem.icon,
						contentDescription = navItem.text
					)
				},
				colors = NavigationDrawerItemDefaults.colors(
					unselectedContainerColor = Color.Transparent
				),
				onClick = { onTabPressed(navItem.categoryType) }
			)
		}
	}
}

@Composable
private fun MyFavorAppContent(
	myFavorUiState: MyFavorAppUiState,
	navigationType: MyFavorAppNavigationType,
	contentType: MyFavorAppContentType,
	onTabPressed: ((CategoryType) -> Unit),
	navigationItemContentList: List<NavigationItemContent>,
	modifier: Modifier,
	onCategoryCardPressed: (Category) -> Unit,
	) {
	Row(modifier = modifier.fillMaxSize()){
		AnimatedVisibility(visible = navigationType == MyFavorAppNavigationType.NAVIGATION_RAIL) {
			val navigationRailContentDescription = stringResource(R.string.navigation_rail)
			NavigationRail(
				currentTab = myFavorUiState.currentCategory,
				onTabPressed = onTabPressed,
				navigationItemContentList = navigationItemContentList,
				modifier = Modifier.testTag(navigationRailContentDescription),
			)
		}
		Column(modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.onPrimary)
		)
		
		{
			if( contentType == MyFavorAppContentType.LIST_AND_DETAIL){
				Log.i("MyFavorListAndDetailContent", "you are in front of MyFavorListAndDetailContent")
				MyFavorListAndDetailContent(
					myFavorUiState = myFavorUiState,
					onCategoryCardPressed = onCategoryCardPressed,
					modifier = Modifier.weight(1f)
				)
			}
			else {
				Log.i("MyFavorListAndDetailContent", "you called MyFavorListOnlyContent")
				MyFavorListOnlyContent(
					myFavorUiState = myFavorUiState,
					onCategoryCardPressed = onCategoryCardPressed,
					modifier = Modifier.weight(1f),
					)
			}
			AnimatedVisibility(visible = navigationType == MyFavorAppNavigationType.BOTTOM_NAVIGATION) {
				BottomNavigationBar(
					currentTab = myFavorUiState.currentCategory,
					onTabPressed = onTabPressed,
					navigationItemContentList = navigationItemContentList,
				)
			}
		}
	}
}
@Composable
private fun NavigationRail(
	currentTab: CategoryType,
	onTabPressed: (CategoryType) -> Unit,
	navigationItemContentList: List<NavigationItemContent>,
	modifier: Modifier = Modifier
) {
	NavigationRail(modifier = modifier.fillMaxHeight()) {
		for (navItem in navigationItemContentList) {
			NavigationRailItem(
				selected = currentTab == navItem.categoryType,
				onClick = { onTabPressed(navItem.categoryType) },
				icon = {
					Icon(
						imageVector = navItem.icon,
						contentDescription = navItem.text
					)
				}
			)
		}
	}
}

@Composable
private fun BottomNavigationBar(
	currentTab: CategoryType,
	onTabPressed: (CategoryType) -> Unit,
	modifier: Modifier = Modifier,
	navigationItemContentList: List<NavigationItemContent>
) {
	NavigationBar(modifier = modifier.fillMaxWidth()) {
		for (navItem in navigationItemContentList) {
			NavigationBarItem(
				selected = currentTab == navItem.categoryType,
				onClick = { onTabPressed(navItem.categoryType) },
				icon = {
					Icon(
						imageVector = navItem.icon,
						contentDescription = navItem.text
					)
				}
			)
		}
	}
}

@Composable
fun MyFavorListAndDetailContent(
	myFavorUiState: MyFavorAppUiState,
	onCategoryCardPressed: (Category) -> Unit,
	modifier: Modifier
) {
	Log.i("MyFavorListAndDetailContent", "you are in MyFavorListAndDetailContent")
	val categories = myFavorUiState.currentFavorsCategory
	Row(modifier = modifier) {
		LazyColumn(
			modifier = Modifier
				.weight(1f)
//          .padding(end = 16.dp, top = 20.dp)
		
		) {
			items(categories, key = { cat -> cat.id }) { cat ->
				MyFavorsCategoryListItem(
					cat = cat,
					onCardClick = {
					myFavorUiState.tempName = cat.name
					onCategoryCardPressed(cat)
					Log.i("MyFavorListAndDetailContent", myFavorUiState.tempName.toString())
					}
				)
			}
		}
		myFavorUiState.isShowingHomepage = true
		val activity = LocalContext.current as Activity
		MyFavorAppDetailScreen(
			myFavorUiState = myFavorUiState,
			modifier = Modifier.weight(1f),
			onBackPressed = {activity.finish()}
		)
	}
}

@Composable
fun MyFavorListOnlyContent(
	myFavorUiState: MyFavorAppUiState,
	modifier: Modifier,
	onCategoryCardPressed: (Category) -> Unit,
)
{
	val itemsInCategory = myFavorUiState.currentFavorsCategory
	LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
		item {
			MyFavorHomeTopBar(modifier = Modifier.fillMaxWidth())
		}
		
		items(itemsInCategory, key = { cat -> cat.id }) { cat ->
			MyFavorsCategoryListItem(
				cat = cat,
				onCardClick = {
					myFavorUiState.tempName = cat.name
					onCategoryCardPressed(cat)
					Log.i("MyFavorsCategoryListItem", myFavorUiState.tempName.toString())
				}
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFavorsCategoryListItem(
	cat: Category,
	modifier: Modifier = Modifier,
	onCardClick: () -> Unit,
) {

	Card(
		modifier = modifier.padding(vertical = 4.dp),
		onClick = onCardClick
	) {
		Row (
			modifier = modifier
				.fillMaxWidth()
				.height(150.dp)
			){
			Image(
				painter = painterResource(cat.avatar),
				contentDescription = cat.name,
				modifier = modifier
					.width(120.dp)
					.fillMaxHeight(),
				contentScale = ContentScale.Crop,
			)
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(20.dp)
			) {
				Text(
					text = cat.name,
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSurface,
					modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
				)
				Text(
					text = cat.address,
					style = MaterialTheme.typography.bodyMedium,
					maxLines = 2,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					overflow = TextOverflow.Ellipsis
				)
				Text(
					text = cat.description,
					style = MaterialTheme.typography.bodyMedium,
					maxLines = 2,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					overflow = TextOverflow.Ellipsis,
					textAlign = TextAlign.Justify
				)
			}
		}
	}

}

@Composable
fun MyFavorHomeTopBar(modifier: Modifier) {
	Row(
		modifier = modifier
			.fillMaxSize()
			.padding(vertical = 8.dp)
	) {
		Text(
			text = stringResource(R.string.title),
			modifier = Modifier
				.padding(16.dp),
			color = MaterialTheme.colorScheme.primary,
			style = MaterialTheme.typography.headlineSmall,
		)
	}
}

data class NavigationItemContent(
	val categoryType: CategoryType,
	val icon: ImageVector,
	val text: String
)
