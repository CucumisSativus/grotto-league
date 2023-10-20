package net.cucumbersome.grottoleague

import net.cucumbersome.grottoleague.matches.preparematches.MatchesConfiguration
import net.cucumbersome.grottoleague.matches.preparematches.PrepareMatchesService
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import java.io.File

@Component
class StartupEventListener(
    val prepareMatchesService: PrepareMatchesService,
    val prepareMatchesConfiguration: MatchesConfiguration
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        logger.info("Starting up, reading players from ${prepareMatchesConfiguration.playersFilePath}")
        val playerFile = File(prepareMatchesConfiguration.playersFilePath)
        if (playerFile.exists()) {
            val input = playerFile.readText()
            val players = PrepareMatchesService.Companion.PlayerToBeCreated.listFromString(input)
            prepareMatchesService.planMatchesFromLines(players)
        } else {
            logger.warn("No players file found at ${prepareMatchesConfiguration.playersFilePath}")
        }


    }

    companion object {
        val logger = org.slf4j.LoggerFactory.getLogger(StartupEventListener::class.java)
    }
}