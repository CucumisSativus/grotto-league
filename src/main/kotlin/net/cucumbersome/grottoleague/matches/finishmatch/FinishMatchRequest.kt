package net.cucumbersome.grottoleague.matches.finishmatch

import java.time.LocalDate

data class FinishMatchRequest(
    val happenedAt: LocalDate,
    val player1Name: String,
    val player2Name: String,
    val player1Points: Int,
    val player2Points: Int
)