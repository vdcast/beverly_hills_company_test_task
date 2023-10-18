package com.example.beverly_hills_company_test_task.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beverly_hills_company_test_task.R
import com.example.beverly_hills_company_test_task.domain.AppViewModel
import com.example.beverly_hills_company_test_task.presentation.ui.theme.Orange10
import com.example.beverly_hills_company_test_task.presentation.ui.theme.Orange20
import com.example.beverly_hills_company_test_task.presentation.ui.utils.BackgroundFill

@Composable
fun Records(
    appViewModel: AppViewModel = hiltViewModel()
) {
    appViewModel.loadRecords()

    val records by appViewModel.records.collectAsState()

    BackgroundFill(color = Orange10)

    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .wrapContentHeight()
            .padding(
                all = 8.dp
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Orange20)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.records),
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.records_date),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(0.4f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.records_time),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.records_place),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            records.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.date,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = item.time,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = item.winner,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}