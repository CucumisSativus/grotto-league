package net.cucumbersome.grottoleague.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Player(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long? = null,
    @Column(unique = true) var name: String,
    var addedAt: LocalDateTime = LocalDateTime.now(),
)

