package net.cucumbersome.grottoleague.matches.finishmatch

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class FinishMatchRequest(
    @JsonProperty("happenedAt") val happenedAt: LocalDate,
    @JsonProperty("player1Name") val player1Name: String,
    @JsonProperty("player2Name") val player2Name: String,
    @JsonProperty("player1Points") val player1Points: Int,
    @JsonProperty("player2Points") val player2Points: Int
)