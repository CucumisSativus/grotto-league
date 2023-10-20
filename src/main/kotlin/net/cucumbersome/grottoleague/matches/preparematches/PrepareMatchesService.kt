package net.cucumbersome.grottoleague.matches.preparematches

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.cucumbersome.grottoleague.player.Army
import net.cucumbersome.grottoleague.player.Player
import net.cucumbersome.grottoleague.player.PlayerRepository

class PrepareMatchesService(
    val playerRepository: PlayerRepository,
    val plannedMatchRepository: PlannedMatchRepository
) {
    fun planMatchesFromLines(playerToBeCreated: List<PlayerToBeCreated>) {
        if(playerToBeCreated.isEmpty()) {
            throw IllegalArgumentException("No players provided")
        }
        val uniquePlayerNames = playerToBeCreated.map{it.name}.toSet()
        if(playerToBeCreated.size != uniquePlayerNames.size) {
            throw IllegalArgumentException("Duplicate player names provided")
        }

        val players = getAllPlayersAndInitializeIfNeeded(playerToBeCreated)
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

    private fun getAllPlayersAndInitializeIfNeeded(playersToBeCreated: List<PlayerToBeCreated>): List<Player> {
        val playerNames = playersToBeCreated.map { it.name }.toSet()
        val existingPlayers = playerRepository.findAll().toList()
        val existingNames = existingPlayers.map { it.name }.toSet()

        val namesThatExistInTheDatabaseButNotProvided = existingNames.filter { !playerNames.contains(it) }
        if(namesThatExistInTheDatabaseButNotProvided.isNotEmpty()) {
            throw IllegalStateException("Player names that exist in the database but not provided: $namesThatExistInTheDatabaseButNotProvided")
        }

        val newPlayers = playersToBeCreated
            .filter { !existingNames.contains(it.name) }
            .map { Player(name = it.name, army = Army.valueOf(it.army)) }
        playerRepository.saveAll(newPlayers)
        val players = newPlayers + existingPlayers
        logger.info("Player count: ${players.size}, new: ${newPlayers.size}, existing: ${existingPlayers.size}")
        return players
    }

    private fun plannedMatchBetween(player1: Player, player2: Player, plannedMatch: PlannedMatch): Boolean {
        return (plannedMatch.player1.name == player1.name && plannedMatch.player2.name == player2.name) ||
                (plannedMatch.player1.name == player2.name && plannedMatch.player2.name == player1.name)
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(PrepareMatchesService::class.java)
        val mapper = jacksonObjectMapper()

        data class PlayerToBeCreated(val name: String, val army: String) {
            companion object {
                fun listFromString(input: String): List<PlayerToBeCreated> {
                    return mapper.readValue<List<PlayerToBeCreated>>(input)
                }
            }
        }
    }
}
