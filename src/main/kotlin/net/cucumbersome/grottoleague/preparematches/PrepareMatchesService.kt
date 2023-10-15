package net.cucumbersome.grottoleague.preparematches

import net.cucumbersome.grottoleague.entities.PlannedMatch
import net.cucumbersome.grottoleague.entities.Player
import net.cucumbersome.grottoleague.repositories.PlannedMatchRepository
import net.cucumbersome.grottoleague.repositories.PlayerRepository

class PrepareMatchesService(
    val playerRepository: PlayerRepository,
    val plannedMatchRepository: PlannedMatchRepository
) {
    fun planMatchesFromString(playerString: String) {
        val playerNames = playerString.split("\n").filter { it.isNotBlank() }
        val players = playerNames.map { Player(name = it) }

        if(players.isEmpty()) {
            throw IllegalArgumentException("No players provided")
        }
        if(players.size != playerNames.toSet().size) {
            throw IllegalArgumentException("Duplicate player names provided")
        }

        logger.info("Creating ${players.size} players")
        playerRepository.saveAll(players)
        val matches = players.flatMap { player1 ->
            players.map { player2 -> Pair(player1, player2) }
        }
            .filter { it.first != it.second }
            .fold(emptyList<PlannedMatch>()) { acc, (player1, player2) ->
                val count = acc.count { plannedMatchBetween(player1, player2, it) }
                if (count == 0) {
                    acc + PlannedMatch(player1 = player1, player2 = player2)
                } else {
                    acc
                }
            }
        logger.info("Creating ${matches.size} planned matches")
        plannedMatchRepository.saveAll(matches)
    }

    private fun plannedMatchBetween(player1: Player, player2: Player, plannedMatch: PlannedMatch): Boolean {
        return (plannedMatch.player1 == player1 && plannedMatch.player2 == player2) ||
                (plannedMatch.player1 == player2 && plannedMatch.player2 == player1)
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(PrepareMatchesService::class.java)
    }
}
