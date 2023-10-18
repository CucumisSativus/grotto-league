package net.cucumbersome.grottoleague

import net.cucumbersome.grottoleague.preparematches.MatchesConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(MatchesConfiguration::class)
class GrottoleagueApplication

fun main(args: Array<String>) {
    runApplication<GrottoleagueApplication>(*args)
}
