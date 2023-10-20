package net.cucumbersome.grottoleague.matches.preparematches

import net.cucumbersome.grottoleague.player.PlayerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PrepareMatchesConfiguration(val playerRepository: PlayerRepository, val plannedMatchRepository: PlannedMatchRepository) {
    @Bean
    fun prepareMatchesService() = PrepareMatchesService(playerRepository, plannedMatchRepository)
}