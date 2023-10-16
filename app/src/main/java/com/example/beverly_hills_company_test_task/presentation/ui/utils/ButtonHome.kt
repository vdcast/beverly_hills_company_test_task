package com.example.beverly_hills_company_test_task.presentation.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beverly_hills_company_test_task.R
import com.example.beverly_hills_company_test_task.presentation.ui.theme.Orange10

@Composable
fun ButtonHome(
    onClick: () -> Unit,
    contentDescription: String,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.button_1),
            contentDescription = contentDescription
        )
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}