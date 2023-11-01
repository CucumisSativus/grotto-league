package net.cucumbersome.grottoleague.infrastructure.authentication

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SpringSecurityConfig(private val authenticationConfiguration: AuthenticationConfiguration) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests{
            it.requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        }
            .csrf { it.disable() }
            .httpBasic(Customizer.withDefaults())

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user: UserDetails = User.builder()
            .username("user")
            .password(passwordEncoder().encode(authenticationConfiguration.apiKey))
            .build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}