package com.fcorallini.habits.home

import android.Manifest
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.fcorallini.habits.MainActivity
import com.fcorallini.habits.home.data.repository.FakeHomeRepository
import com.fcorallini.habits.home.domain.detail.usecases.GetHabitByIdUseCase
import com.fcorallini.habits.home.domain.detail.usecases.InsertHabitUseCase
import com.fcorallini.habits.home.domain.home.usecases.CompleteHabitUseCase
import com.fcorallini.habits.home.domain.home.usecases.GetHabitsForDateUseCase
import com.fcorallini.habits.home.domain.home.usecases.SyncHabitsUseCase
import com.fcorallini.habits.home.presentation.detail.DetailScreen
import com.fcorallini.habits.home.presentation.detail.DetailViewModel
import com.fcorallini.habits.home.presentation.home.HomeScreen
import com.fcorallini.habits.home.presentation.home.HomeViewModel
import com.fcorallini.habits.navigation.Routes
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@HiltAndroidTest
class CreateHabitE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val notificationPermissions = GrantPermissionRule.grant(Manifest.permission.POST_NOTIFICATIONS)

    private lateinit var homeRepository : FakeHomeRepository
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var detailViewModel : DetailViewModel
    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        homeRepository = FakeHomeRepository()
        homeViewModel = HomeViewModel(
            completeHabitUseCase = CompleteHabitUseCase(homeRepository),
            getHabitsForDateUseCase = GetHabitsForDateUseCase(homeRepository),
            syncHabitsUseCase = SyncHabitsUseCase(homeRepository)
        )
        detailViewModel = DetailViewModel(
            savedStateHandle = SavedStateHandle(),
            insertHabitUseCase = InsertHabitUseCase(homeRepository),
            getHabitByIdUseCase = GetHabitByIdUseCase(homeRepository)
        )

        composeRule.activity.setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.Home) {
                composable<Routes.Home> {
                    HomeScreen(
                        onSettings = {
                            navController.navigate(Routes.Settings)
                        },
                        onNewHabit = {
                            navController.navigate(Routes.Detail(null))
                        },
                        onEditHabit = { habit ->
                            navController.navigate(Routes.Detail(habit.id))
                        },
                        viewModel = homeViewModel
                    )
                }
                composable<Routes.Detail> {
                    val detailRoute: Routes.Detail = it.toRoute()
                    DetailScreen(
                        onBack = {
                            navController.popBackStack()
                        },
                        onSave = {
                            navController.popBackStack()
                        },
                        habitId = detailRoute.habitId,
                        viewModel = detailViewModel
                    )
                }
            }
        }
    }

    @Test
    fun createHabit() {
        val habitToCreate = "Go to gym"
        val today = LocalDate.now().dayOfWeek
        composeRule.onNodeWithText("Home").assertIsDisplayed()
        composeRule.onNodeWithText(habitToCreate).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Create habit").performClick()
        assert(navController.currentDestination?.route?.contains("Detail") ?: false)
        composeRule.onNodeWithContentDescription("Enter habit name").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Enter habit name").performClick().performTextInput(habitToCreate)
        composeRule.onNodeWithContentDescription(today.name).performClick()
        composeRule.onNodeWithContentDescription("Enter habit name").performImeAction()
        composeRule.onNodeWithText("Home").assertIsDisplayed()
        composeRule.onNodeWithText(habitToCreate).assertIsDisplayed()
    }
}