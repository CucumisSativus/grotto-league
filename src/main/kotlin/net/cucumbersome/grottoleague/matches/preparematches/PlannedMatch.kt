package net.cucumbersome.grottoleague.matches.preparematches

import jakarta.persistence.*
import net.cucumbersome.grottoleague.player.Player
import java.time.LocalDate

@Entity
@Table(indexes = [Index(columnList = "player1_id, player2_id", unique = true)])
class PlannedMatch (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @ManyToOne var player1: Player,
    @ManyToOne var player2: Player,
    @Column(nullable = true) var playedOn: LocalDate? = null
)