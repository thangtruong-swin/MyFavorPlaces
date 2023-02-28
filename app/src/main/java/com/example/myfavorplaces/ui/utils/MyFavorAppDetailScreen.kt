package com.example.myfavorplaces.ui.utils


import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myfavorplaces.R
import com.example.myfavorplaces.data.Category
import com.example.myfavorplaces.data.local.LocalCategoriesDataProvider
import com.example.myfavorplaces.ui.MyFavorAppUiState

@Composable
fun MyFavorAppDetailScreen(
	myFavorUiState: MyFavorAppUiState,
	modifier: Modifier = Modifier,
	onBackPressed: () -> Unit = {},
	isFullScreen: Boolean = false,
	) {
	
	BackHandler {
		onBackPressed()
	}
	Log.i("MyFavorAppDetailScreen", "YOU ARE CALLED")
	
	LazyColumn(
		modifier = modifier
			.fillMaxSize()
			.background(color = MaterialTheme.colorScheme.inverseOnSurface)
			.padding(top = 24.dp)
	)
	{
		
		item {
			if (isFullScreen) {
				MyFavorDetailsScreenTopBar(onBackPressed, myFavorUiState)
			}
			Log.i("MyFavorListAndDetailContent-MyFavorAppDetailScreen",
				myFavorUiState.tempName.toString())
			
			CategoryDetailsCard(
				item = myFavorUiState.tempName?.let {
					LocalCategoriesDataProvider.getSingleDetailCategoryByName(
						it
					)
				},
				isFullScreen = isFullScreen,
				modifier = if(isFullScreen)
					Modifier.padding(horizontal = 16.dp)
				else
				Modifier.padding(end=16.dp)

			)
		}
	}
	
}

@Composable
fun CategoryDetailsCard(
	item: Category?,
	isFullScreen: Boolean,
	modifier: Modifier
) {
	val context = LocalContext.current
	val displayToast = { text: String ->
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
	}
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.padding(20.dp)
	) {
		Card() {
			Row {
				if (item != null) {
					Image(
						painter = painterResource(item.avatar),
						contentDescription = item.name,
						modifier = Modifier
							.fillMaxWidth()
							.height(180.dp),
						contentScale = ContentScale.Crop,
					)
				}
			}
		}
		
	}
	
	Card(
		modifier = modifier,
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
	) {
		
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(20.dp)
		) {
			if (!isFullScreen) {
				if (item != null) {
					Text(
						text = item.name,
						style = MaterialTheme.typography.titleLarge,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
					)
				}
			} else {
				Spacer(modifier = Modifier.height(12.dp))
			}
//			if (item != null) {
//				Text(
//					text = item.name,
//					style = MaterialTheme.typography.titleLarge,
//					color = MaterialTheme.colorScheme.onSurfaceVariant,
//				)
//			}
			Spacer(modifier = Modifier.height(12.dp))
			if (item != null) {
				Text(
					text = item.address,
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
				)
			}
			Spacer(modifier = Modifier.height(12.dp))
			if (item != null) {
				Text(
					text = item.description,
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					textAlign = TextAlign.Justify
				)
			}
		}
	}
}

@Composable
fun MyFavorDetailsScreenTopBar(
	onBackPressed: () -> Unit,
	myFavorUiState: MyFavorAppUiState,
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(bottom = 24.dp),
		verticalAlignment = Alignment.CenterVertically,
	){
		IconButton(
			onClick = onBackPressed,
			modifier = Modifier
				.padding(horizontal = 16.dp)
				.background(MaterialTheme.colorScheme.surface, shape = CircleShape),
		) {
			Icon(
				imageVector = Icons.Default.ArrowBack,
				contentDescription = stringResource(R.string.navigation_back)
			)
		}
		Row(
			horizontalArrangement = Arrangement.Center,
			modifier = Modifier
				.fillMaxWidth()
				.padding(end = 40.dp)
		) {
			Text(
				text = myFavorUiState.currentCategory.toString(),
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				fontSize = 24.sp
				)
		}
	}
}
