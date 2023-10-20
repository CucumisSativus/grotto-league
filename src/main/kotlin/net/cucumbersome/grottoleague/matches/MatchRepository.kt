package net.cucumbersome.grottoleague.matches

import org.springframework.data.repository.CrudRepository

interface MatchRepository: CrudRepository<Match, Long> {
    fun findAllByOrderByHappenedAtDesc(): List<Match>
}