package net.cucumbersome.grottoleague.matches.preparematches

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "prepare-matches")
data class MatchesConfiguration(
    val playersFilePath: String)
