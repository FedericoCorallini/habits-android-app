package com.fcorallini.habits.onboarding.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fcorallini.habits.R
import com.fcorallini.habits.core.presentation.HabitsButton
import com.fcorallini.habits.core.presentation.HabitsTitle
import com.fcorallini.habits.onboarding.presentation.OnboardingPagerInfo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(
    pages : List<OnboardingPagerInfo>,
    onFinish : () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(0)
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.background(Color.White)) {
        HorizontalPager(
            count = pages.size,
            state = pagerState
        ) { index ->
            val information = pages[index]
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                HabitsTitle(information.title)
                Spacer(Modifier.height(32.dp))
                Image(
                    painterResource(id = information.image),
                    contentDescription = "onboarding",
                    modifier = Modifier.aspectRatio(1f),
                    contentScale = ContentScale.FillHeight
                )
                Spacer(Modifier.height(32.dp))
                Text(information.subtitle.uppercase(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            if(pagerState.currentPage == pages.lastIndex){
                HabitsButton(text = "Get Started", isEnabled = true, modifier = Modifier.fillMaxWidth()) {
                    onFinish()
                }
            }else{
                TextButton(onClick = onFinish ) {
                    Text(text = "Skip")
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.tertiary,
                    inactiveColor = MaterialTheme.colorScheme.primary
                )
                TextButton({
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }) {
                    Text("Next", color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
    }
}