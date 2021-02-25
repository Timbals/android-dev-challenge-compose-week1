package com.example.androiddevchallenge.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.model.SampleData
import com.example.androiddevchallenge.ui.screens.DetailScreen
import com.example.androiddevchallenge.ui.screens.OverviewScreen
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun App() {
    val navController = rememberNavController()

    // use sample data because this is an UI showcase project
    val puppies = SampleData.puppies

    MyTheme {
        NavHost(navController = navController, startDestination = "overview") {
            composable("overview") {
                OverviewScreen(
                    puppies,
                    onPuppySelected = { puppyId ->
                        navController.navigate("detail/$puppyId")
                    }
                )
            }
            composable(
                "detail/{puppyId}",
                arguments = listOf(navArgument("puppyId") { type = NavType.IntType })
            ) { backStackEntry ->
                val puppy = puppies.find {
                    it.id == backStackEntry.arguments?.get("puppyId") ?: -1
                }
                if (puppy != null) {
                    DetailScreen(puppy)
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        App()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        App()
    }
}
