package net.cucumbersome.grottoleague

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class StartupEventListener:  ApplicationListener<ContextRefreshedEvent>{
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        TODO("Not yet implemented")
    }
}