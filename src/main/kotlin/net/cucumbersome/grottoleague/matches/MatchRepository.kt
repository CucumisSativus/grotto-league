package net.cucumbersome.grottoleague.matches

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

interface MatchRepository: CrudRepository<Match, Long> {
    fun findAllByOrderByHappenedAtDesc(): List<Match>
}