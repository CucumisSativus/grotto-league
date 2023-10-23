package net.cucumbersome.grottoleague.matches.finishmatch

import net.cucumbersome.grottoleague.matches.Match
import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.preparematches.PlannedMatchRepository

class FinishMatchService(val plannedMatchRepository: PlannedMatchRepository, val matchRepository: MatchRepository) {
    fun finishMatch(finishMatchRequest: FinishMatchRequest) {
        val existingMatch = matchRepository.findByPlayerNames(finishMatchRequest.player1Name, finishMatchRequest.player2Name)

        if(existingMatch != null) {
            if(existingMatch.player1Points != finishMatchRequest.player1Points || existingMatch.player2Points != finishMatchRequest.player2Points) {
                throw IllegalArgumentException("Match already finished, with different points")
            } else {
                return
            }
        }

        val plannedMatch = plannedMatchRepository.findByPlayerNames(finishMatchRequest.player1Name, finishMatchRequest.player2Name)
            ?: throw IllegalArgumentException("Planned match not found")

        plannedMatch.playedOn = finishMatchRequest.happenedAt
        plannedMatchRepository.save(plannedMatch)

        val match = Match(
            happenedOn = finishMatchRequest.happenedAt,
            player1 = plannedMatch.player1,
            player2 = plannedMatch.player2,
            player1Points = finishMatchRequest.player1Points,
            player2Points = finishMatchRequest.player2Points
        )
        matchRepository.save(match)
    }
}