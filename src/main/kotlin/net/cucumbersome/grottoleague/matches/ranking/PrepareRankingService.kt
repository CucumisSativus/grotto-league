package net.cucumbersome.grottoleague.matches.ranking

import net.cucumbersome.grottoleague.matches.Match
import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.dtos.PlayerDto
import net.cucumbersome.grottoleague.player.Player
import net.cucumbersome.grottoleague.player.PlayerRepository
import org.springframework.stereotype.Service

@Service
class PrepareRankingService(private val playerRepository: PlayerRepository, private val matchRepository: MatchRepository) {
    fun getRanking(): List<RankingDto> {
        val matches = matchRepository.findAll().toList()
        val players = playerRepository.findAll().toList()

        return players.map { buildRankingForPlayer(it, matches) }
            .sortedWith(compareBy(RankingDto::wins, RankingDto::totalPoints).reversed())
    }


    private fun buildRankingForPlayer(player: Player, matches: List<Match>): RankingDto {
        val playerMatches = matches.filter { it.wasPlayedByPlayer(player) }

        val wins = playerMatches.count { it.winner().name == player.name }
        val loses = playerMatches.size - wins
        val totalPoints = playerMatches.sumOf { it.playerPoints(player) }

        return RankingDto(
            PlayerDto.fromPlayer(player),
            wins,
            loses,
            totalPoints
        )
    }
}