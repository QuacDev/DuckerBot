package command.commands

import command.Command
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent

class Ping : Command("ping", arrayListOf()) {
    override suspend fun handle(e: MessageCreateEvent, bot: Kord) {
        e.message.channel.createMessage("My ping is: ${bot.gateway.averagePing}")
    }
}