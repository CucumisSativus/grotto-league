package net.cucumbersome.grottoleague.repositories

import net.cucumbersome.grottoleague.entities.PlannedMatch
import org.springframework.data.repository.CrudRepository

interface PlannedMatchRepository: CrudRepository<PlannedMatch, Long> {
}