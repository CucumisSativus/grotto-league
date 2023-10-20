package net.cucumbersome.grottoleague.matches.preparematches

import org.springframework.data.repository.CrudRepository

interface PlannedMatchRepository: CrudRepository<PlannedMatch, Long> {
}