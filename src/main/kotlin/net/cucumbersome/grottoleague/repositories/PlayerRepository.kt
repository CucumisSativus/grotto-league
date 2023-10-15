package net.cucumbersome.grottoleague.repositories

import net.cucumbersome.grottoleague.entities.Player
import org.springframework.data.repository.CrudRepository

interface PlayerRepository: CrudRepository<Player, Long> {
    fun findAllByNameIn(names: Collection<String>): Iterable<Player>
}