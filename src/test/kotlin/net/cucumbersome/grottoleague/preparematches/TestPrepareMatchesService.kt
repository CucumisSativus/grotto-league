package net.cucumbersome.grottoleague.preparematches

import net.cucumbersome.grottoleague.repositories.PlannedMatchRepository
import net.cucumbersome.grottoleague.repositories.PlayerRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
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
        val playersString = listOf("Player 1", "Player 2", "Player 3")
        service.planMatchesFromString(playersString.joinToString("\n"))

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
        assertThatIllegalArgumentException().isThrownBy { service.planMatchesFromString("") }
    }

    @Test
    fun `throw IllegalArgumentException when plan matches from string with duplicate players`() {
        val service = PrepareMatchesService(playerRepository, plannedMatchRepository)
        assertThatIllegalArgumentException().isThrownBy { service.planMatchesFromString("Player 1\nPlayer 1") }
    }
}