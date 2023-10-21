package net.cucumbersome.grottoleague.matches.finishmatch

import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.preparematches.PlannedMatchRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FinishMatchConfiguration(val plannedMatchRepository: PlannedMatchRepository, val matchRepository: MatchRepository) {
    @Bean
    fun finishMatchService(): FinishMatchService {
        return FinishMatchService(plannedMatchRepository, matchRepository)
    }
}