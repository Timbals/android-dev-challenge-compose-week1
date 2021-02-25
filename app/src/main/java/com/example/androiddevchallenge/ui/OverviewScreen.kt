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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Puppy
import com.example.androiddevchallenge.model.SampleData
import com.example.androiddevchallenge.quantityStringResource
import com.example.androiddevchallenge.ui.theme.MyTheme

@Preview
@Composable
fun OverviewScreenPreview() {
    MyTheme {
        OverviewScreen(SampleData.puppies, onPuppySelected = {})
    }
}

@Composable
fun OverviewScreen(puppies: List<Puppy>, onPuppySelected: (id: Int) -> Unit) {
    LazyColumn {
        items(puppies) { puppy ->
            PuppyItem(
                puppy,
                onSelected = {
                    onPuppySelected.invoke(puppy.id)
                }
            )
        }
    }
}

@Composable
fun PuppyItem(puppy: Puppy, onSelected: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = puppy.image),
                contentDescription = stringResource(
                    id = R.string.puppy_image_description,
                    puppy.name
                ),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(128.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = puppy.name,
                    style = MaterialTheme.typography.h5,
                )
                Text(
                    text = quantityStringResource(
                        id = R.plurals.puppy_age,
                        puppy.age
                    ),
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    }
}
