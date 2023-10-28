package net.cucumbersome.grottoleague.matches.ranking

import net.cucumbersome.grottoleague.matches.dtos.PlayerDto

data class RankingDto(
    val player: PlayerDto,
    val wins: Int,
    val loses: Int,
    val totalPoints: Int
)
