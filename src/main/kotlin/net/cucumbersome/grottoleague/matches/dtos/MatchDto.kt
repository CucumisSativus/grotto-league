package net.cucumbersome.grottoleague.matches.dtos

import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * DTO for {@link net.cucumbersome.grottoleague.matches.Match}
 */
data class MatchDto(
    val happenedOn: LocalDate? = null,
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

    fun presentHappenedOn(): String? {
        return happenedOn?.format(DateTimeFormatter.ISO_DATE)
    }
}