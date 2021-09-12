/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.utils

enum class Rating(val value: String) {
    G("g"), //Contains images that are broadly accepted as appropriate and commonly witnessed by people in a public environment.
    PG("pg"), //Contains images that are commonly witnessed in a public environment, but not as broadly accepted as appropriate.
    PG13("pg-13"), // Contains images that are typically not seen unless sought out, but still commonly witnessed.
    R("r") // Contains images that are typically not seen unless sought out and could be considered alarming if witnessed.
}