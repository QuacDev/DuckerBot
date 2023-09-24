package command

import dev.kord.core.Kord
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.interaction.GlobalChatInputCreateBuilder

abstract class Command(val name: String, val description: String, val builder: GlobalChatInputCreateBuilder.() -> Unit) {
    open suspend fun handle(e: GuildChatInputCommandInteractionCreateEvent, bot: Kord) {}
}