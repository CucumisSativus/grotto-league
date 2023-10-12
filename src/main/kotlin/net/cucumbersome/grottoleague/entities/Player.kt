package net.cucumbersome.grottoleague.entities

import com.github.slugify.Slugify
import jakarta.persistence.*
import java.time.LocalDateTime

val slugify = Slugify.builder().build()
fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
@Entity
class Player (
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long? = null,
    var name: String,
    var addedAt: LocalDateTime = LocalDateTime.now(),
    var slug: String = slugify.slugify("$name ${getRandomString(8)}")
)

@Entity
class Match(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long? = null,
    var happenedAt: LocalDateTime,
    @ManyToOne var player1: Player,
    @Enumerated(EnumType.STRING) var player1Army: Army,
    var player1Points: Int,
    @ManyToOne var player2: Player,
    @Enumerated(EnumType.STRING) var player2Army: Army,
    var player2Points: Int
)