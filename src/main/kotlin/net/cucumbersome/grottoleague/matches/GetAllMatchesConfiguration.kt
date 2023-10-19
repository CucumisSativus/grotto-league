package net.cucumbersome.grottoleague.matches

import net.cucumbersome.grottoleague.preparematches.PlannedMatchRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GetAllMatchesConfiguration(val matchRepository: MatchRepository, val plannedMatchRepository: PlannedMatchRepository) {
    @Bean
    fun getAllMatchesService() = GetAllMatchesService(plannedMatchRepository, matchRepository)
}