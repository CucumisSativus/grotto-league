package net.cucumbersome.grottoleague.matches.finishmatch

import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.preparematches.PlannedMatchRepository
import net.cucumbersome.grottoleague.matches.preparematches.PrepareMatchesService
import net.cucumbersome.grottoleague.player.Army
import net.cucumbersome.grottoleague.player.PlayerRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest
class TestFinishMatchService {

    @Autowired
    lateinit var plannedMatchRepository: PlannedMatchRepository

    @Autowired
    lateinit var matchRepository: MatchRepository

    @Autowired
    lateinit var playerRepository: PlayerRepository

    fun prepareMatchesService() = PrepareMatchesService(playerRepository, plannedMatchRepository)

    val player1Name = "Player 1"
    val player2Name = "Player 2"
    val player3name = "Player 3"
    @Test
    fun `it finds a planned match, creates a match and marks planned match as finished`() {
        prepareMatchesService().planMatchesFromLines(
            listOf(
                PrepareMatchesService.Companion.PlayerToBeCreated(player1Name, Army.ADEPTA_SORORITAS.name),
                PrepareMatchesService.Companion.PlayerToBeCreated(player2Name, Army.CHAOS_DAEMONS.name),
                PrepareMatchesService.Companion.PlayerToBeCreated(player3name, Army.DEATH_GUARD.name)
            )
        )
        val service = FinishMatchService(plannedMatchRepository, matchRepository)
        val now = LocalDate.now()
        service.finishMatch(FinishMatchRequest(now, player1Name, player2Name, 21, 37))

        val matchesCount = matchRepository.count()
        assertThat(matchesCount).isEqualTo(1)
        val match = matchRepository.findAll().first()
        assertThat(match.happenedOn).isEqualTo(now)
        assertThat(match.player1.name).isEqualTo(player1Name)
        assertThat(match.player2.name).isEqualTo(player2Name)
        assertThat(match.player1Points).isEqualTo(21)
        assertThat(match.player2Points).isEqualTo(37)

        val plannedMatchBetweenPlayer1And2 = plannedMatchRepository
            .findAll()
            .find { it.player1.name == player1Name && it.player2.name == player2Name }
        assertThat(plannedMatchBetweenPlayer1And2).isNotNull
        assertThat(plannedMatchBetweenPlayer1And2!!.playedOn).isEqualTo(now)
        assertThat(plannedMatchBetweenPlayer1And2.player1.name).isEqualTo(player1Name)
        assertThat(plannedMatchBetweenPlayer1And2.player2.name).isEqualTo(player2Name)

    }

    @Test
    fun `it throws an exception when there are no planned matches`() {
        val service = FinishMatchService(plannedMatchRepository, matchRepository)
        assertThatIllegalArgumentException().isThrownBy { service.finishMatch(FinishMatchRequest(LocalDate.now(), "Player 1", "Player 2", 21, 37)) }
    }

    @Test
    fun `it does nothing if the matches are already finished`() {
        prepareMatchesService().planMatchesFromLines(
            listOf(
                PrepareMatchesService.Companion.PlayerToBeCreated("Player 1", Army.ADEPTA_SORORITAS.name),
                PrepareMatchesService.Companion.PlayerToBeCreated("Player 2", Army.CHAOS_DAEMONS.name),
                PrepareMatchesService.Companion.PlayerToBeCreated("Player 3", Army.DEATH_GUARD.name)
            )
        )
        val service = FinishMatchService(plannedMatchRepository, matchRepository)
        service.finishMatch(FinishMatchRequest(LocalDate.now(), "Player 1", "Player 2", 21, 37))
        service.finishMatch(FinishMatchRequest(LocalDate.now(), "Player 1", "Player 2", 21, 37))
        val matchesCount = matchRepository.count()
        assertThat(matchesCount).isEqualTo(1)
    }

    @Test
    fun `it throws an error if the finished match has different point`() {
        prepareMatchesService().planMatchesFromLines(
            listOf(
                PrepareMatchesService.Companion.PlayerToBeCreated("Player 1", Army.ADEPTA_SORORITAS.name),
                PrepareMatchesService.Companion.PlayerToBeCreated("Player 2", Army.CHAOS_DAEMONS.name),
                PrepareMatchesService.Companion.PlayerToBeCreated("Player 3", Army.DEATH_GUARD.name)
            )
        )
        val service = FinishMatchService(plannedMatchRepository, matchRepository)
        service.finishMatch(FinishMatchRequest(LocalDate.now(), "Player 1", "Player 2", 22, 33))
        assertThatIllegalArgumentException().isThrownBy { service.finishMatch(FinishMatchRequest(LocalDate.now(), "Player 1", "Player 2", 21, 37)) }
    }
}