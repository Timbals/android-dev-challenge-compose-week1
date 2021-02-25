package com.example.androiddevchallenge.ui

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.palette.graphics.Palette
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Gender
import com.example.androiddevchallenge.model.Puppy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PuppyImage(puppy: Puppy, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = puppy.image),
        contentDescription = stringResource(
            id = R.string.puppy_image_description,
            puppy.name
        ),
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun GenderIcon(gender: Gender, modifier: Modifier = Modifier) {
    val genderImage = when (gender) {
        Gender.MALE -> R.drawable.male
        Gender.FEMALE -> R.drawable.female
    }
    val genderStringResourceId = when (gender) {
        Gender.MALE -> R.string.gender_male
        Gender.FEMALE -> R.string.gender_female
    }
    Icon(
        modifier = modifier,
        painter = painterResource(genderImage),
        contentDescription = stringResource(genderStringResourceId)
    )
}

@Composable
fun quantityStringResource(@PluralsRes id: Int, quantity: Int): String {
    return LocalContext.current.resources.getQuantityString(id, quantity, quantity)
}

@Composable
fun SurfaceColorFromImage(
    @DrawableRes image: Int,
    content: @Composable () -> Unit
) {
    // TODO use cached values as default
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember { mutableStateOf(defaultDominantColor) }
    val onDefaultDominantColor = MaterialTheme.colors.onSurface
    var onDominantColor by remember { mutableStateOf(onDefaultDominantColor) }

    val resources = LocalContext.current.resources

    // asynchronously calculate the dominant color in the given image
    LaunchedEffect(image) {
        calculateDominantSwatchInImage(image, resources)?.let { swatch ->
            dominantColor = Color(swatch.rgb)
            onDominantColor = Color(swatch.bodyTextColor)
        }
    }

    // override the surface color with the calculated dominant color
    // animate the color change after the dominant color has been calculated
    val colors = MaterialTheme.colors.copy(
        surface = animateColorAsState(dominantColor).value,
        onSurface = animateColorAsState(onDominantColor).value
    )

    MaterialTheme(colors = colors, content = content)
}

private suspend fun calculateDominantSwatchInImage(
    @DrawableRes image: Int,
    resources: Resources
): Palette.Swatch? =
    withContext(Dispatchers.Default) {
        val bitmap = BitmapFactory.decodeResource(resources, image)

        val palette = Palette.Builder(bitmap)
            .clearFilters()
            .maximumColorCount(8)
            .generate()

        palette.dominantSwatch
    }
