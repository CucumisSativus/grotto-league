package net.cucumbersome.grottoleague

import net.cucumbersome.grottoleague.infrastructure.authentication.AuthenticationConfiguration
import net.cucumbersome.grottoleague.matches.preparematches.MatchesConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(MatchesConfiguration::class, AuthenticationConfiguration::class)
class GrottoleagueApplication

fun main(args: Array<String>) {
    runApplication<GrottoleagueApplication>(*args)
}
