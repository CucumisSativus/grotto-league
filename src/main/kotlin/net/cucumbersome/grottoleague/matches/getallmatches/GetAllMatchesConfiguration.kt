package net.cucumbersome.grottoleague.matches.getallmatches

import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.preparematches.PlannedMatchRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GetAllMatchesConfiguration(val matchRepository: MatchRepository, val plannedMatchRepository: PlannedMatchRepository) {
    @Bean
    fun getAllMatchesService() = GetAllMatchesService(plannedMatchRepository, matchRepository)
}