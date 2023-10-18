package com.example.beverly_hills_company_test_task.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beverly_hills_company_test_task.R
import com.example.beverly_hills_company_test_task.domain.AppViewModel
import com.example.beverly_hills_company_test_task.presentation.ui.theme.Orange10
import com.example.beverly_hills_company_test_task.presentation.ui.utils.BackgroundFill
import com.example.beverly_hills_company_test_task.presentation.ui.utils.ResetGame

enum class Player {
    X, O, None
}

@Composable
fun Game(
    onHomeScreenClick: () -> Unit,
    appViewModel: AppViewModel = hiltViewModel()
) {
    val boardSize = 3
    var board by remember { mutableStateOf(Array(boardSize) { Array(boardSize) { Player.None } }) }
    var currentPlayer by remember { mutableStateOf(Player.X) }
    var winner by remember { mutableStateOf(Player.None) }
    var gameIsOver by remember { mutableStateOf(false) }

    fun checkWinner(board: Array<Array<Player>>): Player {
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != Player.None)
                return board[i][0]
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != Player.None)
                return board[0][i]
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != Player.None)
            return board[0][0]
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != Player.None)
            return board[0][2]

        return Player.None
    }

    LaunchedEffect(gameIsOver) {
        Log.d("MYLOG", "Winner: $winner, Game Over: $gameIsOver")

    }

    BackgroundFill(color = Orange10)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Current player: $currentPlayer",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

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
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .border(1.dp, Color.Black)
                            .clickable {
                                if (board[i][j] == Player.None && winner == Player.None) {
                                    board[i][j] = currentPlayer
                                    winner = checkWinner(board)
                                    if (winner != Player.None) {
                                        gameIsOver = true
                                    }
                                    currentPlayer =
                                        if (currentPlayer == Player.X) Player.O else Player.X
                                }
                            }
                    ) {
                        Text(
                            text = when (board[i][j]) {
                                Player.X -> "X"
                                Player.O -> "O"
                                else -> ""
                            },
                            fontSize = 30.sp
                        )
                    }
                }
            }
        }


        val isBoardFull = board.all { row -> row.all { it != Player.None } }
        if (isBoardFull) {
            gameIsOver = true
        }

        if (gameIsOver) {
            if(winner != Player.None) {

                ResetGame(boardSize = boardSize, board = board)
                appViewModel.saveRecords(winner = winner)

                AlertDialog(
                    onDismissRequest = { },
                    title = {
                        Text(
                            text = stringResource(
                                id = R.string.game_over,
                                if (winner == Player.X) "X" else "O"
                            )
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(
                                id = R.string.alert_1,
                                if (winner == Player.X) "X" else "O"
                            ),
                            fontSize = 16.sp
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                winner = Player.None
                                gameIsOver = false
                            }
                        ) { Text(stringResource(id = R.string.alert_play_again)) }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                winner = Player.None
                                onHomeScreenClick()
                                gameIsOver = false
                            }
                        ) { Text(text = stringResource(id = R.string.alert_main_menu)) }
                    }
                )
            } else {
                ResetGame(boardSize = boardSize, board = board)
                appViewModel.saveRecords(winner = winner)

                AlertDialog(
                    onDismissRequest = { },
                    title = { Text(text = stringResource(id = R.string.alert_draw)) },
                    text = {
                        Text(
                            text = stringResource(
                                id = R.string.alert_2
                            ),
                            fontSize = 16.sp
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                winner = Player.None
                                gameIsOver = false
                            }
                        ) { Text(stringResource(id = R.string.alert_play_again)) }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                winner = Player.None
                                onHomeScreenClick()
                                gameIsOver = false
                            }
                        ) { Text(text = stringResource(id = R.string.alert_main_menu)) }
                    }
                )
            }
        }
    }
}