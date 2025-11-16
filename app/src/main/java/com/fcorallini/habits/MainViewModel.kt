package com.fcorallini.habits

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fcorallini.authentication_domain.usecases.GetUserIdUseCase
import com.fcorallini.authentication_domain.usecases.LogoutUseCase
import com.fcorallini.onboarding_domain.usecases.HasSeenOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val hasSeenOnboardingUseCase: HasSeenOnboardingUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    var hasSeenOnboarding : Boolean by mutableStateOf(hasSeenOnboardingUseCase())
        private set
    var isLoggedIn : Boolean by mutableStateOf(getUserIdUseCase() != null)
        private set

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}