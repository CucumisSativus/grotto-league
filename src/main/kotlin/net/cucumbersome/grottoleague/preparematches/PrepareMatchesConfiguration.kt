package net.cucumbersome.grottoleague.preparematches

import net.cucumbersome.grottoleague.repositories.PlannedMatchRepository
import net.cucumbersome.grottoleague.repositories.PlayerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PrepareMatchesConfiguration(val playerRepository: PlayerRepository, val plannedMatchRepository: PlannedMatchRepository) {
    @Bean
    fun prepareMatchesService() = PrepareMatchesService(playerRepository, plannedMatchRepository)
}