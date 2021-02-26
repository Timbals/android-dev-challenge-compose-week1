/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

object ColorCache {
    val dominantCache: MutableMap<Int, Color> = mutableMapOf()
    val onDominantCache: MutableMap<Int, Color> = mutableMapOf()
}

@Composable
fun SurfaceColorFromImage(
    @DrawableRes image: Int,
    content: @Composable () -> Unit
) {
    // use starting colors from the cache or the theme on a cache miss
    val defaultDominantColor = ColorCache.dominantCache[image] ?: MaterialTheme.colors.surface
    val onDefaultDominantColor = ColorCache.onDominantCache[image] ?: MaterialTheme.colors.onSurface

    var dominantColor by remember { mutableStateOf(defaultDominantColor) }
    var onDominantColor by remember { mutableStateOf(onDefaultDominantColor) }

    // check if there was a cache miss
    if (
        !ColorCache.dominantCache.containsKey(image) ||
        !ColorCache.onDominantCache.containsKey(image)
    ) {
        val resources = LocalContext.current.resources

        // asynchronously calculate the dominant color in the given image
        LaunchedEffect(image) {
            calculateDominantSwatchInImage(image, resources)?.let { swatch ->
                dominantColor = Color(swatch.rgb)
                onDominantColor = Color(swatch.bodyTextColor)

                ColorCache.dominantCache[image] = dominantColor
                ColorCache.onDominantCache[image] = onDominantColor
            }
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
