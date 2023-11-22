package net.cucumbersome.grottoleague.matches

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MatchRepository: CrudRepository<Match, Long> {
    fun findAllByOrderByHappenedOnDesc(): List<Match>

    @Query(
        """select p from Match p
where (p.player1.name = ?1 and p.player2.name = ?2) or (p.player2.name = ?1 and p.player1.name = ?2)"""
    )
    fun findByPlayerNames(
        name: String,
        name2: String,
    ): Match?

    @Query(
        """select p from Match p
            where (p.player1.name = ?1 or p.player2.name = ?1)
            order by p.happenedOn desc"""
    )
    fun findAllByPlayerNameOrderByHappenedOnDesc(name: String): List<Match>
}