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
package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Puppy
import com.example.androiddevchallenge.model.SampleData
import com.example.androiddevchallenge.ui.GenderIcon
import com.example.androiddevchallenge.ui.PuppyImage
import com.example.androiddevchallenge.ui.SurfaceColorFromImage
import com.example.androiddevchallenge.ui.quantityStringResource
import com.example.androiddevchallenge.ui.theme.MyTheme

@Preview
@Composable
fun PuppyItemListPreview() {
    MyTheme {
        PuppyItemList(SampleData.puppies, onPuppySelected = {})
    }
}

@Preview
@Composable
fun PuppyItemPreview() {
    MyTheme {
        PuppyItem(SampleData.puppies[0], onSelected = {})
    }
}

@Composable
fun OverviewScreen(puppies: List<Puppy>, onPuppySelected: (id: Int) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
            )
        },
        content = {
            PuppyItemList(puppies, onPuppySelected)
        }
    )
}

@Composable
private fun PuppyItemList(
    puppies: List<Puppy>,
    onPuppySelected: (id: Int) -> Unit
) {
    LazyColumn {
        items(puppies) { puppy ->
            PuppyItem(
                puppy,
                Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                onSelected = {
                    onPuppySelected.invoke(puppy.id)
                }
            )
        }
    }
}

@Composable
private fun PuppyItem(puppy: Puppy, modifier: Modifier = Modifier, onSelected: () -> Unit) {
    SurfaceColorFromImage(puppy.image) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onSelected)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PuppyImage(
                    puppy,
                    Modifier
                        .clip(MaterialTheme.shapes.small)
                        .size(128.dp)
                )
                PuppyDescription(puppy, Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
private fun PuppyDescription(puppy: Puppy, modifier: Modifier = Modifier) {
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = puppy.name,
                style = MaterialTheme.typography.h5,
            )
            Spacer(Modifier.width(8.dp))
            GenderIcon(puppy.gender, Modifier.size(24.dp))
        }
        Text(
            text = quantityStringResource(
                R.plurals.puppy_age,
                puppy.age
            ),
            style = MaterialTheme.typography.subtitle1,
        )
        Text(
            text = puppy.breed,
            style = MaterialTheme.typography.subtitle1,
        )
    }
}
