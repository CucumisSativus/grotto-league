package net.cucumbersome.grottoleague.player

import org.springframework.data.repository.CrudRepository

interface PlayerRepository: CrudRepository<Player, Long> {
    fun findAllByNameIn(names: Collection<String>): Iterable<Player>
}