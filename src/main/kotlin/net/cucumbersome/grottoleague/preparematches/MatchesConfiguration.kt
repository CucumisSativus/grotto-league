package net.cucumbersome.grottoleague.preparematches

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "prepare-matches")
data class MatchesConfiguration(
    val playersFilePath: String)
