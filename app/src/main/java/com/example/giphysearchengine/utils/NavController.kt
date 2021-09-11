package com.example.giphysearchengine.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavController.safeNavigate(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val destinationId =
        (currentDestination?.getAction(resId)?.destinationId ?: graph.getAction(resId)) ?: resId

    graph.find { it.id == destinationId }
        ?.let {
            navigate(resId, args, navOptions, navigatorExtras)
        }
}

fun NavController.safeNavigate(
    directions: NavDirections,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    safeNavigate(directions.actionId, directions.arguments, navOptions, navigatorExtras)
}
