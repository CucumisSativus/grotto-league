package net.cucumbersome.grottoleague

import net.cucumbersome.grottoleague.matches.getallmatches.GetAllMatchesService
import net.cucumbersome.grottoleague.matches.ranking.PrepareRankingService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(val getAllMatchesService: GetAllMatchesService, val prepareRankingService: PrepareRankingService) {
    @GetMapping("/")
    fun home(model: Model): String {
        model["matches"] = getAllMatchesService.getAllMatches()
        model["ranking"] = prepareRankingService.getRanking()
        return "home"
    }
}