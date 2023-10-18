package net.cucumbersome.grottoleague.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(indexes = [Index(columnList = "player1_id, player2_id", unique = true)])
class Match(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long? = null,
    var happenedAt: LocalDateTime,
    @ManyToOne var player1: Player,
    @Enumerated(EnumType.STRING) var player1Army: Army,
    var player1Points: Int,
    @ManyToOne var player2: Player,
    @Enumerated(EnumType.STRING) var player2Army: Army,
    var player2Points: Int,
)