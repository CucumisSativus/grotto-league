package net.cucumbersome.grottoleague.matches.dtos

import jakarta.persistence.*
import net.cucumbersome.grottoleague.entities.Player
import java.io.Serializable

/**
 * DTO for {@link net.cucumbersome.grottoleague.entities.Player}
 */
data class PlayerDto(val name: String, val slug: String, val army: String) : Serializable {
    companion object {
        fun fromPlayer(player: Player): PlayerDto {
            return PlayerDto(
                name = player.name,
                slug = player.slug,
                army = player.army.displayName
            )
        }
    }
}