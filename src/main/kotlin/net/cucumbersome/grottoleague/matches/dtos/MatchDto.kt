package net.cucumbersome.grottoleague.matches.dtos

import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * DTO for {@link net.cucumbersome.grottoleague.matches.Match}
 */
data class MatchDto(
    val happenedAt: LocalDateTime? = null,
    val player1: PlayerDto,
    val player1Points: Int? = null,
    val player2: PlayerDto,
    val player2Points: Int? = null,
) : Serializable {
    fun winner(): PlayerDto? {
        return if (player1Points != null && player2Points != null) {
            if (player1Points > player2Points) {
                player1
            } else {
                player2
            }
        } else {
            null
        }
    }

    fun presentHappenedAt(): String? {
        return happenedAt?.format(DateTimeFormatter.ISO_DATE)
    }
}