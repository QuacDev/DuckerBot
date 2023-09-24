package command.commands

import command.Command
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.edit
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent

class Ping : Command(
    "ping",
    "Displays Ping",
    {

    }
) {
    override suspend fun handle(e: GuildChatInputCommandInteractionCreateEvent, bot: Kord) {
        val i = e.interaction
        val response = i.deferPublicResponseUnsafe()

        response.edit { content = "My ping is: ${bot.gateway.averagePing}" }
    }
}