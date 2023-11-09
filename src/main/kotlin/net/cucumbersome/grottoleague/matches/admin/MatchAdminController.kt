package net.cucumbersome.grottoleague.matches.admin

import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.finishmatch.FinishMatchRequest
import net.cucumbersome.grottoleague.matches.finishmatch.FinishMatchService
import net.cucumbersome.grottoleague.matches.preparematches.PlannedMatchRepository
import net.cucumbersome.grottoleague.player.PlayerRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@Controller
@RequestMapping("/match-admin")
class MatchAdminController(private val matchRepository: MatchRepository, private val playerRepository: PlayerRepository, private val plannedMatchRepository: PlannedMatchRepository, private val finishMatchService: FinishMatchService) {
    @GetMapping("/")
    fun index(model: Model): String {
        model["plannedMatches"] = plannedMatchRepository.findAll()
        return "admin/index"
    }

    @GetMapping("/finish/{plannedMatchId}")
    fun finishMatch(@PathVariable plannedMatchId: Long, model: Model): String {
        val plannedMatchOptional = plannedMatchRepository.findById(plannedMatchId)
        if (plannedMatchOptional.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found")
        }
        val plannedMatch = plannedMatchOptional.get()
        model["plannedMatch"] = plannedMatch
        model["form"] = FinishMatchRequest(
            happenedAt = LocalDate.now(),
            player1Name = plannedMatch.player1.name,
            player2Name = plannedMatch.player2.name,
            player1Points = 0,
            player2Points = 0
        )
        return "admin/finish-match"
    }

    @PostMapping("/finish/{plannedMatchId}")
    fun finishMatchPost(@PathVariable plannedMatchId: Long, @ModelAttribute finishMatchRequest: FinishMatchRequest, result: BindingResult, model: Model): String {
        finishMatchService.finishMatch(finishMatchRequest)

        return "redirect:/match-admin/"
    }
}