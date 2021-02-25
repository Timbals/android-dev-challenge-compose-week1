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
package com.example.androiddevchallenge.model

import com.example.androiddevchallenge.R

object SampleData {
    private var id = 1

    val puppies = listOf(
        Puppy(
            id++,
            "Max",
            3,
            Gender.MALE,
            "Siberian Husky",
            R.drawable.puppy1 // https://www.pexels.com/photo/black-and-white-siberian-husky-puppy-on-brown-grass-field-3726314/
        ),
        Puppy(
            id++,
            "Fikri",
            1,
            Gender.FEMALE,
            "Dachshund",
            R.drawable.puppy2 // https://www.pexels.com/photo/adorable-little-dachshund-puppy-on-comfy-couch-4490129/
        ),
        Puppy(
            id++,
            "Trixie",
            4,
            Gender.FEMALE,
            "Border Collie",
            R.drawable.puppy3 // https://www.pexels.com/photo/black-and-white-border-collie-puppy-3908806/
        ),
        Puppy(
            id++,
            "Butch",
            4,
            Gender.MALE,
            "Shih Tzu",
            R.drawable.puppy4 // https://www.pexels.com/photo/adult-white-and-brown-shih-tzu-3361739/
        )
    )
}
