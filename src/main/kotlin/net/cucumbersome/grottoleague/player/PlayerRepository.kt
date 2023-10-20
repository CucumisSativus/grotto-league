package net.cucumbersome.grottoleague.player

import net.cucumbersome.grottoleague.player.Player
import org.springframework.data.repository.CrudRepository

interface PlayerRepository: CrudRepository<Player, Long> {
    fun findAllByNameIn(names: Collection<String>): Iterable<Player>
}