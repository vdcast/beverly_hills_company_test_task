package com.example.beverly_hills_company_test_task.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beverly_hills_company_test_task.R
import com.example.beverly_hills_company_test_task.domain.AppViewModel
import com.example.beverly_hills_company_test_task.presentation.ui.theme.Orange10
import com.example.beverly_hills_company_test_task.presentation.ui.utils.BackgroundFill

@Composable
fun Splash(
    onWebViewOpen: () -> Unit,
    onHomeOpen: () -> Unit,
    appViewModel: AppViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        appViewModel.launchFetchingData(
            context = context,
            onGameOpen = { onHomeOpen() },
            onWebViewOpen = { onWebViewOpen() },
        )
    }


    BackgroundFill(color = Orange10)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 64.dp,
                    horizontal = 48.dp
                ),
            text = stringResource(id = R.string.splash_welcome),
            fontSize = 22.sp
        )
        Text(
            text = stringResource(id = R.string.loading),
            fontSize = 22.sp
        )
        CircularProgressIndicator(
            modifier = Modifier
                .padding(48.dp),
        )
    }
}