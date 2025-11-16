package com.fcorallini.onboarding_presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.fcorallini.onboarding_presentation.components.OnboardingPager

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinish : () -> Unit
) {
    LaunchedEffect(viewModel.hasSeenOnboarding) {
        if(viewModel.hasSeenOnboarding) {
            onFinish()
        }
    }
    val pages = listOf(
        OnboardingPagerInfo(
            title = "Welcome to\nMonumental Habits",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.illustration
        ),
        OnboardingPagerInfo(
            title = "Create new habits easily",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.habits
        ),
        OnboardingPagerInfo(
            title = "Keep track of your progress",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.progress
        ),
        OnboardingPagerInfo(
            title = "Join a supportive community",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.community
        )
    )
    OnboardingPager(pages, {
        viewModel.completeOnboarding()
        onFinish()
    })
}