package net.cucumbersome.grottoleague.matches.finishmatch

import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/matches/finish")
class FinishMatchController(val finishMatchService: FinishMatchService) {
    @PutMapping("/")
    fun finishMatch(@RequestBody finishMatchRequest: FinishMatchRequest) {
        log.info("Finishing match: {}", finishMatchRequest)
        finishMatchService.finishMatch(finishMatchRequest)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(e: HttpMessageNotReadableException) {
        log.warn("Returning HTTP 400 Bad Request", e)
        throw e
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(FinishMatchController::class.java)
    }
}