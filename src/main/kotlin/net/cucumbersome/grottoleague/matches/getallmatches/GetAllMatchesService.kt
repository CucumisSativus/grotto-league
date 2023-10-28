package net.cucumbersome.grottoleague.matches.getallmatches

import net.cucumbersome.grottoleague.matches.Match
import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.dtos.MatchDto
import net.cucumbersome.grottoleague.matches.dtos.PlayerDto
import net.cucumbersome.grottoleague.matches.preparematches.PlannedMatch
import net.cucumbersome.grottoleague.matches.preparematches.PlannedMatchRepository
import org.springframework.stereotype.Service

@Service
class GetAllMatchesService(val plannedMatchRepository: PlannedMatchRepository, val matchRepository: MatchRepository) {
    fun getAllMatches(): List<MatchDto> {
        val plannedMatches = plannedMatchRepository.allNotPlayed().map { plannedMatcheToMatchDto(it) }
        val matches = matchRepository.findAllByOrderByHappenedOnDesc().map { matchToMatchDto(it) }
        return matches + plannedMatches
    }

    private fun plannedMatcheToMatchDto(plannedMatches: PlannedMatch): MatchDto {
        return MatchDto(
            happenedOn = null,
            player1 = PlayerDto.fromPlayer(plannedMatches.player1),
            player1Points = null,
            player2 = PlayerDto.fromPlayer(plannedMatches.player2),
            player2Points = null
        )
    }

    private fun matchToMatchDto(match: Match): MatchDto {
        return MatchDto(
            happenedOn = match.happenedOn,
            player1 = PlayerDto.fromPlayer(match.player1),
            player1Points = match.player1Points,
            player2 = PlayerDto.fromPlayer(match.player2),
            player2Points = match.player2Points
        )
    }
}