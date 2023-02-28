package com.example.myfavorplaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

import androidx.compose.ui.tooling.preview.Preview
import com.example.myfavorplaces.ui.theme.MyFavorPlacesTheme
import com.example.myfavorplaces.ui.utils.MyFavorApp

class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		setContent {
			MyFavorPlacesTheme {
				val windowSize = calculateWindowSizeClass(this)
				// A surface container using the 'background' color from the theme
					MyFavorApp(
						windowSize = windowSize.widthSizeClass
					)
			}
		}
	}
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun DefaultPreview() {
	MyFavorPlacesTheme {
		MyFavorApp(
			windowSize = WindowWidthSizeClass.Expanded,
			)
	}
}