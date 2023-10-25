package net.cucumbersome.grottoleague.matches.finishmatch

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.preparematches.PlannedMatchRepository
import net.cucumbersome.grottoleague.matches.preparematches.PrepareMatchesService
import net.cucumbersome.grottoleague.player.Army
import net.cucumbersome.grottoleague.player.PlayerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestFinishMatchController(@Autowired val restTemplate: TestRestTemplate) {
    @Autowired
    lateinit var plannedMatchRepository: PlannedMatchRepository

    @Autowired
    lateinit var matchRepository: MatchRepository

    @Autowired
    lateinit var playerRepository: PlayerRepository


    fun prepareMatchesService() = PrepareMatchesService(playerRepository, plannedMatchRepository)

    val player1Name = "Player 1"
    val player2Name = "Player 2"
    val mapper = ObjectMapper()

    @BeforeEach
    fun init() {
        playerRepository.deleteAll()
        matchRepository.deleteAll()
        plannedMatchRepository.deleteAll()
    }
    @Test
    fun `it finds a planned match, creates a match and marks planned match as finished`() {
        prepareMatchesService().planMatchesFromLines(
            listOf(
                PrepareMatchesService.Companion.PlayerToBeCreated(player1Name, Army.ADEPTA_SORORITAS.name),
                PrepareMatchesService.Companion.PlayerToBeCreated(player2Name, Army.CHAOS_DAEMONS.name),
            )
        )

        val node: ObjectNode =
            mapper.createObjectNode()
                .put("happenedAt", "2021-01-01")
                .put("player1Name", player1Name)
                .put("player2Name", player2Name)
                .put("player1Points", 21)
                .put("player2Points", 37)
        val nodeAsString = mapper.writeValueAsString(node)

        val resp = restTemplate.exchange(
            URI("/api/matches/finish/"),
            HttpMethod.PUT,
            HttpEntity(node),
            String::class.java
        )
        assertThat(resp.statusCode).isEqualTo(status().isOk)


        val plannedMatch = plannedMatchRepository.findByPlayerNames(player1Name, player1Name)
        assertThat(plannedMatch).isNotNull
        assertThat(plannedMatch!!.playedOn).isEqualTo("2021-01-01")


        val matchesCount = matchRepository.count()
        assertThat(matchesCount).isEqualTo(1)
        val match = matchRepository.findAll().first()
        assertThat(match.happenedOn).isEqualTo("2021-01-01")
        assertThat(match.player1.name).isEqualTo(player1Name)
        assertThat(match.player2.name).isEqualTo(player2Name)
        assertThat(match.player1Points).isEqualTo(21)
        assertThat(match.player2Points).isEqualTo(37)
    }
}