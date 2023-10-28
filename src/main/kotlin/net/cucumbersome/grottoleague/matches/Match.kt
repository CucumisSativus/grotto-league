package net.cucumbersome.grottoleague.matches

import jakarta.persistence.*
import net.cucumbersome.grottoleague.player.Player
import java.lang.IllegalArgumentException
import java.time.LocalDate

@Entity
@Table(indexes = [Index(columnList = "player1_id, player2_id", unique = true)])
class Match(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long? = null,
    var happenedOn: LocalDate,
    @ManyToOne var player1: Player,
    var player1Points: Int,
    @ManyToOne var player2: Player,
    var player2Points: Int,

) {
    fun wasPlayedByPlayer(player: Player): Boolean {
        return player1.name == player.name || player2.name == player.name
    }

    fun winner(): Player {
        return if(player1Points > player2Points) {
            player1
        } else {
            player2
        }
    }

    fun playerPoints(player: Player): Int {
        return if(player.name == player1.name) player1Points
        else if(player.name == player2.name) player2Points
        else throw IllegalArgumentException("Player $player did not participate in the match")
    }
}