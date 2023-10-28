package net.cucumbersome.grottoleague.matches.ranking

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import net.cucumbersome.grottoleague.matches.Match
import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.dtos.PlayerDto
import net.cucumbersome.grottoleague.player.Army
import net.cucumbersome.grottoleague.player.Player
import net.cucumbersome.grottoleague.player.PlayerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class TestPrepareRankingService {

    @MockkBean
    lateinit var playerRepository: PlayerRepository

    @MockkBean
    lateinit var matchRepository: MatchRepository

    val player1Name = "player1"
    val player2Name = "player2"
    val player3Name = "player3"
    val player4Name = "player4"
    val player5Name = "player5"

    @Test
    fun `build ranking from players and matches`() {
        val service = PrepareRankingService(playerRepository, matchRepository)
        val player1 = Player(name = player1Name, army = Army.ADEPTA_SORORITAS)
        val player2 = Player(name = player2Name, army = Army.ADEPTUS_MECHANICUS)
        val player3 = Player(name = player3Name, army = Army.DEATH_GUARD)
        val player4 = Player(name = player4Name, army = Army.ELDAR)
        val player5 = Player(name = player5Name, army = Army.DARK_ANGELS)

        val players = listOf(
            player1,
            player2,
            player3,
            player4,
            player5
        )

        every { playerRepository.findAll() } returns players

        val matches = listOf(
            Match(
                happenedOn = LocalDate.now(),
                player1 = player2,
                player1Points = 10,
                player2 = player3,
                player2Points = 11
            ),
            Match(
                happenedOn = LocalDate.now(),
                player1 = player1,
                player1Points = 20,
                player2 = player3,
                player2Points = 21
                ),
            Match(
                happenedOn = LocalDate.now(),
                player1 = player2,
                player1Points = 80,
                player2 = player4,
                player2Points = 22
            )
        )
        every { matchRepository.findAll() } returns matches

        val ranking = service.getRanking()

        val expectedRanking = listOf(
            RankingDto(PlayerDto.fromPlayer(player3), wins = 2, loses = 0, totalPoints = 32),
            RankingDto(PlayerDto.fromPlayer(player2), wins = 1, loses = 1, totalPoints = 90),
            RankingDto(PlayerDto.fromPlayer(player4), wins = 0, loses = 1, totalPoints = 22),
            RankingDto(PlayerDto.fromPlayer(player1), wins = 0, loses = 1, totalPoints = 20),
            RankingDto(PlayerDto.fromPlayer(player5), wins = 0, loses = 0, totalPoints = 0)
        )

        assertThat(ranking).isEqualTo(expectedRanking)
    }
}