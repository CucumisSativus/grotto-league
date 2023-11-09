package net.cucumbersome.grottoleague.matches.admin

import net.cucumbersome.grottoleague.matches.MatchRepository
import net.cucumbersome.grottoleague.matches.dtos.MatchDto
import net.cucumbersome.grottoleague.matches.finishmatch.FinishMatchService
import net.cucumbersome.grottoleague.matches.getallmatches.GetAllMatchesService
import net.cucumbersome.grottoleague.player.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminService(
    @Autowired private val playerRepository: PlayerRepository,
    @Autowired private val matchRepository: MatchRepository,
    @Autowired private val getAllMatchesService: GetAllMatchesService,
    @Autowired private val finishMatchesService: FinishMatchService
) {
//    fun getAllMatches(): List<MatchDto> {
//        return getAllMatchesService.getAllForAdmin()
//    }



}