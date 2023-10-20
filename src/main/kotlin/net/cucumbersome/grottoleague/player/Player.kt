package net.cucumbersome.grottoleague.player

import com.github.slugify.Slugify
import jakarta.persistence.*
import java.time.LocalDateTime

val slugify = Slugify.builder().build()
@Entity
@Table(indexes = [Index(columnList = "slug", unique = true)])
class Player(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long? = null,
    @Column(unique = true) var name: String,
    var addedAt: LocalDateTime = LocalDateTime.now(),
    var slug: String = slugify.slugify(name),
    @Enumerated(EnumType.STRING) var army: Army,
)

