package command

import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent

abstract class Command(val name: String, val aliases: ArrayList<String>) {
    open suspend fun handle(e: MessageCreateEvent, bot: Kord) {}
}