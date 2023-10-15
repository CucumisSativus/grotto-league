package net.cucumbersome.grottoleague.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(indexes = [Index(columnList = "player1_id, player2_id", unique = true)])
class PlannedMatch (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @ManyToOne var player1: Player,
    @ManyToOne var player2: Player,
    var happenedAt: LocalDateTime? = null
)