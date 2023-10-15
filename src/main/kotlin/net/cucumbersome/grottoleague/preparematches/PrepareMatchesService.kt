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

        if(playerNames.isEmpty()) {
            throw IllegalArgumentException("No players provided")
        }
        if(playerNames.size != playerNames.toSet().size) {
            throw IllegalArgumentException("Duplicate player names provided")
        }

        val players = getAllPlayersAndInitializeIfNeeded(playerNames)
        val currentMatches = plannedMatchRepository.findAll().toList()

        val matches = eachAgainstOther(players)
            .filter { it.first != it.second }
            .fold(currentMatches) { acc, pair -> function(acc, pair.first, pair.second) }
        logger.info("Scheduling ${matches.size} matches, new count: ${matches.size - currentMatches.size}, existing count: ${currentMatches.size}")
        plannedMatchRepository.saveAll(matches)
    }

    private fun function(acc: List<PlannedMatch>, player1: Player, player2: Player): List<PlannedMatch> {
        val count = acc.count { plannedMatchBetween(player1, player2, it) }
        return if (count == 0) {
            acc + PlannedMatch(player1 = player1, player2 = player2)
        } else {
            acc
        }
    }

    private fun eachAgainstOther(players: List<Player>) =
        players.flatMap { player1 ->
            players.map { player2 ->
                if (player1.name < player2.name) {
                    Pair(player1, player2)
                } else {
                    Pair(player2, player1)
                }
            }
        }

    private fun getAllPlayersAndInitializeIfNeeded(playerNames: List<String>): List<Player> {
        val existingPlayers = playerRepository.findAllByNameIn(playerNames).toList()
        val existingNames = existingPlayers.map { it.name }.toSet()
        val newNames = playerNames.filter { !existingNames.contains(it) }
        val newPlayers = newNames.map { Player(name = it) }
        playerRepository.saveAll(newPlayers)
        val players = newPlayers + existingPlayers
        logger.info("Player count: ${players.size}, new: ${newPlayers.size}, existing: ${existingPlayers.size}")
        return players
    }

    private fun plannedMatchBetween(player1: Player, player2: Player, plannedMatch: PlannedMatch): Boolean {
        return (plannedMatch.player1 == player1 && plannedMatch.player2 == player2) ||
                (plannedMatch.player1 == player2 && plannedMatch.player2 == player1)
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(PrepareMatchesService::class.java)
    }
}
