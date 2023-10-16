package com.example.beverly_hills_company_test_task.presentation.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.beverly_hills_company_test_task.presentation.ui.theme.Orange10

@Composable
fun BackgroundFill(
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color)
    )
}