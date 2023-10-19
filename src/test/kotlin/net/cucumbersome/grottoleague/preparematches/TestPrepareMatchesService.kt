package net.cucumbersome.grottoleague.preparematches

import net.cucumbersome.grottoleague.repositories.PlayerRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class TestPrepareMatchesService {
    @Autowired
    lateinit var plannedMatchRepository: PlannedMatchRepository

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Test
    fun `plan matches from string with players`() {
        val service = PrepareMatchesService(playerRepository, plannedMatchRepository)
        val players = listOf("Player 1", "Player 2", "Player 3")
        service.planMatchesFromLines(players)

        val savedPlayers = playerRepository.findAll()
        val playerNames = savedPlayers.map { it.name }.toSet()
        assertThat(playerNames).containsExactlyInAnyOrder("Player 1", "Player 2", "Player 3")

        val savedPlannedMatches = plannedMatchRepository.findAll()
        val plannedMatchNames = savedPlannedMatches.map { it.player1.name + " vs " + it.player2.name }.toSet()
        val expectedMatchesNames = listOf("Player 1 vs Player 2", "Player 1 vs Player 3", "Player 2 vs Player 3")
        assertThat(plannedMatchNames).containsExactlyInAnyOrder(*expectedMatchesNames.toTypedArray())
    }

    @Test
    fun `throw IllegalArgumentException when plan matches from string with no players`() {
        val service = PrepareMatchesService(playerRepository, plannedMatchRepository)
        assertThatIllegalArgumentException().isThrownBy { service.planMatchesFromLines(emptyList()) }
    }

    @Test
    fun `throw IllegalArgumentException when plan matches from string with duplicate players`() {
        val service = PrepareMatchesService(playerRepository, plannedMatchRepository)
        val players = listOf("Player 1", "Player 1")
        assertThatIllegalArgumentException().isThrownBy { service.planMatchesFromLines(players) }
    }

    @Test
    fun `do nothing if the players with the same name are in the database`() {
        val service = PrepareMatchesService(playerRepository, plannedMatchRepository)
        val players = listOf("Player 1", "Player 2")

        service.planMatchesFromLines(players)
        service.planMatchesFromLines(players)

        val savedPlannedMatchesCount = plannedMatchRepository.count()
        assertThat(savedPlannedMatchesCount).isEqualTo(1)
    }


    @Test
    fun `schedule new matches for new players`() {
        val service = PrepareMatchesService(playerRepository, plannedMatchRepository)
        service.planMatchesFromLines(listOf("Player 1", "Player 2"))

        assertThat(plannedMatchRepository.count()).isEqualTo(1)

        service.planMatchesFromLines(listOf("Player 1", "Player 2", "Player 3"))

        val savedPlayers = playerRepository.findAll()
        val playerNames = savedPlayers.map { it.name }.toSet()
        assertThat(playerNames).containsExactlyInAnyOrder("Player 1", "Player 2", "Player 3")

        val savedPlannedMatches = plannedMatchRepository.findAll()
        val plannedMatchNames = savedPlannedMatches.map { it.player1.name + " vs " + it.player2.name }.toSet()
        val expectedMatchesNames = listOf("Player 1 vs Player 2", "Player 1 vs Player 3", "Player 2 vs Player 3")
        assertThat(plannedMatchNames).containsExactlyInAnyOrder(*expectedMatchesNames.toTypedArray())
    }

    @Test
    fun `throw exception if there exist a player in the database but not in the string`() {
        val service = PrepareMatchesService(playerRepository, plannedMatchRepository)
        service.planMatchesFromLines(listOf("Player 1", "Player 2", "Player 3"))
        assertThatIllegalStateException().isThrownBy { service.planMatchesFromLines(listOf("Player 1", "Player 3")) }
    }
}