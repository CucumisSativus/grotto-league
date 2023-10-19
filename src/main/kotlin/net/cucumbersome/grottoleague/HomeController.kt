package net.cucumbersome.grottoleague

import net.cucumbersome.grottoleague.matches.GetAllMatchesService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(val getAllMatchesService: GetAllMatchesService) {
    @GetMapping("/")
    fun home(model: Model): String {
        model["matches"] = getAllMatchesService.getAllMatches()
        return "home"
    }
}