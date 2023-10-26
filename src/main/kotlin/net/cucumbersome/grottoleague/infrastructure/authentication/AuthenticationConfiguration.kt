package net.cucumbersome.grottoleague.infrastructure.authentication

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "authentication")
data class AuthenticationConfiguration(val apiKey: String)
