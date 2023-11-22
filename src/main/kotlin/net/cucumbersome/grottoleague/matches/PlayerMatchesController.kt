package net.cucumbersome.grottoleague.matches

import net.cucumbersome.grottoleague.matches.getallmatches.GetAllMatchesService
import net.cucumbersome.grottoleague.player.PlayerRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class PlayerMatchesController(val playerRepository: PlayerRepository, val getAllMatchesService: GetAllMatchesService) {
    @GetMapping("/players/{slug}/matches")
    fun getAllMatchesForPlayer(@PathVariable slug: String, model: Model): String {
        val player = playerRepository.findBySlug(slug)
        if (player == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found")
        }
        model["player"] = player
        model["matches"] = getAllMatchesService.getAllMatchesForPlayer(player.name)
        return "player-matches"
    }
}