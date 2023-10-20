package net.cucumbersome.grottoleague.matches

import jakarta.persistence.*
import net.cucumbersome.grottoleague.player.Player
import java.time.LocalDateTime

@Entity
@Table(indexes = [Index(columnList = "player1_id, player2_id", unique = true)])
class Match(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long? = null,
    var happenedAt: LocalDateTime,
    @ManyToOne var player1: Player,
    var player1Points: Int,
    @ManyToOne var player2: Player,
    var player2Points: Int,
)