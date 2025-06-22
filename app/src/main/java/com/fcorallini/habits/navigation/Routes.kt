package com.fcorallini.habits.navigation

import kotlinx.serialization.Serializable

interface Routes {
    @Serializable
    object Login : Routes

    @Serializable
    object Onboarding : Routes

    @Serializable
    object Home : Routes

    @Serializable
    object SignUp : Routes

    @Serializable
    data class Detail(
        val habitId : String?
    ) : Routes

    @Serializable
    object Settings : Routes
}