package com.mycaruae.app.feature.services

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.mycaruae.app.feature.insurance.InsuranceListContent
import com.mycaruae.app.feature.maintenance.MaintenanceHistoryContent
import com.mycaruae.app.ui.components.CocTopBar
import com.mycaruae.app.ui.theme.Amber80
import com.mycaruae.app.ui.theme.CocIcons
import com.mycaruae.app.ui.theme.Navy10
import com.mycaruae.app.ui.theme.Navy20
import com.mycaruae.app.ui.theme.Navy30
import kotlinx.coroutines.launch

@Composable
fun ServicesScreen(
    onNavigateToMaintenanceDetail: (String) -> Unit,
    onNavigateToMaintenanceAdd: () -> Unit,
    onNavigateToInsuranceAdd: () -> Unit,
) {
    val tabs = listOf("Maintenance", "Insurance")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (pagerState.currentPage) {
                        0 -> onNavigateToMaintenanceAdd()
                        1 -> onNavigateToInsuranceAdd()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
            ) {
                Icon(CocIcons.Add, contentDescription = "Add")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            // Premium header
            CocTopBar(title = "Services")

            // Tab row with navy theme
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Navy20,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Amber80,
                    )
                },
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                color = if (pagerState.currentPage == index) Amber80
                                else Color.White.copy(alpha = 0.5f),
                            )
                        },
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                when (page) {
                    0 -> MaintenanceHistoryContent(
                        onNavigateToDetail = onNavigateToMaintenanceDetail,
                    )
                    1 -> InsuranceListContent()
                }
            }
        }
    }
}