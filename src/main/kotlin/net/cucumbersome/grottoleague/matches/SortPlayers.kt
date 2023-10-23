package net.cucumbersome.grottoleague.matches

import net.cucumbersome.grottoleague.player.Player

object SortPlayers {
    fun sortPlayers(player1: Player, player2: Player): Pair<Player, Player> {
        return if(player1.name < player2.name) {
            Pair(player1, player2)
        } else {
            Pair(player2, player1)
        }
    }
}