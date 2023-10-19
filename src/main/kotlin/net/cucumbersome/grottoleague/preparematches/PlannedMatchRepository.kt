package net.cucumbersome.grottoleague.preparematches

import org.springframework.data.repository.CrudRepository

interface PlannedMatchRepository: CrudRepository<PlannedMatch, Long> {
}