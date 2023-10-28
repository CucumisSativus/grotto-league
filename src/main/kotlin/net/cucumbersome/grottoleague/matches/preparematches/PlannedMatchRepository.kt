package net.cucumbersome.grottoleague.matches.preparematches

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface PlannedMatchRepository: CrudRepository<PlannedMatch, Long> {


    @Query(
        """select p from PlannedMatch p
where (p.player1.name = ?1 and p.player2.name = ?2) or (p.player2.name = ?1 and p.player1.name = ?2)"""
    )
    fun findByPlayerNames(
        name: String,
        name2: String,
    ): PlannedMatch?


    @Query("select p from PlannedMatch p where p.playedOn is null")
    fun allNotPlayed(): List<PlannedMatch>

}