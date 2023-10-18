package com.example.beverly_hills_company_test_task.presentation.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.beverly_hills_company_test_task.presentation.ui.screens.Player

@Composable
fun ResetGame(boardSize: Int, board: Array<Array<Player>>) {
    for (i in 0 until boardSize) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 32.dp
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            for (j in 0 until boardSize) {
                board[i][j] = Player.None
            }

        }
    }
}