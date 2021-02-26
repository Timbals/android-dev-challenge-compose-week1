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

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Puppy
import com.example.androiddevchallenge.model.SampleData
import com.example.androiddevchallenge.ui.PuppyImage
import com.example.androiddevchallenge.ui.quantityStringResource
import com.example.androiddevchallenge.ui.theme.MyTheme

@Preview
@Composable
fun DetailScreenPreview() {
    MyTheme {
        DetailScreen(SampleData.puppies[0])
    }
}

@Composable
fun DetailScreen(puppy: Puppy) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Details about ${puppy.name}")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            val context = LocalContext.current

            ExtendedFloatingActionButton(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.paw),
                        contentDescription = stringResource(R.string.paw_description)
                    )
                },
                text = {
                    Text(stringResource(R.string.puppy_adopt, puppy.name))
                },
                onClick = {
                    Toast.makeText(context, R.string.not_implemented, Toast.LENGTH_SHORT).show()
                }
            )
        },
        content = {
            Column {
                PuppyImage(
                    puppy,
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.0f)
                )

                Text(
                    text = puppy.name,
                    style = MaterialTheme.typography.h4,
                )
                Text(
                    text = quantityStringResource(
                        id = R.plurals.puppy_age,
                        puppy.age
                    ),
                    style = MaterialTheme.typography.h5,
                )
            }
        }
    )
}
