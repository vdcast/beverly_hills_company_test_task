package com.example.beverly_hills_company_test_task.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.beverly_hills_company_test_task.R
import com.example.beverly_hills_company_test_task.presentation.ui.theme.Orange10
import com.example.beverly_hills_company_test_task.presentation.ui.utils.BackgroundFill
import com.example.beverly_hills_company_test_task.presentation.ui.utils.ButtonHome

@Composable
fun Home(
    onGameCLick: () -> Unit,
    onWebViewClick: () -> Unit,
    onSplashClick: () -> Unit,
) {
    BackgroundFill(color = Orange10)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ButtonHome(
            onClick = { onGameCLick() },
            text = stringResource(id = R.string.game),
            contentDescription = "button game"
        )
        ButtonHome(
            onClick = { onWebViewClick() },
            text = stringResource(id = R.string.webview),
            contentDescription = "button webview"
        )
        ButtonHome(
            onClick = { onSplashClick() },
            text = stringResource(id = R.string.splash),
            contentDescription = "button splash"
        )
    }

}